package ms55.manaliquidizer.client.config;

import ms55.manaliquidizer.ManaLiquidizer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ManaLiquidizer.MODID, bus = Bus.MOD)
public class Config {
	public static class General {
		public final DoubleValue ANTECEDENT;
		public final DoubleValue CONSEQUENT;

		public General(ForgeConfigSpec.Builder builder) {
			builder.push("Mana to Fluid (Mana -> Antecedent, Fluid -> Consequent)")
				.comment("To make this ratio useful, or lossless, when switching from mana to fluid, or vice-versa, the value will be multiplied by the ratio given, "
					   + "instead of dividing by it when switching from fluid to mana "
					   + "(which will cause confusions with pack-makers) (when you turn the lower value to the higher value you divide, the opposite applies to higher to lower)"
					   + ", but remember, it's all up to your knowledge of how ratios work, to get lossless values make the antecedent smaller than the consequent");

			ANTECEDENT = builder
				.comment("The antecedent of the mana to fluid ratio, "
					   + "this ratio WILL be simplified, so if you set this to 1000 and the other to 100 it will act as a 10 : 1 ratio")
				.defineInRange("ratio_antecedent", 1.0, 1.0, Double.MAX_VALUE);

			CONSEQUENT = builder
					.comment("The consequent of the mana to fluid ratio, "
						   + "this ratio WILL be simplified, so if you set this to 1000 and the other to 100 it will act as a 1 : 10 ratio")
					.defineInRange("ratio_consequent", 1.0, 1.0, Double.MAX_VALUE);

			builder.pop();
		}
	}

	public static class Misc {
		public final IntValue HEX_CODE;
		public final ConfigValue<String> FLUID;

		public Misc(ForgeConfigSpec.Builder builder) {
			builder.push("Misc configs")
				.comment("Basically stuff that will mostly never get used but nice to have!");

			HEX_CODE = builder
				.comment("(DOESN'T WORK YET) Please, do NOT be a dummy while changing this, use a site to convert an 8 digit hexcode to an int, for example this (Take the decimal number) : https://www.rapidtables.com/convert/number/hex-to-decimal.html")
				.defineInRange("manaFluidHexCode", 0xFF1080FF, Integer.MIN_VALUE, Integer.MAX_VALUE); //0xFFFFFFFF, 0x00000000);

			FLUID = builder
				.comment("Change this to change the fluid outputted from the mana liquidizer and/or needed to be inputted to make the mana")
				.define("manaFluidReplacement", "manaliquidizer:mana_fluid");
			
			builder.pop();
		}
	}

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final General GENERAL;
	public static final Misc MISC;

	static {
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		GENERAL = new General(builder);
		MISC = new Misc(builder);

		COMMON_SPEC = builder.build();
	}
}