package ms55.manaliquidizer.common.data;

import ms55.manaliquidizer.ManaLiquidizer;
import ms55.manaliquidizer.common.fluid.ModFluids;
import ms55.manaliquidizer.common.tags.ManaTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ManaFluidTagsProvider extends FluidTagsProvider {
	public ManaFluidTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
		super(generatorIn, ManaLiquidizer.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		tag(ManaTags.MANA_FLUID).add(ModFluids.MANA_FLUID_STILL.get(), ModFluids.MANA_FLUID_FLOWING.get());
	}
}