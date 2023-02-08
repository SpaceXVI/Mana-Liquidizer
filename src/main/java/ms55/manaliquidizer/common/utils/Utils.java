package ms55.manaliquidizer.common.utils;

import net.minecraft.resources.ResourceLocation;

public class Utils {

	public static ResourceLocation getResourceLocation(String str) {
		return new ResourceLocation(str.substring(0, str.indexOf(":")), str.substring(str.indexOf(":") + 1));
	}
}
