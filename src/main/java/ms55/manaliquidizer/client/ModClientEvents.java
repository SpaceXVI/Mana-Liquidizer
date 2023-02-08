package ms55.manaliquidizer.client;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import ms55.manaliquidizer.ManaLiquidizer;
import ms55.manaliquidizer.common.tile.ModTiles;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import vazkii.botania.api.BotaniaForgeClientCapabilities;
import vazkii.botania.api.block.IWandHUD;
import vazkii.botania.forge.CapabilityUtil;

@Mod.EventBusSubscriber(modid = ManaLiquidizer.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {
	private static final Supplier<Map<BlockEntityType<?>, Function<BlockEntity, IWandHUD>>> WAND_HUD = Suppliers.memoize(() -> {
		var ret = new IdentityHashMap<BlockEntityType<?>, Function<BlockEntity, IWandHUD>>();
		ModTiles.registerWandHudCaps((factory, types) -> {
			for (var type : types) {
				ret.put(type, factory);
			}
		});
		return Collections.unmodifiableMap(ret);
	});

	@SubscribeEvent
	public static void clientInit(FMLClientSetupEvent evt) {
		MinecraftForge.EVENT_BUS.addGenericListener(BlockEntity.class, ModClientEvents::attachBeCapabilities);
	}

	private static void attachBeCapabilities(AttachCapabilitiesEvent<BlockEntity> event) {
		var be = event.getObject();

		var makeWandHud = WAND_HUD.get().get(be.getType());
		if (makeWandHud != null) {
			event.addCapability(prefix("wand_hud"), CapabilityUtil.makeProvider(BotaniaForgeClientCapabilities.WAND_HUD, makeWandHud.apply(be)));
			
		}
	}
}
