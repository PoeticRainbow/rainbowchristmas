package poeticrainbow.rainbowchristmas.registry.block;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class IcicleBlock extends Block {
    public static final IntegerProperty COUNT;
    public static final VoxelShape SHAPE;

    public IcicleBlock(Properties settings) {
        super(settings);
        registerDefaultState(getStateDefinition().any().setValue(COUNT, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(COUNT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos());
        if (blockState.is(this)) {
            return blockState.setValue(COUNT, Math.min(4, blockState.getValue(COUNT) + 1));
        }
        return super.getStateForPlacement(ctx);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return canSurvive((Level) world, pos);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        if (ctx.getItemInHand().is(this.asItem()) && state.getValue(COUNT) < 4) {
            return true;
        }

        return super.canBeReplaced(state, ctx);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> droppedItems = new java.util.ArrayList<>(List.of());
        for (int i = 0; i < state.getValue(COUNT); i++) {
            droppedItems.addAll(super.getDrops(state, builder));
        }
        return droppedItems;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public boolean canSurvive(Level world, BlockPos pos) {
        BlockState blockAbove = world.getBlockState(pos.above());
        return blockAbove.isFaceSturdy(world, pos, Direction.DOWN) || blockAbove.is(BlockTags.LEAVES);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!canSurvive(world, pos)) {
            world.destroyBlock(pos, true);
        }
        super.tick(state, world, pos, random);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (direction == Direction.UP && !canSurvive((Level) world, pos)) {
            tickView.scheduleTick(pos, this, 1);
        }
        return state;
    }

    static {
        COUNT = IntegerProperty.create("count", 1, 4);
        SHAPE = box(2, 2, 2, 14, 16, 14);
    }
}
