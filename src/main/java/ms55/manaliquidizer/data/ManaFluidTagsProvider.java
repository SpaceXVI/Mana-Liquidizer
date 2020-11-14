package ms55.manaliquidizer.data;

import ms55.manaliquidizer.ManaLiquidizer;
import ms55.manaliquidizer.fluid.ModFluids;
import ms55.manaliquidizer.tags.ManaTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ManaFluidTagsProvider extends FluidTagsProvider {

	public ManaFluidTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
		super(generatorIn, ManaLiquidizer.MODID, existingFileHelper);
	}

	@Override
	protected void registerTags() {
		getOrCreateBuilder(ManaTags.MANA_FLUID).add(ModFluids.MANA_FLUID.get(), ModFluids.MANA_FLUID_FLOWING.get());
	}
}