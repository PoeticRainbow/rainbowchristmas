package poeticrainbow.rainbowchristmas.gen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;
import poeticrainbow.rainbowchristmas.RainbowChristmas;
import poeticrainbow.rainbowchristmas.registry.ModBlocks;
import poeticrainbow.rainbowchristmas.registry.ModItems;
import poeticrainbow.rainbowchristmas.render.RainbowTintSource;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModModels::new);
        pack.addProvider(ModBlockTags::new);
        pack.addProvider(ModItemTags::new);
        pack.addProvider(ModRecipes::new);
        pack.addProvider(ModBlockLootTables::new);
        pack.addProvider(ModChestLootTables::new);
    }

    public static class ModModels extends FabricModelProvider {
        public ModModels(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators generator) {
            generator.registerSimpleTintedItemModel(
                    ModBlocks.RAINBOW_HOLIDAY_LEAVES,
                    ModelLocationUtils.getModelLocation(ModBlocks.RAINBOW_HOLIDAY_LEAVES),
                    new RainbowTintSource());
        }

        @Override
        public void generateItemModels(ItemModelGenerators generator) {
            ModBlocks.GARLANDS.forEach(block -> generator.declareCustomModelItem(block.asItem()));
            ModBlocks.CHRISTMAS_LIGHTS.forEach(block -> generator.declareCustomModelItem(block.asItem()));
            ModBlocks.ORNAMENTS.forEach(block -> generator.declareCustomModelItem(block.asItem()));

            generator.generateFlatItem(
                ModBlocks.NORTH_POLE.asItem(),
                new ModelTemplate(Optional.of(RainbowChristmas.id("block/north_pole_middle")), Optional.empty())
            );

            ModItems.FESTIVE_HATS.forEach(generator::declareCustomModelItem);

            generator.declareCustomModelItem(ModBlocks.MISTLETOE.asItem());
            generator.declareCustomModelItem(ModBlocks.WREATH.asItem());
            generator.declareCustomModelItem(ModBlocks.STAR_TOPPER.asItem());
            generator.declareCustomModelItem(ModBlocks.ICICLE.asItem());

            generator.declareCustomModelItem(ModItems.HARD_CANDY);
            generator.declareCustomModelItem(ModItems.CANDY_CANE);
            generator.declareCustomModelItem(ModItems.GINGERBREAD_COOKIE);
            generator.declareCustomModelItem(ModItems.CHOCOLATE_MILK_BUCKET);
            generator.declareCustomModelItem(ModItems.HOT_CHOCOLATE_BUCKET);
            generator.declareCustomModelItem(ModItems.WARM_SPICES);
        }
    }

    public static class ModBlockTags extends FabricTagProvider.BlockTagProvider {
        public static final TagKey<Block> PLANT_SUSTAINABLE = TagKey.create(Registries.BLOCK, RainbowChristmas.id("plant_sustainable"));
        public static final TagKey<Block> GARLAND = TagKey.create(Registries.BLOCK, RainbowChristmas.id("garland"));

        public ModBlockTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider wrapperLookup) {
            valueLookupBuilder(PLANT_SUSTAINABLE).add(ModBlocks.CHRISTMAS_TREE_STAND);
            valueLookupBuilder(BlockTags.DRY_VEGETATION_MAY_PLACE_ON).add(ModBlocks.CHRISTMAS_TREE_STAND);

            // Leaves
            var leavesBuilder = valueLookupBuilder(BlockTags.LEAVES);
            ModBlocks.HOLIDAY_LEAVES.forEach(leavesBuilder::add);

            // Garland
            var garlandBuilder = valueLookupBuilder(GARLAND);
            ModBlocks.GARLANDS.forEach(garlandBuilder::add);

            // Hoe Mineable
            var hoeMineableBuilder = valueLookupBuilder(BlockTags.MINEABLE_WITH_HOE);
            ModBlocks.GARLANDS.forEach(hoeMineableBuilder::add);
            ModBlocks.HOLIDAY_LEAVES.forEach(hoeMineableBuilder::add);

            var pickaxeMineableBuilder = valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE);
            pickaxeMineableBuilder.add(ModBlocks.HARD_CANDY_BLOCK);
            pickaxeMineableBuilder.add(ModBlocks.HARD_CANDY_PILLAR);
            pickaxeMineableBuilder.add(ModBlocks.HARD_CANDY_TILES);
            pickaxeMineableBuilder.add(ModBlocks.HARD_CANDY_TILE_STAIRS);
            pickaxeMineableBuilder.add(ModBlocks.HARD_CANDY_TILE_SLAB);

            pickaxeMineableBuilder.add(ModBlocks.CANDY_CANE_PILLAR);
            pickaxeMineableBuilder.add(ModBlocks.CANDY_CANE_TILES);
            pickaxeMineableBuilder.add(ModBlocks.CANDY_CANE_TILE_STAIRS);
            pickaxeMineableBuilder.add(ModBlocks.CANDY_CANE_TILE_SLAB);
            pickaxeMineableBuilder.add(ModBlocks.NORTH_POLE);

            pickaxeMineableBuilder.add(ModBlocks.GINGERBREAD_BLOCK);
            pickaxeMineableBuilder.add(ModBlocks.GINGERBREAD_BLOCK_STAIRS);
            pickaxeMineableBuilder.add(ModBlocks.GINGERBREAD_BLOCK_SLAB);

            pickaxeMineableBuilder.add(ModBlocks.GINGERBREAD_BRICKS);
            pickaxeMineableBuilder.add(ModBlocks.GINGERBREAD_BRICK_STAIRS);
            pickaxeMineableBuilder.add(ModBlocks.GINGERBREAD_BRICK_SLAB);

            pickaxeMineableBuilder.add(ModBlocks.GINGERBREAD_SHINGLES);
            pickaxeMineableBuilder.add(ModBlocks.GINGERBREAD_SHINGLE_STAIRS);
            pickaxeMineableBuilder.add(ModBlocks.GINGERBREAD_SHINGLE_SLAB);
        }
    }

    public static class ModItemTags extends FabricTagProvider.ItemTagProvider {
        public static final TagKey<Item> ORNAMENT = TagKey.create(Registries.ITEM, RainbowChristmas.id("ornament"));
        public static final TagKey<Item> GARLAND = TagKey.create(Registries.ITEM, RainbowChristmas.id("garland"));
        public static final TagKey<Item> ICE = TagKey.create(Registries.ITEM, RainbowChristmas.id("ice"));

        public ModItemTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider wrapperLookup) {
            var ornamentBuilder = valueLookupBuilder(ORNAMENT);
            ModBlocks.ORNAMENTS.forEach(block -> ornamentBuilder.add(block.asItem()));

            var garlandBuilder = valueLookupBuilder(GARLAND);
            ModBlocks.GARLANDS.forEach(block -> garlandBuilder.add(block.asItem()));

            var leavesBuilder = valueLookupBuilder(ItemTags.LEAVES);
            ModBlocks.HOLIDAY_LEAVES.forEach(block -> leavesBuilder.add(block.asItem()));

            var iceBuilder = valueLookupBuilder(ICE);
            iceBuilder.add(Items.ICE, Items.PACKED_ICE, Items.BLUE_ICE);
        }
    }

    public static class ModRecipes extends FabricRecipeProvider {
        public ModRecipes(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        public @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider registries, @NotNull RecipeOutput consumer) {
            return new RecipeProvider(registries, consumer) {
                @Override
                public void buildRecipes() {
                    shaped(RecipeCategory.DECORATIONS, ModBlocks.CLEAR_ORNAMENT).pattern("a")
                            .pattern("b")
                            .define('a', Items.STRING)
                            .define('b', Items.GLASS_BOTTLE)
                            .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                            .unlockedBy(getHasName(Items.GLASS_BOTTLE), has(Items.GLASS_BOTTLE))
                            .save(output);


                    shaped(RecipeCategory.DECORATIONS, ModBlocks.WREATH)
                            .pattern("#X#")
                            .pattern("# #")
                            .pattern("###")
                            .define('#', ItemTags.LEAVES)
                            .define('X', Items.SWEET_BERRIES)
                            .unlockedBy("has_leaves", has(ItemTags.LEAVES))
                            .save(output);

                    shaped(RecipeCategory.DECORATIONS, ModBlocks.STAR_TOPPER)
                            .pattern("#X#")
                            .pattern("XXX")
                            .pattern("#$#")
                            .define('#', Items.GLOWSTONE_DUST)
                            .define('X', Items.GLOWSTONE)
                            .define('$', Items.STICK)
                            .unlockedBy(getHasName(Items.GLOWSTONE_DUST), has(Items.GLOWSTONE_DUST))
                            .unlockedBy(getHasName(Items.GLOWSTONE), has(Items.GLOWSTONE))
                            .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                            .save(output);

                    shaped(RecipeCategory.DECORATIONS, ModBlocks.MISTLETOE)
                            .pattern("$#$")
                            .pattern("#X#")
                            .pattern("%#%")
                            .define('#', ItemTags.LEAVES)
                            .define('X', ConventionalItemTags.DIAMOND_GEMS)
                            .define('$', Items.STRING)
                            .define('%', Items.SWEET_BERRIES)
                            .unlockedBy("has_leaves", has(ItemTags.LEAVES))
                            .unlockedBy("has_diamonds", has(ConventionalItemTags.DIAMOND_GEMS))
                            .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                            .unlockedBy(getHasName(Items.SWEET_BERRIES), has(Items.SWEET_BERRIES))
                            .save(output);

                    shaped(RecipeCategory.MISC, ModBlocks.SHREDDER)
                            .pattern("#$#")
                            .pattern("#%#")
                            .pattern("#$#")
                            .define('#', ItemTags.STONE_TOOL_MATERIALS)
                            .define('$', Items.REDSTONE)
                            .define('%', Items.SHEARS)
                            .unlockedBy("has_cobble", has(ItemTags.STONE_TOOL_MATERIALS))
                            .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                            .save(consumer);

                    shaped(RecipeCategory.DECORATIONS, ModBlocks.CHRISTMAS_TREE_STAND)
                            .pattern("#$#")
                            .pattern("#%#")
                            .pattern("###")
                            .define('#', Items.BRICK)
                            .define('$', Blocks.DIRT)
                            .define('%', Items.LIGHT_BLUE_DYE)
                            .unlockedBy(getHasName(Items.BRICK), has(Items.BRICK))
                            .save(output);

                    shaped(RecipeCategory.DECORATIONS, ModBlocks.ICICLE, 8)
                            .pattern("#")
                            .pattern("#")
                            .define('#', ModItemTags.ICE)
                            .unlockedBy("has_ice", has(ModItemTags.ICE))
                            .save(output);

                    shaped(RecipeCategory.FOOD, ModItems.CANDY_CANE, 8)
                            .pattern("#$")
                            .pattern("X#")
                            .define('#', ModItems.HARD_CANDY)
                            .define('$', Items.RED_DYE)
                            .define('X', Items.WHITE_DYE)
                            .unlockedBy(getHasName(Items.SUGAR), has(Items.SUGAR))
                            .save(output);

                    shaped(RecipeCategory.FOOD, ModItems.CANDY_CANE, 8)
                            .pattern("#X")
                            .pattern("$#")
                            .define('#', ModItems.HARD_CANDY)
                            .define('$', Items.RED_DYE)
                            .define('X', Items.WHITE_DYE)
                            .unlockedBy(getHasName(Items.SUGAR), has(Items.SUGAR))
                            .save(output, "candy_cane_flipped");

                    // Hard Candy
                    shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HARD_CANDY_BLOCK)
                            .pattern("##")
                            .pattern("##")
                            .define('#', ModItems.HARD_CANDY)
                            .unlockedBy(getHasName(ModItems.HARD_CANDY), has(ModItems.HARD_CANDY))
                            .save(output);
                    shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HARD_CANDY_TILES, 4)
                            .pattern("##")
                            .pattern("##")
                            .define('#', ModBlocks.HARD_CANDY_BLOCK)
                            .unlockedBy(getHasName(ModItems.HARD_CANDY), has(ModItems.HARD_CANDY))
                            .save(output);
                    stairBuilder(ModBlocks.HARD_CANDY_TILE_STAIRS, Ingredient.of(ModBlocks.HARD_CANDY_TILES))
                            .unlockedBy(getHasName(ModBlocks.HARD_CANDY_TILES), has(ModBlocks.HARD_CANDY_TILES))
                            .save(output);
                    slabBuilder(
                                    RecipeCategory.BUILDING_BLOCKS,
                                    ModBlocks.HARD_CANDY_TILE_SLAB, Ingredient.of(ModBlocks.HARD_CANDY_TILES))
                            .unlockedBy(getHasName(ModBlocks.HARD_CANDY_TILES), has(ModBlocks.HARD_CANDY_TILES))
                            .save(output);
                    shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HARD_CANDY_PILLAR, 2)
                            .pattern("#")
                            .pattern("#")
                            .define('#', ModBlocks.HARD_CANDY_TILES)
                            .unlockedBy(getHasName(ModBlocks.HARD_CANDY_TILES), has(ModBlocks.HARD_CANDY_TILES))
                            .save(output);

                    // Candy Cane
                    create2x2ConversionRecipe(ModBlocks.CANDY_CANE_TILES, ModItems.CANDY_CANE).save(output);
                    stairBuilder(ModBlocks.CANDY_CANE_TILE_STAIRS, Ingredient.of(ModBlocks.CANDY_CANE_TILES))
                            .unlockedBy(getHasName(ModBlocks.CANDY_CANE_TILES), has(ModBlocks.CANDY_CANE_TILES))
                            .save(output);
                    slabBuilder(
                                    RecipeCategory.BUILDING_BLOCKS,
                                    ModBlocks.CANDY_CANE_TILE_SLAB, Ingredient.of(ModBlocks.CANDY_CANE_TILES))
                            .unlockedBy(getHasName(ModBlocks.CANDY_CANE_TILES), has(ModBlocks.CANDY_CANE_TILES))
                            .save(output);
                    shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CANDY_CANE_PILLAR, 2)
                            .pattern("#")
                            .pattern("#")
                            .define('#', ModBlocks.CANDY_CANE_TILES)
                            .unlockedBy(getHasName(ModBlocks.CANDY_CANE_TILES), has(ModBlocks.CANDY_CANE_TILES))
                            .save(output);

                    wallBuilder(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NORTH_POLE, Ingredient.of(ModBlocks.CANDY_CANE_TILES))
                            .unlockedBy(getHasName(ModBlocks.CANDY_CANE_TILES), has(ModBlocks.CANDY_CANE_TILES))
                            .save(output);

                    // Gingerbread
                    create2x2ConversionRecipe(ModBlocks.GINGERBREAD_BLOCK, ModItems.GINGERBREAD_COOKIE).save(output);
                    stairBuilder(ModBlocks.GINGERBREAD_BLOCK_STAIRS, Ingredient.of(ModBlocks.GINGERBREAD_BLOCK))
                            .unlockedBy(getHasName(ModBlocks.GINGERBREAD_BLOCK), has(ModBlocks.GINGERBREAD_BLOCK))
                            .save(output);
                    slabBuilder(
                                    RecipeCategory.BUILDING_BLOCKS,
                                    ModBlocks.GINGERBREAD_BLOCK_SLAB, Ingredient.of(ModBlocks.GINGERBREAD_BLOCK))
                            .unlockedBy(getHasName(ModBlocks.GINGERBREAD_BLOCK), has(ModBlocks.GINGERBREAD_BLOCK))
                            .save(output);

                    create2x2ConversionRecipe(ModBlocks.GINGERBREAD_BRICKS, ModBlocks.GINGERBREAD_BLOCK).save(output);
                    stairBuilder(ModBlocks.GINGERBREAD_BRICK_STAIRS, Ingredient.of(ModBlocks.GINGERBREAD_BRICKS))
                            .unlockedBy(getHasName(ModBlocks.GINGERBREAD_BRICKS), has(ModBlocks.GINGERBREAD_BRICKS))
                            .save(output);
                    slabBuilder(
                                    RecipeCategory.BUILDING_BLOCKS,
                                    ModBlocks.GINGERBREAD_BRICK_SLAB, Ingredient.of(ModBlocks.GINGERBREAD_BRICKS))
                            .unlockedBy(getHasName(ModBlocks.GINGERBREAD_BRICKS), has(ModBlocks.GINGERBREAD_BRICKS))
                            .save(output);


                    create2x2ConversionRecipe(ModBlocks.GINGERBREAD_SHINGLES, ModBlocks.GINGERBREAD_BRICKS).save(output);
                    stairBuilder(ModBlocks.GINGERBREAD_SHINGLE_STAIRS, Ingredient.of(ModBlocks.GINGERBREAD_SHINGLES))
                            .unlockedBy(getHasName(ModBlocks.GINGERBREAD_SHINGLES), has(ModBlocks.GINGERBREAD_SHINGLES))
                            .save(output);
                    slabBuilder(RecipeCategory.BUILDING_BLOCKS,
                                    ModBlocks.GINGERBREAD_SHINGLE_SLAB, Ingredient.of(ModBlocks.GINGERBREAD_SHINGLES))
                            .unlockedBy(getHasName(ModBlocks.GINGERBREAD_SHINGLES), has(ModBlocks.GINGERBREAD_SHINGLES))
                            .save(output);

                    // Hard Candy Item
                    SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.SUGAR), RecipeCategory.FOOD, ModItems.HARD_CANDY, 0f, 200)
                            .unlockedBy(getHasName(Items.SUGAR), has(Items.SUGAR))
                            .save(output);
                    SimpleCookingRecipeBuilder.smoking(Ingredient.of(Items.SUGAR), RecipeCategory.FOOD, ModItems.HARD_CANDY, 0f, 100)
                            .unlockedBy(getHasName(Items.SUGAR), has(Items.SUGAR))
                            .save(output, "hard_candy_smoking");

                    shaped(RecipeCategory.FOOD, ModItems.GINGERBREAD_COOKIE, 8)
                            .pattern(" # ")
                            .pattern("$%$")
                            .define('#', ModItems.WARM_SPICES)
                            .define('$', Items.WHEAT)
                            .define('%', Items.SUGAR)
                            .unlockedBy(getHasName(Items.SUGAR), has(Items.SUGAR))
                            .save(output);

                    shapeless(RecipeCategory.FOOD, ModItems.CHOCOLATE_MILK_BUCKET)
                            .requires(Items.COCOA_BEANS)
                            .requires(Items.MILK_BUCKET)
                            .requires(Items.SUGAR)
                            .unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET))
                            .save(output);

                    SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CHOCOLATE_MILK_BUCKET), RecipeCategory.FOOD, ModItems.HOT_CHOCOLATE_BUCKET,
                                    0f, 200)
                            .unlockedBy(getHasName(ModItems.CHOCOLATE_MILK_BUCKET), has(ModItems.CHOCOLATE_MILK_BUCKET))
                            .save(output);
                    SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModItems.CHOCOLATE_MILK_BUCKET), RecipeCategory.FOOD, ModItems.HOT_CHOCOLATE_BUCKET,
                                    0f, 100)
                            .unlockedBy(getHasName(ModItems.CHOCOLATE_MILK_BUCKET), has(ModItems.CHOCOLATE_MILK_BUCKET))
                            .save(output, "hot_chocolate_bucket_smoking");

                    createFestiveHatRecipe(ModItems.WHITE_FESTIVE_HAT.asItem(), Items.WHITE_DYE).save(output);
                    createFestiveHatRecipe(ModItems.LIGHT_GRAY_FESTIVE_HAT.asItem(), Items.LIGHT_GRAY_DYE).save(output);
                    createFestiveHatRecipe(ModItems.GRAY_FESTIVE_HAT.asItem(), Items.GRAY_DYE).save(output);
                    createFestiveHatRecipe(ModItems.BLACK_FESTIVE_HAT.asItem(), Items.BLACK_DYE).save(output);
                    createFestiveHatRecipe(ModItems.BROWN_FESTIVE_HAT.asItem(), Items.BROWN_DYE).save(output);
                    createFestiveHatRecipe(ModItems.RED_FESTIVE_HAT.asItem(), Items.RED_DYE).save(output);
                    createFestiveHatRecipe(ModItems.ORANGE_FESTIVE_HAT.asItem(), Items.ORANGE_DYE).save(output);
                    createFestiveHatRecipe(ModItems.YELLOW_FESTIVE_HAT.asItem(), Items.YELLOW_DYE).save(output);
                    createFestiveHatRecipe(ModItems.LIME_FESTIVE_HAT.asItem(), Items.LIME_DYE).save(output);
                    createFestiveHatRecipe(ModItems.GREEN_FESTIVE_HAT.asItem(), Items.GREEN_DYE).save(output);
                    createFestiveHatRecipe(ModItems.CYAN_FESTIVE_HAT.asItem(), Items.CYAN_DYE).save(output);
                    createFestiveHatRecipe(ModItems.LIGHT_BLUE_FESTIVE_HAT.asItem(), Items.LIGHT_BLUE_DYE).save(output);
                    createFestiveHatRecipe(ModItems.BLUE_FESTIVE_HAT.asItem(), Items.BLUE_DYE).save(output);
                    createFestiveHatRecipe(ModItems.PURPLE_FESTIVE_HAT.asItem(), Items.PURPLE_DYE).save(output);
                    createFestiveHatRecipe(ModItems.MAGENTA_FESTIVE_HAT.asItem(), Items.MAGENTA_DYE).save(output);
                    createFestiveHatRecipe(ModItems.PINK_FESTIVE_HAT.asItem(), Items.PINK_DYE).save(output);

                    createDyedOrnamentRecipe(ModBlocks.WHITE_ORNAMENT.asItem(), Items.WHITE_DYE).save(output);
                    createDyedOrnamentRecipe(ModBlocks.LIGHT_GRAY_ORNAMENT.asItem(), Items.LIGHT_GRAY_DYE).save(output);
                    createDyedOrnamentRecipe(ModBlocks.GRAY_ORNAMENT.asItem(), Items.GRAY_DYE).save(output);
                    createDyedOrnamentRecipe(ModBlocks.BLACK_ORNAMENT.asItem(), Items.BLACK_DYE).save(output);
                    createDyedOrnamentRecipe(ModBlocks.BROWN_ORNAMENT.asItem(), Items.BROWN_DYE).save(output);
                    createDyedOrnamentRecipe(ModBlocks.RED_ORNAMENT.asItem(), Items.RED_DYE).save(output);
                    createDyedOrnamentRecipe(ModBlocks.ORANGE_ORNAMENT.asItem(), Items.ORANGE_DYE).save(output);
                    createDyedOrnamentRecipe(ModBlocks.YELLOW_ORNAMENT.asItem(), Items.YELLOW_DYE).save(output);
                    createDyedOrnamentRecipe(ModBlocks.LIME_ORNAMENT.asItem(), Items.LIME_DYE).save(output);
                    createDyedOrnamentRecipe(ModBlocks.GREEN_ORNAMENT.asItem(), Items.GREEN_DYE).save(output);
                    createDyedOrnamentRecipe(ModBlocks.CYAN_ORNAMENT.asItem(), Items.CYAN_DYE).save(output);
                    createDyedOrnamentRecipe(ModBlocks.LIGHT_BLUE_ORNAMENT.asItem(), Items.LIGHT_BLUE_DYE).save(output);
                    createDyedOrnamentRecipe(ModBlocks.BLUE_ORNAMENT.asItem(), Items.BLUE_DYE).save(output);
                    createDyedOrnamentRecipe(ModBlocks.PURPLE_ORNAMENT.asItem(), Items.PURPLE_DYE).save(output);
                    createDyedOrnamentRecipe(ModBlocks.MAGENTA_ORNAMENT.asItem(), Items.MAGENTA_DYE).save(output);
                    createDyedOrnamentRecipe(ModBlocks.PINK_ORNAMENT.asItem(), Items.PINK_DYE).save(output);

                    createDyedGarlandRecipe(ModBlocks.WHITE_GARLAND.asItem(), Items.WHITE_DYE).save(output);
                    createDyedGarlandRecipe(ModBlocks.LIGHT_GRAY_GARLAND.asItem(), Items.LIGHT_GRAY_DYE).save(output);
                    createDyedGarlandRecipe(ModBlocks.GRAY_GARLAND.asItem(), Items.GRAY_DYE).save(output);
                    createDyedGarlandRecipe(ModBlocks.BLACK_GARLAND.asItem(), Items.BLACK_DYE).save(output);
                    createDyedGarlandRecipe(ModBlocks.BROWN_GARLAND.asItem(), Items.BROWN_DYE).save(output);
                    createDyedGarlandRecipe(ModBlocks.RED_GARLAND.asItem(), Items.RED_DYE).save(output);
                    createDyedGarlandRecipe(ModBlocks.ORANGE_GARLAND.asItem(), Items.ORANGE_DYE).save(output);
                    createDyedGarlandRecipe(ModBlocks.YELLOW_GARLAND.asItem(), Items.YELLOW_DYE).save(output);
                    createDyedGarlandRecipe(ModBlocks.LIME_GARLAND.asItem(), Items.LIME_DYE).save(output);
                    createDyedGarlandRecipe(ModBlocks.GREEN_GARLAND.asItem(), Items.GREEN_DYE).save(output);
                    createDyedGarlandRecipe(ModBlocks.CYAN_GARLAND.asItem(), Items.CYAN_DYE).save(output);
                    createDyedGarlandRecipe(ModBlocks.LIGHT_BLUE_GARLAND.asItem(), Items.LIGHT_BLUE_DYE).save(output);
                    createDyedGarlandRecipe(ModBlocks.BLUE_GARLAND.asItem(), Items.BLUE_DYE).save(output);
                    createDyedGarlandRecipe(ModBlocks.PURPLE_GARLAND.asItem(), Items.PURPLE_DYE).save(output);
                    createDyedGarlandRecipe(ModBlocks.MAGENTA_GARLAND.asItem(), Items.MAGENTA_DYE).save(output);
                    createDyedGarlandRecipe(ModBlocks.PINK_GARLAND.asItem(), Items.PINK_DYE).save(output);

                    createGarlandFromHolidayLeavesRecipe(ModBlocks.WHITE_GARLAND.asItem(), ModBlocks.WHITE_HOLIDAY_LEAVES.asItem()).save(output, "white_garland_from_holiday_leaves");
                    createGarlandFromHolidayLeavesRecipe(ModBlocks.LIGHT_GRAY_GARLAND.asItem(), ModBlocks.LIGHT_GRAY_HOLIDAY_LEAVES.asItem()).save(output, "light_gray_garland_from_holiday_leaves");
                    createGarlandFromHolidayLeavesRecipe(ModBlocks.GRAY_GARLAND.asItem(), ModBlocks.GRAY_HOLIDAY_LEAVES.asItem()).save(output, "gray_garland_from_holiday_leaves");
                    createGarlandFromHolidayLeavesRecipe(ModBlocks.BLACK_GARLAND.asItem(), ModBlocks.BLACK_HOLIDAY_LEAVES.asItem()).save(output, "black_garland_from_holiday_leaves");
                    createGarlandFromHolidayLeavesRecipe(ModBlocks.BROWN_GARLAND.asItem(), ModBlocks.BROWN_HOLIDAY_LEAVES.asItem()).save(output, "brown_garland_from_holiday_leaves");
                    createGarlandFromHolidayLeavesRecipe(ModBlocks.RED_GARLAND.asItem(), ModBlocks.RED_HOLIDAY_LEAVES.asItem()).save(output, "red_garland_from_holiday_leaves");
                    createGarlandFromHolidayLeavesRecipe(ModBlocks.ORANGE_GARLAND.asItem(), ModBlocks.ORANGE_HOLIDAY_LEAVES.asItem()).save(output, "orange_garland_from_holiday_leaves");
                    createGarlandFromHolidayLeavesRecipe(ModBlocks.YELLOW_GARLAND.asItem(), ModBlocks.YELLOW_HOLIDAY_LEAVES.asItem()).save(output, "yellow_garland_from_holiday_leaves");
                    createGarlandFromHolidayLeavesRecipe(ModBlocks.LIME_GARLAND.asItem(), ModBlocks.LIME_HOLIDAY_LEAVES.asItem()).save(output, "lime_garland_from_holiday_leaves");
                    createGarlandFromHolidayLeavesRecipe(ModBlocks.GREEN_GARLAND.asItem(), ModBlocks.GREEN_HOLIDAY_LEAVES.asItem()).save(output, "green_garland_from_holiday_leaves");
                    createGarlandFromHolidayLeavesRecipe(ModBlocks.CYAN_GARLAND.asItem(), ModBlocks.CYAN_HOLIDAY_LEAVES.asItem()).save(output, "cyan_garland_from_holiday_leaves");
                    createGarlandFromHolidayLeavesRecipe(ModBlocks.LIGHT_BLUE_GARLAND.asItem(), ModBlocks.LIGHT_BLUE_HOLIDAY_LEAVES.asItem()).save(output, "light_blue_garland_from_holiday_leaves");
                    createGarlandFromHolidayLeavesRecipe(ModBlocks.BLUE_GARLAND.asItem(), ModBlocks.BLUE_HOLIDAY_LEAVES.asItem()).save(output, "blue_garland_from_holiday_leaves");
                    createGarlandFromHolidayLeavesRecipe(ModBlocks.PURPLE_GARLAND.asItem(), ModBlocks.PURPLE_HOLIDAY_LEAVES.asItem()).save(output, "purple_garland_from_holiday_leaves");
                    createGarlandFromHolidayLeavesRecipe(ModBlocks.MAGENTA_GARLAND.asItem(), ModBlocks.MAGENTA_HOLIDAY_LEAVES.asItem()).save(output, "magenta_garland_from_holiday_leaves");
                    createGarlandFromHolidayLeavesRecipe(ModBlocks.PINK_GARLAND.asItem(), ModBlocks.PINK_HOLIDAY_LEAVES.asItem()).save(output, "pink_garland_from_holiday_leaves");

                    createDyedHolidayLeavesRecipe(ModBlocks.RAINBOW_HOLIDAY_LEAVES.asItem(), Items.AMETHYST_SHARD).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.WHITE_HOLIDAY_LEAVES.asItem(), Items.WHITE_DYE).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.LIGHT_GRAY_HOLIDAY_LEAVES.asItem(), Items.LIGHT_GRAY_DYE).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.GRAY_HOLIDAY_LEAVES.asItem(), Items.GRAY_DYE).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.BLACK_HOLIDAY_LEAVES.asItem(), Items.BLACK_DYE).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.BROWN_HOLIDAY_LEAVES.asItem(), Items.BROWN_DYE).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.RED_HOLIDAY_LEAVES.asItem(), Items.RED_DYE).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.ORANGE_HOLIDAY_LEAVES.asItem(), Items.ORANGE_DYE).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.YELLOW_HOLIDAY_LEAVES.asItem(), Items.YELLOW_DYE).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.LIME_HOLIDAY_LEAVES.asItem(), Items.LIME_DYE).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.GREEN_HOLIDAY_LEAVES.asItem(), Items.GREEN_DYE).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.CYAN_HOLIDAY_LEAVES.asItem(), Items.CYAN_DYE).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.LIGHT_BLUE_HOLIDAY_LEAVES.asItem(), Items.LIGHT_BLUE_DYE).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.BLUE_HOLIDAY_LEAVES.asItem(), Items.BLUE_DYE).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.PURPLE_HOLIDAY_LEAVES.asItem(), Items.PURPLE_DYE).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.MAGENTA_HOLIDAY_LEAVES.asItem(), Items.MAGENTA_DYE).save(output);
                    createDyedHolidayLeavesRecipe(ModBlocks.PINK_HOLIDAY_LEAVES.asItem(), Items.PINK_DYE).save(output);

                    createChristmasLightsRecipe(ModBlocks.MULTICOLOR_CHRISTMAS_LIGHTS.asItem(), Items.AMETHYST_SHARD).save(output);
                    createChristmasLightsRecipe(ModBlocks.WHITE_CHRISTMAS_LIGHTS.asItem(), Items.WHITE_DYE).save(output);
                    createChristmasLightsRecipe(ModBlocks.LIGHT_GRAY_CHRISTMAS_LIGHTS.asItem(), Items.LIGHT_GRAY_DYE).save(output);
                    createChristmasLightsRecipe(ModBlocks.GRAY_CHRISTMAS_LIGHTS.asItem(), Items.GRAY_DYE).save(output);
                    createChristmasLightsRecipe(ModBlocks.BLACK_CHRISTMAS_LIGHTS.asItem(), Items.BLACK_DYE).save(output);
                    createChristmasLightsRecipe(ModBlocks.BROWN_CHRISTMAS_LIGHTS.asItem(), Items.BROWN_DYE).save(output);
                    createChristmasLightsRecipe(ModBlocks.RED_CHRISTMAS_LIGHTS.asItem(), Items.RED_DYE).save(output);
                    createChristmasLightsRecipe(ModBlocks.ORANGE_CHRISTMAS_LIGHTS.asItem(), Items.ORANGE_DYE).save(output);
                    createChristmasLightsRecipe(ModBlocks.YELLOW_CHRISTMAS_LIGHTS.asItem(), Items.YELLOW_DYE).save(output);
                    createChristmasLightsRecipe(ModBlocks.LIME_CHRISTMAS_LIGHTS.asItem(), Items.LIME_DYE).save(output);
                    createChristmasLightsRecipe(ModBlocks.GREEN_CHRISTMAS_LIGHTS.asItem(), Items.GREEN_DYE).save(output);
                    createChristmasLightsRecipe(ModBlocks.CYAN_CHRISTMAS_LIGHTS.asItem(), Items.CYAN_DYE).save(output);
                    createChristmasLightsRecipe(ModBlocks.LIGHT_BLUE_CHRISTMAS_LIGHTS.asItem(), Items.LIGHT_BLUE_DYE).save(output);
                    createChristmasLightsRecipe(ModBlocks.BLUE_CHRISTMAS_LIGHTS.asItem(), Items.BLUE_DYE).save(output);
                    createChristmasLightsRecipe(ModBlocks.PURPLE_CHRISTMAS_LIGHTS.asItem(), Items.PURPLE_DYE).save(output);
                    createChristmasLightsRecipe(ModBlocks.MAGENTA_CHRISTMAS_LIGHTS.asItem(), Items.MAGENTA_DYE).save(output);
                    createChristmasLightsRecipe(ModBlocks.PINK_CHRISTMAS_LIGHTS.asItem(), Items.PINK_DYE).save(output);
                }

                public ShapedRecipeBuilder create2x2ConversionRecipe(ItemLike output, ItemLike input) {
                    return shaped(RecipeCategory.BUILDING_BLOCKS, output, 4)
                            .pattern("##")
                            .pattern("##")
                            .define('#', input)
                            .unlockedBy(getHasName(input), has(input));
                }

                public ShapedRecipeBuilder createFestiveHatRecipe(Item hat, Item dye) {
                    return shaped(RecipeCategory.COMBAT, hat)
                            .pattern("###")
                            .pattern("#$#")
                            .define('#', ItemTags.WOOL)
                            .define('$', dye)
                            .group("festive_hat")
                            .unlockedBy("has_wool", has(ItemTags.WOOL))
                            .unlockedBy(getHasName(dye), has(dye));
                }

                public ShapedRecipeBuilder createDyedOrnamentRecipe(Item ornament, Item dye) {
                    return shaped(RecipeCategory.DECORATIONS, ornament, 4)
                            .pattern(" # ")
                            .pattern("#X#")
                            .pattern(" # ")
                            .define('#', ModItemTags.ORNAMENT)
                            .define('X', dye)
                            .group("ornament")
                            .unlockedBy(getHasName(ModBlocks.CLEAR_ORNAMENT), has(ModBlocks.CLEAR_ORNAMENT));
                }

                public ShapedRecipeBuilder createDyedGarlandRecipe(Item garland, Item dye) {
                    return shaped(RecipeCategory.DECORATIONS, garland, 6)
                            .pattern("$X$")
                            .pattern("###")
                            .define('#', ItemTags.LEAVES)
                            .define('$', Items.STRING)
                            .define('X', dye)
                            .group("garland")
                            .unlockedBy("has_leaves", has(ItemTags.LEAVES));
                }

                public ShapedRecipeBuilder createGarlandFromHolidayLeavesRecipe(Item garland, Item holidayLeaves) {
                    return shaped(RecipeCategory.DECORATIONS, garland, 6)
                            .pattern("$ $")
                            .pattern("###")
                            .define('#', holidayLeaves)
                            .define('$', Items.STRING)
                            .group("garland_from_holiday_leaves")
                            .unlockedBy(getHasName(holidayLeaves), has(holidayLeaves));
                }

                public ShapedRecipeBuilder createDyedHolidayLeavesRecipe(Item leaves, Item dye) {
                    return shaped(RecipeCategory.DECORATIONS, leaves, 8)
                            .pattern("###")
                            .pattern("#X#")
                            .pattern("###")
                            .define('#', ItemTags.LEAVES)
                            .define('X', dye)
                            .group("holiday_leaves")
                            .unlockedBy("has_leaves", has(ItemTags.LEAVES));
                }

                public ShapedRecipeBuilder createChristmasLightsRecipe(Item lights, Item dye) {
                    return shaped(RecipeCategory.DECORATIONS, lights, 6)
                            .pattern("$X$")
                            .pattern("###")
                            .define('#', Items.GLASS_BOTTLE)
                            .define('X', dye)
                            .define('$', Items.STRING)
                            .group("christmas_lights")
                            .unlockedBy(getHasName(dye), has(dye))
                            .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                            .unlockedBy(getHasName(Items.GLASS_BOTTLE), has(Items.GLASS_BOTTLE));
                }
            };
        }

        @Override
        public String getName() {
            return "Rainbow Christmas Recipe Generator";
        }
    }

    public static class ModBlockLootTables extends FabricBlockLootTableProvider {
        protected ModBlockLootTables(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            dropSelf(ModBlocks.CHRISTMAS_TREE_STAND);
            dropSelf(ModBlocks.MISTLETOE);
            dropSelf(ModBlocks.WREATH);
            dropSelf(ModBlocks.FAUX_SNOW);
            dropSelf(ModBlocks.STAR_TOPPER);
            dropSelf(ModBlocks.ICICLE);
            dropSelf(ModBlocks.SHREDDER);

            dropSelf(ModBlocks.HARD_CANDY_BLOCK);
            dropSelf(ModBlocks.HARD_CANDY_TILES);
            add(ModBlocks.HARD_CANDY_TILE_SLAB, createSlabItemTable(ModBlocks.HARD_CANDY_TILE_SLAB));
            dropSelf(ModBlocks.HARD_CANDY_TILE_STAIRS);
            dropSelf(ModBlocks.HARD_CANDY_PILLAR);

            dropSelf(ModBlocks.CANDY_CANE_TILES);
            add(ModBlocks.CANDY_CANE_TILE_SLAB, createSlabItemTable(ModBlocks.CANDY_CANE_TILE_SLAB));
            dropSelf(ModBlocks.CANDY_CANE_TILE_STAIRS);
            dropSelf(ModBlocks.CANDY_CANE_PILLAR);
            dropSelf(ModBlocks.NORTH_POLE);

            dropSelf(ModBlocks.GINGERBREAD_BLOCK);
            add(ModBlocks.GINGERBREAD_BLOCK_SLAB, createSlabItemTable(ModBlocks.GINGERBREAD_BLOCK_SLAB));
            dropSelf(ModBlocks.GINGERBREAD_BLOCK_STAIRS);
            dropSelf(ModBlocks.GINGERBREAD_BRICKS);
            add(ModBlocks.GINGERBREAD_BRICK_SLAB, createSlabItemTable(ModBlocks.GINGERBREAD_BRICK_SLAB));
            dropSelf(ModBlocks.GINGERBREAD_BRICK_STAIRS);
            dropSelf(ModBlocks.GINGERBREAD_SHINGLES);
            add(ModBlocks.GINGERBREAD_SHINGLE_SLAB, createSlabItemTable(ModBlocks.GINGERBREAD_SHINGLE_SLAB));
            dropSelf(ModBlocks.GINGERBREAD_SHINGLE_STAIRS);


            ModBlocks.ORNAMENTS.forEach(this::dropSelf);
            ModBlocks.GARLANDS.forEach(this::dropSelf);
            ModBlocks.CHRISTMAS_LIGHTS.forEach(this::dropSelf);
            ModBlocks.HOLIDAY_LEAVES.forEach(this::dropSelf);
        }
    }

    public static class ModChestLootTables extends SimpleFabricLootTableProvider {
        public ModChestLootTables(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(output, registryLookup, LootContextParamSets.CHEST);
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
            lootTableBiConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(RainbowChristmas.MOD_ID, "gingerbread_house")),
                    LootTable.lootTable().withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(24.0f))
                            .add(LootItem.lootTableItem(ModItems.WARM_SPICES))
                            .add(LootItem.lootTableItem(ModItems.CANDY_CANE))
                            .add(LootItem.lootTableItem(ModItems.GINGERBREAD_COOKIE))));
            lootTableBiConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(RainbowChristmas.MOD_ID, "gingerbread_house_festive_hat_materials")),
                    LootTable.lootTable().withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(15.0f))
                            .add(LootItem.lootTableItem(Items.WHITE_WOOL).setWeight(4))
                            .add(LootItem.lootTableItem(Items.RED_DYE))
                            .add(LootItem.lootTableItem(Items.GREEN_DYE))
                            .add(LootItem.lootTableItem(Items.WHITE_DYE))));

        }
    }
}
