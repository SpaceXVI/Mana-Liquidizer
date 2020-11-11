package ms55.manaliquidizer.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ms55.manaliquidizer.container.ManaLiquidizerContainer;
import ms55.manaliquidizer.fluid.ModFluids;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.ManaNetworkEvent;
import vazkii.botania.common.block.tile.TileMod;
import vazkii.botania.common.core.handler.ManaNetworkHandler;

public class ManaLiquidizerTile extends TileMod implements IFluidTank, IManaPool, IManaReceiver, INamedContainerProvider, ITickableTileEntity {
	private int mana;
	private int maxMana = 10000;

	private Mode mode = Mode.TO_MANA_FLUID;

    protected FluidTank tank = new FluidTank(FluidAttributes.BUCKET_VOLUME * 10);

    private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

    protected IIntArray data = new IIntArray() {
		@Override
		public int get(int i) {
			switch (i) {
				case 0: return ManaLiquidizerTile.this.getCurrentMana();
				case 1: return ManaLiquidizerTile.this.getCurrentFluidMana();
				default: return 0;
			}
		}

		@Override
		public void set(int i, int value) { }

		@Override
		public int size() {
			return 2;
		}
	};

	public ManaLiquidizerTile() {
		super(ModTiles.MANA_LIQUIDIZER.get());
		
		this.onChunkUnloaded();
	}

	@Override
	public void tick() {
		if (!ManaNetworkHandler.instance.isPoolIn(this) && !isRemoved()) {
			ManaNetworkEvent.addPool(this);
		}

		if (mode == Mode.TO_MANA_FLUID) {
			if (mana > 0 && !(tank.getFluidAmount() >= tank.getCapacity())) {
				//5000 mana fluid / 1000 mana, 6000
				//1000 - 5000 - 4000
				if (tank.getFluid().isEmpty()) {
					tank.setFluid(new FluidStack(ModFluids.MANA_FLUID.get().getFluid(), Math.min(mana, maxMana)));
				} else {
					tank.getFluid().setAmount(Math.min(tank.getFluidAmount() + mana, maxMana));
				}
				mana = Math.max(mana - tank.getFluidAmount(), 0); //Also the same as making mana = 0
				world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
			}
		} else {
			if (tank.getFluidAmount() > 0 && !(mana >= maxMana)) {
				//5000 mana / 1000 mana fluid, 6000
				//1000 - 5000 - 4000
				mana = Math.min(mana + tank.getFluid().getAmount(), maxMana);
				tank.getFluid().setAmount(Math.max(tank.getFluidAmount() - mana, 0)); //Also the same as making manaFluid = 0
				world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
			}
		}
	}

	@Override
    protected void invalidateCaps() {
		super.invalidateCaps();
		ManaNetworkEvent.removePool(this);
    }

	@Override
    public void onChunkUnloaded() {
		super.onChunkUnloaded();
		ManaNetworkEvent.removePool(this);
    }

	@Override
	public void writePacketNBT(CompoundNBT tag) {
		super.writePacketNBT(tag);

		tag.putInt("mana", mana);

	    tank.writeToNBT(tag);
	}

	@Override
	public void readPacketNBT(CompoundNBT tag) {
		super.readPacketNBT(tag);

		mana = tag.getInt("mana");

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

	public void onWanded(PlayerEntity player) {
		if (player == null || player.isSneaking()) {
			mode = Mode.values()[mode.ordinal() == 0 ? 1 : 0];
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
			if (player != null && world.isRemote) {
				player.sendMessage(new TranslationTextComponent(mode.text), null);
			}
		}
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
		markDirty();
		world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
	}

	@Override
	public boolean canReceiveManaFromBursts() {
		return !isFull() && mode == Mode.TO_MANA_FLUID;
	}

	@Override
	public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
		return ManaLiquidizerContainer.create(windowId, playerInventory, this, data);
	}

	public boolean isUsableByPlayer(PlayerEntity player) {
        BlockPos pos = this.getPos();
        return player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }

	@Override
	public ITextComponent getDisplayName() {
		return (ITextComponent) new TranslationTextComponent("container.mana_liquidizer");
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
		return stack.getFluid().isEquivalentTo(ModFluids.MANA_FLUID.get().getFluid());
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		if (tank.getCapacity() - tank.getFluidAmount() != 0) {
			if ((tank.getCapacity() - tank.getFluidAmount()) > resource.getAmount()) {
				//2000 available - 1000 fluid = 1000
				return (tank.getCapacity() - tank.getFluidAmount()) - resource.getAmount();
			} else {
				//2000 fluid - 1000 available = 1000
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
			world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
        }

        return stack;
    }
}