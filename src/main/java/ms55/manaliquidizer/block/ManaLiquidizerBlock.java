package ms55.manaliquidizer.block;

import javax.annotation.Nonnull;

import ms55.manaliquidizer.tile.ManaLiquidizerTile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.common.block.BlockMod;

public class ManaLiquidizerBlock extends BlockMod implements IWandable {

	public ManaLiquidizerBlock(Properties properties) {
		super(properties);
	}

	@Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

	@Nonnull
	@Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ManaLiquidizerTile();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
		if (!world.isRemote()) {
			TileEntity tile = world.getTileEntity(pos);

			if (tile instanceof ManaLiquidizerTile) {
				if (!player.isSneaking()) {
					ItemStack stack = player.getHeldItem(hand);
		            if (!stack.isEmpty() && FluidUtil.interactWithFluidHandler(player, hand, ((ManaLiquidizerTile) tile).getTank())) {
		                player.inventory.markDirty();
		                return ActionResultType.SUCCESS;
		            } else {
						player.openContainer((ManaLiquidizerTile) tile);
		            }
		        }
			}
		}

		return ActionResultType.SUCCESS;
	}

	@Override
	public boolean onUsedByWand(PlayerEntity player, ItemStack stack, World world, BlockPos pos, Direction side) {
		if (world.getTileEntity(pos) instanceof ManaLiquidizerTile) {
			((ManaLiquidizerTile) world.getTileEntity(pos)).onWanded(player);
		}
		return true;
	}
}