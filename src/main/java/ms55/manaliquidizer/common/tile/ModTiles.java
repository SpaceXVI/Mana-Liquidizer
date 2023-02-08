package ms55.manaliquidizer.common.tile;

import ms55.manaliquidizer.ManaLiquidizer;
import ms55.manaliquidizer.common.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.api.block.IWandHUD;
import vazkii.botania.common.block.tile.ModTiles.BECapConsumer;

public class ModTiles {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ManaLiquidizer.MODID);

	public static final RegistryObject<BlockEntityType<ManaLiquidizerTile>> MANA_LIQUIDIZER = BLOCK_ENTITIES.register("mana_liquidizer", () -> BlockEntityType.Builder.of(ManaLiquidizerTile::new, ModBlocks.MANA_LIQUIDIZER.get()).build(null));

	public static void registerWandHudCaps(BECapConsumer<IWandHUD> consumer) {
		consumer.accept(be -> new ManaLiquidizerTile.WandHud((ManaLiquidizerTile) be), ModTiles.MANA_LIQUIDIZER.get());
	}
}