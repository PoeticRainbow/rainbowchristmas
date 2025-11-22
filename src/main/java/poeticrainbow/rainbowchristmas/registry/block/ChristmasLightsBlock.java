package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import poeticrainbow.rainbowchristmas.registry.TooltipContainer;

public class ChristmasLightsBlock extends StrungDecorationBlock implements TooltipContainer {
    public ChristmasLightsBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = super.getOutlineShape(state, world, pos, context);
        if (shape.isEmpty()) return ALL_SHAPE;
        return shape;
    }

    @Override
    public String getTooltipTranslationKey() {
        return "tooltip.rainbowchristmas.christmas_lights";
    }
}
