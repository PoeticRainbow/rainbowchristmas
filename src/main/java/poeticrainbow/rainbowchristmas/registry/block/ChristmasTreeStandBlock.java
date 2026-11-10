package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChristmasTreeStandBlock extends Block {
    private static final VoxelShape SHAPE = Shapes.or(
            Shapes.box(0.125, 0, 0.125, 0.875, 1, 0.875),
            Shapes.box(0.0625, 0.375, 0.0625, 0.9375, 0.8125, 0.9375),
            Shapes.box(0, 0.75, 0, 1, 1, 1));


    public ChristmasTreeStandBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0f;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return true;
    }
}
