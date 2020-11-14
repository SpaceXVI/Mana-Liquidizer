package ms55.manaliquidizer.tags;

import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag.INamedTag;

public class ManaTags {
	public static final INamedTag<Fluid> MANA_FLUID = forgeTag("mana_fluid");

    private static INamedTag<Fluid> forgeTag(String name) {
    	System.out.println("forge" + name);
        return FluidTags.makeWrapperTag("forge:" + name);
    }
}