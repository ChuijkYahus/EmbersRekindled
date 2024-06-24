package com.rekindled.embers.blockentity;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.rekindled.embers.ConfigManager;
import com.rekindled.embers.RegistryManager;
import com.rekindled.embers.api.capabilities.EmbersCapabilities;
import com.rekindled.embers.api.event.DialInformationEvent;
import com.rekindled.embers.api.event.EmberEvent;
import com.rekindled.embers.api.event.MachineRecipeEvent;
import com.rekindled.embers.api.power.IEmberCapability;
import com.rekindled.embers.api.tile.IExtraDialInformation;
import com.rekindled.embers.api.upgrades.UpgradeContext;
import com.rekindled.embers.api.upgrades.UpgradeUtil;
import com.rekindled.embers.datagen.EmbersSounds;
import com.rekindled.embers.particle.GlowParticleOptions;
import com.rekindled.embers.particle.SmokeParticleOptions;
import com.rekindled.embers.particle.SparkParticleOptions;
import com.rekindled.embers.power.DefaultEmberCapability;
import com.rekindled.embers.recipe.IMeltingRecipe;
import com.rekindled.embers.util.Misc;
import com.rekindled.embers.util.sound.ISoundController;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class MelterBottomBlockEntity extends BlockEntity implements ISoundController, IExtraDialInformation {

	public IEmberCapability capability = new DefaultEmberCapability() {
		@Override
		public void onContentsChanged() {
			super.onContentsChanged();
			MelterBottomBlockEntity.this.setChanged();
		}
	};
	static Random random = new Random();
	int progress = -1;

	public static final int SOUND_PROCESS = 1;
	public static final int[] SOUND_IDS = new int[]{SOUND_PROCESS};

	HashSet<Integer> soundsPlaying = new HashSet<>();
	public boolean isWorking;
	public List<UpgradeContext> upgrades;
	public IMeltingRecipe cachedRecipe = null;

	public MelterBottomBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(RegistryManager.MELTER_BOTTOM_ENTITY.get(), pPos, pBlockState);
		capability.setEmberCapacity(8000);
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		capability.deserializeNBT(nbt);
		if (nbt.contains("progress"))
			progress = nbt.getInt("progress");
		isWorking = nbt.getBoolean("working");
	}

	@Override
	public void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		capability.writeToNBT(nbt);
		nbt.putInt("progress", progress);
		nbt.putBoolean("working", isWorking);
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag nbt = super.getUpdateTag();
		nbt.putBoolean("working", isWorking);
		return nbt;
	}

	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (!this.remove && cap == EmbersCapabilities.EMBER_CAPABILITY) {
			return capability.getCapability(cap, side);
		}
		return super.getCapability(cap, side);
	}

	public static void clientTick(Level level, BlockPos pos, BlockState state, MelterBottomBlockEntity blockEntity) {
		blockEntity.upgrades = UpgradeUtil.getUpgrades(level, pos, Misc.horizontals);
		UpgradeUtil.verifyUpgrades(blockEntity, blockEntity.upgrades);
		blockEntity.handleSound();
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, MelterBottomBlockEntity blockEntity) {
		MelterTopBlockEntity top = (MelterTopBlockEntity) level.getBlockEntity(pos.above());
		blockEntity.upgrades = UpgradeUtil.getUpgrades(level, pos, Misc.horizontals);
		UpgradeUtil.verifyUpgrades(blockEntity, blockEntity.upgrades);
		if (UpgradeUtil.doTick(blockEntity, blockEntity.upgrades))
			return;
		if(top != null && !top.inventory.getStackInSlot(0).isEmpty()) {
			double emberCost = UpgradeUtil.getTotalEmberConsumption(blockEntity, ConfigManager.MELTER_EMBER_COST.get(), blockEntity.upgrades);
			if (blockEntity.capability.getEmber() >= emberCost) {
				boolean cancel = UpgradeUtil.doWork(blockEntity, blockEntity.upgrades);
				if(!cancel) {
					UpgradeUtil.throwEvent(blockEntity, new EmberEvent(blockEntity, EmberEvent.EnumType.CONSUME, emberCost), blockEntity.upgrades);
					blockEntity.capability.removeAmount(emberCost, true);

					if (level instanceof ServerLevel serverLevel) {
						if (random.nextInt(20) == 0) {
							serverLevel.sendParticles(new SparkParticleOptions(GlowParticleOptions.EMBER_COLOR, random.nextFloat() + 0.45f), pos.getX() + 0.5f, pos.getY() + 1.85f, pos.getZ() + 0.5f, 1, 0.125, 0.0, 0.125, 1.0);
						}
						if (random.nextInt(10) == 0) {
							serverLevel.sendParticles(new SmokeParticleOptions(SmokeParticleOptions.SMOKE_COLOR, 4.0f), pos.getX() + 0.5f, pos.getY() + 1.5f, pos.getZ() + 0.5f, 12, 0.125, 0.125, 0.125, 1.0);
						}
					}

					blockEntity.isWorking = true;
					blockEntity.progress++;
					if (blockEntity.progress >= UpgradeUtil.getWorkTime(blockEntity, ConfigManager.MELTER_PROCESS_TIME.get(), blockEntity.upgrades)) {
						RecipeWrapper wrapper = new RecipeWrapper(top.inventory);
						blockEntity.cachedRecipe = Misc.getRecipe(blockEntity.cachedRecipe, RegistryManager.MELTING.get(), wrapper, level);
						if (blockEntity.cachedRecipe != null) {
							FluidStack output = blockEntity.cachedRecipe.getOutput(wrapper);
							FluidTank tank = top.getTank();
							output = UpgradeUtil.transformOutput(blockEntity, output, blockEntity.upgrades);
							if (output != null && tank.fill(output, FluidAction.SIMULATE) >= output.getAmount()) {
								tank.fill(output, FluidAction.EXECUTE);
								//the recipe is responsible for taking items from the inventory
								blockEntity.cachedRecipe.process(wrapper);
								top.setChanged();
								blockEntity.progress = 0;
								UpgradeUtil.throwEvent(blockEntity, new MachineRecipeEvent.Success<>(blockEntity, blockEntity.cachedRecipe), blockEntity.upgrades);
							}
						}
					}
				} else {
					blockEntity.isWorking = false;
					if (blockEntity.progress > 0) {
						blockEntity.progress = 0;
					}
				}
			} else {
				blockEntity.isWorking = false;
				if (blockEntity.progress > 0) {
					blockEntity.progress = 0;
				}
			}
		} else {
			blockEntity.isWorking = false;
			if (blockEntity.progress > 0) {
				blockEntity.progress = 0;
			}
		}
		blockEntity.setChanged();
	}

	@Override
	public void playSound(int id) {
		switch (id) {
		case SOUND_PROCESS:
			EmbersSounds.playMachineSound(this, SOUND_PROCESS, EmbersSounds.MELTER_LOOP.get(), SoundSource.BLOCKS, true, 1.0f, 1.0f, (float)worldPosition.getX()+0.5f,(float)worldPosition.getY()+1.0f,(float)worldPosition.getZ()+0.5f);
			break;
		}
		soundsPlaying.add(id);
	}

	@Override
	public void stopSound(int id) {
		soundsPlaying.remove(id);
	}

	@Override
	public boolean isSoundPlaying(int id) {
		return soundsPlaying.contains(id);
	}

	@Override
	public int[] getSoundIDs() {
		return SOUND_IDS;
	}

	@Override
	public boolean shouldPlaySound(int id) {
		return id == SOUND_PROCESS && isWorking;
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		capability.invalidate();
	}

	@Override
	public void addDialInformation(Direction facing, List<Component> information, String dialType) {
		UpgradeUtil.throwEvent(this, new DialInformationEvent(this, information, dialType), upgrades);
	}

	@Override
	public void setChanged() {
		super.setChanged();
		if (!level.isClientSide())
			((ServerLevel) level).getChunkSource().blockChanged(worldPosition);
	}
}
