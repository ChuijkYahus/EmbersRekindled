package com.rekindled.embers.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rekindled.embers.RegistryManager;
import com.rekindled.embers.datagen.EmbersSounds;
import com.rekindled.embers.particle.SmokeParticleOptions;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.loot.LootModifier;

public class AshenAmuletLootModifier extends LootModifier {

	public static final Codec<AshenAmuletLootModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, AshenAmuletLootModifier::new));

	public AshenAmuletLootModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	public Codec<AshenAmuletLootModifier> codec() {
		return CODEC;
	}

	@Override
	public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		int amount = 0;
		for (ItemStack stack : generatedLoot)
			amount += stack.getCount();

		generatedLoot.clear();

		if (amount > 0) {
			generatedLoot.add(new ItemStack(RegistryManager.ASH.get(), amount));

			Vec3 pos = context.getParam(LootContextParams.ORIGIN);
			context.getLevel().playSound(null, pos.x, pos.y, pos.z, EmbersSounds.ASHEN_AMULET_BURN.get(), SoundSource.PLAYERS, 0.5f, Misc.random.nextFloat()*0.5f + 0.2f);
			context.getLevel().sendParticles(SmokeParticleOptions.SMOKE, pos.x, pos.y, pos.z, 20, 0.25, 0.25, 0.25, 0.1);
		}
		return generatedLoot;
	}
}
