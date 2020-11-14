package ms55.manaliquidizer.block;

import ms55.manaliquidizer.ManaLiquidizer;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ManaLiquidizer.MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ManaLiquidizer.MODID);

	public static final RegistryObject<Block> MANA_LIQUIDIZER = BLOCKS.register("mana_liquidizer", () -> new ManaLiquidizerBlock(Block.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(2.0F, 6.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Item> BLOCK_ITEM_MANA_LIQUIDIZER = ITEMS.register("mana_liquidizer", () -> new BlockItem(MANA_LIQUIDIZER.get(), new Item.Properties()
		.maxStackSize(64)
		.group(ManaLiquidizer.ITEM_GROUP)));

	public static void registerBlocks() {
	    BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
	    ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}