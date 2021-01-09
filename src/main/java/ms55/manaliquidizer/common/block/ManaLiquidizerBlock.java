package ms55.manaliquidizer.common.block;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import ms55.manaliquidizer.common.tile.ManaLiquidizerTile;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidUtil;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.common.block.BlockMod;

public class ManaLiquidizerBlock extends BlockMod implements IWandable, IWandHUD {

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
		                world.notifyBlockUpdate(tile.getPos(), tile.getBlockState(), tile.getBlockState(), 2);
		                return ActionResultType.SUCCESS;
		            }
		        }
			}
		}

		return ActionResultType.SUCCESS;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void renderHUD(MatrixStack ms, Minecraft mc, World world, BlockPos pos) {
		((ManaLiquidizerTile) world.getTileEntity(pos)).renderHUD(ms, mc);
	}

	@Override
	public boolean onUsedByWand(PlayerEntity player, ItemStack stack, World world, BlockPos pos, Direction side) {
		if (world.getTileEntity(pos) instanceof ManaLiquidizerTile) {
			((ManaLiquidizerTile) world.getTileEntity(pos)).onWanded(player);
		}
		return true;
	}
}