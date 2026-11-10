package poeticrainbow.rainbowchristmas.registry.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import poeticrainbow.rainbowchristmas.registry.TooltipContainer;

public class TooltipItem extends Item implements TooltipContainer {
    public TooltipItem(Properties settings) {
        super(settings);
    }

    @Override
    public String getTooltipTranslationKey() {
        Identifier id = BuiltInRegistries.ITEM.getKey(this);
        return "tooltip." + id.getNamespace() + "." + id.getPath();
    }
}
