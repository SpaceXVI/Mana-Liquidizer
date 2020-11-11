package ms55.manaliquidizer.container;

import ms55.manaliquidizer.tile.ManaLiquidizerTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.IIntArray;

public class ManaLiquidizerContainer extends Container {
	public ManaLiquidizerTile tile;
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

    public int getCurrentMana() {
		return this.data.get(0);
	}

	public int getCurrentFluidMana() {
		return this.data.get(1);
	}

	public static Container create(int windowId, PlayerInventory playerInventory, ManaLiquidizerTile tile, IIntArray data) {
		return new ManaLiquidizerContainer(windowId, playerInventory, tile, data);
	}
}