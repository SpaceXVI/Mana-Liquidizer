package ms55.manaliquidizer.common.fluid;

import com.mojang.math.Vector3f;

import ms55.manaliquidizer.ManaLiquidizer;
import ms55.manaliquidizer.common.block.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
	public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, ManaLiquidizer.MODID);
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.Keys.FLUIDS, ManaLiquidizer.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ManaLiquidizer.MODID);

	public static final ResourceLocation FLUID_STILL   = new ResourceLocation("minecraft:block/water_still"  );
    public static final ResourceLocation FLUID_FLOWING = new ResourceLocation("minecraft:block/water_flow"   );
    public static final ResourceLocation FLUID_OVERLAY = new ResourceLocation("minecraft:block/water_overlay");

	private static ForgeFlowingFluid.Properties makeProperties() {
        return new ForgeFlowingFluid.Properties(MANA_FLUID_TYPE, MANA_FLUID_STILL, MANA_FLUID_FLOWING)
            .bucket(MANA_FLUID_BUCKET).block(MANA_FLUID_BLOCK);
    }

	public static RegistryObject<FluidType> MANA_FLUID_TYPE = FLUID_TYPES.register("mana_fluid", () -> new BaseFluidType(FLUID_STILL, FLUID_FLOWING, FLUID_OVERLAY,
			0xFF0091CF, new Vector3f(0, 145f / 255f, 207f / 255f), FluidType.Properties.create().sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH).lightLevel(2).temperature(150)
            .supportsBoating(true)));

	public static RegistryObject<FlowingFluid> MANA_FLUID_STILL = FLUIDS.register("mana_fluid", () ->
    	new ForgeFlowingFluid.Source(makeProperties()));

	public static RegistryObject<FlowingFluid> MANA_FLUID_FLOWING = FLUIDS.register("mana_fluid_flowing", () ->
    	new ForgeFlowingFluid.Flowing(makeProperties()));

	public static RegistryObject<LiquidBlock> MANA_FLUID_BLOCK = ModBlocks.BLOCKS.register("mana_fluid_block", () ->
     	new LiquidBlock(MANA_FLUID_STILL, Block.Properties.of(Material.WATER, MaterialColor.WATER).noCollission().strength(100.0F).noLootTable()));

	public static RegistryObject<Item> MANA_FLUID_BUCKET = ITEMS.register("mana_fluid_bucket", () ->
     	new BucketItem(MANA_FLUID_STILL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ManaLiquidizer.ITEM_GROUP)));
}