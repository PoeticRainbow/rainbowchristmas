package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class NorthPoleBlock extends Block {
    public static EnumProperty<PoleShape> POLE_SHAPE;
    public static VoxelShape BOTTOM_SHAPE;
    public static VoxelShape MIDDLE_SHAPE;
    public static VoxelShape TOP_SHAPE;
    public static VoxelShape FULL_SHAPE;

    public NorthPoleBlock(Settings settings) {
        super(settings);
        getStateManager().getDefaultState().with(POLE_SHAPE, PoleShape.FULL);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(POLE_SHAPE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch(state.get(POLE_SHAPE)) {
            case BOTTOM -> BOTTOM_SHAPE;
            case MIDDLE -> MIDDLE_SHAPE;
            case TOP -> TOP_SHAPE;
            case FULL -> FULL_SHAPE;
        };
    }

    public BlockState getStateFromNeighborBlocks(World world, BlockPos pos) {
        BlockState state = getDefaultState();

        BlockState blockBelow = world.getBlockState(pos.down());
        BlockState blockAbove = world.getBlockState(pos.up());
        boolean isPoleBelow = blockBelow.isOf(this);
        boolean isPoleAbove = blockAbove.isOf(this);

        if (isPoleBelow) {
            state = isPoleAbove ? state.with(POLE_SHAPE, PoleShape.MIDDLE) : state.with(POLE_SHAPE, PoleShape.TOP);
        }
        if (!isPoleAbove && !isPoleBelow) {
            state = state.with(POLE_SHAPE, PoleShape.FULL);
        }
        return state;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (neighborState.isOf(this) && direction.getAxis().isVertical()) {
            return getStateFromNeighborBlocks((World) world, pos);
        }
        return state;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getStateFromNeighborBlocks(ctx.getWorld(), ctx.getBlockPos());
    }

    static {
        MIDDLE_SHAPE = createCuboidShape(4, 0, 4, 12, 16, 12);
        BOTTOM_SHAPE = VoxelShapes.combine(MIDDLE_SHAPE, createCuboidShape(0, 0, 0, 16, 2, 16), BooleanBiFunction.OR);
        TOP_SHAPE = VoxelShapes.combine(MIDDLE_SHAPE, createCuboidShape(0, 14, 0, 16, 16, 16), BooleanBiFunction.OR);
        FULL_SHAPE = VoxelShapes.combine(BOTTOM_SHAPE, TOP_SHAPE, BooleanBiFunction.OR);
        POLE_SHAPE = EnumProperty.of("shape", PoleShape.class);
    }

    enum PoleShape implements StringIdentifiable {
        BOTTOM("bottom"),
        MIDDLE("middle"),
        TOP("top"),
        FULL("full");

        private final String name;

        PoleShape(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return name;
        }

        @Override
        public String toString() {
            return asString();
        }
    }
}
