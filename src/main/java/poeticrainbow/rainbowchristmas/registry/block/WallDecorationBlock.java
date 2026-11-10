package poeticrainbow.rainbowchristmas.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WallDecorationBlock extends HorizontalDirectionalBlock {
    private static final VoxelShape NORTH_SHAPE;
    private static final VoxelShape EAST_SHAPE;
    private static final VoxelShape SOUTH_SHAPE;
    private static final VoxelShape WEST_SHAPE;

    public WallDecorationBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return !(world.isEmptyBlock(pos.north()) && world.isEmptyBlock(pos.east()) &&
                world.isEmptyBlock(pos.south()) && world.isEmptyBlock(pos.west()));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch(state.getValue(FACING)) {
            default -> NORTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction side = ctx.getClickedFace();
        if (side.getAxis().isHorizontal()) return defaultBlockState().setValue(FACING, ctx.getClickedFace());

        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        if (!world.isEmptyBlock(pos.north())) return defaultBlockState().setValue(FACING, Direction.SOUTH);
        if (!world.isEmptyBlock(pos.east())) return defaultBlockState().setValue(FACING, Direction.WEST);
        if (!world.isEmptyBlock(pos.south())) return defaultBlockState().setValue(FACING, Direction.NORTH);
        if (!world.isEmptyBlock(pos.west())) return defaultBlockState().setValue(FACING, Direction.EAST);
        return defaultBlockState();
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0f;
    }

    static {
        NORTH_SHAPE = Shapes.box(0, 0, 0.9375, 1, 1, 1);
        EAST_SHAPE = Shapes.box(0, 0, 0, 0.0625, 1, 1);
        SOUTH_SHAPE = Shapes.box(0, 0, 0, 1, 1, 0.0625);
        WEST_SHAPE = Shapes.box(0.9375, 0, 0, 1, 1, 1);
    }
}
