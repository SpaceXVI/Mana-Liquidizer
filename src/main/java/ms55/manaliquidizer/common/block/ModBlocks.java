package ms55.manaliquidizer.common.block;

import ms55.manaliquidizer.ManaLiquidizer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ManaLiquidizer.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ManaLiquidizer.MODID);

	public static final RegistryObject<Block> MANA_LIQUIDIZER = BLOCKS.register("mana_liquidizer", 
			() -> new ManaLiquidizerBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(2.0F, 6.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Item> BLOCK_ITEM_MANA_LIQUIDIZER = ITEMS.register("mana_liquidizer", () -> new BlockItem(MANA_LIQUIDIZER.get(), new Item.Properties()
		.stacksTo(64)
		.tab(ManaLiquidizer.ITEM_GROUP)));
}