package ms55.manaliquidizer.data;

import ms55.manaliquidizer.ManaLiquidizer;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = ManaLiquidizer.MODID, bus = Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

		if(event.includeServer()) {
			ManaFluidTagsProvider fluidTags = new ManaFluidTagsProvider(gen, helper);

			gen.addProvider(fluidTags);
		}
	}
}