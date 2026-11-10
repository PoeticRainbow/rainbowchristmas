package poeticrainbow.rainbowchristmas;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poeticrainbow.rainbowchristmas.registry.ModBlocks;
import poeticrainbow.rainbowchristmas.registry.ModItems;
import poeticrainbow.rainbowchristmas.registry.ModMisc;
import poeticrainbow.rainbowchristmas.registry.TooltipContainer;
import poeticrainbow.rainbowchristmas.render.MulticolorProvider;

import java.util.List;

public class RainbowChristmas implements ModInitializer, ClientModInitializer {
    public static String MOD_ID = "rainbowchristmas";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Time to get festive!");
        ModBlocks.onInitialize();
        ModItems.onInitialize();
        ModMisc.onInitialize();
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Giving your client a little holiday spirit!");
        //ColorProviderRegistry.ITEM.register(MulticolorProvider::rainbowItemColorProvider, ModBlocks.RAINBOW_HOLIDAY_LEAVES.asItem());
        BlockColorRegistry.register(List.of(new MulticolorProvider.RainbowColorProvider()), ModBlocks.RAINBOW_HOLIDAY_LEAVES);


        ItemTooltipCallback.EVENT.register((stack, context, tooltipType, list) -> {
            var container = getTooltipContainerFromStack(stack);
            if (container != null) {
                var key = container.getTooltipTranslationKey();

                Component tooltip = Component.translatable(key);
                String[] lines = tooltip.getString().split("\\n");
                for (String line : lines) {
                    list.add(Component.nullToEmpty(line).copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
                }
            }
        });
    }

    public static TooltipContainer getTooltipContainerFromStack(ItemStack stack) {
        if (!stack.isEmpty()) {
            var item = stack.getItem();
            if (item instanceof TooltipContainer container) {
                return container;
            }
            if (item instanceof BlockItem blockItem) {
                var block = blockItem.getBlock();
                if (block instanceof TooltipContainer container) {
                    return container;
                }
            }
        }
        return null;
    }

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
}
