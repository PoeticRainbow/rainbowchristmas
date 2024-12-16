package poeticrainbow.rainbowchristmas.registry.block;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
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

    public StrungDecorationBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(NORTH, true)
                .with(EAST, true)
                .with(SOUTH, true)
                .with(WEST, true));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(NORTH);
        builder.add(EAST);
        builder.add(SOUTH);
        builder.add(WEST);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        // If the update came from a different height then ignore
        if (pos.getY() != neighborPos.getY() || neighborState.getBlock() instanceof StrungDecorationBlock) return state;
        if (world instanceof World worldWorld) {
            return getStateFromNeighbors(worldWorld, pos);
        } else {
            return state;
        }
    }

    public BlockState getStateFromNeighbors(World world, BlockPos pos) {
        BlockState allWallsState = super.getDefaultState();
        BlockState hangingState = allWallsState.with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false);

        if (world.getBlockState(pos) == hangingState) {
            return hangingState;
        }

        // If neighbor has shape that touches the edges of the block
        VoxelShape slabShape = VoxelShapes.cuboid(0, 0, 0, 1, 0.5, 1);
        if (!VoxelShapes.isSideCovered(slabShape, world.getBlockState(pos.north()).getCollisionShape(world, pos), Direction.SOUTH))
            allWallsState = allWallsState.with(NORTH, false);
        if (!VoxelShapes.isSideCovered(slabShape, world.getBlockState(pos.east()).getCollisionShape(world, pos), Direction.WEST))
            allWallsState = allWallsState.with(EAST, false);
        if (!VoxelShapes.isSideCovered(slabShape, world.getBlockState(pos.south()).getCollisionShape(world, pos), Direction.NORTH))
            allWallsState = allWallsState.with(SOUTH, false);
        if (!VoxelShapes.isSideCovered(slabShape, world.getBlockState(pos.west()).getCollisionShape(world, pos), Direction.EAST))
            allWallsState = allWallsState.with(WEST, false);

        return allWallsState;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();
        if (state.get(NORTH)) shape = VoxelShapes.combine(shape, NORTH_SHAPE, BooleanBiFunction.OR);
        if (state.get(EAST)) shape = VoxelShapes.combine(shape, EAST_SHAPE, BooleanBiFunction.OR);
        if (state.get(SOUTH)) shape = VoxelShapes.combine(shape, SOUTH_SHAPE, BooleanBiFunction.OR);
        if (state.get(WEST)) shape = VoxelShapes.combine(shape, WEST_SHAPE, BooleanBiFunction.OR);
        return shape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            ItemStack itemInHand = player.getStackInHand(hand);
            if (itemInHand.isIn(ConventionalItemTags.SHEAR_TOOLS)) {
                BlockState newState = state;
                Direction side = BlockInteractionUtil.getDirectionOfClosestSideFromHitPosition(hit.getPos(), pos);
                switch (side) {
                    case NORTH -> newState = newState.with(SOUTH, false);
                    case EAST -> newState = newState.with(WEST, false);
                    case SOUTH -> newState = newState.with(NORTH, false);
                    case WEST -> newState = newState.with(EAST, false);
                    default -> {
                        return ActionResult.FAIL;
                    }
                }
                if (!player.isCreative()) itemInHand.damage(1, player);
                world.setBlockState(pos, newState);
                player.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0f, 1.0f);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if (ctx.getSide() == Direction.DOWN || ctx.getSide() == Direction.UP) {
            return getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false);
        }
        return getStateFromNeighbors(ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.getBlock() instanceof StrungDecorationBlock) {
            boolean isStateHanging = !(state.get(NORTH) || state.get(EAST) || state.get(SOUTH) || state.get(WEST));
            boolean isStateFromHanging = !(stateFrom.get(NORTH) || stateFrom.get(EAST) || stateFrom.get(SOUTH) || stateFrom.get(WEST));
            // if hanging states match, then hide
            return isStateHanging == isStateFromHanging;
        }
        return false;
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
            case FRONT_BACK -> state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
            default -> state;
        };
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_90 -> state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
            case CLOCKWISE_180 -> state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
            case COUNTERCLOCKWISE_90 -> state.with(NORTH, state.get(EAST)).with(WEST, state.get(NORTH)).with(SOUTH, state.get(WEST)).with(EAST, state.get(SOUTH));
            default -> state;
        };
    }

    static {
        NORTH = BooleanProperty.of("north");
        EAST = BooleanProperty.of("east");
        SOUTH = BooleanProperty.of("south");
        WEST = BooleanProperty.of("west");

        NORTH_SHAPE = VoxelShapes.cuboid(0, 0, 0, 1, 1, 0.0625);
        EAST_SHAPE = VoxelShapes.cuboid(0.9375, 0, 0, 1, 1, 1);
        SOUTH_SHAPE = VoxelShapes.cuboid(0, 0, 0.9375, 1, 1, 1);
        WEST_SHAPE = VoxelShapes.cuboid(0, 0, 0, 0.0625, 1, 1);
        ALL_SHAPE = VoxelShapes.combine(
                VoxelShapes.combine(NORTH_SHAPE, EAST_SHAPE, BooleanBiFunction.OR),
                VoxelShapes.combine(SOUTH_SHAPE, WEST_SHAPE, BooleanBiFunction.OR),
                BooleanBiFunction.OR);
    }
}
