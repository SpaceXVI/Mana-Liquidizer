package ms55.manaliquidizer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ManaItemGroup extends CreativeModeTab {
	public ManaItemGroup() {
		super(ManaLiquidizer.MODID);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ManaLiquidizer.MODID, "mana_liquidizer")));
	}
}