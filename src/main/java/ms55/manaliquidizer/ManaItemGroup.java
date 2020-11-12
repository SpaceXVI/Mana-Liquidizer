package ms55.manaliquidizer;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ManaItemGroup extends ItemGroup {
	public ManaItemGroup() {
		super(ManaLiquidizer.MODID);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ManaLiquidizer.MODID, "mana_liquidizer")));
	}
}