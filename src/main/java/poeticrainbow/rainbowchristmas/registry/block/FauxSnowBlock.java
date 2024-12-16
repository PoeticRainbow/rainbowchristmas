package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TransparentBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Formatting;
import net.minecraft.util.StringIdentifiable;
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

import java.util.List;

public class FauxSnowBlock extends TransparentBlock {
    public static final EnumProperty<FauxSnowShape> FAUX_SNOW_SHAPE;
    public static final EnumProperty<Direction> FACING;

    public FauxSnowBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FAUX_SNOW_SHAPE, FauxSnowShape.FLAT).with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FAUX_SNOW_SHAPE);
        builder.add(FACING);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        Text fauxSnowTooltip = Text.translatable("tooltip.rainbowchristmas.faux_snow");
        String[] lines = fauxSnowTooltip.getString().split("\\n");
        for (String line : lines) {
            tooltip.add(Text.of(line).copy().setStyle(Style.EMPTY.withColor(Formatting.GRAY)));
        }
        super.appendTooltip(stack, context, tooltip, options);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return createCuboidShape(-0.05, -2, -0.05, 16.05, 2, 16.05);
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (direction == Direction.DOWN) {
            return stateFrom.isOpaque();
        }
        return stateFrom.equals(state);
    }

    public BlockState updateBlockStateFromBelow(BlockState blockBelow, World world, BlockPos pos) {
        BlockState defaultState = this.getStateManager().getDefaultState();

        if (blockBelow.isSideSolidFullSquare(world, pos, Direction.UP)) {
            return defaultState;
        }

        // Slab handling
        double maxYBelow = blockBelow.getOutlineShape(world, pos).getMax(Direction.Axis.Y);
        if (maxYBelow > 0.375d && maxYBelow < 0.625) {
            defaultState = defaultState.with(FAUX_SNOW_SHAPE, FauxSnowShape.SLAB);
        }
        // Stair handling
        if (blockBelow.getOrEmpty(Properties.STAIR_SHAPE).isPresent()) {
            defaultState = defaultState.with(FACING, blockBelow.get(Properties.HORIZONTAL_FACING));
            switch (blockBelow.get(Properties.STAIR_SHAPE)) {
                case STRAIGHT -> defaultState = defaultState.with(FAUX_SNOW_SHAPE, FauxSnowShape.STRAIGHT);
                case OUTER_LEFT -> defaultState = defaultState.with(FAUX_SNOW_SHAPE, FauxSnowShape.OUTER_LEFT);
                case OUTER_RIGHT -> defaultState = defaultState.with(FAUX_SNOW_SHAPE, FauxSnowShape.OUTER_RIGHT);
                case INNER_LEFT -> defaultState = defaultState.with(FAUX_SNOW_SHAPE, FauxSnowShape.INNER_LEFT);
                case INNER_RIGHT -> defaultState = defaultState.with(FAUX_SNOW_SHAPE, FauxSnowShape.INNER_RIGHT);
            }
        }

        return defaultState;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockBelow = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
        BlockState defaultState = this.getStateManager().getDefaultState();

        if (blockBelow.isSideSolidFullSquare(ctx.getWorld(), ctx.getBlockPos(), Direction.UP)) {
            return defaultState;
        }

        return updateBlockStateFromBelow(blockBelow, ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (!this.canPlaceAt(state, world, pos)) {
            tickView.scheduleBlockTick(pos, this, 1);
        }
        if (world instanceof World worldWorld) {
            return updateBlockStateFromBelow(world.getBlockState(pos.down()), worldWorld, pos);
        }
        return state;
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!this.canPlaceAt(state, world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockBelow = world.getBlockState(pos.down());
        return !blockBelow.isAir() && !blockBelow.isOf(this);
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(FACING, mirror.apply(state.get(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    static {
        FAUX_SNOW_SHAPE = EnumProperty.of("shape", FauxSnowShape.class);
        FACING = Properties.HORIZONTAL_FACING;
    }

    public enum FauxSnowShape implements StringIdentifiable {
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

        public String asString() {
            return this.name;
        }
    }
}
