package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class NorthPoleBlock extends Block {
    public static EnumProperty<PoleShape> POLE_SHAPE;
    public static VoxelShape BOTTOM_SHAPE;
    public static VoxelShape MIDDLE_SHAPE;
    public static VoxelShape TOP_SHAPE;
    public static VoxelShape FULL_SHAPE;

    public NorthPoleBlock(Properties settings) {
        super(settings);
        getStateDefinition().any().setValue(POLE_SHAPE, PoleShape.FULL);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(POLE_SHAPE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch(state.getValue(POLE_SHAPE)) {
            case BOTTOM -> BOTTOM_SHAPE;
            case MIDDLE -> MIDDLE_SHAPE;
            case TOP -> TOP_SHAPE;
            case FULL -> FULL_SHAPE;
        };
    }

    public BlockState getStateFromNeighborBlocks(Level world, BlockPos pos) {
        BlockState state = defaultBlockState();

        BlockState blockBelow = world.getBlockState(pos.below());
        BlockState blockAbove = world.getBlockState(pos.above());
        boolean isPoleBelow = blockBelow.is(this);
        boolean isPoleAbove = blockAbove.is(this);

        if (isPoleBelow) {
            state = isPoleAbove ? state.setValue(POLE_SHAPE, PoleShape.MIDDLE) : state.setValue(POLE_SHAPE, PoleShape.TOP);
        }
        if (!isPoleAbove && !isPoleBelow) {
            state = state.setValue(POLE_SHAPE, PoleShape.FULL);
        }
        return state;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (neighborState.is(this) && direction.getAxis().isVertical() && world instanceof Level worldWorld) {
            return getStateFromNeighborBlocks(worldWorld, pos);
        }
        return state;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return getStateFromNeighborBlocks(ctx.getLevel(), ctx.getClickedPos());
    }

    static {
        MIDDLE_SHAPE = box(4, 0, 4, 12, 16, 12);
        BOTTOM_SHAPE = Shapes.joinUnoptimized(MIDDLE_SHAPE, box(0, 0, 0, 16, 2, 16), BooleanOp.OR);
        TOP_SHAPE = Shapes.joinUnoptimized(MIDDLE_SHAPE, box(0, 14, 0, 16, 16, 16), BooleanOp.OR);
        FULL_SHAPE = Shapes.joinUnoptimized(BOTTOM_SHAPE, TOP_SHAPE, BooleanOp.OR);
        POLE_SHAPE = EnumProperty.create("shape", PoleShape.class);
    }

    public enum PoleShape implements StringRepresentable {
        BOTTOM("bottom"),
        MIDDLE("middle"),
        TOP("top"),
        FULL("full");

        private final String name;

        PoleShape(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public String toString() {
            return getSerializedName();
        }
    }
}
