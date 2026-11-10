package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import poeticrainbow.rainbowchristmas.registry.TooltipContainer;

public class ChristmasLightsBlock extends StrungDecorationBlock implements TooltipContainer {
    public ChristmasLightsBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        VoxelShape shape = super.getShape(state, world, pos, context);
        if (shape.isEmpty()) return ALL_SHAPE;
        return shape;
    }

    @Override
    public String getTooltipTranslationKey() {
        return "tooltip.rainbowchristmas.christmas_lights";
    }
}
