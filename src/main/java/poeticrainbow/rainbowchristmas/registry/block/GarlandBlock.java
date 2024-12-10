package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GarlandBlock extends StrungDecorationBlock {
    public GarlandBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        Text garlandTooltip = Text.translatable("tooltip.rainbowchristmas.garland");
        String[] lines = garlandTooltip.getString().split("\\n");
        for (String line : lines) {
            tooltip.add(Text.of(line).copy().setStyle(Style.EMPTY.withColor(Formatting.GRAY)));
        }
        super.appendTooltip(stack, world, tooltip, options);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = super.getOutlineShape(state, world, pos, context);
        if (shape.isEmpty()) return VoxelShapes.combine(ALL_SHAPE, VoxelShapes.cuboid(0, 0.5, 0, 1, 1, 1), BooleanBiFunction.AND);
        return shape;
    }
}
