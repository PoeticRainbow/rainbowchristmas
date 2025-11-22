package poeticrainbow.rainbowchristmas.registry.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import poeticrainbow.rainbowchristmas.registry.TooltipContainer;

public class TooltipItem extends Item implements TooltipContainer {
    public TooltipItem(Settings settings) {
        super(settings);
    }

    @Override
    public String getTooltipTranslationKey() {
        Identifier id = Registries.ITEM.getId(this);
        return "tooltip." + id.getNamespace() + "." + id.getPath();
    }
}
