package ms55.manaliquidizer;

import ms55.manaliquidizer.client.config.Config;
import ms55.manaliquidizer.common.block.ModBlocks;
import ms55.manaliquidizer.common.fluid.ModFluids;
import ms55.manaliquidizer.common.tile.ModTiles;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;

@Mod(ManaLiquidizer.MODID)
public class ManaLiquidizer {
	public static final String MODID = "manaliquidizer";
	public static final String NAME = "Mana Liquidizer";

	public static final ItemGroup ITEM_GROUP = new ManaItemGroup();

	public ManaLiquidizer() {
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(Type.COMMON, Config.COMMON_SPEC, "manaliquidizer.toml");

        ModBlocks.registerBlocks();
        //ModContainers.registerContainers(); //Not needed anymore, but kept there as I might need it for other projects!
        ModFluids.registerFluids();
        ModTiles.registerTileEntities();
    }
}