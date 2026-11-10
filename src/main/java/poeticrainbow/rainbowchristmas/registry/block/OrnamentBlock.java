package poeticrainbow.rainbowchristmas.registry.block;

import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.Nullable;
import poeticrainbow.rainbowchristmas.registry.TooltipContainer;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class OrnamentBlock extends DirectionalBlock implements TooltipContainer {
    private static final VoxelShape UP_SHAPE;
    private static final VoxelShape NORTH_SHAPE;
    private static final VoxelShape EAST_SHAPE;
    private static final VoxelShape SOUTH_SHAPE;
    private static final VoxelShape WEST_SHAPE;
    private static final VoxelShape DOWN_SHAPE;

    public OrnamentBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN));
    }

    @Override
    protected MapCodec<? extends DirectionalBlock> codec() {
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    public VoxelShape getVoxelShapeFromFacing(BlockState state) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case DOWN -> DOWN_SHAPE;
            case UP -> UP_SHAPE;
        };
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return getVoxelShapeFromFacing(state);
    }

    @Override
    public String getTooltipTranslationKey() {
        return "tooltip.rainbowchristmas.ornament";
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        Vec3 point = new Vec3(random.nextFloat(), random.nextFloat(), random.nextFloat());
        Optional<Vec3> closestPoint = getVoxelShapeFromFacing(state).closestPointTo(point);
        if (closestPoint.isPresent() && random.nextFloat() < 0.2) {
            Vec3 pointOnSurface = new Vec3(pos.getX(), pos.getY(), pos.getZ()).add(closestPoint.get());
            world.addParticle(
                ParticleTypes.END_ROD,
                pointOnSurface.x(), pointOnSurface.y(), pointOnSurface.z(),
                0d, 0.0d, 0d
            );
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getClickedFace());
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0f;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return true;
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
        NORTH_SHAPE = Shapes.box(0.3125, 0.125, 0.5625, 0.6875, 0.875, 1);
        EAST_SHAPE = Shapes.box(0, 0.125, 0.3125, 0.4375, 0.875, 0.6875);
        SOUTH_SHAPE = Shapes.box(0.3125, 0.125, 0, 0.6875, 0.875, 0.4375);
        WEST_SHAPE = Shapes.box(0.5625, 0.125, 0.3125, 1, 0.875, 0.6875);
        UP_SHAPE = Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.625, 0.6875);
        DOWN_SHAPE = Shapes.box(0.3125, 0.375, 0.3125, 0.6875, 1, 0.6875);
    }
}
