package poeticrainbow.rainbowchristmas;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poeticrainbow.rainbowchristmas.registry.ModBlocks;
import poeticrainbow.rainbowchristmas.registry.ModItems;
import poeticrainbow.rainbowchristmas.registry.ModMisc;
import poeticrainbow.rainbowchristmas.registry.TooltipContainer;
import poeticrainbow.rainbowchristmas.render.MulticolorProvider;

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
        ColorProviderRegistry.BLOCK.register(MulticolorProvider::holidayLeavesBlockColorProvider, ModBlocks.RAINBOW_HOLIDAY_LEAVES);

        BlockRenderLayerMap.putBlock(ModBlocks.CHRISTMAS_TREE_STAND, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.FAUX_SNOW, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.MISTLETOE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.WREATH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.STAR_TOPPER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.ICICLE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.NORTH_POLE, BlockRenderLayer.CUTOUT);

        ModBlocks.ORNAMENTS.forEach(block -> BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT));
        ModBlocks.GARLANDS.forEach(block -> BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT));
        ModBlocks.CHRISTMAS_LIGHTS.forEach(block -> BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT));
        ModBlocks.HOLIDAY_LEAVES.forEach(block -> BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT));

        ItemTooltipCallback.EVENT.register((stack, context, tooltipType, list) -> {
            var container = getTooltipContainerFromStack(stack);
            if (container != null) {
                var key = container.getTooltipTranslationKey();

                Text tooltip = Text.translatable(key);
                String[] lines = tooltip.getString().split("\\n");
                for (String line : lines) {
                    list.add(Text.of(line).copy().setStyle(Style.EMPTY.withColor(Formatting.GRAY)));
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
        return Identifier.of(MOD_ID, name);
    }
}
