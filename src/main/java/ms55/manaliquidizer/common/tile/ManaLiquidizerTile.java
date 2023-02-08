package ms55.manaliquidizer.common.tile;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import ms55.manaliquidizer.client.config.Config;
import ms55.manaliquidizer.common.fluid.ModFluids;
import ms55.manaliquidizer.common.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.block.IWandHUD;
import vazkii.botania.api.block.IWandable;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IKeyLocked;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.ManaBlockType;
import vazkii.botania.api.mana.ManaNetworkAction;
import vazkii.botania.api.mana.spark.IManaSpark;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.client.core.helper.RenderHelper;
import vazkii.botania.client.gui.HUDHandler;
import vazkii.botania.common.block.tile.TileMod;
import vazkii.botania.common.block.tile.mana.IThrottledPacket;
import vazkii.botania.common.handler.ManaNetworkHandler;
import vazkii.botania.common.item.ItemManaTablet;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.xplat.IXplatAbstractions;

public class ManaLiquidizerTile extends TileMod implements IFluidTank, IManaPool, IManaReceiver, ISparkAttachable, IWandable, IKeyLocked, IThrottledPacket {
	private int mana;
	public final int maxMana = 10000;
	private int ticks = 0;
	
	private boolean sendPacket;

	private Mode mode = Mode.TO_MANA_FLUID;

    protected FluidTank tank;
    
    private ConfigValue<String> fluidValue;
    private Fluid fluidActual;

    private String inputKey = "";
	private final String outputKey = "";

    private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

    private final double ratio = Config.GENERAL.ANTECEDENT.get() / Config.GENERAL.CONSEQUENT.get();

	public ManaLiquidizerTile(BlockPos pos, BlockState state) {
		super(ModTiles.MANA_LIQUIDIZER.get(), pos, state);

		fluidValue = Config.MISC.FLUID;
		fluidActual = ForgeRegistries.FLUIDS.getValue(Utils.getResourceLocation(fluidValue.get()));

		tank = new FluidTank(FluidAttributes.BUCKET_VOLUME * 10, (fluid) -> fluid.getFluid().isSame(fluidActual)); 
	}

	private void initManaCapAndNetwork() {
		if (!ManaNetworkHandler.instance.isPoolIn(level, this) && !isRemoved()) {
			IXplatAbstractions.INSTANCE.fireManaNetworkEvent(this, ManaBlockType.POOL, ManaNetworkAction.ADD);
		}
	}

	public static void clientTick(Level level, BlockPos worldPosition, BlockState state, ManaLiquidizerTile tile) {
		tile.initManaCapAndNetwork();
	}

