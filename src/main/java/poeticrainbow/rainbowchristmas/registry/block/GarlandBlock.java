package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import poeticrainbow.rainbowchristmas.registry.TooltipContainer;

public class GarlandBlock extends StrungDecorationBlock implements TooltipContainer {
    public GarlandBlock(Settings settings) {
        super(settings);
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = super.getOutlineShape(state, world, pos, context);
        if (shape.isEmpty())
            return VoxelShapes.combine(ALL_SHAPE, VoxelShapes.cuboid(0, 0.5, 0, 1, 1, 1), BooleanBiFunction.AND);
        return shape;
    }

    @Override
    public String getTooltipTranslationKey() {
        return "tooltip.rainbowchristmas.garland";
    }
}
