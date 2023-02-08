package ms55.manaliquidizer;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

import ms55.manaliquidizer.client.config.Config;
import ms55.manaliquidizer.common.block.ModBlocks;
import ms55.manaliquidizer.common.fluid.ModFluids;
import ms55.manaliquidizer.common.tile.ManaLiquidizerBlockEntity;
import ms55.manaliquidizer.common.tile.ModTiles;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.block.Wandable;
import vazkii.botania.api.mana.ManaReceiver;
import vazkii.botania.api.mana.spark.SparkAttachable;
import vazkii.botania.forge.CapabilityUtil;

@Mod(ManaLiquidizer.MODID)
public class ManaLiquidizer {
	public static final String MODID = "manaliquidizer";
	public static final String NAME = "Mana Liquidizer";

	public static final CreativeModeTab ITEM_GROUP = new ManaItemGroup();

	public ManaLiquidizer() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(this);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(Type.COMMON, Config.COMMON_SPEC, "manaliquidizer.toml");

        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.ITEMS.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);
        ModFluids.FLUID_TYPES.register(modEventBus);
        ModFluids.ITEMS.register(modEventBus);
        ModTiles.BLOCK_ENTITIES.register(modEventBus);
    }

	private static void attachBeCapabilities(AttachCapabilitiesEvent<BlockEntity> event) {
		var be = event.getObject();

		if (be instanceof ManaLiquidizerBlockEntity container) {
			event.addCapability(prefix("wandable"), CapabilityUtil.makeProvider(BotaniaForgeCapabilities.WANDABLE, (Wandable) be));
			event.addCapability(prefix("mana_receiver"), CapabilityUtil.makeProvider(BotaniaForgeCapabilities.MANA_RECEIVER, (ManaReceiver) be));
			event.addCapability(prefix("spark_attachable"), CapabilityUtil.makeProvider(BotaniaForgeCapabilities.SPARK_ATTACHABLE, (SparkAttachable) be));
		}
	}

	@SubscribeEvent
	public void commonSetup(FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.addGenericListener(BlockEntity.class, ManaLiquidizer::attachBeCapabilities);
	}
}