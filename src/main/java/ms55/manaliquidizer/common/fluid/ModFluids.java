package ms55.manaliquidizer.common.fluid;

import ms55.manaliquidizer.ManaLiquidizer;
import ms55.manaliquidizer.common.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFluids {
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, ManaLiquidizer.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ManaLiquidizer.MODID);

	public static final ResourceLocation FLUID_STILL = new ResourceLocation("minecraft:block/water_still");
    public static final ResourceLocation FLUID_FLOWING = new ResourceLocation("minecraft:block/water_flow");
    public static final ResourceLocation FLUID_OVERLAY = new ResourceLocation("minecraft:block/water_overlay");

	private static ForgeFlowingFluid.Properties makeProperties() {
        return new ForgeFlowingFluid.Properties(MANA_FLUID, MANA_FLUID_FLOWING,
        	FluidAttributes.builder(FLUID_STILL, FLUID_FLOWING).overlay(FLUID_OVERLAY).color(0xFF4BD5F2))
            .bucket(MANA_FLUID_BUCKET).block(MANA_FLUID_BLOCK);
    }

	public static RegistryObject<FlowingFluid> MANA_FLUID = FLUIDS.register("mana_fluid", () ->
    	new ForgeFlowingFluid.Source(makeProperties()));

	public static RegistryObject<FlowingFluid> MANA_FLUID_FLOWING = FLUIDS.register("mana_fluid_flowing", () ->
    	new ForgeFlowingFluid.Flowing(makeProperties()));

	public static RegistryObject<FlowingFluidBlock> MANA_FLUID_BLOCK = ModBlocks.BLOCKS.register("mana_fluid_block", () ->
     	new FlowingFluidBlock(MANA_FLUID, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

	public static RegistryObject<Item> MANA_FLUID_BUCKET = ITEMS.register("mana_fluid_bucket", () ->
     	new BucketItem(MANA_FLUID, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ManaLiquidizer.ITEM_GROUP)));

	public static void registerFluids() {
		FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
