package ms55.manaliquidizer.container;

import ms55.manaliquidizer.ManaLiquidizer;
import ms55.manaliquidizer.tile.ModTiles;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IntArray;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ManaLiquidizer.MODID);

    public static final RegistryObject<ContainerType<ManaLiquidizerContainer>> MANA_LIQUIDIZER = CONTAINERS.register("mana_liquidizer", () -> IForgeContainerType.create((windowId, inv, data) -> {
        return new ManaLiquidizerContainer(windowId, inv, ModTiles.MANA_LIQUIDIZER.get().create(), new IntArray(2));
    }));

    public static void registerContainers() {
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}