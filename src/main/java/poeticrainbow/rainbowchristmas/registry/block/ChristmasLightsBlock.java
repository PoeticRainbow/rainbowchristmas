package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.List;

public class ChristmasLightsBlock extends StrungDecorationBlock {
    public ChristmasLightsBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        Text garlandTooltip = Text.translatable("tooltip.rainbowchristmas.christmas_lights");
        String[] lines = garlandTooltip.getString().split("\\n");
        for (String line : lines) {
            tooltip.add(Text.of(line).copy().setStyle(Style.EMPTY.withColor(Formatting.GRAY)));
        }
        super.appendTooltip(stack, context, tooltip, options);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = super.getOutlineShape(state, world, pos, context);
        if (shape.isEmpty()) return ALL_SHAPE;
        return shape;
    }
}
