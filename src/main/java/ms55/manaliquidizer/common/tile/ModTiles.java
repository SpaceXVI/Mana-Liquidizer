package ms55.manaliquidizer.common.tile;

import ms55.manaliquidizer.ManaLiquidizer;
import ms55.manaliquidizer.common.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.api.block.WandHUD;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntities.BECapConsumer;

public class ModTiles {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ManaLiquidizer.MODID);

	public static final RegistryObject<BlockEntityType<ManaLiquidizerBlockEntity>> MANA_LIQUIDIZER = BLOCK_ENTITIES.register("mana_liquidizer",
			() -> BlockEntityType.Builder.of(ManaLiquidizerBlockEntity::new, ModBlocks.MANA_LIQUIDIZER.get()).build(null));

	public static void registerWandHudCaps(BECapConsumer<WandHUD> consumer) {
		consumer.accept(be -> new ManaLiquidizerBlockEntity.WandHud((ManaLiquidizerBlockEntity) be), ModTiles.MANA_LIQUIDIZER.get());
	}
}