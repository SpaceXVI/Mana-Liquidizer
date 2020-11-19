package ms55.manaliquidizer.common.tile;

import ms55.manaliquidizer.ManaLiquidizer;
import ms55.manaliquidizer.common.block.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTiles {
	private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ManaLiquidizer.MODID);

	public static final RegistryObject<TileEntityType<ManaLiquidizerTile>> MANA_LIQUIDIZER = TILE_ENTITIES.register("mana_liquidizer", () -> TileEntityType.Builder.create(ManaLiquidizerTile::new, ModBlocks.MANA_LIQUIDIZER.get()).build(null));

	public static void registerTileEntities() {
		TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}