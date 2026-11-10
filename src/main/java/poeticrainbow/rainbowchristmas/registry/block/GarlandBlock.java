package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import poeticrainbow.rainbowchristmas.registry.TooltipContainer;

public class GarlandBlock extends StrungDecorationBlock implements TooltipContainer {
    public GarlandBlock(Properties settings) {
        super(settings);
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        VoxelShape shape = super.getShape(state, world, pos, context);
        if (shape.isEmpty())
            return Shapes.joinUnoptimized(ALL_SHAPE, Shapes.box(0, 0.5, 0, 1, 1, 1), BooleanOp.AND);
        return shape;
    }

    @Override
    public String getTooltipTranslationKey() {
        return "tooltip.rainbowchristmas.garland";
    }
}
