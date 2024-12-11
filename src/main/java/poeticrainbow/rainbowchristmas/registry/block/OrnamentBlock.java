package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class OrnamentBlock extends FacingBlock {
    private static final VoxelShape UP_SHAPE;
    private static final VoxelShape NORTH_SHAPE;
    private static final VoxelShape EAST_SHAPE;
    private static final VoxelShape SOUTH_SHAPE;
    private static final VoxelShape WEST_SHAPE;
    private static final VoxelShape DOWN_SHAPE;

    public OrnamentBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.DOWN));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    public VoxelShape getVoxelShapeFromFacing(BlockState state) {
        return switch (state.get(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case DOWN -> DOWN_SHAPE;
            case UP -> UP_SHAPE;
        };
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getVoxelShapeFromFacing(state);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("tooltip.rainbowchristmas.ornament").setStyle(Style.EMPTY.withColor(Formatting.GRAY)));
        super.appendTooltip(stack, world, tooltip, options);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Vec3d point = new Vec3d(random.nextFloat(), random.nextFloat(), random.nextFloat());
        Optional<Vec3d> closestPoint = getVoxelShapeFromFacing(state).getClosestPointTo(point);
        if (closestPoint.isPresent() && random.nextFloat() < 0.2) {
            Vec3d pointOnSurface = new Vec3d(pos.getX(), pos.getY(), pos.getZ()).add(closestPoint.get());
            world.addParticle(ParticleTypes.END_ROD,
                    pointOnSurface.getX(), pointOnSurface.getY(), pointOnSurface.getZ(),
                    0d, 0.0d, 0d);
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getSide());
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
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
        NORTH_SHAPE = VoxelShapes.cuboid(0.3125, 0.125, 0.5625, 0.6875, 0.875, 1);
        EAST_SHAPE = VoxelShapes.cuboid(0, 0.125, 0.3125, 0.4375, 0.875, 0.6875);
        SOUTH_SHAPE = VoxelShapes.cuboid(0.3125, 0.125, 0, 0.6875, 0.875, 0.4375);
        WEST_SHAPE = VoxelShapes.cuboid(0.5625, 0.125, 0.3125, 1, 0.875, 0.6875);
        UP_SHAPE = VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.625, 0.6875);
        DOWN_SHAPE = VoxelShapes.cuboid(0.3125, 0.375, 0.3125, 0.6875, 1, 0.6875);
    }
}
