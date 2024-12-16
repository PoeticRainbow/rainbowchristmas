package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IcicleBlock extends Block {
    public static final IntProperty COUNT;
    public static final VoxelShape SHAPE;

    public IcicleBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(COUNT, 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(COUNT);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return blockState.with(COUNT, Math.min(4, blockState.get(COUNT) + 1));
        }
        return super.getPlacementState(ctx);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canSurvive((World) world, pos);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext ctx) {
        if (ctx.getStack().isOf(this.asItem()) && state.get(COUNT) < 4) {
            return true;
        }

        return super.canReplace(state, ctx);
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
        List<ItemStack> droppedItems = new java.util.ArrayList<>(List.of());
        for (int i = 0; i < state.get(COUNT); i++) {
            droppedItems.addAll(super.getDroppedStacks(state, builder));
        }
        return super.getDroppedStacks(state, builder);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public boolean canSurvive(World world, BlockPos pos) {
        BlockState blockAbove = world.getBlockState(pos.up());
        return blockAbove.isSideSolidFullSquare(world, pos, Direction.DOWN) || blockAbove.isIn(BlockTags.LEAVES);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!canSurvive(world, pos)) {
            world.breakBlock(pos, true);
        }
        super.scheduledTick(state, world, pos, random);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (direction == Direction.UP && !canSurvive((World) world, pos)) {
            tickView.scheduleBlockTick(pos, this, 1);
        }
        return state;
    }

    static {
        COUNT = IntProperty.of("count", 1, 4);
        SHAPE = createCuboidShape(2, 2, 2, 14, 16, 14);
    }
}
