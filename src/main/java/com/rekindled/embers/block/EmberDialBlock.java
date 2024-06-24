package com.rekindled.embers.block;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.rekindled.embers.Embers;
import com.rekindled.embers.RegistryManager;
import com.rekindled.embers.api.capabilities.EmbersCapabilities;
import com.rekindled.embers.api.power.IEmberCapability;
import com.rekindled.embers.blockentity.EmberDialBlockEntity;
import com.rekindled.embers.util.DecimalFormats;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EmberDialBlock extends DialBaseBlock {

	public static final String DIAL_TYPE = "ember";

	public EmberDialBlock(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState pState) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos.relative(state.getValue(FACING), -1));
		if (blockEntity != null) {
			IEmberCapability cap = blockEntity.getCapability(EmbersCapabilities.EMBER_CAPABILITY, state.getValue(FACING).getOpposite()).orElse(blockEntity.getCapability(EmbersCapabilities.EMBER_CAPABILITY, null).orElse(null));
			if (cap != null) {
				if (cap.getEmber() >= cap.getEmberCapacity())
					return 15;
				return (int) (Math.ceil(14.0 * cap.getEmber() / cap.getEmberCapacity()));
			}
		}
		return 0;
	}

	@Override
	protected void getBEData(Direction facing, ArrayList<Component> text, BlockEntity blockEntity, int maxLines) {
		if (blockEntity instanceof EmberDialBlockEntity dial && dial.display) {
			text.add(formatEmber(dial.ember, dial.capacity));
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static MutableComponent formatEmber(double ember, double emberCapacity) {
		DecimalFormat emberFormat = DecimalFormats.getDecimalFormat(Embers.MODID + ".decimal_format.ember");
		return Component.translatable(Embers.MODID + ".tooltip.emberdial.ember", emberFormat.format(ember), emberFormat.format(emberCapacity));
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return RegistryManager.EMBER_DIAL_ENTITY.get().create(pPos, pState);
	}

	@Override
	public String getDialType() {
		return DIAL_TYPE;
	}
}