	public static void serverTick(Level level, BlockPos worldPosition, BlockState state, ManaLiquidizerTile tile) {
		tile.initManaCapAndNetwork();

		if (tile.sendPacket && tile.ticks % 10 == 0) {
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(tile);
			tile.sendPacket = false;
		}

		// Mana is the antecedent and Fluid is the consequent
		// So if the ratio is 3 : 5, then 3000 mana * (3/5)
		// So fluid to mana = fluid / (5/3) = fluid * (5/3)
		if (tile.mode == Mode.TO_MANA_FLUID) {
			if (tile.mana > 0 && !(tile.tank.getFluidAmount() >= tile.tank.getCapacity())) {
				//If the onewayroute config is true and the way mode does NOT = manatofluidonly then do nothing
				if (Config.MISC.ISONEWAY.get() && !Config.MISC.WAY_MODE.get().equalsIgnoreCase("manaToFluidOnly")) {
					//Do nothing
				} else {
					//5600 mana = 5600 * (3/5) = 3360 fluid
					double amount = tile.mana * tile.ratio;
					if (tile.tank.getFluid().isEmpty()) {
						tile.tank.setFluid(new FluidStack(tile.fluidActual, Math.min((int) amount, tile.maxMana)));
					} else {
						tile.tank.getFluid().setAmount(Math.min(tile.tank.getFluidAmount() + (int) amount, tile.maxMana));
					}
					//Let's say you only gained 600 fluid from 1000 mana, then if you used the amount par then 600-600 = 0, so by that you lost 400 in the process which is not a good thing
					tile.mana = Math.max(tile.mana - tile.tank.getFluidAmount(), 0);
					level.sendBlockUpdated(tile.worldPosition, tile.getBlockState(), tile.getBlockState(), 2);
				}
			}
		} else {
			if (tile.tank.getFluidAmount() > 0 && !(tile.mana >= tile.maxMana)) {
				//If the onewayroute config is true and the way mode does NOT = fluidToManaOnly then do nothing
				if (Config.MISC.ISONEWAY.get() && !Config.MISC.WAY_MODE.get().equalsIgnoreCase("fluidToManaOnly")) {
					//Do nothing
				} else {
					//3360 fluid = 5600 * (5/3) = 5600 mana
					double amount = tile.tank.getFluid().getAmount() * tile.ratio;
					tile.mana = Math.min(tile.mana + (int) amount, tile.maxMana);
					//Let's say you only gained 600 mana from 1000 fluid, then if you used the amount par then 600-600 = 0, so by that you lost 400 in the process which is not a good thing
					tile.tank.getFluid().setAmount(Math.max(tile.tank.getFluidAmount() - tile.mana, 0));
					level.sendBlockUpdated(tile.worldPosition, tile.getBlockState(), tile.getBlockState(), 2);
				}
			}
		}

		tile.ticks++;
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		IXplatAbstractions.INSTANCE.fireManaNetworkEvent(this, ManaBlockType.POOL, ManaNetworkAction.REMOVE);
	}

	@Override
	public void writePacketNBT(CompoundTag tag) {
		super.writePacketNBT(tag);

		tag.putInt("mana", mana);
		tag.putString("mode", mode.text);
		tag.putString("inputKey", inputKey);
		tag.putString("outputKey", outputKey);

	    tank.writeToNBT(tag);
	}

