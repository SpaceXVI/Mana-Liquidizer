package ms55.manaliquidizer.common.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class ManaTags {
	public static final TagKey<Fluid> MANA_FLUID = forgeTag("mana_fluid");

    private static TagKey<Fluid> forgeTag(String name) {
        return FluidTags.create(new ResourceLocation("forge", name));
    }
}