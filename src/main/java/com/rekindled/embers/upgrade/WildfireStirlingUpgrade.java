package com.rekindled.embers.upgrade;

import java.util.HashSet;
import java.util.List;

import com.rekindled.embers.Embers;
import com.rekindled.embers.RegistryManager;
import com.rekindled.embers.api.upgrades.UpgradeContext;
import com.rekindled.embers.blockentity.WildfireStirlingBlockEntity;
import com.rekindled.embers.recipe.FluidHandlerContext;
import com.rekindled.embers.util.Misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

public class WildfireStirlingUpgrade extends DefaultUpgradeProvider {

	private static HashSet<Class<? extends BlockEntity>> blacklist = new HashSet<>();

	public static void registerBlacklistedTile(Class<? extends BlockEntity> tile) {
		blacklist.add(tile);
	}

	public WildfireStirlingUpgrade(BlockEntity tile) {
		super(new ResourceLocation(Embers.MODID, "wildfire_stirling"), tile);
	}

	public static double getMultiplier(double multiplier, int distance, int count) {
		if (distance > 1) {
			multiplier = 1.0 + (multiplier - 1.0) / (distance * 0.5);
		}
		if (count > 2) {
			multiplier = 1.0 + (multiplier - 1.0) / (count * 0.4);
		}
		return multiplier;
	}

	@Override
	public int getLimit(BlockEntity tile) {
		return blacklist.contains(tile.getClass()) ? 0 : super.getLimit(tile);
	}

	@Override
	public double transformEmberConsumption(BlockEntity tile, double ember, int distance, int count) {
		return ember / getMultiplier(getCatalystMultiplier(), distance, count);
	}

	@Override
	public boolean doWork(BlockEntity tile, List<UpgradeContext> upgrades, int distance, int count) {
		if (getCatalystMultiplier() != 1.0 && this.tile instanceof WildfireStirlingBlockEntity) {
			depleteCatalyst(1);
			((WildfireStirlingBlockEntity) this.tile).setActive(20);
		}
		return false; //No cancel
	}

	private double getCatalystMultiplier() {
		if (this.tile instanceof WildfireStirlingBlockEntity stirling) {
			FluidHandlerContext context = new FluidHandlerContext(stirling.tank);
			if (stirling.burnTime <= 0 || stirling.cachedRecipe == null)
				stirling.cachedRecipe = Misc.getRecipe(stirling.cachedRecipe, RegistryManager.GASEOUS_FUEL.get(), context, stirling.getLevel());
			return stirling.cachedRecipe == null ? 1.0 : stirling.cachedRecipe.getPowerMultiplier(context);
		}
		return 1.0;
	}

	private void depleteCatalyst(int amt) {
		if (this.tile instanceof WildfireStirlingBlockEntity stirling) {
			stirling.burnTime -= amt;
			if (stirling.burnTime < 0) {
				FluidHandlerContext context = new FluidHandlerContext(stirling.tank);
				stirling.cachedRecipe = Misc.getRecipe(stirling.cachedRecipe, RegistryManager.GASEOUS_FUEL.get(), context, stirling.getLevel());
				while (stirling.burnTime < 0 && stirling.cachedRecipe != null && stirling.cachedRecipe.matches(context, stirling.getLevel())) {
					stirling.burnTime += stirling.cachedRecipe.process(context, 1);
				}
				if (stirling.burnTime < 0)
					stirling.burnTime = 0;
			}
		}
	}
}
