package poeticrainbow.rainbowchristmas.registry.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class WallDecorationBlock extends HorizontalFacingBlock {
    private static final VoxelShape NORTH_SHAPE;
    private static final VoxelShape EAST_SHAPE;
    private static final VoxelShape SOUTH_SHAPE;
    private static final VoxelShape WEST_SHAPE;

    public WallDecorationBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return !(world.isAir(pos.north()) && world.isAir(pos.east()) &&
                world.isAir(pos.south()) && world.isAir(pos.west()));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch(state.get(FACING)) {
            default -> NORTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
        };
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction side = ctx.getSide();
        if (side.getAxis().isHorizontal()) return getDefaultState().with(FACING, ctx.getSide());

        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        if (!world.isAir(pos.north())) return getDefaultState().with(FACING, Direction.SOUTH);
        if (!world.isAir(pos.east())) return getDefaultState().with(FACING, Direction.WEST);
        if (!world.isAir(pos.south())) return getDefaultState().with(FACING, Direction.NORTH);
        if (!world.isAir(pos.west())) return getDefaultState().with(FACING, Direction.EAST);
        return getDefaultState();
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }

    static {
        NORTH_SHAPE = VoxelShapes.cuboid(0, 0, 0.9375, 1, 1, 1);
        EAST_SHAPE = VoxelShapes.cuboid(0, 0, 0, 0.0625, 1, 1);
        SOUTH_SHAPE = VoxelShapes.cuboid(0, 0, 0, 1, 1, 0.0625);
        WEST_SHAPE = VoxelShapes.cuboid(0.9375, 0, 0, 1, 1, 1);
    }
}
