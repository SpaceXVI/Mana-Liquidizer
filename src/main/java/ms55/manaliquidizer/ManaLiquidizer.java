package ms55.manaliquidizer;

import ms55.manaliquidizer.block.ModBlocks;
import ms55.manaliquidizer.container.ModContainers;
import ms55.manaliquidizer.fluid.ModFluids;
import ms55.manaliquidizer.tile.ModTiles;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(ManaLiquidizer.MODID)
public class ManaLiquidizer {
	public static final String MODID = "manaliquidizer";
	public static final String NAME = "Mana Liquidizer";

	public ManaLiquidizer() {
        MinecraftForge.EVENT_BUS.register(this);

        //ModLoadingContext.get().registerConfig(Type.COMMON, ManaLiquidizer.COMMON_SPEC, "manaliquidizer.toml");

        ModBlocks.registerBlocks();
        ModContainers.registerContainers();
        ModFluids.registerFluids();
        ModTiles.registerTileEntities();
    }
}