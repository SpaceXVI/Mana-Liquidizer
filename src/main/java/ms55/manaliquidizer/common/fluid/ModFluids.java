package ms55.manaliquidizer.common.fluid;

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
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, ManaLiquidizer.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ManaLiquidizer.MODID);

	public static final ResourceLocation FLUID_STILL   = new ResourceLocation("minecraft:block/water_still"  ); // minecraft:block/water_still -> BotaniaAPI.MODID, "block/mana_water"
    public static final ResourceLocation FLUID_FLOWING = new ResourceLocation("minecraft:block/water_flow"   ); // minecraft:block/water_flow -> BotaniaAPI.MODID, "block/mana_water"
    public static final ResourceLocation FLUID_OVERLAY = new ResourceLocation("minecraft:block/water_overlay"); // minecraft:block/water_overlay -> BotaniaAPI.MODID, "block/mana_water"

	private static ForgeFlowingFluid.Properties makeProperties() {
        return new ForgeFlowingFluid.Properties(MANA_FLUID, MANA_FLUID_FLOWING,
        	FluidAttributes.builder(FLUID_STILL, FLUID_FLOWING).sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY).overlay(FLUID_OVERLAY).color(0xFF0091CF))
            .bucket(MANA_FLUID_BUCKET).block(MANA_FLUID_BLOCK);
    }

	public static RegistryObject<FlowingFluid> MANA_FLUID = FLUIDS.register("mana_fluid", () ->
    	new ForgeFlowingFluid.Source(makeProperties()));

	public static RegistryObject<FlowingFluid> MANA_FLUID_FLOWING = FLUIDS.register("mana_fluid_flowing", () ->
    	new ForgeFlowingFluid.Flowing(makeProperties()));

	public static RegistryObject<LiquidBlock> MANA_FLUID_BLOCK = ModBlocks.BLOCKS.register("mana_fluid_block", () ->
     	new LiquidBlock(MANA_FLUID, Block.Properties.of(Material.WATER, MaterialColor.WATER).noCollission().strength(100.0F).noDrops()));

	public static RegistryObject<Item> MANA_FLUID_BUCKET = ITEMS.register("mana_fluid_bucket", () ->
     	new BucketItem(MANA_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ManaLiquidizer.ITEM_GROUP)));
}