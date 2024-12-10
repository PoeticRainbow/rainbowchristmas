package poeticrainbow.rainbowchristmas.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import poeticrainbow.rainbowchristmas.RainbowChristmas;
import poeticrainbow.rainbowchristmas.registry.block.*;

import java.util.List;

public class ModBlocks {
    public static Block CHRISTMAS_TREE_STAND = register(new ChristmasTreeStandBlock(FabricBlockSettings.create().nonOpaque()
            .solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never)), "christmas_tree_stand", true);
    public static Block MISTLETOE = register(new MistletoeBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS)
            .ticksRandomly().nonOpaque().solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never)), "mistletoe", true);
    public static Block FAUX_SNOW = register(new FauxSnowBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.SNOW)
            .nonOpaque().solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never)), "faux_snow", true);
    public static Block WREATH = register(new WallDecorationBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.GRASS)
            .nonOpaque().solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never)), "wreath", true);
    public static Block STAR_TOPPER = register(new TreeTopperBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.GLASS)
            .nonOpaque().solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never).luminance(15)), "star_topper", true);
    public static Block ICICLE = register(new IcicleBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.GLASS)
            .nonOpaque().solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never)), "icicle", true);
    public static Block SHREDDER = register(new ShredderBlock(FabricBlockSettings.create()), "shredder", true);

    public static AbstractBlock.Settings HARD_CANDY_SETTINGS = FabricBlockSettings.create().strength(0.8f, 3.0f).mapColor(MapColor.PALE_YELLOW).sounds(BlockSoundGroup.STONE);
    public static Block HARD_CANDY_BLOCK = register(new Block(HARD_CANDY_SETTINGS), "hard_candy_block", true);
    public static Block HARD_CANDY_TILES = register(new Block(HARD_CANDY_SETTINGS), "hard_candy_tiles", true);
    public static Block HARD_CANDY_TILE_STAIRS = register(new StairsBlock(HARD_CANDY_TILES.getDefaultState(), HARD_CANDY_SETTINGS),
            "hard_candy_tile_stairs", true);
    public static Block HARD_CANDY_TILE_SLAB = register(new SlabBlock(HARD_CANDY_SETTINGS), "hard_candy_tile_slab", true);
    public static Block HARD_CANDY_PILLAR = register(new PillarBlock(HARD_CANDY_SETTINGS), "hard_candy_pillar", true);

    public static AbstractBlock.Settings CANDY_CANE_SETTINGS = FabricBlockSettings.create().strength(0.8f, 3.0f).mapColor(MapColor.RED).sounds(BlockSoundGroup.STONE);
    public static Block CANDY_CANE_TILES = register(new Block(CANDY_CANE_SETTINGS),
            "candy_cane_tiles", true);
    public static Block CANDY_CANE_TILE_STAIRS = register(new StairsBlock(HARD_CANDY_TILES.getDefaultState(), CANDY_CANE_SETTINGS),
            "candy_cane_tile_stairs", true);
    public static Block CANDY_CANE_TILE_SLAB = register(new SlabBlock(CANDY_CANE_SETTINGS),
            "candy_cane_tile_slab", true);
    public static Block CANDY_CANE_PILLAR = register(new PillarBlock(CANDY_CANE_SETTINGS),
            "candy_cane_pillar", true);
    public static Block NORTH_POLE = register(new NorthPoleBlock(CANDY_CANE_SETTINGS), "north_pole", true);

    public static AbstractBlock.Settings GINGERBREAD_SETTINGS = FabricBlockSettings.create().strength(0.8f, 3.0f).mapColor(MapColor.BROWN).sounds(BlockSoundGroup.STONE);
    public static Block GINGERBREAD_BLOCK = register(new Block(GINGERBREAD_SETTINGS), "gingerbread_block", true);
    public static Block GINGERBREAD_BLOCK_STAIRS = register(new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), GINGERBREAD_SETTINGS),
            "gingerbread_block_stairs", true);
    public static Block GINGERBREAD_BLOCK_SLAB = register(new SlabBlock(GINGERBREAD_SETTINGS),
            "gingerbread_block_slab", true);

    public static Block GINGERBREAD_BRICKS = register(new Block(GINGERBREAD_SETTINGS),
            "gingerbread_bricks", true);
    public static Block GINGERBREAD_BRICK_STAIRS = register(new StairsBlock(GINGERBREAD_BRICKS.getDefaultState(), GINGERBREAD_SETTINGS),
            "gingerbread_brick_stairs", true);
    public static Block GINGERBREAD_BRICK_SLAB = register(new SlabBlock(GINGERBREAD_SETTINGS),
            "gingerbread_brick_slab", true);

    public static Block GINGERBREAD_SHINGLES = register(new Block(GINGERBREAD_SETTINGS), "gingerbread_shingles", true);
    public static Block GINGERBREAD_SHINGLE_STAIRS = register(new StairsBlock(GINGERBREAD_SHINGLES.getDefaultState(), GINGERBREAD_SETTINGS),
            "gingerbread_shingle_stairs", true);
    public static Block GINGERBREAD_SHINGLE_SLAB = register(new SlabBlock(GINGERBREAD_SETTINGS),
            "gingerbread_shingle_slab", true);


    public static Block RAINBOW_HOLIDAY_LEAVES = registerHolidayLeaves("rainbow_holiday_leaves");
    public static Block WHITE_HOLIDAY_LEAVES = registerHolidayLeaves("white_holiday_leaves");
    public static Block LIGHT_GRAY_HOLIDAY_LEAVES = registerHolidayLeaves("light_gray_holiday_leaves");
    public static Block GRAY_HOLIDAY_LEAVES = registerHolidayLeaves("gray_holiday_leaves");
    public static Block BLACK_HOLIDAY_LEAVES = registerHolidayLeaves("black_holiday_leaves");
    public static Block BROWN_HOLIDAY_LEAVES = registerHolidayLeaves("brown_holiday_leaves");
    public static Block RED_HOLIDAY_LEAVES = registerHolidayLeaves("red_holiday_leaves");
    public static Block ORANGE_HOLIDAY_LEAVES = registerHolidayLeaves("orange_holiday_leaves");
    public static Block YELLOW_HOLIDAY_LEAVES = registerHolidayLeaves("yellow_holiday_leaves");
    public static Block LIME_HOLIDAY_LEAVES = registerHolidayLeaves("lime_holiday_leaves");
    public static Block GREEN_HOLIDAY_LEAVES = registerHolidayLeaves("green_holiday_leaves");
    public static Block CYAN_HOLIDAY_LEAVES = registerHolidayLeaves("cyan_holiday_leaves");
    public static Block LIGHT_BLUE_HOLIDAY_LEAVES = registerHolidayLeaves("light_blue_holiday_leaves");
    public static Block BLUE_HOLIDAY_LEAVES = registerHolidayLeaves("blue_holiday_leaves");
    public static Block PURPLE_HOLIDAY_LEAVES = registerHolidayLeaves("purple_holiday_leaves");
    public static Block MAGENTA_HOLIDAY_LEAVES = registerHolidayLeaves("magenta_holiday_leaves");
    public static Block PINK_HOLIDAY_LEAVES = registerHolidayLeaves("pink_holiday_leaves");

    public static List<Block> HOLIDAY_LEAVES = List.of(RAINBOW_HOLIDAY_LEAVES, WHITE_HOLIDAY_LEAVES, LIGHT_GRAY_HOLIDAY_LEAVES,
            GRAY_HOLIDAY_LEAVES, BLACK_HOLIDAY_LEAVES, BROWN_HOLIDAY_LEAVES, RED_HOLIDAY_LEAVES, ORANGE_HOLIDAY_LEAVES,
            YELLOW_HOLIDAY_LEAVES, LIME_HOLIDAY_LEAVES, GREEN_HOLIDAY_LEAVES, GREEN_HOLIDAY_LEAVES, CYAN_HOLIDAY_LEAVES,
            LIGHT_BLUE_HOLIDAY_LEAVES, BLUE_HOLIDAY_LEAVES, PURPLE_HOLIDAY_LEAVES, MAGENTA_HOLIDAY_LEAVES, PINK_HOLIDAY_LEAVES);

    public static Block WHITE_GARLAND = registerGarland("white_garland");
    public static Block LIGHT_GRAY_GARLAND = registerGarland("light_gray_garland");
    public static Block GRAY_GARLAND = registerGarland("gray_garland");
    public static Block BLACK_GARLAND = registerGarland("black_garland");
    public static Block BROWN_GARLAND = registerGarland("brown_garland");
    public static Block RED_GARLAND = registerGarland("red_garland");
    public static Block ORANGE_GARLAND = registerGarland("orange_garland");
    public static Block YELLOW_GARLAND = registerGarland("yellow_garland");
    public static Block LIME_GARLAND = registerGarland("lime_garland");
    public static Block GREEN_GARLAND = registerGarland("green_garland");
    public static Block CYAN_GARLAND = registerGarland("cyan_garland");
    public static Block LIGHT_BLUE_GARLAND = registerGarland("light_blue_garland");
    public static Block BLUE_GARLAND = registerGarland("blue_garland");
    public static Block PURPLE_GARLAND = registerGarland("purple_garland");
    public static Block MAGENTA_GARLAND = registerGarland("magenta_garland");
    public static Block PINK_GARLAND = registerGarland("pink_garland");

    public static List<Block> GARLANDS = List.of(WHITE_GARLAND, LIGHT_GRAY_GARLAND, GRAY_GARLAND, BLACK_GARLAND, BROWN_GARLAND,
            RED_GARLAND, ORANGE_GARLAND, YELLOW_GARLAND, LIME_GARLAND, GREEN_GARLAND, GREEN_GARLAND, CYAN_GARLAND,
            LIGHT_BLUE_GARLAND, BLUE_GARLAND, PURPLE_GARLAND, MAGENTA_GARLAND, PINK_GARLAND);

    public static Block MULTICOLOR_CHRISTMAS_LIGHTS = registerChristmasLights("multicolor_christmas_lights");
    public static Block WHITE_CHRISTMAS_LIGHTS = registerChristmasLights("white_christmas_lights");
    public static Block LIGHT_GRAY_CHRISTMAS_LIGHTS = registerChristmasLights("light_gray_christmas_lights");
    public static Block GRAY_CHRISTMAS_LIGHTS = registerChristmasLights("gray_christmas_lights");
    public static Block BLACK_CHRISTMAS_LIGHTS = registerChristmasLights("black_christmas_lights");
    public static Block BROWN_CHRISTMAS_LIGHTS = registerChristmasLights("brown_christmas_lights");
    public static Block RED_CHRISTMAS_LIGHTS = registerChristmasLights("red_christmas_lights");
    public static Block ORANGE_CHRISTMAS_LIGHTS = registerChristmasLights("orange_christmas_lights");
    public static Block YELLOW_CHRISTMAS_LIGHTS = registerChristmasLights("yellow_christmas_lights");
    public static Block LIME_CHRISTMAS_LIGHTS = registerChristmasLights("lime_christmas_lights");
    public static Block GREEN_CHRISTMAS_LIGHTS = registerChristmasLights("green_christmas_lights");
    public static Block CYAN_CHRISTMAS_LIGHTS = registerChristmasLights("cyan_christmas_lights");
    public static Block LIGHT_BLUE_CHRISTMAS_LIGHTS = registerChristmasLights("light_blue_christmas_lights");
    public static Block BLUE_CHRISTMAS_LIGHTS = registerChristmasLights("blue_christmas_lights");
    public static Block PURPLE_CHRISTMAS_LIGHTS = registerChristmasLights("purple_christmas_lights");
    public static Block MAGENTA_CHRISTMAS_LIGHTS = registerChristmasLights("magenta_christmas_lights");
    public static Block PINK_CHRISTMAS_LIGHTS = registerChristmasLights("pink_christmas_lights");

    public static List<Block> CHRISTMAS_LIGHTS = List.of(MULTICOLOR_CHRISTMAS_LIGHTS, WHITE_CHRISTMAS_LIGHTS, LIGHT_GRAY_CHRISTMAS_LIGHTS,
            GRAY_CHRISTMAS_LIGHTS, BLACK_CHRISTMAS_LIGHTS, BROWN_CHRISTMAS_LIGHTS, RED_CHRISTMAS_LIGHTS, ORANGE_CHRISTMAS_LIGHTS,
            YELLOW_CHRISTMAS_LIGHTS, LIME_CHRISTMAS_LIGHTS, GREEN_CHRISTMAS_LIGHTS, GREEN_CHRISTMAS_LIGHTS, CYAN_CHRISTMAS_LIGHTS,
            LIGHT_BLUE_CHRISTMAS_LIGHTS, BLUE_CHRISTMAS_LIGHTS, PURPLE_CHRISTMAS_LIGHTS, MAGENTA_CHRISTMAS_LIGHTS, PINK_CHRISTMAS_LIGHTS);

    public static Block CLEAR_ORNAMENT = registerOrnament("clear_ornament");
    public static Block WHITE_ORNAMENT = registerOrnament("white_ornament");
    public static Block LIGHT_GRAY_ORNAMENT = registerOrnament("light_gray_ornament");
    public static Block GRAY_ORNAMENT = registerOrnament("gray_ornament");
    public static Block BLACK_ORNAMENT = registerOrnament("black_ornament");
    public static Block BROWN_ORNAMENT = registerOrnament("brown_ornament");
    public static Block RED_ORNAMENT = registerOrnament("red_ornament");
    public static Block ORANGE_ORNAMENT = registerOrnament("orange_ornament");
    public static Block YELLOW_ORNAMENT = registerOrnament("yellow_ornament");
    public static Block LIME_ORNAMENT = registerOrnament("lime_ornament");
    public static Block GREEN_ORNAMENT = registerOrnament("green_ornament");
    public static Block CYAN_ORNAMENT = registerOrnament("cyan_ornament");
    public static Block LIGHT_BLUE_ORNAMENT = registerOrnament("light_blue_ornament");
    public static Block BLUE_ORNAMENT = registerOrnament("blue_ornament");
    public static Block PURPLE_ORNAMENT = registerOrnament("purple_ornament");
    public static Block MAGENTA_ORNAMENT = registerOrnament("magenta_ornament");
    public static Block PINK_ORNAMENT = registerOrnament("pink_ornament");

    public static List<Block> ORNAMENTS = List.of(CLEAR_ORNAMENT, WHITE_ORNAMENT, LIGHT_GRAY_ORNAMENT, GRAY_ORNAMENT,
            BLACK_ORNAMENT, BROWN_ORNAMENT, RED_ORNAMENT, ORANGE_ORNAMENT, YELLOW_ORNAMENT, LIME_ORNAMENT, GREEN_ORNAMENT,
            GREEN_ORNAMENT, CYAN_ORNAMENT, LIGHT_BLUE_ORNAMENT, BLUE_ORNAMENT, PURPLE_ORNAMENT, MAGENTA_ORNAMENT, PINK_ORNAMENT);



    public static Block registerOrnament(String name) {
        return register(new OrnamentBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.GLASS).nonOpaque()
                        .solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never)),
                name, true);
    }

    public static Block registerGarland(String name) {
        return register(new GarlandBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.GRASS).nonOpaque()
                        .solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never)),
                name, true);
    }

    public static Block registerChristmasLights(String name) {
        return register(new ChristmasLightsBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.WOOD).nonOpaque()
                        .solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never).luminance(15)),
                name, true);
    }

    public static Block registerHolidayLeaves(String name) {
        return register(new HolidayLeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).nonOpaque()
                        .solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never)),
                name, true);
    }

    public static Block register(Block block, String name, boolean makeItem) {
        Identifier id = RainbowChristmas.id(name);
        if (makeItem) ModItems.register(new BlockItem(block, new Item.Settings()), name);
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void onInitialize() {}
}
