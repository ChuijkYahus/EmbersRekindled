package com.rekindled.embers.compat.jei;

import java.text.DecimalFormat;

import com.rekindled.embers.Embers;
import com.rekindled.embers.RegistryManager;
import com.rekindled.embers.recipe.IGaseousFuelRecipe;
import com.rekindled.embers.util.DecimalFormats;
import com.rekindled.embers.util.Misc;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class GaseousFuelCategory implements IRecipeCategory<IGaseousFuelRecipe> {

	private final IDrawable background;
	private final IDrawable icon;
	public static Component title = Component.translatable(Embers.MODID + ".jei.recipe.gaseous_fuel");
	public static ResourceLocation texture = new ResourceLocation(Embers.MODID, "textures/gui/jei_gaseous_fuel.png");

	public GaseousFuelCategory(IGuiHelper helper) {
		background = helper.createDrawable(texture, 0, 0, 126, 31);
		icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(RegistryManager.WILDFIRE_STIRLING_ITEM.get()));
	}

	@Override
	public RecipeType<IGaseousFuelRecipe> getRecipeType() {
		return JEIPlugin.GASEOUS_FUEL;
	}

	@Override
	public Component getTitle() {
		return title;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, IGaseousFuelRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 6, 7)
		.addTooltipCallback(IngotTooltipCallback.INSTANCE)
		.setFluidRenderer(recipe.getDisplayInput().getFluids().get(0).getAmount(), false, 16, 16)
		.addIngredients(ForgeTypes.FLUID_STACK, recipe.getDisplayInput().getFluids());
	}

	@SuppressWarnings("resource")
	@Override
	public void draw(IGaseousFuelRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		DecimalFormat multiplierFormat = DecimalFormats.getDecimalFormat(Embers.MODID + ".decimal_format.ember_multiplier");
		Misc.drawComponents(Minecraft.getInstance().font, guiGraphics, 28, 5, Component.translatable(Embers.MODID + ".jei.recipe.gaseous_fuel.burn_time", recipe.getDisplayBurnTime()));
		Misc.drawComponents(Minecraft.getInstance().font, guiGraphics, 28, 17, Component.translatable(Embers.MODID + ".jei.recipe.gaseous_fuel.power_multiplier", multiplierFormat.format(recipe.getDisplayMultiplier())));
	}
}
