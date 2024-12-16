package poeticrainbow.rainbowchristmas;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poeticrainbow.rainbowchristmas.registry.ModBlocks;
import poeticrainbow.rainbowchristmas.registry.ModItems;
import poeticrainbow.rainbowchristmas.registry.ModMisc;
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

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHRISTMAS_TREE_STAND, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FAUX_SNOW, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MISTLETOE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WREATH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STAR_TOPPER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICICLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.NORTH_POLE, RenderLayer.getCutout());

        ModBlocks.ORNAMENTS.forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout()));
        ModBlocks.GARLANDS.forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout()));
        ModBlocks.CHRISTMAS_LIGHTS.forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout()));
        ModBlocks.HOLIDAY_LEAVES.forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout()));
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }
}
