package poeticrainbow.rainbowchristmas.registry.block;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import poeticrainbow.rainbowchristmas.util.BlockInteractionUtil;

public class StrungDecorationBlock extends Block {
    public static final BooleanProperty NORTH;
    public static final BooleanProperty EAST;
    public static final BooleanProperty SOUTH;
    public static final BooleanProperty WEST;

    public static final VoxelShape NORTH_SHAPE;
    public static final VoxelShape EAST_SHAPE;
    public static final VoxelShape SOUTH_SHAPE;
    public static final VoxelShape WEST_SHAPE;
    public static final VoxelShape ALL_SHAPE;

    public StrungDecorationBlock(Properties settings) {
        super(settings);
        registerDefaultState(getStateDefinition().any()
                .setValue(NORTH, true)
                .setValue(EAST, true)
                .setValue(SOUTH, true)
                .setValue(WEST, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH);
        builder.add(EAST);
        builder.add(SOUTH);
        builder.add(WEST);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        // If the update came from a different height then ignore
        if (pos.getY() != neighborPos.getY() || neighborState.getBlock() instanceof StrungDecorationBlock) return state;
        if (world instanceof Level worldWorld) {
            return getStateFromNeighbors(worldWorld, pos);
        } else {
            return state;
        }
    }

    public BlockState getStateFromNeighbors(Level world, BlockPos pos) {
        BlockState allWallsState = super.defaultBlockState();
        BlockState hangingState = allWallsState.setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false);

        if (world.getBlockState(pos) == hangingState) {
            return hangingState;
        }

        // If neighbor has shape that touches the edges of the block
        VoxelShape slabShape = Shapes.box(0, 0, 0, 1, 0.5, 1);
        if (!Shapes.blockOccludes(slabShape, world.getBlockState(pos.north()).getCollisionShape(world, pos), Direction.SOUTH))
            allWallsState = allWallsState.setValue(NORTH, false);
        if (!Shapes.blockOccludes(slabShape, world.getBlockState(pos.east()).getCollisionShape(world, pos), Direction.WEST))
            allWallsState = allWallsState.setValue(EAST, false);
        if (!Shapes.blockOccludes(slabShape, world.getBlockState(pos.south()).getCollisionShape(world, pos), Direction.NORTH))
            allWallsState = allWallsState.setValue(SOUTH, false);
        if (!Shapes.blockOccludes(slabShape, world.getBlockState(pos.west()).getCollisionShape(world, pos), Direction.EAST))
            allWallsState = allWallsState.setValue(WEST, false);

        return allWallsState;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Shapes.empty();
        if (state.getValue(NORTH)) shape = Shapes.joinUnoptimized(shape, NORTH_SHAPE, BooleanOp.OR);
        if (state.getValue(EAST)) shape = Shapes.joinUnoptimized(shape, EAST_SHAPE, BooleanOp.OR);
        if (state.getValue(SOUTH)) shape = Shapes.joinUnoptimized(shape, SOUTH_SHAPE, BooleanOp.OR);
        if (state.getValue(WEST)) shape = Shapes.joinUnoptimized(shape, WEST_SHAPE, BooleanOp.OR);
        return shape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0f;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide()) {
            ItemStack itemInHand = player.getItemInHand(hand);
            if (itemInHand.is(ConventionalItemTags.SHEAR_TOOLS)) {
                BlockState newState = state;
                Direction side = BlockInteractionUtil.getDirectionOfClosestSideFromHitPosition(hit.getLocation(), pos);
                switch (side) {
                    case NORTH -> newState = newState.setValue(SOUTH, false);
                    case EAST -> newState = newState.setValue(WEST, false);
                    case SOUTH -> newState = newState.setValue(NORTH, false);
                    case WEST -> newState = newState.setValue(EAST, false);
                    default -> {
                        return InteractionResult.FAIL;
                    }
                }
                if (!player.isCreative()) itemInHand.hurtWithoutBreaking(1, player);
                world.setBlockAndUpdate(pos, newState);
                player.playSound(SoundEvents.SHEEP_SHEAR, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        if (ctx.getClickedFace() == Direction.DOWN || ctx.getClickedFace() == Direction.UP) {
            return defaultBlockState().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false);
        }
        return getStateFromNeighbors(ctx.getLevel(), ctx.getClickedPos());
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.getBlock() instanceof StrungDecorationBlock) {
            boolean isStateHanging = !(state.getValue(NORTH) || state.getValue(EAST) || state.getValue(SOUTH) || state.getValue(WEST));
            boolean isStateFromHanging = !(stateFrom.getValue(NORTH) || stateFrom.getValue(EAST) || stateFrom.getValue(SOUTH) || stateFrom.getValue(WEST));
            // if hanging states match, then hide
            return isStateHanging == isStateFromHanging;
        }
        return false;
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
            case FRONT_BACK -> state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
            default -> state;
        };
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_90 -> state.setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH));
            case CLOCKWISE_180 -> state.setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90 -> state.setValue(NORTH, state.getValue(EAST)).setValue(WEST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(WEST)).setValue(EAST, state.getValue(SOUTH));
            default -> state;
        };
    }

    static {
        NORTH = BooleanProperty.create("north");
        EAST = BooleanProperty.create("east");
        SOUTH = BooleanProperty.create("south");
        WEST = BooleanProperty.create("west");

        NORTH_SHAPE = Shapes.box(0, 0, 0, 1, 1, 0.0625);
        EAST_SHAPE = Shapes.box(0.9375, 0, 0, 1, 1, 1);
        SOUTH_SHAPE = Shapes.box(0, 0, 0.9375, 1, 1, 1);
        WEST_SHAPE = Shapes.box(0, 0, 0, 0.0625, 1, 1);
        ALL_SHAPE = Shapes.joinUnoptimized(
                Shapes.joinUnoptimized(NORTH_SHAPE, EAST_SHAPE, BooleanOp.OR),
                Shapes.joinUnoptimized(SOUTH_SHAPE, WEST_SHAPE, BooleanOp.OR),
                BooleanOp.OR);
    }
}
