package com.rekindled.embers.upgrade;

import java.util.List;

import com.rekindled.embers.Embers;
import com.rekindled.embers.RegistryManager;
import com.rekindled.embers.api.event.EmberBoreBladeRenderEvent;
import com.rekindled.embers.api.event.MachineRecipeEvent;
import com.rekindled.embers.api.event.UpgradeEvent;
import com.rekindled.embers.api.upgrades.UpgradeContext;
import com.rekindled.embers.blockentity.EmberBoreBlockEntity;
import com.rekindled.embers.blockentity.ExcavationBucketsBlockEntity;
import com.rekindled.embers.recipe.BoringContext;
import com.rekindled.embers.recipe.IBoringRecipe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

public class ExcavationBucketsUpgrade extends DefaultUpgradeProvider {

	@OnlyIn(Dist.CLIENT)
	public static BakedModel buckets;

	public ExcavationBucketsUpgrade(BlockEntity tile) {
		super(new ResourceLocation(Embers.MODID, "excavation_buckets"), tile);
	}

	@Override
	public int getPriority() {
		return -90; //after the clockwork attenuator
	}

	@Override
	public int getLimit(BlockEntity tile) {
		if (tile instanceof EmberBoreBlockEntity)
			return Integer.MAX_VALUE;
		return 0;
	}

	@Override
	public boolean doTick(BlockEntity tile, List<UpgradeContext> upgrades, int distance, int count) {
		((ExcavationBucketsBlockEntity) this.tile).lastAngle = ((ExcavationBucketsBlockEntity) this.tile).angle;
		if (tile instanceof EmberBoreBlockEntity bore && bore.isRunning)
			((ExcavationBucketsBlockEntity) this.tile).angle += 14.0f * bore.speedMod;
		return false;
	}

	@Override
	public void throwEvent(BlockEntity tile, List<UpgradeContext> upgrades, UpgradeEvent event, int distance, int count) {
		if (tile instanceof EmberBoreBlockEntity bore && event instanceof MachineRecipeEvent<?> recipeEvent && recipeEvent.getRecipe() instanceof List<?>) {
			@SuppressWarnings("unchecked")
			List<IBoringRecipe> recipes = (List<IBoringRecipe>) recipeEvent.getRecipe();

			ResourceKey<Biome> biome = tile.getLevel().getBiome(tile.getBlockPos()).unwrapKey().get();
			BoringContext context = new BoringContext(tile.getLevel().dimension().location(), biome.location(), tile.getBlockPos().getY(), tile.getLevel().getBlockStatesIfLoaded(bore.getBladeBoundingBox()).toArray(i -> new BlockState[i]));
			List<IBoringRecipe> newRecipes = tile.getLevel().getRecipeManager().getRecipesFor(RegistryManager.EXCAVATION.get(), context, tile.getLevel());

			recipes.addAll(newRecipes);
		}
		if (tile.getLevel().isClientSide()) {
			CLientStuff.throwEvent(tile, upgrades, event, distance, count);
		}
	}

	public static class CLientStuff {

		public static void throwEvent(BlockEntity tile, List<UpgradeContext> upgrades, UpgradeEvent event, int distance, int count) {
			if (buckets != null && event instanceof EmberBoreBladeRenderEvent renderEvent) {
				Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(renderEvent.getPose().last(), renderEvent.getBuffer().getBuffer(Sheets.solidBlockSheet()), renderEvent.getBlockState(), buckets, 0.0f, 0.0f, 0.0f, renderEvent.getLight(), renderEvent.getOverlay(), ModelData.EMPTY, Sheets.solidBlockSheet());
			}
		}
	}
}
