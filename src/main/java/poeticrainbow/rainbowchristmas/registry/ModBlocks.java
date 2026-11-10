package poeticrainbow.rainbowchristmas.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import poeticrainbow.rainbowchristmas.RainbowChristmas;
import poeticrainbow.rainbowchristmas.registry.block.*;

import java.util.List;

public class ModBlocks {
    public static Block CHRISTMAS_TREE_STAND = register(new ChristmasTreeStandBlock(BlockBehaviour.Properties.of()
            .setId(keyOfBlock("christmas_tree_stand")).mapColor(MapColor.COLOR_LIGHT_BLUE)
            .noOcclusion().isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::never)),
            "christmas_tree_stand", true);
    public static Block MISTLETOE = register(new MistletoeBlock(BlockBehaviour.Properties.of()
            .setId(keyOfBlock("mistletoe")).sound(SoundType.GRASS).mapColor(MapColor.COLOR_RED)
            .randomTicks().noOcclusion().isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::never)),
            "mistletoe", true);
    public static Block FAUX_SNOW = register(new FauxSnowBlock(BlockBehaviour.Properties.of()
            .setId(keyOfBlock("faux_snow")).sound(SoundType.SNOW).mapColor(MapColor.WOOL)
            .noOcclusion().isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::never)),
            "faux_snow", true);
    public static Block WREATH = register(new WallDecorationBlock(BlockBehaviour.Properties.of()
            .setId(keyOfBlock("wreath")).sound(SoundType.GRASS).mapColor(MapColor.COLOR_GREEN)
            .noOcclusion().isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::never)),
            "wreath", true);
    public static Block STAR_TOPPER = register(new TreeTopperBlock(BlockBehaviour.Properties.of()
            .setId(keyOfBlock("star_topper")).sound(SoundType.GLASS).mapColor(MapColor.GOLD)
            .noOcclusion().isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::never).lightLevel(value -> 15)),
            "star_topper", true);
    public static Block ICICLE = register(new IcicleBlock(BlockBehaviour.Properties.of()
            .setId(keyOfBlock("icicle")).sound(SoundType.GLASS).mapColor(MapColor.COLOR_LIGHT_BLUE)
            .noOcclusion().isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::never)),
            "icicle", true);
    public static Block SHREDDER = register(new ShredderBlock(BlockBehaviour.Properties.of()
            .setId(keyOfBlock("shredder")).sound(SoundType.STONE).mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops().strength(0.8f, 3.0f)),
            "shredder", true);

    public static BlockBehaviour.Properties HARD_CANDY_SETTINGS = BlockBehaviour.Properties.of().strength(0.8f, 3.0f).mapColor(MapColor.SAND).sound(SoundType.STONE);
    public static Block HARD_CANDY_BLOCK = register(new Block(HARD_CANDY_SETTINGS
            .setId(keyOfBlock("hard_candy_block"))), "hard_candy_block", true);
    public static Block HARD_CANDY_TILES = register(new Block(HARD_CANDY_SETTINGS
            .setId(keyOfBlock("hard_candy_tiles"))), "hard_candy_tiles", true);
    public static Block HARD_CANDY_TILE_STAIRS = register(new StairBlock(HARD_CANDY_TILES.defaultBlockState(), HARD_CANDY_SETTINGS
            .setId(keyOfBlock("hard_candy_tile_stairs"))), "hard_candy_tile_stairs", true);
    public static Block HARD_CANDY_TILE_SLAB = register(new SlabBlock(HARD_CANDY_SETTINGS
            .setId(keyOfBlock("hard_candy_tile_slab"))), "hard_candy_tile_slab", true);
    public static Block HARD_CANDY_PILLAR = register(new RotatedPillarBlock(HARD_CANDY_SETTINGS
            .setId(keyOfBlock("hard_candy_pillar"))), "hard_candy_pillar", true);

    public static BlockBehaviour.Properties CANDY_CANE_SETTINGS = BlockBehaviour.Properties.of().strength(0.8f, 3.0f).mapColor(MapColor.COLOR_RED).sound(SoundType.STONE);
    public static Block CANDY_CANE_TILES = register(new Block(CANDY_CANE_SETTINGS
            .setId(keyOfBlock("candy_cane_tiles"))),
            "candy_cane_tiles", true);
    public static Block CANDY_CANE_TILE_STAIRS = register(new StairBlock(HARD_CANDY_TILES.defaultBlockState(), CANDY_CANE_SETTINGS
            .setId(keyOfBlock("candy_cane_tile_stairs"))),
            "candy_cane_tile_stairs", true);
    public static Block CANDY_CANE_TILE_SLAB = register(new SlabBlock(CANDY_CANE_SETTINGS
            .setId(keyOfBlock("candy_cane_tile_slab"))),
            "candy_cane_tile_slab", true);
    public static Block CANDY_CANE_PILLAR = register(new RotatedPillarBlock(CANDY_CANE_SETTINGS
            .setId(keyOfBlock("candy_cane_pillar"))),
            "candy_cane_pillar", true);
    public static Block NORTH_POLE = register(new NorthPoleBlock(CANDY_CANE_SETTINGS
            .setId(keyOfBlock("north_pole"))),
            "north_pole", true);

    public static BlockBehaviour.Properties GINGERBREAD_SETTINGS = BlockBehaviour.Properties.of().strength(0.8f, 3.0f).mapColor(MapColor.COLOR_BROWN).sound(SoundType.STONE);
    public static Block GINGERBREAD_BLOCK = register(new Block(GINGERBREAD_SETTINGS
            .setId(keyOfBlock("gingerbread_block"))), "gingerbread_block", true);
    public static Block GINGERBREAD_BLOCK_STAIRS = register(new StairBlock(GINGERBREAD_BLOCK.defaultBlockState(), GINGERBREAD_SETTINGS
                    .setId(keyOfBlock("gingerbread_block_stairs"))),
            "gingerbread_block_stairs", true);
    public static Block GINGERBREAD_BLOCK_SLAB = register(new SlabBlock(GINGERBREAD_SETTINGS
                    .setId(keyOfBlock("gingerbread_block_slab"))),
            "gingerbread_block_slab", true);

    public static Block GINGERBREAD_BRICKS = register(new Block(GINGERBREAD_SETTINGS
                    .setId(keyOfBlock("gingerbread_bricks"))),
            "gingerbread_bricks", true);
    public static Block GINGERBREAD_BRICK_STAIRS = register(new StairBlock(GINGERBREAD_BRICKS.defaultBlockState(), GINGERBREAD_SETTINGS
                    .setId(keyOfBlock("gingerbread_brick_stairs"))),
            "gingerbread_brick_stairs", true);
    public static Block GINGERBREAD_BRICK_SLAB = register(new SlabBlock(GINGERBREAD_SETTINGS
                    .setId(keyOfBlock("gingerbread_brick_slab"))),
            "gingerbread_brick_slab", true);

    public static Block GINGERBREAD_SHINGLES = register(new Block(GINGERBREAD_SETTINGS
                    .setId(keyOfBlock("gingerbread_shingles"))), "gingerbread_shingles", true);
    public static Block GINGERBREAD_SHINGLE_STAIRS = register(new StairBlock(GINGERBREAD_SHINGLES.defaultBlockState(), GINGERBREAD_SETTINGS
                    .setId(keyOfBlock("gingerbread_shingle_stairs"))),
            "gingerbread_shingle_stairs", true);
    public static Block GINGERBREAD_SHINGLE_SLAB = register(new SlabBlock(GINGERBREAD_SETTINGS
                    .setId(keyOfBlock("gingerbread_shingle_slab"))),
            "gingerbread_shingle_slab", true);


    public static Block RAINBOW_HOLIDAY_LEAVES = registerHolidayLeaves("rainbow_holiday_leaves", MapColor.COLOR_PURPLE);
    public static Block WHITE_HOLIDAY_LEAVES = registerHolidayLeaves("white_holiday_leaves", MapColor.SNOW);
    public static Block LIGHT_GRAY_HOLIDAY_LEAVES = registerHolidayLeaves("light_gray_holiday_leaves", MapColor.COLOR_LIGHT_GRAY);
    public static Block GRAY_HOLIDAY_LEAVES = registerHolidayLeaves("gray_holiday_leaves", MapColor.COLOR_GRAY);
    public static Block BLACK_HOLIDAY_LEAVES = registerHolidayLeaves("black_holiday_leaves", MapColor.COLOR_BLACK);
    public static Block BROWN_HOLIDAY_LEAVES = registerHolidayLeaves("brown_holiday_leaves", MapColor.COLOR_BROWN);
    public static Block RED_HOLIDAY_LEAVES = registerHolidayLeaves("red_holiday_leaves", MapColor.COLOR_RED);
    public static Block ORANGE_HOLIDAY_LEAVES = registerHolidayLeaves("orange_holiday_leaves", MapColor.COLOR_ORANGE);
    public static Block YELLOW_HOLIDAY_LEAVES = registerHolidayLeaves("yellow_holiday_leaves", MapColor.COLOR_YELLOW);
    public static Block LIME_HOLIDAY_LEAVES = registerHolidayLeaves("lime_holiday_leaves", MapColor.COLOR_LIGHT_GREEN);
    public static Block GREEN_HOLIDAY_LEAVES = registerHolidayLeaves("green_holiday_leaves", MapColor.COLOR_GREEN);
    public static Block CYAN_HOLIDAY_LEAVES = registerHolidayLeaves("cyan_holiday_leaves", MapColor.COLOR_CYAN);
    public static Block LIGHT_BLUE_HOLIDAY_LEAVES = registerHolidayLeaves("light_blue_holiday_leaves", MapColor.COLOR_LIGHT_BLUE);
    public static Block BLUE_HOLIDAY_LEAVES = registerHolidayLeaves("blue_holiday_leaves", MapColor.COLOR_BLUE);
    public static Block PURPLE_HOLIDAY_LEAVES = registerHolidayLeaves("purple_holiday_leaves", MapColor.COLOR_PURPLE);
    public static Block MAGENTA_HOLIDAY_LEAVES = registerHolidayLeaves("magenta_holiday_leaves", MapColor.COLOR_MAGENTA);
    public static Block PINK_HOLIDAY_LEAVES = registerHolidayLeaves("pink_holiday_leaves", MapColor.COLOR_PINK);

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
            RED_GARLAND, ORANGE_GARLAND, YELLOW_GARLAND, LIME_GARLAND, GREEN_GARLAND, CYAN_GARLAND,
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
            YELLOW_CHRISTMAS_LIGHTS, LIME_CHRISTMAS_LIGHTS, GREEN_CHRISTMAS_LIGHTS, CYAN_CHRISTMAS_LIGHTS, LIGHT_BLUE_CHRISTMAS_LIGHTS,
            BLUE_CHRISTMAS_LIGHTS, PURPLE_CHRISTMAS_LIGHTS, MAGENTA_CHRISTMAS_LIGHTS, PINK_CHRISTMAS_LIGHTS);

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
            CYAN_ORNAMENT, LIGHT_BLUE_ORNAMENT, BLUE_ORNAMENT, PURPLE_ORNAMENT, MAGENTA_ORNAMENT, PINK_ORNAMENT);


    public static Block registerOrnament(String name) {
        return register(
            new OrnamentBlock(BlockBehaviour.Properties.of()
                                                    .setId(ResourceKey.create(Registries.BLOCK, RainbowChristmas.id(name)))
                                                    .sound(SoundType.GLASS)
                                                    .noOcclusion()
                                                    .isRedstoneConductor(Blocks::never)
                                                    .isSuffocating(Blocks::never)
                                                    .isViewBlocking(Blocks::never)),
            name, true
        );
    }

    public static Block registerGarland(String name) {
        return register(
            new GarlandBlock(BlockBehaviour.Properties.of()
                                                   .setId(ResourceKey.create(Registries.BLOCK, RainbowChristmas.id(name)))
                                                   .sound(SoundType.GRASS)
                                                   .mapColor(MapColor.NONE)
                                                   .noOcclusion()
                                                   .isRedstoneConductor(Blocks::never)
                                                   .isSuffocating(Blocks::never)
                                                   .isViewBlocking(Blocks::never)),
            name, true
        );
    }

    public static Block registerChristmasLights(String name) {
        return register(
            new ChristmasLightsBlock(BlockBehaviour.Properties.of()
                                                           .setId(ResourceKey.create(Registries.BLOCK, RainbowChristmas.id(name)))
                                                           .sound(SoundType.WOOD)
                                                           .mapColor(MapColor.NONE)
                                                           .noOcclusion()
                                                           .isRedstoneConductor(Blocks::never)
                                                           .isSuffocating(Blocks::never)
                                                           .isViewBlocking(Blocks::never)
                                                           .lightLevel(value -> 15)),
            name, true
        );
    }

    public static Block registerHolidayLeaves(String name, MapColor color) {
        return register(
            new HolidayLeavesBlock(
                0.0f, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                                            .setId(ResourceKey.create(Registries.BLOCK, RainbowChristmas.id(name)))
                                            .mapColor(color)
                                            .noOcclusion()
                                            .isRedstoneConductor(Blocks::never)
                                            .isSuffocating(Blocks::never)
                                            .isViewBlocking(Blocks::never)
            ),
            name, true
        );
    }

    public static Block register(Block block, String name, boolean makeItem) {
        Identifier id = RainbowChristmas.id(name);
        if (makeItem) {
            var blockItem = new BlockItem(
                block, new Item.Properties().useBlockDescriptionPrefix()
                                          .setId(ResourceKey.create(Registries.ITEM, RainbowChristmas.id(name)))
            );
            ModItems.register(blockItem, name);
        }
        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static ResourceKey<Block> keyOfBlock(String name) {
        return ResourceKey.create(Registries.BLOCK, RainbowChristmas.id(name));
    }

    public static void onInitialize() {}
}
