package ms55.manaliquidizer;

import ms55.manaliquidizer.block.ModBlocks;
import ms55.manaliquidizer.fluid.ModFluids;
import ms55.manaliquidizer.tile.ModTiles;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(ManaLiquidizer.MODID)
public class ManaLiquidizer {
	public static final String MODID = "manaliquidizer";
	public static final String NAME = "Mana Liquidizer";

	public static final ItemGroup ITEM_GROUP = new ManaItemGroup();

	public ManaLiquidizer() {
        MinecraftForge.EVENT_BUS.register(this);

        ModBlocks.registerBlocks();
        //ModContainers.registerContainers(); //Not needed anymore, but kept there as I might need it for other projects!
        ModFluids.registerFluids();
        ModTiles.registerTileEntities();
    }
}