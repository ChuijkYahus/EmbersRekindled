package com.rekindled.embers.block;

import java.util.ArrayList;

import com.rekindled.embers.Embers;
import com.rekindled.embers.datagen.EmbersFluidTags;
import com.rekindled.embers.util.FluidAmounts;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidDialBlock extends DialBaseBlock {

	public static final String DIAL_TYPE = "fluid";

	public FluidDialBlock(Properties pProperties) {
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
			IFluidHandler cap = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, state.getValue(FACING).getOpposite()).orElse(blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, null).orElse(null));
			if (cap != null) {
				int totalCapacity = 0;
				int totalContents = 0;
				for (int i = 0; i < cap.getTanks(); i++) {
					totalCapacity += cap.getTankCapacity(i);
					totalContents += cap.getFluidInTank(i).getAmount();
				}
				if (totalContents >= totalCapacity)
					return 15;
				return (int) (Math.ceil(14.0 * totalContents / totalCapacity));
			}
		}
		return 0;
	}

	@Override
	protected void getBEData(Direction facing, ArrayList<String> text, BlockEntity blockEntity, int maxLines) {
		IFluidHandler cap = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, facing).orElse(blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, null).orElse(null));
		if (cap != null) {
			for (int i = 0; i < cap.getTanks(); i++) {
				FluidStack contents = cap.getFluidInTank(i);
				int capacity = cap.getTankCapacity(i);
				text.add(formatFluidStack(contents, capacity));
				if (contents.getFluid().is(EmbersFluidTags.INGOT_TOOLTIP) && contents.getAmount() >= FluidAmounts.NUGGET_AMOUNT)
					text.add(FluidAmounts.getIngotTooltip(contents.getAmount()));
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static String formatFluidStack(FluidStack contents, int capacity) {
		if (!contents.isEmpty())
			return I18n.get(Embers.MODID + ".tooltip.fluiddial.fluid", contents.getDisplayName().getString(), contents.getAmount(), capacity);
		else
			return I18n.get(Embers.MODID + ".tooltip.fluiddial.nofluid", capacity);
	}

	@Override
	public String getDialType() {
		return DIAL_TYPE;
	}
}
