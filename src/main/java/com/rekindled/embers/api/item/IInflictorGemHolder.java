package com.rekindled.embers.api.item;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IInflictorGemHolder {
	int getGemSlots(ItemStack holder);

	boolean canAttachGem(ItemStack holder, ItemStack gem);

	void attachGem(ItemStack holder, ItemStack gem, int slot);

	ItemStack detachGem(ItemStack holder, int slot);

	void clearGems(ItemStack holder);

	default int getAttachedGemCount(ItemStack holder) {
		int amt = 0;
		for (ItemStack stack : getAttachedGems(holder)) {
			if(!stack.isEmpty())
				amt++;
		}
		return amt;
	}

	ItemStack[] getAttachedGems(ItemStack holder);

	float getTotalDamageResistance(LivingEntity entity, DamageSource source, ItemStack holder);
}
