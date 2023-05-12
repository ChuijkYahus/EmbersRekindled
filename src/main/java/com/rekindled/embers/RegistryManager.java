package com.rekindled.embers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.mojang.serialization.Codec;
import com.rekindled.embers.block.ArchaicLightBlock;
import com.rekindled.embers.block.BeamSplitterBlock;
import com.rekindled.embers.block.BinBlock;
import com.rekindled.embers.block.CaminiteRingBlock;
import com.rekindled.embers.block.CaminiteRingEdgeBlock;
import com.rekindled.embers.block.CaminiteValveBlock;
import com.rekindled.embers.block.CaminiteValveEdgeBlock;
import com.rekindled.embers.block.ClockworkAttenuatorBlock;
import com.rekindled.embers.block.CopperCellBlock;
import com.rekindled.embers.block.CopperChargerBlock;
import com.rekindled.embers.block.CreativeEmberBlock;
import com.rekindled.embers.block.CrystalCellBlock;
import com.rekindled.embers.block.CrystalCellEdgeBlock;
import com.rekindled.embers.block.EmberActivatorBlock;
import com.rekindled.embers.block.EmberBoreBlock;
import com.rekindled.embers.block.EmberBoreEdgeBlock;
import com.rekindled.embers.block.EmberDialBlock;
import com.rekindled.embers.block.EmberEjectorBlock;
import com.rekindled.embers.block.EmberEmitterBlock;
import com.rekindled.embers.block.EmberFunnelBlock;
import com.rekindled.embers.block.EmberLanternBlock;
import com.rekindled.embers.block.EmberReceiverBlock;
import com.rekindled.embers.block.EmberRelayBlock;
import com.rekindled.embers.block.EmberSiphonBlock;
import com.rekindled.embers.block.FluidDialBlock;
import com.rekindled.embers.block.FluidExtractorBlock;
import com.rekindled.embers.block.FluidPipeBlock;
import com.rekindled.embers.block.FluidVesselBlock;
import com.rekindled.embers.block.GeologicSeparatorBlock;
import com.rekindled.embers.block.HearthCoilBlock;
import com.rekindled.embers.block.HearthCoilEdgeBlock;
import com.rekindled.embers.block.ItemDialBlock;
import com.rekindled.embers.block.ItemDropperBlock;
import com.rekindled.embers.block.ItemExtractorBlock;
import com.rekindled.embers.block.ItemPipeBlock;
import com.rekindled.embers.block.ItemVacuumBlock;
import com.rekindled.embers.block.MechanicalCoreBlock;
import com.rekindled.embers.block.MelterBlock;
import com.rekindled.embers.block.MirrorRelayBlock;
import com.rekindled.embers.block.MixerCentrifugeBlock;
import com.rekindled.embers.block.PressureRefineryBlock;
import com.rekindled.embers.block.ReservoirBlock;
import com.rekindled.embers.block.ReservoirEdgeBlock;
import com.rekindled.embers.block.StampBaseBlock;
import com.rekindled.embers.block.StamperBlock;
import com.rekindled.embers.block.WaterloggableLeverBlock;
import com.rekindled.embers.blockentity.BeamSplitterBlockEntity;
import com.rekindled.embers.blockentity.BinBlockEntity;
import com.rekindled.embers.blockentity.CaminiteValveBlockEntity;
import com.rekindled.embers.blockentity.ClockworkAttenuatorBlockEntity;
import com.rekindled.embers.blockentity.CopperCellBlockEntity;
import com.rekindled.embers.blockentity.CopperChargerBlockEntity;
import com.rekindled.embers.blockentity.CreativeEmberBlockEntity;
import com.rekindled.embers.blockentity.CrystalCellBlockEntity;
import com.rekindled.embers.blockentity.EmberActivatorBottomBlockEntity;
import com.rekindled.embers.blockentity.EmberActivatorTopBlockEntity;
import com.rekindled.embers.blockentity.EmberBoreBlockEntity;
import com.rekindled.embers.blockentity.EmberEjectorBlockEntity;
import com.rekindled.embers.blockentity.EmberEmitterBlockEntity;
import com.rekindled.embers.blockentity.EmberFunnelBlockEntity;
import com.rekindled.embers.blockentity.EmberReceiverBlockEntity;
import com.rekindled.embers.blockentity.EmberRelayBlockEntity;
import com.rekindled.embers.blockentity.EmberSiphonBlockEntity;
import com.rekindled.embers.blockentity.FluidExtractorBlockEntity;
import com.rekindled.embers.blockentity.FluidPipeBlockEntity;
import com.rekindled.embers.blockentity.FluidVesselBlockEntity;
import com.rekindled.embers.blockentity.GeologicSeparatorBlockEntity;
import com.rekindled.embers.blockentity.HearthCoilBlockEntity;
import com.rekindled.embers.blockentity.ItemDropperBlockEntity;
import com.rekindled.embers.blockentity.ItemExtractorBlockEntity;
import com.rekindled.embers.blockentity.ItemPipeBlockEntity;
import com.rekindled.embers.blockentity.ItemVacuumBlockEntity;
import com.rekindled.embers.blockentity.MechanicalCoreBlockEntity;
import com.rekindled.embers.blockentity.MelterBottomBlockEntity;
import com.rekindled.embers.blockentity.MelterTopBlockEntity;
import com.rekindled.embers.blockentity.MirrorRelayBlockEntity;
import com.rekindled.embers.blockentity.MixerCentrifugeBottomBlockEntity;
import com.rekindled.embers.blockentity.MixerCentrifugeTopBlockEntity;
import com.rekindled.embers.blockentity.PressureRefineryBottomBlockEntity;
import com.rekindled.embers.blockentity.PressureRefineryTopBlockEntity;
import com.rekindled.embers.blockentity.ReservoirBlockEntity;
import com.rekindled.embers.blockentity.StampBaseBlockEntity;
import com.rekindled.embers.blockentity.StamperBlockEntity;
import com.rekindled.embers.datagen.EmbersSounds;
import com.rekindled.embers.entity.AncientGolemEntity;
import com.rekindled.embers.entity.EmberPacketEntity;
import com.rekindled.embers.entity.EmberProjectileEntity;
import com.rekindled.embers.fluidtypes.EmbersFluidType.FluidInfo;
import com.rekindled.embers.fluidtypes.MoltenMetalFluidType;
import com.rekindled.embers.item.AncientCodexItem;
import com.rekindled.embers.item.BlazingRayItem;
import com.rekindled.embers.item.CinderStaffItem;
import com.rekindled.embers.item.ClockworkAxeItem;
import com.rekindled.embers.item.ClockworkHammerItem;
import com.rekindled.embers.item.ClockworkPickaxeItem;
import com.rekindled.embers.item.CopperCellBlockItem;
import com.rekindled.embers.item.EmberCartridgeItem;
import com.rekindled.embers.item.EmberJarItem;
import com.rekindled.embers.item.FluidVesselBlockItem;
import com.rekindled.embers.item.FuelItem;
import com.rekindled.embers.item.TinkerHammerItem;
import com.rekindled.embers.item.TinkerLensItem;
import com.rekindled.embers.particle.GlowParticleOptions;
import com.rekindled.embers.particle.SmokeParticleOptions;
import com.rekindled.embers.particle.SparkParticleOptions;
import com.rekindled.embers.particle.StarParticleOptions;
import com.rekindled.embers.particle.VaporParticleOptions;
import com.rekindled.embers.recipe.BoringRecipe;
import com.rekindled.embers.recipe.EmberActivationRecipe;
import com.rekindled.embers.recipe.MeltingRecipe;
import com.rekindled.embers.recipe.MetalCoefficientRecipe;
import com.rekindled.embers.recipe.MixingRecipe;
import com.rekindled.embers.recipe.StampingRecipe;
import com.rekindled.embers.recipe.TagStampingRecipe;
import com.rekindled.embers.util.GrandhammerLootModifier;
import com.rekindled.embers.util.Misc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryManager {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Embers.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Embers.MODID);
	public static final DeferredRegister<FluidType> FLUIDTYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Embers.MODID);
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Embers.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Embers.MODID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Embers.MODID);
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Embers.MODID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Embers.MODID);
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Embers.MODID);
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Embers.MODID);
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Embers.MODID);

	public static List<FluidStuff> fluidList = new ArrayList<FluidStuff>();

	public static FluidStuff addFluid(String localizedName, FluidInfo info, Material material, BiFunction<FluidType.Properties, FluidInfo, FluidType> type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Source> source, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Flowing> flowing, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, FluidType.Properties prop) {
		FluidStuff fluid = new FluidStuff(info.name, localizedName, info.color, type.apply(prop, info), block, fluidProperties, source, flowing, material);
		fluidList.add(fluid);
		return fluid;
	}

	public static FluidStuff addFluid(String localizedName, FluidInfo info, Material material, BiFunction<FluidType.Properties, FluidInfo, FluidType> type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, FluidType.Properties prop) {
		return addFluid(localizedName, info, material, type, block, ForgeFlowingFluid.Source::new, ForgeFlowingFluid.Flowing::new, fluidProperties, prop);
	}

	public static FluidType.Properties moltenMetalProps() {
		return FluidType.Properties.create()
				.canSwim(false)
				.canDrown(false)
				.pathType(BlockPathTypes.LAVA)
				.adjacentPathType(null)
				.motionScale(0.0023333333333333335D)
				.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
				.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
				.lightLevel(12)
				.density(3000)
				.viscosity(6000)
				.temperature(1100);
	}

	public static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) {
		return ENTITY_TYPES.register(name, () -> builder.build(Embers.MODID + ":" + name));
	}

	public static <T extends ParticleOptions> RegistryObject<ParticleType<T>> registerParticle(String name, boolean overrideLimiter, ParticleOptions.Deserializer<T> deserializer, Codec<T> codec) {
		return PARTICLE_TYPES.register(name, () -> new ParticleType<T>(overrideLimiter, deserializer) {
			public Codec<T> codec() {
				return codec;
			}
		});
	}

	public static <T extends Recipe<?>> RegistryObject<RecipeType<T>> registerRecipeType(final String identifier) {
		return RECIPE_TYPES.register(identifier, () -> new RecipeType<T>() {
			public String toString() {
				return Embers.MODID + ":" + identifier;
			}
		});
	}

	//blocks
	public static final RegistryObject<Block> LEAD_ORE = BLOCKS.register("lead_ore", () -> new DropExperienceBlock(Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(3.0f)));
	public static final RegistryObject<Block> DEEPSLATE_LEAD_ORE = BLOCKS.register("deepslate_lead_ore", () -> new DropExperienceBlock(Properties.copy(LEAD_ORE.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));
	public static final RegistryObject<Block> RAW_LEAD_BLOCK = BLOCKS.register("raw_lead_block", () -> new Block(Properties.of(Material.METAL, MaterialColor.TERRACOTTA_PURPLE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(3.0F, 6.0F)));
	public static final RegistryObject<Block> LEAD_BLOCK = BLOCKS.register("lead_block", () -> new Block(Properties.of(Material.METAL, MaterialColor.TERRACOTTA_PURPLE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));

	public static final RegistryObject<Block> SILVER_ORE = BLOCKS.register("silver_ore", () -> new DropExperienceBlock(Properties.of(Material.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(3.0f)));
	public static final RegistryObject<Block> DEEPSLATE_SILVER_ORE = BLOCKS.register("deepslate_silver_ore", () -> new DropExperienceBlock(Properties.copy(SILVER_ORE.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));
	public static final RegistryObject<Block> RAW_SILVER_BLOCK = BLOCKS.register("raw_silver_block", () -> new Block(Properties.of(Material.METAL, MaterialColor.ICE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(3.0F, 6.0F)));
	public static final RegistryObject<Block> SILVER_BLOCK = BLOCKS.register("silver_block", () -> new Block(Properties.of(Material.METAL, MaterialColor.ICE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));

	public static final RegistryObject<Block> DAWNSTONE_BLOCK = BLOCKS.register("dawnstone_block", () -> new Block(Properties.of(Material.METAL, MaterialColor.TERRACOTTA_YELLOW).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));

	public static final RegistryObject<Block> CAMINITE_BRICKS = BLOCKS.register("caminite_bricks", () -> new Block(Properties.of(Material.STONE, MaterialColor.WOOD).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.6f)));
	public static final StoneDecoBlocks CAMINITE_BRICKS_DECO = new StoneDecoBlocks("caminite_bricks", CAMINITE_BRICKS, Properties.of(Material.STONE, MaterialColor.WOOD).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.6f));
	public static final RegistryObject<Block> ARCHAIC_BRICKS = BLOCKS.register("archaic_bricks", () -> new Block(Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.6f)));
	public static final StoneDecoBlocks ARCHAIC_BRICKS_DECO = new StoneDecoBlocks("archaic_bricks", ARCHAIC_BRICKS, Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.6f));
	public static final RegistryObject<Block> ARCHAIC_EDGE = BLOCKS.register("archaic_edge", () -> new Block(Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> ARCHAIC_TILE = BLOCKS.register("archaic_tile", () -> new Block(Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.6f)));
	public static final StoneDecoBlocks ARCHAIC_TILE_DECO = new StoneDecoBlocks("archaic_tile", ARCHAIC_TILE, Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.6f), true, true, false);
	public static final RegistryObject<Block> ARCHAIC_LIGHT = BLOCKS.register("archaic_light", () -> new ArchaicLightBlock(Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(1.6f).lightLevel(state -> 15)));
	public static final RegistryObject<Block> EMBER_LANTERN = BLOCKS.register("ember_lantern", () -> new EmberLanternBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).lightLevel(state -> 15)));

	public static final RegistryObject<Block> COPPER_CELL = BLOCKS.register("copper_cell", () -> new CopperCellBlock(Properties.of(Material.METAL, MaterialColor.TERRACOTTA_ORANGE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.4f).noOcclusion()));
	public static final RegistryObject<Block> CREATIVE_EMBER = BLOCKS.register("creative_ember_source", () -> new CreativeEmberBlock(Properties.of(Material.METAL, MaterialColor.TERRACOTTA_YELLOW).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> EMBER_DIAL = BLOCKS.register("ember_dial", () -> new EmberDialBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> ITEM_DIAL = BLOCKS.register("item_dial", () -> new ItemDialBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> FLUID_DIAL = BLOCKS.register("fluid_dial", () -> new FluidDialBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_EMITTER = BLOCKS.register("ember_emitter", () -> new EmberEmitterBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(0.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_RECEIVER = BLOCKS.register("ember_receiver", () -> new EmberReceiverBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(0.6f).noOcclusion()));
	public static final RegistryObject<Block> CAMINITE_LEVER = BLOCKS.register("caminite_lever", () -> new WaterloggableLeverBlock(Properties.of(Material.STONE, MaterialColor.NONE).noCollission().sound(SoundType.STONE).strength(0.75f)));
	public static final RegistryObject<Block> ITEM_PIPE = BLOCKS.register("item_pipe", () -> new ItemPipeBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> ITEM_EXTRACTOR = BLOCKS.register("item_extractor", () -> new ItemExtractorBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_BORE = BLOCKS.register("ember_bore", () -> new EmberBoreBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_BORE_EDGE = BLOCKS.register("ember_bore_edge", () -> new EmberBoreEdgeBlock(Properties.of(Material.HEAVY_METAL, MaterialColor.WOOD).sound(EmbersSounds.MULTIBLOCK_EXTRA).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> MECHANICAL_CORE = BLOCKS.register("mechanical_core", () -> new MechanicalCoreBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_ACTIVATOR = BLOCKS.register("ember_activator", () -> new EmberActivatorBlock(Properties.of(Material.METAL, MaterialColor.COLOR_ORANGE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> MELTER = BLOCKS.register("melter", () -> new MelterBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> FLUID_PIPE = BLOCKS.register("fluid_pipe", () -> new FluidPipeBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> FLUID_EXTRACTOR = BLOCKS.register("fluid_extractor", () -> new FluidExtractorBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> FLUID_VESSEL = BLOCKS.register("fluid_vessel", () -> new FluidVesselBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> STAMPER = BLOCKS.register("stamper", () -> new StamperBlock(Properties.of(Material.METAL, MaterialColor.WOOD).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> STAMP_BASE = BLOCKS.register("stamp_base", () -> new StampBaseBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> BIN = BLOCKS.register("bin", () -> new BinBlock(Properties.of(Material.METAL, MaterialColor.TERRACOTTA_PURPLE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> MIXER_CENTRIFUGE = BLOCKS.register("mixer_centrifuge", () -> new MixerCentrifugeBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> ITEM_DROPPER = BLOCKS.register("item_dropper", () -> new ItemDropperBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> PRESSURE_REFINERY = BLOCKS.register("pressure_refinery", () -> new PressureRefineryBlock(Properties.of(Material.METAL, MaterialColor.COLOR_ORANGE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_EJECTOR = BLOCKS.register("ember_ejector", () -> new EmberEjectorBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(0.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_FUNNEL = BLOCKS.register("ember_funnel", () -> new EmberFunnelBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_RELAY = BLOCKS.register("ember_relay", () -> new EmberRelayBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(0.6f).noOcclusion()));
	public static final RegistryObject<Block> MIRROR_RELAY = BLOCKS.register("mirror_relay", () -> new MirrorRelayBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(0.6f).noOcclusion()));
	public static final RegistryObject<Block> BEAM_SPLITTER = BLOCKS.register("beam_splitter", () -> new BeamSplitterBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(0.6f).noOcclusion()));
	public static final RegistryObject<Block> ITEM_VACUUM = BLOCKS.register("item_vacuum", () -> new ItemVacuumBlock(Properties.of(Material.METAL, MaterialColor.TERRACOTTA_PURPLE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> HEARTH_COIL = BLOCKS.register("hearth_coil", () -> new HearthCoilBlock(Properties.of(Material.METAL, MaterialColor.COLOR_ORANGE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> HEARTH_COIL_EDGE = BLOCKS.register("hearth_coil_edge", () -> new HearthCoilEdgeBlock(Properties.of(Material.HEAVY_METAL, MaterialColor.WOOD).sound(EmbersSounds.MULTIBLOCK_EXTRA).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> RESERVOIR = BLOCKS.register("reservoir", () -> new ReservoirBlock(Properties.of(Material.METAL, MaterialColor.WOOD).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> RESERVOIR_EDGE = BLOCKS.register("reservoir_edge", () -> new ReservoirEdgeBlock(Properties.of(Material.HEAVY_METAL, MaterialColor.WOOD).sound(EmbersSounds.MULTIBLOCK_EXTRA).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> CAMINITE_RING = BLOCKS.register("caminite_ring", () -> new CaminiteRingBlock(Properties.of(Material.HEAVY_METAL, MaterialColor.NONE).noCollission().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> CAMINITE_RING_EDGE = BLOCKS.register("caminite_ring_edge", () -> new CaminiteRingEdgeBlock(Properties.of(Material.HEAVY_METAL, MaterialColor.WOOD).sound(EmbersSounds.MULTIBLOCK_EXTRA).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> CAMINITE_VALVE = BLOCKS.register("caminite_valve", () -> new CaminiteValveBlock(Properties.of(Material.HEAVY_METAL, MaterialColor.NONE).noCollission().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> CAMINITE_VALVE_EDGE = BLOCKS.register("caminite_valve_edge", () -> new CaminiteValveEdgeBlock(Properties.of(Material.HEAVY_METAL, MaterialColor.WOOD).sound(EmbersSounds.MULTIBLOCK_EXTRA).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> CRYSTAL_CELL = BLOCKS.register("crystal_cell", () -> new CrystalCellBlock(Properties.of(Material.METAL, MaterialColor.WOOD).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> CRYSTAL_CELL_EDGE = BLOCKS.register("crystal_cell_edge", () -> new CrystalCellEdgeBlock(Properties.of(Material.HEAVY_METAL, MaterialColor.WOOD).sound(EmbersSounds.MULTIBLOCK_EXTRA).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> CLOCKWORK_ATTENUATOR = BLOCKS.register("clockwork_attenuator", () -> new ClockworkAttenuatorBlock(Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> GEOLOGIC_SEPARATOR = BLOCKS.register("geologic_separator", () -> new GeologicSeparatorBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> COPPER_CHARGER = BLOCKS.register("copper_charger", () -> new CopperChargerBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_SIPHON = BLOCKS.register("ember_siphon", () -> new EmberSiphonBlock(Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));

	//itemblocks
	public static final RegistryObject<Item> LEAD_ORE_ITEM = ITEMS.register("lead_ore", () -> new BlockItem(LEAD_ORE.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> DEEPSLATE_LEAD_ORE_ITEM = ITEMS.register("deepslate_lead_ore", () -> new BlockItem(DEEPSLATE_LEAD_ORE.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> RAW_LEAD_BLOCK_ITEM = ITEMS.register("raw_lead_block", () -> new BlockItem(RAW_LEAD_BLOCK.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> LEAD_BLOCK_ITEM = ITEMS.register("lead_block", () -> new BlockItem(LEAD_BLOCK.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));

	public static final RegistryObject<Item> SILVER_ORE_ITEM = ITEMS.register("silver_ore", () -> new BlockItem(SILVER_ORE.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> DEEPSLATE_SILVER_ORE_ITEM = ITEMS.register("deepslate_silver_ore", () -> new BlockItem(DEEPSLATE_SILVER_ORE.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> RAW_SILVER_BLOCK_ITEM = ITEMS.register("raw_silver_block", () -> new BlockItem(RAW_SILVER_BLOCK.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> SILVER_BLOCK_ITEM = ITEMS.register("silver_block", () -> new BlockItem(SILVER_BLOCK.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));

	public static final RegistryObject<Item> DAWNSTONE_BLOCK_ITEM = ITEMS.register("dawnstone_block", () -> new BlockItem(DAWNSTONE_BLOCK.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));

	public static final RegistryObject<Item> CAMINITE_BRICKS_ITEM = ITEMS.register("caminite_bricks", () -> new BlockItem(CAMINITE_BRICKS.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> ARCHAIC_BRICKS_ITEM = ITEMS.register("archaic_bricks", () -> new BlockItem(ARCHAIC_BRICKS.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> ARCHAIC_EDGE_ITEM = ITEMS.register("archaic_edge", () -> new BlockItem(ARCHAIC_EDGE.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> ARCHAIC_TILE_ITEM = ITEMS.register("archaic_tile", () -> new BlockItem(ARCHAIC_TILE.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> ARCHAIC_LIGHT_ITEM = ITEMS.register("archaic_light", () -> new BlockItem(ARCHAIC_LIGHT.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> EMBER_LANTERN_ITEM = ITEMS.register("ember_lantern", () -> new BlockItem(EMBER_LANTERN.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));

	public static final RegistryObject<Item> COPPER_CELL_ITEM = ITEMS.register("copper_cell", () -> new CopperCellBlockItem(COPPER_CELL.get(), new Item.Properties().stacksTo(1).tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> CREATIVE_EMBER_ITEM = ITEMS.register("creative_ember_source", () -> new BlockItem(CREATIVE_EMBER.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> EMBER_DIAL_ITEM = ITEMS.register("ember_dial", () -> new BlockItem(EMBER_DIAL.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> ITEM_DIAL_ITEM = ITEMS.register("item_dial", () -> new BlockItem(ITEM_DIAL.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> FLUID_DIAL_ITEM = ITEMS.register("fluid_dial", () -> new BlockItem(FLUID_DIAL.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> EMBER_EMITTER_ITEM = ITEMS.register("ember_emitter", () -> new BlockItem(EMBER_EMITTER.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> EMBER_RECEIVER_ITEM = ITEMS.register("ember_receiver", () -> new BlockItem(EMBER_RECEIVER.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> CAMINITE_LEVER_ITEM = ITEMS.register("caminite_lever", () -> new BlockItem(CAMINITE_LEVER.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> ITEM_PIPE_ITEM = ITEMS.register("item_pipe", () -> new BlockItem(ITEM_PIPE.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> ITEM_EXTRACTOR_ITEM = ITEMS.register("item_extractor", () -> new BlockItem(ITEM_EXTRACTOR.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> EMBER_BORE_ITEM = ITEMS.register("ember_bore", () -> new BlockItem(EMBER_BORE.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> MECHANICAL_CORE_ITEM = ITEMS.register("mechanical_core", () -> new BlockItem(MECHANICAL_CORE.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> EMBER_ACTIVATOR_ITEM = ITEMS.register("ember_activator", () -> new BlockItem(EMBER_ACTIVATOR.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> MELTER_ITEM = ITEMS.register("melter", () -> new BlockItem(MELTER.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> FLUID_PIPE_ITEM = ITEMS.register("fluid_pipe", () -> new BlockItem(FLUID_PIPE.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> FLUID_EXTRACTOR_ITEM = ITEMS.register("fluid_extractor", () -> new BlockItem(FLUID_EXTRACTOR.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> FLUID_VESSEL_ITEM = ITEMS.register("fluid_vessel", () -> new FluidVesselBlockItem(FLUID_VESSEL.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> STAMPER_ITEM = ITEMS.register("stamper", () -> new BlockItem(STAMPER.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> STAMP_BASE_ITEM = ITEMS.register("stamp_base", () -> new BlockItem(STAMP_BASE.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> BIN_ITEM = ITEMS.register("bin", () -> new BlockItem(BIN.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> MIXER_CENTRIFUGE_ITEM = ITEMS.register("mixer_centrifuge", () -> new BlockItem(MIXER_CENTRIFUGE.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> ITEM_DROPPER_ITEM = ITEMS.register("item_dropper", () -> new BlockItem(ITEM_DROPPER.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> PRESSURE_REFINERY_ITEM = ITEMS.register("pressure_refinery", () -> new BlockItem(PRESSURE_REFINERY.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> EMBER_EJECTOR_ITEM = ITEMS.register("ember_ejector", () -> new BlockItem(EMBER_EJECTOR.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> EMBER_FUNNEL_ITEM = ITEMS.register("ember_funnel", () -> new BlockItem(EMBER_FUNNEL.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> EMBER_RELAY_ITEM = ITEMS.register("ember_relay", () -> new BlockItem(EMBER_RELAY.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> MIRROR_RELAY_ITEM = ITEMS.register("mirror_relay", () -> new BlockItem(MIRROR_RELAY.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> BEAM_SPLITTER_ITEM = ITEMS.register("beam_splitter", () -> new BlockItem(BEAM_SPLITTER.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> ITEM_VACUUM_ITEM = ITEMS.register("item_vacuum", () -> new BlockItem(ITEM_VACUUM.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> HEARTH_COIL_ITEM = ITEMS.register("hearth_coil", () -> new BlockItem(HEARTH_COIL.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> RESERVOIR_ITEM = ITEMS.register("reservoir", () -> new BlockItem(RESERVOIR.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> CAMINITE_RING_ITEM = ITEMS.register("caminite_ring", () -> new BlockItem(CAMINITE_RING.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> CAMINITE_VALVE_ITEM = ITEMS.register("caminite_valve", () -> new BlockItem(CAMINITE_VALVE.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> CRYSTAL_CELL_ITEM = ITEMS.register("crystal_cell", () -> new BlockItem(CRYSTAL_CELL.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> CLOCKWORK_ATTENUATOR_ITEM = ITEMS.register("clockwork_attenuator", () -> new BlockItem(CLOCKWORK_ATTENUATOR.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> GEOLOGIC_SEPARATOR_ITEM = ITEMS.register("geologic_separator", () -> new BlockItem(GEOLOGIC_SEPARATOR.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> COPPER_CHARGER_ITEM = ITEMS.register("copper_charger", () -> new BlockItem(COPPER_CHARGER.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> EMBER_SIPHON_ITEM = ITEMS.register("ember_siphon", () -> new BlockItem(EMBER_SIPHON.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));

	//items
	public static final RegistryObject<Item> TINKER_HAMMER = ITEMS.register("tinker_hammer", () -> new TinkerHammerItem(new Item.Properties().stacksTo(1).tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> TINKER_LENS = ITEMS.register("tinker_lens", () -> new TinkerLensItem(new Item.Properties().stacksTo(1).tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> ANCIENT_CODEX = ITEMS.register("ancient_codex", () -> new AncientCodexItem(new Item.Properties().stacksTo(1).tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> ATMOSPHERIC_GAUGE = ITEMS.register("atmospheric_gauge", () -> new Item(new Item.Properties().stacksTo(1).tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> EMBER_JAR = ITEMS.register("ember_jar", () -> new EmberJarItem(new Item.Properties().stacksTo(1).tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> EMBER_CARTRIDGE = ITEMS.register("ember_cartridge", () -> new EmberCartridgeItem(new Item.Properties().stacksTo(1).tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> CLOCKWORK_PICKAXE = ITEMS.register("clockwork_pickaxe", () -> new ClockworkPickaxeItem(new Item.Properties().stacksTo(1).tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> CLOCKWORK_AXE = ITEMS.register("clockwork_axe", () -> new ClockworkAxeItem(new Item.Properties().stacksTo(1).tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> GRANDHAMMER = ITEMS.register("grandhammer", () -> new ClockworkHammerItem(new Item.Properties().stacksTo(1).tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> BLAZING_RAY = ITEMS.register("blazing_ray", () -> new BlazingRayItem(new Item.Properties().stacksTo(1).tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> CINDER_STAFF = ITEMS.register("cinder_staff", () -> new CinderStaffItem(new Item.Properties().stacksTo(1).tab(Embers.TAB_EMBERS)));

	public static final RegistryObject<Item> EMBER_CRYSTAL = ITEMS.register("ember_crystal", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> EMBER_SHARD = ITEMS.register("ember_shard", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> EMBER_GRIT = ITEMS.register("ember_grit", () -> new FuelItem(new Item.Properties().tab(Embers.TAB_EMBERS), 1600));
	public static final RegistryObject<Item> CAMINITE_BLEND = ITEMS.register("caminite_blend", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> CAMINITE_BRICK = ITEMS.register("caminite_brick", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> ARCHAIC_BRICK = ITEMS.register("archaic_brick", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> ANCIENT_MOTIVE_CORE = ITEMS.register("ancient_motive_core", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));

	public static final RegistryObject<Item> RAW_CAMINITE_PLATE = ITEMS.register("raw_caminite_plate", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> RAW_INGOT_STAMP = ITEMS.register("raw_ingot_stamp", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> RAW_NUGGET_STAMP = ITEMS.register("raw_nugget_stamp", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> RAW_PLATE_STAMP = ITEMS.register("raw_plate_stamp", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));

	public static final RegistryObject<Item> CAMINITE_PLATE = ITEMS.register("caminite_plate", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> INGOT_STAMP = ITEMS.register("ingot_stamp", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> NUGGET_STAMP = ITEMS.register("nugget_stamp", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> PLATE_STAMP = ITEMS.register("plate_stamp", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));

	public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	//public static final RegistryObject<Item> GOLD_PLATE = ITEMS.register("gold_plate", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> COPPER_PLATE = ITEMS.register("copper_plate", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));

	public static final RegistryObject<Item> RAW_LEAD = ITEMS.register("raw_lead", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> LEAD_PLATE = ITEMS.register("lead_plate", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));

	public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> SILVER_PLATE = ITEMS.register("silver_plate", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));

	public static final RegistryObject<Item> DAWNSTONE_INGOT = ITEMS.register("dawnstone_ingot", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> DAWNSTONE_NUGGET = ITEMS.register("dawnstone_nugget", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));
	public static final RegistryObject<Item> DAWNSTONE_PLATE = ITEMS.register("dawnstone_plate", () -> new Item(new Item.Properties().tab(Embers.TAB_EMBERS)));

	//fluids
	public static final FluidStuff MOLTEN_IRON = addFluid("Molten Iron", new FluidInfo("molten_iron", 0xC72913, 0.1F, 1.5F), Material.LAVA, MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_GOLD = addFluid("Molten Gold", new FluidInfo("molten_gold", 0xF9C026, 0.1F, 1.5F), Material.LAVA, MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_COPPER = addFluid("Molten Copper", new FluidInfo("molten_copper", 0xEA7E38, 0.1F, 1.5F), Material.LAVA, MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_LEAD = addFluid("Molten Lead", new FluidInfo("molten_lead", 0x665975, 0.1F, 1.5F), Material.LAVA, MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_SILVER = addFluid("Molten Silver", new FluidInfo("molten_silver", 0xBCEAF7, 0.1F, 1.5F), Material.LAVA, MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_DAWNSTONE = addFluid("Molten Dawnstone", new FluidInfo("molten_dawnstone", 0xFF9C36, 0.1F, 1.5F), Material.LAVA, MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_NICKEL = addFluid("Molten Nickel", new FluidInfo("molten_nickel", 0xDDEBC0, 0.1F, 1.5F), Material.LAVA, MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_TIN = addFluid("Molten Tin", new FluidInfo("molten_tin", 0xDCEDE5, 0.1F, 1.5F), Material.LAVA, MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_ALUMINUM = addFluid("Molten Aluminum", new FluidInfo("molten_aluminum", 0xFFAE9C, 0.1F, 1.5F), Material.LAVA, MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_BRONZE = addFluid("Molten Bronze", new FluidInfo("molten_bronze", 0xEDAE66, 0.1F, 1.5F), Material.LAVA, MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_ELECTRUM = addFluid("Molten Electrum", new FluidInfo("molten_electrum", 0xFAE176, 0.1F, 1.5F), Material.LAVA, MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	//block entities
	public static final RegistryObject<BlockEntityType<CopperCellBlockEntity>> COPPER_CELL_ENTITY = BLOCK_ENTITY_TYPES.register("copper_cell", () -> BlockEntityType.Builder.of(CopperCellBlockEntity::new, COPPER_CELL.get()).build(null));
	public static final RegistryObject<BlockEntityType<CreativeEmberBlockEntity>> CREATIVE_EMBER_ENTITY = BLOCK_ENTITY_TYPES.register("creative_ember_source", () -> BlockEntityType.Builder.of(CreativeEmberBlockEntity::new, CREATIVE_EMBER.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberEmitterBlockEntity>> EMBER_EMITTER_ENTITY = BLOCK_ENTITY_TYPES.register("ember_emitter", () -> BlockEntityType.Builder.of(EmberEmitterBlockEntity::new, EMBER_EMITTER.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberReceiverBlockEntity>> EMBER_RECEIVER_ENTITY = BLOCK_ENTITY_TYPES.register("ember_receiver", () -> BlockEntityType.Builder.of(EmberReceiverBlockEntity::new, EMBER_RECEIVER.get()).build(null));
	public static final RegistryObject<BlockEntityType<ItemPipeBlockEntity>> ITEM_PIPE_ENTITY = BLOCK_ENTITY_TYPES.register("item_pipe", () -> BlockEntityType.Builder.of(ItemPipeBlockEntity::new, ITEM_PIPE.get()).build(null));
	public static final RegistryObject<BlockEntityType<ItemExtractorBlockEntity>> ITEM_EXTRACTOR_ENTITY = BLOCK_ENTITY_TYPES.register("item_extractor", () -> BlockEntityType.Builder.of(ItemExtractorBlockEntity::new, ITEM_EXTRACTOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberBoreBlockEntity>> EMBER_BORE_ENTITY = BLOCK_ENTITY_TYPES.register("ember_bore", () -> BlockEntityType.Builder.of(EmberBoreBlockEntity::new, EMBER_BORE.get()).build(null));
	public static final RegistryObject<BlockEntityType<MechanicalCoreBlockEntity>> MECHANICAL_CORE_ENTITY = BLOCK_ENTITY_TYPES.register("mechanical_core", () -> BlockEntityType.Builder.of(MechanicalCoreBlockEntity::new, MECHANICAL_CORE.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberActivatorBottomBlockEntity>> EMBER_ACTIVATOR_BOTTOM_ENTITY = BLOCK_ENTITY_TYPES.register("ember_activator_bottom", () -> BlockEntityType.Builder.of(EmberActivatorBottomBlockEntity::new, EMBER_ACTIVATOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberActivatorTopBlockEntity>> EMBER_ACTIVATOR_TOP_ENTITY = BLOCK_ENTITY_TYPES.register("ember_activator_top", () -> BlockEntityType.Builder.of(EmberActivatorTopBlockEntity::new, EMBER_ACTIVATOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<MelterBottomBlockEntity>> MELTER_BOTTOM_ENTITY = BLOCK_ENTITY_TYPES.register("melter_bottom", () -> BlockEntityType.Builder.of(MelterBottomBlockEntity::new, MELTER.get()).build(null));
	public static final RegistryObject<BlockEntityType<MelterTopBlockEntity>> MELTER_TOP_ENTITY = BLOCK_ENTITY_TYPES.register("melter_top", () -> BlockEntityType.Builder.of(MelterTopBlockEntity::new, MELTER.get()).build(null));
	public static final RegistryObject<BlockEntityType<FluidPipeBlockEntity>> FLUID_PIPE_ENTITY = BLOCK_ENTITY_TYPES.register("fluid_pipe", () -> BlockEntityType.Builder.of(FluidPipeBlockEntity::new, FLUID_PIPE.get()).build(null));
	public static final RegistryObject<BlockEntityType<FluidExtractorBlockEntity>> FLUID_EXTRACTOR_ENTITY = BLOCK_ENTITY_TYPES.register("fluid_extractor", () -> BlockEntityType.Builder.of(FluidExtractorBlockEntity::new, FLUID_EXTRACTOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<FluidVesselBlockEntity>> FLUID_VESSEL_ENTITY = BLOCK_ENTITY_TYPES.register("fluid_vesel", () -> BlockEntityType.Builder.of(FluidVesselBlockEntity::new, FLUID_VESSEL.get()).build(null));
	public static final RegistryObject<BlockEntityType<StamperBlockEntity>> STAMPER_ENTITY = BLOCK_ENTITY_TYPES.register("stamper", () -> BlockEntityType.Builder.of(StamperBlockEntity::new, STAMPER.get()).build(null));
	public static final RegistryObject<BlockEntityType<StampBaseBlockEntity>> STAMP_BASE_ENTITY = BLOCK_ENTITY_TYPES.register("stamp_base", () -> BlockEntityType.Builder.of(StampBaseBlockEntity::new, STAMP_BASE.get()).build(null));
	public static final RegistryObject<BlockEntityType<BinBlockEntity>> BIN_ENTITY = BLOCK_ENTITY_TYPES.register("bin", () -> BlockEntityType.Builder.of(BinBlockEntity::new, BIN.get()).build(null));
	public static final RegistryObject<BlockEntityType<MixerCentrifugeBottomBlockEntity>> MIXER_CENTRIFUGE_BOTTOM_ENTITY = BLOCK_ENTITY_TYPES.register("mixer_centrifuge_bottom", () -> BlockEntityType.Builder.of(MixerCentrifugeBottomBlockEntity::new, MIXER_CENTRIFUGE.get()).build(null));
	public static final RegistryObject<BlockEntityType<MixerCentrifugeTopBlockEntity>> MIXER_CENTRIFUGE_TOP_ENTITY = BLOCK_ENTITY_TYPES.register("mixer_centrifuge_top", () -> BlockEntityType.Builder.of(MixerCentrifugeTopBlockEntity::new, MIXER_CENTRIFUGE.get()).build(null));
	public static final RegistryObject<BlockEntityType<ItemDropperBlockEntity>> ITEM_DROPPER_ENTITY = BLOCK_ENTITY_TYPES.register("item_dropper", () -> BlockEntityType.Builder.of(ItemDropperBlockEntity::new, ITEM_DROPPER.get()).build(null));
	public static final RegistryObject<BlockEntityType<PressureRefineryBottomBlockEntity>> PRESSURE_REFINERY_BOTTOM_ENTITY = BLOCK_ENTITY_TYPES.register("pressure_refinery_bottom", () -> BlockEntityType.Builder.of(PressureRefineryBottomBlockEntity::new, PRESSURE_REFINERY.get()).build(null));
	public static final RegistryObject<BlockEntityType<PressureRefineryTopBlockEntity>> PRESSURE_REFINERY_TOP_ENTITY = BLOCK_ENTITY_TYPES.register("pressure_refinery_top", () -> BlockEntityType.Builder.of(PressureRefineryTopBlockEntity::new, PRESSURE_REFINERY.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberEjectorBlockEntity>> EMBER_EJECTOR_ENTITY = BLOCK_ENTITY_TYPES.register("ember_ejector", () -> BlockEntityType.Builder.of(EmberEjectorBlockEntity::new, EMBER_EJECTOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberFunnelBlockEntity>> EMBER_FUNNEL_ENTITY = BLOCK_ENTITY_TYPES.register("ember_funnel", () -> BlockEntityType.Builder.of(EmberFunnelBlockEntity::new, EMBER_FUNNEL.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberRelayBlockEntity>> EMBER_RELAY_ENTITY = BLOCK_ENTITY_TYPES.register("ember_relay", () -> BlockEntityType.Builder.of(EmberRelayBlockEntity::new, EMBER_RELAY.get()).build(null));
	public static final RegistryObject<BlockEntityType<MirrorRelayBlockEntity>> MIRROR_RELAY_ENTITY = BLOCK_ENTITY_TYPES.register("mirror_relay", () -> BlockEntityType.Builder.of(MirrorRelayBlockEntity::new, MIRROR_RELAY.get()).build(null));
	public static final RegistryObject<BlockEntityType<BeamSplitterBlockEntity>> BEAM_SPLITTER_ENTITY = BLOCK_ENTITY_TYPES.register("beam_splitter", () -> BlockEntityType.Builder.of(BeamSplitterBlockEntity::new, BEAM_SPLITTER.get()).build(null));
	public static final RegistryObject<BlockEntityType<ItemVacuumBlockEntity>> ITEM_VACUUM_ENTITY = BLOCK_ENTITY_TYPES.register("item_vacuum", () -> BlockEntityType.Builder.of(ItemVacuumBlockEntity::new, ITEM_VACUUM.get()).build(null));
	public static final RegistryObject<BlockEntityType<HearthCoilBlockEntity>> HEARTH_COIL_ENTITY = BLOCK_ENTITY_TYPES.register("hearth_coil", () -> BlockEntityType.Builder.of(HearthCoilBlockEntity::new, HEARTH_COIL.get()).build(null));
	public static final RegistryObject<BlockEntityType<ReservoirBlockEntity>> RESERVOIR_ENTITY = BLOCK_ENTITY_TYPES.register("reservoir", () -> BlockEntityType.Builder.of(ReservoirBlockEntity::new, RESERVOIR.get()).build(null));
	public static final RegistryObject<BlockEntityType<CaminiteValveBlockEntity>> CAMINITE_VALVE_ENTITY = BLOCK_ENTITY_TYPES.register("caminite_valve", () -> BlockEntityType.Builder.of(CaminiteValveBlockEntity::new, CAMINITE_VALVE_EDGE.get()).build(null));
	public static final RegistryObject<BlockEntityType<CrystalCellBlockEntity>> CRYSTAL_CELL_ENTITY = BLOCK_ENTITY_TYPES.register("crystal_cell", () -> BlockEntityType.Builder.of(CrystalCellBlockEntity::new, CRYSTAL_CELL.get()).build(null));
	public static final RegistryObject<BlockEntityType<ClockworkAttenuatorBlockEntity>> CLOCKWORK_ATTENUATOR_ENTITY = BLOCK_ENTITY_TYPES.register("clockwork_attenuator", () -> BlockEntityType.Builder.of(ClockworkAttenuatorBlockEntity::new, CLOCKWORK_ATTENUATOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<GeologicSeparatorBlockEntity>> GEOLOGIC_SEPARATOR_ENTITY = BLOCK_ENTITY_TYPES.register("geologic_separator", () -> BlockEntityType.Builder.of(GeologicSeparatorBlockEntity::new, GEOLOGIC_SEPARATOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<CopperChargerBlockEntity>> COPPER_CHARGER_ENTITY = BLOCK_ENTITY_TYPES.register("copper_charger", () -> BlockEntityType.Builder.of(CopperChargerBlockEntity::new, COPPER_CHARGER.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberSiphonBlockEntity>> EMBER_SIPHON_ENTITY = BLOCK_ENTITY_TYPES.register("ember_siphon", () -> BlockEntityType.Builder.of(EmberSiphonBlockEntity::new, EMBER_SIPHON.get()).build(null));

	//entities
	public static final RegistryObject<EntityType<EmberPacketEntity>> EMBER_PACKET = registerEntity("ember_packet", EntityType.Builder.<EmberPacketEntity>of(EmberPacketEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune().clientTrackingRange(3).updateInterval(1));
	public static final RegistryObject<EntityType<EmberProjectileEntity>> EMBER_PROJECTILE = registerEntity("ember_projectile", EntityType.Builder.<EmberProjectileEntity>of(EmberProjectileEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune().clientTrackingRange(3).updateInterval(1));
	public static final RegistryObject<EntityType<AncientGolemEntity>> ANCIENT_GOLEM = registerEntity("ancient_golem", EntityType.Builder.<AncientGolemEntity>of(AncientGolemEntity::new, MobCategory.MONSTER).sized(0.6F, 1.8F).fireImmune().clientTrackingRange(8));

	//spawn eggs
	public static final RegistryObject<Item> ANCIENT_GOLEM_SPAWN_EGG = ITEMS.register("ancient_golem_spawn_egg", () -> new ForgeSpawnEggItem(ANCIENT_GOLEM, Misc.intColor(48, 38, 35), Misc.intColor(79, 66, 61), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	//particle types
	public static final RegistryObject<ParticleType<GlowParticleOptions>> GLOW_PARTICLE = registerParticle("glow", false, GlowParticleOptions.DESERIALIZER, GlowParticleOptions.CODEC);
	public static final RegistryObject<ParticleType<StarParticleOptions>> STAR_PARTICLE = registerParticle("star", false, StarParticleOptions.DESERIALIZER, StarParticleOptions.CODEC);
	public static final RegistryObject<ParticleType<SparkParticleOptions>> SPARK_PARTICLE = registerParticle("spark", false, SparkParticleOptions.DESERIALIZER, SparkParticleOptions.CODEC);
	public static final RegistryObject<ParticleType<SmokeParticleOptions>> SMOKE_PARTICLE = registerParticle("smoke", false, SmokeParticleOptions.DESERIALIZER, SmokeParticleOptions.CODEC);
	public static final RegistryObject<ParticleType<VaporParticleOptions>> VAPOR_PARTICLE = registerParticle("vapor", false, VaporParticleOptions.DESERIALIZER, VaporParticleOptions.CODEC);

	//recipe types
	public static final RegistryObject<RecipeType<BoringRecipe>> BORING = registerRecipeType("boring");
	public static final RegistryObject<RecipeType<EmberActivationRecipe>> EMBER_ACTIVATION = registerRecipeType("ember_activation");
	public static final RegistryObject<RecipeType<MeltingRecipe>> MELTING = registerRecipeType("melting");
	public static final RegistryObject<RecipeType<StampingRecipe>> STAMPING = registerRecipeType("stamping");
	public static final RegistryObject<RecipeType<MixingRecipe>> MIXING = registerRecipeType("mixing");
	public static final RegistryObject<RecipeType<MetalCoefficientRecipe>> METAL_COEFFICIENT = registerRecipeType("metal_coefficient");

	//recipe serializers
	public static final RegistryObject<RecipeSerializer<BoringRecipe>> BORING_SERIALIZER = RECIPE_SERIALIZERS.register("boring", () -> BoringRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<EmberActivationRecipe>> EMBER_ACTIVATION_SERIALIZER = RECIPE_SERIALIZERS.register("ember_activation", () -> EmberActivationRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<MeltingRecipe>> MELTING_SERIALIZER = RECIPE_SERIALIZERS.register("melting", () -> MeltingRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<StampingRecipe>> STAMPING_SERIALIZER = RECIPE_SERIALIZERS.register("stamping", () -> StampingRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<TagStampingRecipe>> TAG_STAMPING_SERIALIZER = RECIPE_SERIALIZERS.register("tag_stamping", () -> TagStampingRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<MixingRecipe>> MIXING_SERIALIZER = RECIPE_SERIALIZERS.register("mixing", () -> MixingRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<MetalCoefficientRecipe>> METAL_COEFFICIENT_SERIALIZER = RECIPE_SERIALIZERS.register("metal_coefficient", () -> MetalCoefficientRecipe.SERIALIZER);

	//loot modifiers
	public static final RegistryObject<Codec<GrandhammerLootModifier>> GRANDHAMMER_MODIFIER = LOOT_MODIFIERS.register("grandhammer", () -> GrandhammerLootModifier.CODEC);

	public static void registerDispenserBehaviour(final FMLCommonSetupEvent event) {
		DispenseItemBehavior dispenseBucket = new DefaultDispenseItemBehavior() {
			private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

			@Override
			public ItemStack execute(BlockSource source, ItemStack stack) {
				DispensibleContainerItem container = (DispensibleContainerItem)stack.getItem();
				BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				Level level = source.getLevel();
				if (container.emptyContents(null, level, blockpos, null)) {
					container.checkExtraContent(null, level, stack, blockpos);
					return new ItemStack(Items.BUCKET);
				} else {
					return this.defaultDispenseItemBehavior.dispense(source, stack);
				}
			}
		};
		event.enqueueWork(() -> {
			for (FluidStuff fluid : fluidList) {
				DispenserBlock.registerBehavior(fluid.FLUID_BUCKET.get(), dispenseBucket);
			}
		});
	}

	public static class FluidStuff {

		public final ForgeFlowingFluid.Properties PROPERTIES;

		public final RegistryObject<ForgeFlowingFluid.Source> FLUID;
		public final RegistryObject<ForgeFlowingFluid.Flowing> FLUID_FLOW;
		public final RegistryObject<FluidType> TYPE;

		public final RegistryObject<LiquidBlock> FLUID_BLOCK;

		public final RegistryObject<BucketItem> FLUID_BUCKET;

		public final String name;
		public final String localizedName;
		public final int color;

		public FluidStuff(String name, String localizedName, int color, FluidType type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Source> source, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Flowing> flowing, Material material) {
			this.name = name;
			this.localizedName = localizedName;
			this.color = color;

			FLUID = FLUIDS.register(name, () -> source.apply(getFluidProperties()));
			FLUID_FLOW = FLUIDS.register("flowing_" + name, () -> flowing.apply(getFluidProperties()));
			TYPE = FLUIDTYPES.register(name, () -> type);

			PROPERTIES = new ForgeFlowingFluid.Properties(TYPE, FLUID, FLUID_FLOW);
			if (fluidProperties != null)
				fluidProperties.accept(PROPERTIES);

			FLUID_BLOCK = BLOCKS.register(name + "_block", () -> block.apply(FLUID, Block.Properties.of(material).lightLevel((state) -> { return type.getLightLevel(); }).randomTicks().strength(100.0F).noLootTable()));
			FLUID_BUCKET = ITEMS.register(name + "_bucket", () -> new BucketItem(FLUID, new BucketItem.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(Embers.TAB_EMBERS)));

			PROPERTIES.bucket(FLUID_BUCKET).block(FLUID_BLOCK);
		}

		public ForgeFlowingFluid.Properties getFluidProperties() {
			return PROPERTIES;       
		}
	}

	public static class StoneDecoBlocks {

		public String name;

		public RegistryObject<Block> block;
		public RegistryObject<StairBlock> stairs = null;
		public RegistryObject<Item> stairsItem = null;
		public RegistryObject<SlabBlock> slab = null;
		public RegistryObject<Item> slabItem = null;
		public RegistryObject<WallBlock> wall = null;
		public RegistryObject<Item> wallItem = null;

		public StoneDecoBlocks(String name, RegistryObject<Block> block, Properties properties, boolean stairs, boolean slab, boolean wall) {
			this.name = name;
			this.block = block;
			if (stairs) {
				this.stairs = BLOCKS.register(name + "_stairs", () -> new StairBlock(() -> block.get().defaultBlockState(), properties));
				this.stairsItem = ITEMS.register(name + "_stairs", () -> new BlockItem(this.stairs.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
			}
			if (slab) {
				this.slab = BLOCKS.register(name + "_slab", () -> new SlabBlock(properties));
				this.slabItem = ITEMS.register(name + "_slab", () -> new BlockItem(this.slab.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
			}
			if (wall) {
				this.wall = BLOCKS.register(name + "_wall", () -> new WallBlock(properties));
				this.wallItem = ITEMS.register(name + "_wall", () -> new BlockItem(this.wall.get(), new Item.Properties().tab(Embers.TAB_EMBERS)));
			}
		}

		public StoneDecoBlocks(String name, RegistryObject<Block> block, Properties properties) {
			this(name, block, properties, true, true, true);
		}
	}
}
