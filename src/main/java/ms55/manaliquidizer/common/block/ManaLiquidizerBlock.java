package ms55.manaliquidizer.common.block;

import javax.annotation.Nullable;

import ms55.manaliquidizer.common.tile.ManaLiquidizerTile;
import ms55.manaliquidizer.common.tile.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidUtil;
import vazkii.botania.common.block.BlockMod;

public class ManaLiquidizerBlock extends BlockMod implements EntityBlock {

	public ManaLiquidizerBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if (!level.isClientSide()) {
			BlockEntity be = level.getBlockEntity(pos);

			if (be instanceof ManaLiquidizerTile) {
				if (!player.isCrouching()) {
					ItemStack stack = player.getItemInHand(hand);
		            if (!stack.isEmpty() && FluidUtil.interactWithFluidHandler(player, hand, ((ManaLiquidizerTile) be).getTank())) {
		                player.getInventory().setChanged();
		                level.sendBlockUpdated(be.getBlockPos(), be.getBlockState(), be.getBlockState(), 2);
		                return InteractionResult.SUCCESS;
		            }
		        }
			}
		}

		return InteractionResult.SUCCESS;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ManaLiquidizerTile(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, ModTiles.MANA_LIQUIDIZER.get(), level.isClientSide ? ManaLiquidizerTile::clientTick : ManaLiquidizerTile::serverTick);
	}

//	@Override
//	public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
//		ManaLiquidizerTile liquidizer = (ManaLiquidizerTile) world.getBlockEntity(pos);
//		return ManaLiquidizerTile.calculateComparatorLevel(liquidizer.getCurrentMana(), liquidizer.maxMana);
//	}
}