package ms55.manaliquidizer.common.utils;

import net.minecraft.util.ResourceLocation;

public class Utils {

	public static ResourceLocation getResourceLocation(String str) {
		return new ResourceLocation(str.substring(0, str.indexOf(":")), str.substring(str.indexOf(":") + 1));
	}
}
