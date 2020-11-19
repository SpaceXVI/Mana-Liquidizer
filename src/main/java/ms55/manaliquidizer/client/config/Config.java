package ms55.manaliquidizer.client.config;

import ms55.manaliquidizer.ManaLiquidizer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
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

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final General GENERAL;

	static {
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		GENERAL = new General(builder);

		COMMON_SPEC = builder.build();
	}
}