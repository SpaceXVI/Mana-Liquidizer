package ms55.manaliquidizer.client.config;

import ms55.manaliquidizer.ManaLiquidizer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
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
			builder.push("Mana to Fluid ratio (Mana -> First Number, Fluid -> Second Number)")
				.comment("Keep both of these values 1 if you want the trasnformation to be lossless, change if you want otherwise.");

			ANTECEDENT = builder
				.comment("The first number of the mana to fluid ratio, indicates the mana")
				.defineInRange("ratio_antecedent", 1.0, 1.0, Double.MAX_VALUE);

			CONSEQUENT = builder
					.comment("The second number of the mana to fluid ratio, indicates the fluid")
					.defineInRange("ratio_consequent", 1.0, 1.0, Double.MAX_VALUE);

			builder.pop();
		}
	}

	public static class Misc {
		public final IntValue HEX_CODE;
		public final ConfigValue<String> FLUID;

		public final BooleanValue ISONEWAY;
		public final ConfigValue<String> WAY_MODE;

		public Misc(ForgeConfigSpec.Builder builder) {
			builder.push("Misc configs")
				.comment("Basically stuff that will mostly never get used but nice to have!");

			HEX_CODE = builder
				.comment("(DOESN'T WORK YET) Please, do NOT be a dummy while changing this, use a site to convert an 8 digit hexcode to an int, for example this (Take the decimal number) : https://www.rapidtables.com/convert/number/hex-to-decimal.html")
				.defineInRange("manaFluidHexCode", 0xFF1080FF, Integer.MIN_VALUE, Integer.MAX_VALUE);

			FLUID = builder
				.comment("Change this to change the fluid outputted from the mana liquidizer and/or needed to be inputted to make the mana")
				.define("manaFluidReplacement", "manaliquidizer:mana_fluid");

			ISONEWAY = builder
				.comment("Set this to true if you want a one way route of the mana liquidizer, i.e converting mana to fluid, but not back. Please change oneWayMode to make the route work the way you want")
				.define("isOneWayOnly", false);

			WAY_MODE = builder
					.comment("(Requires isOneWayOnly to be true), Change this to fluidToManaOnly to allow conversion from fluid to mana ONLY, or keep it manaToFluidOnly for the opposite")
					.define("oneWayMode", "manaToFluidOnly");

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