	@Override
	public void readPacketNBT(CompoundTag tag) {
		super.readPacketNBT(tag);

		mana = tag.getInt("mana");
		mode = tag.getString("mode").equalsIgnoreCase(Mode.TO_MANA_FLUID.text) ? Mode.TO_MANA_FLUID : Mode.TO_MANA;

		if (tag.contains("inputKey")) {
			inputKey = tag.getString("inputKey");
		}
		if (tag.contains("outputKey")) {
			inputKey = tag.getString("outputKey");
		}

		tank.readFromNBT(tag);
	}

	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
	        return holder.cast();
		}
	    return super.getCapability(capability, facing);
	}

	public FluidTank getTank(){
	    return this.tank;
	}

	@Override
	public int getCurrentMana() {
		return mana;
	}

	public int getCurrentFluidMana() {
		return tank.getFluidAmount();
	}

	@Override
	public boolean isFull() {
		return mana >= maxMana;
	}

	@Override
	public void receiveMana(int mana) {
		this.mana = Math.min(this.mana + mana, maxMana);
		setChanged();
		markDispatchable();
	}

	@Override
	public void markDispatchable() {
		sendPacket = true;
	}

	@Override
	public boolean canReceiveManaFromBursts() {
		return !isFull();
	}

	public static int calculateComparatorLevel(int mana, int max) {
		int val = (int) ((double) mana / (double) max * 15.0);
		if (mana > 0) {
			val = Math.max(val, 1);
		}
		return val;
	}

	public static class WandHud implements IWandHUD {
		private final ManaLiquidizerTile pool;

		public WandHud(ManaLiquidizerTile pool) {
			this.pool = pool;
		}

		@Override
		public void renderHUD(PoseStack ms, Minecraft mc) {
			ItemStack bucket = new ItemStack(ModFluids.MANA_FLUID_BUCKET.get());

			ItemStack tablet = new ItemStack(ModItems.manaTablet);
			ItemManaTablet.setStackCreative(tablet);

			int color = 0x4444FF;
			int color2 = 0x1644ff;

			if (pool.mode == Mode.TO_MANA) {
				BotaniaAPIClient.instance().drawSimpleManaHUD(ms, color, pool.getCurrentMana(), pool.maxMana, "Mana");
			} else {
				BotaniaAPIClient.instance().drawSimpleManaHUD(ms, color2, pool.tank.getFluidAmount(), pool.tank.getCapacity(), "Mana Fluid");
			}

			int x = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 11;
			int y = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 30;

			int u = pool.mode == Mode.TO_MANA ? 22 : 0;
			int v = 38;

			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			RenderSystem.setShaderTexture(0, HUDHandler.manaBar);
			RenderHelper.drawTexturedModalRect(ms, x, y, u, v, 22, 15);
			RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

			mc.getItemRenderer().renderAndDecorateItem(tablet, x - 20, y);
			mc.getItemRenderer().renderAndDecorateItem(bucket, x + 26, y);

			RenderSystem.disableBlend();
		}
	}

	public static enum Mode {
		TO_MANA_FLUID("To Mana Fluid"),
		TO_MANA("To Mana");

		public String text;

		Mode(String text) {
			this.text = text;
		}
	}

	@Override
	public boolean isOutputtingPower() {
		return false;
	}

	@Override
	public DyeColor getColor() {
		return null;
	}

	@Override
	public void setColor(DyeColor color) { }

	@Override
	public FluidStack getFluid() {
		return tank.getFluid();
	}

	@Override
	public int getFluidAmount() {
		return tank.getFluidAmount();
	}

	@Override
	public int getCapacity() {
		return tank.getCapacity();
	}

	@Override
	public boolean isFluidValid(FluidStack stack) {
		return stack.getFluid().isSame(fluidActual);
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		if (tank.getCapacity() - tank.getFluidAmount() != 0) {
			if ((tank.getCapacity() - tank.getFluidAmount()) > resource.getAmount()) {
				//2000 available - 1000 fluid = 1000
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
				return (tank.getCapacity() - tank.getFluidAmount()) - resource.getAmount();
			} else {
				//2000 fluid - 1000 available = 1000
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
				return resource.getAmount() - (tank.getCapacity() - tank.getFluidAmount());
			}
		}

		return 0;
	}

	@Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !resource.isFluidEqual(tank.getFluid())) {
            return FluidStack.EMPTY;
        }

        return drain(resource.getAmount(), action);
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        int drained = maxDrain;
        if (tank.getFluid().getAmount() < drained) {
            drained = tank.getFluid().getAmount();
        }

        FluidStack stack = new FluidStack(tank.getFluid(), drained);
        if (action.execute() && drained > 0) {
        	tank.getFluid().shrink(drained);
			level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
        }

        return stack;
    }

	@Override
	public boolean onUsedByWand(Player player, ItemStack stack, Direction side) {
		if (player == null || player.isShiftKeyDown()) {
			mode = Mode.values()[mode.ordinal() == 0 ? 1 : 0];
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
			if (player != null && level.isClientSide) {
				player.sendMessage(new TranslatableComponent(mode.text), null);
			}
		}
		return true;
	}

	@Override
	public boolean canAttachSpark(ItemStack stack) {
		return true;
	}

	@Override
	public int getAvailableSpaceForMana() {
		return Math.max(0, maxMana - getCurrentMana());
	}

	@Override
	public IManaSpark getAttachedSpark() {
		List<Entity> sparks = level.getEntitiesOfClass(Entity.class, new AABB(worldPosition.above(), worldPosition.above().offset(1, 1, 1)), Predicates.instanceOf(IManaSpark.class));
		if (sparks.size() == 1) {
			Entity e = sparks.get(0);
			return (IManaSpark) e;
		}

		return null;
	}

	@Override
	public boolean areIncomingTranfersDone() {
		return false;
	}

	@Override
	public Level getManaReceiverLevel() {
		return getLevel();
	}

	@Override
	public BlockPos getManaReceiverPos() {
		return getBlockPos();
	}

	@Override
	public String getInputKey() {
		return inputKey;
	}

	@Override
	public String getOutputKey() {
		return outputKey;
	}
}