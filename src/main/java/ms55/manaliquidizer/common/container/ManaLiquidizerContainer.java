package ms55.manaliquidizer.common.container;

public class ManaLiquidizerContainer /*extends Container*/ {
	/*public ManaLiquidizerTile tile;
	private final IIntArray data;

	protected ManaLiquidizerContainer(int windowId, PlayerInventory playerInventory, ManaLiquidizerTile tile, IIntArray data) {
		super(ModContainers.MANA_LIQUIDIZER.get(), windowId);

		this.tile = tile;
		this.data = data;

		int i, j;

		for (i = 0; i < 3; i++) {
			for (j = 0; j < 9; j++) {
	            this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (j = 0; j < 9; j++) {
	         this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 142));
		}

		this.trackIntArray(data);
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return this.tile.isUsableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int index) {
	      ItemStack itemstack = ItemStack.EMPTY;
	      Slot slot = this.inventorySlots.get(index);
	      if (slot != null && slot.getHasStack()) {
	         ItemStack itemstack1 = slot.getStack();
	         itemstack = itemstack1.copy();
	         if (index < player.container.getSize()) {
	            if (!this.mergeItemStack(itemstack1, player.container.getSize(), this.inventorySlots.size(), true)) {
	               return ItemStack.EMPTY;
	            }
	         } else if (!this.mergeItemStack(itemstack1, 0, player.container.getSize(), false)) {
	            return ItemStack.EMPTY;
	         }

	         if (itemstack1.isEmpty()) {
	            slot.putStack(ItemStack.EMPTY);
	         } else {
	            slot.onSlotChanged();
	         }
	      }

	      return itemstack;
	   }

    public int getCurrentMana() {
		return this.data.get(0);
	}

	public int getCurrentFluidMana() {
		return this.data.get(1);
	}

	public static Container create(int windowId, PlayerInventory playerInventory, ManaLiquidizerTile tile, IIntArray data) {
		return new ManaLiquidizerContainer(windowId, playerInventory, tile, data);
	}*/
}