package ms55.manaliquidizer.gui;

import ms55.manaliquidizer.ManaLiquidizer;
import ms55.manaliquidizer.container.ModContainers;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = ManaLiquidizer.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ScreenHandler {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent e) {
        ScreenManager.registerFactory(ModContainers.MANA_LIQUIDIZER.get(), ManaLiquidizerScreen::new);
    }
}