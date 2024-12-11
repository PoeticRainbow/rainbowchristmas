package poeticrainbow.rainbowchristmas.registry.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TooltipItem extends Item {
    public TooltipItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Identifier id = Registries.ITEM.getId(this);
        tooltip.add(Text.translatable("tooltip." + id.getNamespace() + "." + id.getPath())
                .setStyle(Style.EMPTY.withColor(Formatting.GRAY)));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
