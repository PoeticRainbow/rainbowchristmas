package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import poeticrainbow.rainbowchristmas.registry.TooltipContainer;

public class FauxSnowBlock extends TransparentBlock implements TooltipContainer {
    public static final EnumProperty<FauxSnowShape> FAUX_SNOW_SHAPE;
    public static final EnumProperty<Direction> FACING;

    public FauxSnowBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FAUX_SNOW_SHAPE, FauxSnowShape.FLAT).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FAUX_SNOW_SHAPE);
        builder.add(FACING);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return box(-0.05, 0, -0.05, 16.05, 2, 16.05);
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction) {
        if (direction == Direction.DOWN) {
            return stateFrom.canOcclude();
        }
        return stateFrom.equals(state);
    }

    public BlockState updateBlockStateFromBelow(BlockState blockBelow, Level world, BlockPos pos) {
        BlockState defaultState = this.getStateDefinition().any();

        if (blockBelow.isFaceSturdy(world, pos, Direction.UP)) {
            return defaultState;
        }

        // Slab handling
        double maxYBelow = blockBelow.getShape(world, pos).max(Direction.Axis.Y);
        if (maxYBelow > 0.375d && maxYBelow < 0.625) {
            defaultState = defaultState.setValue(FAUX_SNOW_SHAPE, FauxSnowShape.SLAB);
        }
        // Stair handling
        if (blockBelow.getOptionalValue(BlockStateProperties.STAIRS_SHAPE).isPresent()) {
            defaultState = defaultState.setValue(FACING, blockBelow.getValue(BlockStateProperties.HORIZONTAL_FACING));
            switch (blockBelow.getValue(BlockStateProperties.STAIRS_SHAPE)) {
                case STRAIGHT -> defaultState = defaultState.setValue(FAUX_SNOW_SHAPE, FauxSnowShape.STRAIGHT);
                case OUTER_LEFT -> defaultState = defaultState.setValue(FAUX_SNOW_SHAPE, FauxSnowShape.OUTER_LEFT);
                case OUTER_RIGHT -> defaultState = defaultState.setValue(FAUX_SNOW_SHAPE, FauxSnowShape.OUTER_RIGHT);
                case INNER_LEFT -> defaultState = defaultState.setValue(FAUX_SNOW_SHAPE, FauxSnowShape.INNER_LEFT);
                case INNER_RIGHT -> defaultState = defaultState.setValue(FAUX_SNOW_SHAPE, FauxSnowShape.INNER_RIGHT);
            }
        }

        return defaultState;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockBelow = ctx.getLevel().getBlockState(ctx.getClickedPos().below());
        BlockState defaultState = this.getStateDefinition().any();

        if (blockBelow.isFaceSturdy(ctx.getLevel(), ctx.getClickedPos(), Direction.UP)) {
            return defaultState;
        }

        return updateBlockStateFromBelow(blockBelow, ctx.getLevel(), ctx.getClickedPos());
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (!this.canSurvive(state, world, pos)) {
            tickView.scheduleTick(pos, this, 1);
        }
        if (world instanceof Level worldWorld) {
            return updateBlockStateFromBelow(world.getBlockState(pos.below()), worldWorld, pos);
        }
        return state;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0f;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!this.canSurvive(state, world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockState blockBelow = world.getBlockState(pos.below());
        return !blockBelow.isAir() && !blockBelow.is(this);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    static {
        FAUX_SNOW_SHAPE = EnumProperty.create("shape", FauxSnowShape.class);
        FACING = BlockStateProperties.HORIZONTAL_FACING;
    }

    @Override
    public String getTooltipTranslationKey() {
        return "tooltip.rainbowchristmas.faux_snow";
    }

    public enum FauxSnowShape implements StringRepresentable {
        FLAT("full"),
        SLAB("slab"),
        STRAIGHT("straight_stair"),
        OUTER_LEFT("outer_left_stair"),
        OUTER_RIGHT("outer_right_stair"),
        INNER_LEFT("inner_left_stair"),
        INNER_RIGHT("inner_right_stair");

        private final String name;

        FauxSnowShape(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public String getSerializedName() {
            return this.name;
        }
    }
}
