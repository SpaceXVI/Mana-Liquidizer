package ms55.manaliquidizer.common.data;

import ms55.manaliquidizer.ManaLiquidizer;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = ManaLiquidizer.MODID, bus = Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

		ManaFluidTagsProvider fluidTags = new ManaFluidTagsProvider(gen, helper);
		gen.addProvider(event.includeServer(), fluidTags);
	}
}