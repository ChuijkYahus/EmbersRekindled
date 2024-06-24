package com.rekindled.embers.block;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.rekindled.embers.api.block.IDial;
import com.rekindled.embers.api.tile.IExtraDialInformation;
import com.rekindled.embers.network.PacketHandler;
import com.rekindled.embers.network.message.MessageDialUpdateRequest;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class DialBaseBlock extends DirectionalBlock implements IDial, EntityBlock, SimpleWaterloggedBlock {

	protected static final VoxelShape UP_AABB = Shapes.box(0.3125,0,0.3125,0.6875,0.125,0.6875);
	protected static final VoxelShape DOWN_AABB = Shapes.box(0.3125,0.875,0.3125,0.6875,1.0,0.6875);
	protected static final VoxelShape NORTH_AABB = Shapes.box(0.3125,0.3125,0.875,0.6875,0.6875,1.0);
	protected static final VoxelShape SOUTH_AABB = Shapes.box(0.3125,0.3125,0,0.6875,0.6875,0.125);
	protected static final VoxelShape WEST_AABB = Shapes.box(0.875,0.3125,0.3125,1.0,0.6875,0.6875);
	protected static final VoxelShape EAST_AABB = Shapes.box(0.0,0.3125,0.3125,0.125,0.6875,0.6875);

	public DialBaseBlock(Properties pProperties) {
		super(pProperties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.POWER, 0).setValue(BlockStateProperties.WATERLOGGED, false));
	}

	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		switch (pState.getValue(FACING)) {
		case UP:
			return UP_AABB;
		case DOWN:
			return DOWN_AABB;
		case EAST:
			return EAST_AABB;
		case WEST:
			return WEST_AABB;
		case SOUTH:
			return SOUTH_AABB;
		case NORTH:
		default:
			return NORTH_AABB;
		}
	}

	//okay this is jank but I don't see how this could possibly go wrong
	@Override
	public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
		if (state.hasAnalogOutputSignal()) {
			BlockEntity blockEntity = level.getBlockEntity(pos.relative(state.getValue(FACING), -1));
			if (blockEntity != null && blockEntity.hasLevel()) {
				int power = state.getAnalogOutputSignal(blockEntity.getLevel(), pos);
				if (state.getValue(BlockStateProperties.POWER) != power) {
					blockEntity.getLevel().setBlock(pos, state.setValue(BlockStateProperties.POWER, power), 1);
					blockEntity.getLevel().updateNeighbourForOutputSignal(pos, state.getBlock());
				}
			}
		}
	}

	@Override
	public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
		return canAttach(pLevel, pPos, pState.getValue(FACING).getOpposite());
	}

	public static boolean canAttach(LevelReader pReader, BlockPos pPos, Direction pDirection) {
		return !pReader.getBlockState(pPos.relative(pDirection)).isAir();
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext) {
		for(Direction direction : pContext.getNearestLookingDirections()) {
			BlockState blockstate = this.defaultBlockState().setValue(FACING, direction.getOpposite());
			if (blockstate.canSurvive(pContext.getLevel(), pContext.getClickedPos())) {
				return blockstate.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(pContext.getLevel().getFluidState(pContext.getClickedPos()).getType() == Fluids.WATER));
			}
		}
		return null;
	}

	@Override
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		if (pState.getValue(BlockStateProperties.WATERLOGGED)) {
			pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
		}
		return pState.getValue(FACING).getOpposite() == pFacing && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
	}

	@Override
	public BlockState rotate(BlockState pState, Rotation pRot) {
		return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState pState, Mirror pMirror) {
		return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(FACING).add(BlockStateProperties.POWER).add(BlockStateProperties.WATERLOGGED);
	}

	@Override
	public List<Component> getDisplayInfo(Level world, BlockPos pos, BlockState state, int maxLines) {
		ArrayList<Component> text = new ArrayList<Component>();
		BlockEntity tileEntity = world.getBlockEntity(pos);
		if (tileEntity != null) {
			Direction facing = state.getValue(FACING);
			getBEData(facing, text, tileEntity, maxLines);
			if (world.getBlockEntity(pos.relative(facing, -1)) instanceof IExtraDialInformation facingTile)
				facingTile.addDialInformation(facing, text, getDialType());
		}
		return text;
	}

	protected abstract void getBEData(Direction facing, ArrayList<Component> text, BlockEntity blockEntity, int maxLines);

	@Override
	public void updateBEData(BlockPos pos, int maxLines) {
		PacketHandler.INSTANCE.sendToServer(new MessageDialUpdateRequest(pos, maxLines));
	}

	@Override
	public FluidState getFluidState(BlockState pState) {
		return pState.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
	}
}
