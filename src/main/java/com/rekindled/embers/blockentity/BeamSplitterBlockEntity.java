package com.rekindled.embers.blockentity;

import java.util.Random;

import com.rekindled.embers.RegistryManager;
import com.rekindled.embers.api.power.IEmberPacketProducer;
import com.rekindled.embers.api.power.IEmberPacketReceiver;
import com.rekindled.embers.api.tile.ISparkable;
import com.rekindled.embers.datagen.EmbersSounds;
import com.rekindled.embers.entity.EmberPacketEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class BeamSplitterBlockEntity extends BlockEntity implements IEmberPacketProducer, IEmberPacketReceiver, ISparkable {

	public BlockPos target1 = null;
	public BlockPos target2 = null;
	public Random random = new Random();
	public boolean polled = false;

	public BeamSplitterBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(RegistryManager.BEAM_SPLITTER_ENTITY.get(), pPos, pBlockState);
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		if (nbt.contains("target1X")){
			target1 = new BlockPos(nbt.getInt("target1X"), nbt.getInt("target1Y"), nbt.getInt("target1Z"));
		}
		if (nbt.contains("target2X")){
			target2 = new BlockPos(nbt.getInt("target2X"), nbt.getInt("target2Y"), nbt.getInt("target2Z"));
		}
	}

	@Override
	public void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		if (target1 != null){
			nbt.putInt("target1X", target1.getX());
			nbt.putInt("target1Y", target1.getY());
			nbt.putInt("target1Z", target1.getZ());
		}
		if (target2 != null){
			nbt.putInt("target2X", target2.getX());
			nbt.putInt("target2Y", target2.getY());
			nbt.putInt("target2Z", target2.getZ());
		}
	}

	@Override
	public boolean hasRoomFor(double ember) {
		if (polled)
			return false;
		polled = true;

		if (hasRoomTarget(target1, ember / 2.0) && hasRoomTarget(target2, ember / 2.0)) {
			polled = false;
			return true;
		}
		if (hasRoomTarget(target1, ember)) {
			polled = false;
			return true;
		}
		if (hasRoomTarget(target2, ember)) {
			polled = false;
			return true;
		}
		polled = false;
		return false;
	}

	public boolean hasRoomTarget(BlockPos target, double ember) {
		if (target != null && level.isLoaded(target) && level.getBlockEntity(target) instanceof IEmberPacketReceiver targetBE) {
			return targetBE.hasRoomFor(ember);
		}
		return false;
	}

	@Override
	public void sparkProgress(BlockEntity tile, double ember) {
		split(ember);
	}

	@Override
	public boolean onReceive(EmberPacketEntity packet) {
		if (packet.pos != getBlockPos()) {
			split(packet.value);
		}
		return true;
	}

	public void split(double ember) {
		if ((target1 != null || target2 != null) && ember > 0.1) {
			Axis axis = level.getBlockState(worldPosition).getValue(BlockStateProperties.AXIS);
			double value = ember / 2.0;
			boolean room1 = hasRoomTarget(target1, value) || target2 == null;
			boolean room2 = hasRoomTarget(target2, value) || target1 == null;
			if (room1 != room2)
				value = ember;

			if (room1 || (!room1 && !room2)) {
				EmberPacketEntity packet1 = RegistryManager.EMBER_PACKET.get().create(level);
				Vec3 velocity1 = EmberEmitterBlockEntity.getBurstVelocity(Direction.get(AxisDirection.POSITIVE, axis));
				packet1.initCustom(worldPosition, target1, velocity1.x, velocity1.y, velocity1.z, value);
				level.addFreshEntity(packet1);
			}
			if (room2 || (!room1 && !room2)) {
				EmberPacketEntity packet2 = RegistryManager.EMBER_PACKET.get().create(level);
				Vec3 velocity2 = EmberEmitterBlockEntity.getBurstVelocity(Direction.get(AxisDirection.NEGATIVE, axis));
				packet2.initCustom(worldPosition, target2, velocity2.x, velocity2.y, velocity2.z, value);
				level.addFreshEntity(packet2);
			}
			level.playSound(null, worldPosition, ember >= 100 ? EmbersSounds.EMBER_EMIT_BIG.get() : EmbersSounds.EMBER_EMIT.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
		}
	}

	@Override
	public void setTargetPosition(BlockPos pos, Direction side) {
		if (pos != worldPosition && side.getAxis() == level.getBlockState(worldPosition).getValue(BlockStateProperties.AXIS)) {
			if (side.getAxisDirection() == AxisDirection.POSITIVE) {
				target1 = pos;
			} else {
				target2 = pos;
			}
			this.setChanged();
		}
	}

	@Override
	public Direction getEmittingDirection(Direction side) {
		if (side.getAxis() == level.getBlockState(worldPosition).getValue(BlockStateProperties.AXIS)) {
			return side;
		}
		return null;
	}
}
