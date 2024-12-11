package poeticrainbow.rainbowchristmas.gen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import poeticrainbow.rainbowchristmas.RainbowChristmas;
import poeticrainbow.rainbowchristmas.registry.ModBlocks;
import poeticrainbow.rainbowchristmas.registry.ModItems;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModBlockTags::new);
        pack.addProvider(ModItemTags::new);
        pack.addProvider(ModRecipes::new);
        pack.addProvider(ModBlockLootTables::new);
        pack.addProvider(ModChestLootTables::new);
    }

    public static class ModBlockTags extends FabricTagProvider.BlockTagProvider {
        public static final TagKey<Block> PLANT_SUSTAINABLE = TagKey.of(RegistryKeys.BLOCK, RainbowChristmas.id("plant_sustainable"));
        public static final TagKey<Block> GARLAND = TagKey.of(RegistryKeys.BLOCK, RainbowChristmas.id("garland"));

        public ModBlockTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(PLANT_SUSTAINABLE).add(ModBlocks.CHRISTMAS_TREE_STAND);
            getOrCreateTagBuilder(BlockTags.DEAD_BUSH_MAY_PLACE_ON).add(ModBlocks.CHRISTMAS_TREE_STAND);

            // Leaves
            FabricTagProvider<Block>.FabricTagBuilder leavesBuilder = getOrCreateTagBuilder(BlockTags.LEAVES);
            ModBlocks.HOLIDAY_LEAVES.forEach(leavesBuilder::add);

            // Garland
            FabricTagProvider<Block>.FabricTagBuilder garlandBuilder = getOrCreateTagBuilder(GARLAND);
            ModBlocks.GARLANDS.forEach(garlandBuilder::add);

            // Hoe Mineable
            FabricTagProvider<Block>.FabricTagBuilder hoeMineableBuilder = getOrCreateTagBuilder(BlockTags.HOE_MINEABLE);
            ModBlocks.GARLANDS.forEach(hoeMineableBuilder::add);
            ModBlocks.HOLIDAY_LEAVES.forEach(hoeMineableBuilder::add);

            FabricTagProvider<Block>.FabricTagBuilder pickaxeMineableBuilder = getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE);
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
        public static final TagKey<Item> ORNAMENT = TagKey.of(RegistryKeys.ITEM, RainbowChristmas.id("ornament"));
        public static final TagKey<Item> GARLAND = TagKey.of(RegistryKeys.ITEM, RainbowChristmas.id("garland"));
        public static final TagKey<Item> ICE = TagKey.of(RegistryKeys.ITEM, RainbowChristmas.id("ice"));

        public ModItemTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            FabricTagBuilder ornamentBuilder = getOrCreateTagBuilder(ORNAMENT);
            ModBlocks.ORNAMENTS.forEach(block -> ornamentBuilder.add(block.asItem()));

            FabricTagBuilder garlandBuilder = getOrCreateTagBuilder(GARLAND);
            ModBlocks.GARLANDS.forEach(block -> garlandBuilder.add(block.asItem()));

            FabricTagProvider<Item>.FabricTagBuilder leavesBuilder = getOrCreateTagBuilder(ItemTags.LEAVES);
            ModBlocks.HOLIDAY_LEAVES.forEach(block -> leavesBuilder.add(block.asItem()));

            FabricTagBuilder iceBuilder = getOrCreateTagBuilder(ICE);
            iceBuilder.add(Items.ICE, Items.PACKED_ICE, Items.BLUE_ICE);
        }
    }

    public static class ModRecipes extends FabricRecipeProvider {

        public ModRecipes(FabricDataOutput output) {
            super(output);
        }

        public void offer2x2ConversionRecipe(Consumer<RecipeJsonProvider> consumer, ItemConvertible output, ItemConvertible input) {
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                    .pattern("##")
                    .pattern("##")
                    .input('#', input)
                    .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                    .offerTo(consumer);
        }

        @Override
        public void generate(Consumer<RecipeJsonProvider> consumer) {
            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.CLEAR_ORNAMENT)
                    .pattern("a")
                    .pattern("b")
                    .input('a', Items.STRING)
                    .input('b', Items.GLASS_BOTTLE)
                    .criterion(FabricRecipeProvider.hasItem(Items.STRING), FabricRecipeProvider.conditionsFromItem(Items.STRING))
                    .criterion(FabricRecipeProvider.hasItem(Items.GLASS_BOTTLE), FabricRecipeProvider.conditionsFromItem(Items.GLASS_BOTTLE))
                    .offerTo(consumer);

            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.WREATH)
                    .pattern("#X#")
                    .pattern("# #")
                    .pattern("###")
                    .input('#', ItemTags.LEAVES)
                    .input('X', Items.SWEET_BERRIES)
                    .criterion("has_leaves", FabricRecipeProvider.conditionsFromTag(ItemTags.LEAVES))
                    .offerTo(consumer);

            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.STAR_TOPPER)
                    .pattern("#X#")
                    .pattern("XXX")
                    .pattern("#$#")
                    .input('#', Items.GLOWSTONE_DUST)
                    .input('X', Items.GLOWSTONE)
                    .input('$', Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(Items.GLOWSTONE_DUST), FabricRecipeProvider.conditionsFromItem(Items.GLOWSTONE_DUST))
                    .criterion(FabricRecipeProvider.hasItem(Items.GLOWSTONE), FabricRecipeProvider.conditionsFromItem(Items.GLOWSTONE))
                    .criterion(FabricRecipeProvider.hasItem(Items.STICK), FabricRecipeProvider.conditionsFromItem(Items.STICK))
                    .offerTo(consumer);

            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.MISTLETOE)
                    .pattern("$#$")
                    .pattern("#X#")
                    .pattern("%#%")
                    .input('#', ItemTags.LEAVES)
                    .input('X', ConventionalItemTags.DIAMONDS)
                    .input('$', Items.STRING)
                    .input('%', Items.SWEET_BERRIES)
                    .criterion("has_leaves", FabricRecipeProvider.conditionsFromTag(ItemTags.LEAVES))
                    .criterion("has_diamonds", FabricRecipeProvider.conditionsFromTag(ConventionalItemTags.DIAMONDS))
                    .criterion(FabricRecipeProvider.hasItem(Items.STRING), FabricRecipeProvider.conditionsFromItem(Items.STRING))
                    .criterion(FabricRecipeProvider.hasItem(Items.SWEET_BERRIES), FabricRecipeProvider.conditionsFromItem(Items.SWEET_BERRIES))
                    .offerTo(consumer);

            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.CHRISTMAS_TREE_STAND)
                    .pattern("#$#")
                    .pattern("#%#")
                    .pattern("###")
                    .input('#', Items.BRICK)
                    .input('$', Blocks.DIRT)
                    .input('%', Items.LIGHT_BLUE_DYE)
                    .criterion(FabricRecipeProvider.hasItem(Items.BRICK), FabricRecipeProvider.conditionsFromItem(Items.BRICK))
                    .offerTo(consumer);

            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.ICICLE, 8)
                    .pattern("#")
                    .pattern("#")
                    .input('#', ModItemTags.ICE)
                    .criterion("has_ice", FabricRecipeProvider.conditionsFromTag(ModItemTags.ICE))
                    .offerTo(consumer);

            ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CANDY_CANE, 8)
                    .pattern("#$")
                    .pattern("X#")
                    .input('#', ModItems.HARD_CANDY)
                    .input('$', Items.RED_DYE)
                    .input('X', Items.WHITE_DYE)
                    .criterion(FabricRecipeProvider.hasItem(Items.SUGAR), FabricRecipeProvider.conditionsFromItem(Items.SUGAR))
                    .offerTo(consumer);

            ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CANDY_CANE, 8)
                    .pattern("#X")
                    .pattern("$#")
                    .input('#', ModItems.HARD_CANDY)
                    .input('$', Items.RED_DYE)
                    .input('X', Items.WHITE_DYE)
                    .criterion(FabricRecipeProvider.hasItem(Items.SUGAR), FabricRecipeProvider.conditionsFromItem(Items.SUGAR))
                    .offerTo(consumer, "candy_cane_flipped");

            // Hard Candy
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HARD_CANDY_BLOCK)
                    .pattern("##")
                    .pattern("##")
                    .input('#', ModItems.HARD_CANDY)
                    .criterion(FabricRecipeProvider.hasItem(ModItems.HARD_CANDY), FabricRecipeProvider.conditionsFromItem(ModItems.HARD_CANDY))
                    .offerTo(consumer);
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HARD_CANDY_TILES, 4)
                    .pattern("##")
                    .pattern("##")
                    .input('#', ModBlocks.HARD_CANDY_BLOCK)
                    .criterion(FabricRecipeProvider.hasItem(ModItems.HARD_CANDY), FabricRecipeProvider.conditionsFromItem(ModItems.HARD_CANDY))
                    .offerTo(consumer);
            RecipeProvider.createStairsRecipe(ModBlocks.HARD_CANDY_TILE_STAIRS, Ingredient.ofItems(ModBlocks.HARD_CANDY_TILES))
                    .criterion(FabricRecipeProvider.hasItem(ModBlocks.HARD_CANDY_TILES), FabricRecipeProvider.conditionsFromItem(ModBlocks.HARD_CANDY_TILES))
                    .offerTo(consumer);
            RecipeProvider.createSlabRecipe(
                    RecipeCategory.BUILDING_BLOCKS,
                    ModBlocks.HARD_CANDY_TILE_SLAB, Ingredient.ofItems(ModBlocks.HARD_CANDY_TILES))
                    .criterion(FabricRecipeProvider.hasItem(ModBlocks.HARD_CANDY_TILES), FabricRecipeProvider.conditionsFromItem(ModBlocks.HARD_CANDY_TILES))
                    .offerTo(consumer);
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HARD_CANDY_PILLAR, 2)
                    .pattern("#")
                    .pattern("#")
                    .input('#', ModBlocks.HARD_CANDY_TILES)
                    .criterion(FabricRecipeProvider.hasItem(ModBlocks.HARD_CANDY_TILES), FabricRecipeProvider.conditionsFromItem(ModBlocks.HARD_CANDY_TILES))
                    .offerTo(consumer);

            // Candy Cane
            RecipeProvider.offer2x2CompactingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CANDY_CANE_TILES, ModItems.CANDY_CANE);
            RecipeProvider.createStairsRecipe(ModBlocks.CANDY_CANE_TILE_STAIRS, Ingredient.ofItems(ModBlocks.CANDY_CANE_TILES))
                    .criterion(FabricRecipeProvider.hasItem(ModBlocks.CANDY_CANE_TILES), FabricRecipeProvider.conditionsFromItem(ModBlocks.CANDY_CANE_TILES))
                    .offerTo(consumer);
            RecipeProvider.createSlabRecipe(
                            RecipeCategory.BUILDING_BLOCKS,
                            ModBlocks.CANDY_CANE_TILE_SLAB, Ingredient.ofItems(ModBlocks.CANDY_CANE_TILES))
                    .criterion(FabricRecipeProvider.hasItem(ModBlocks.CANDY_CANE_TILES), FabricRecipeProvider.conditionsFromItem(ModBlocks.CANDY_CANE_TILES))
                    .offerTo(consumer);
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CANDY_CANE_PILLAR, 2)
                    .pattern("#")
                    .pattern("#")
                    .input('#', ModBlocks.CANDY_CANE_TILES)
                    .criterion(FabricRecipeProvider.hasItem(ModBlocks.CANDY_CANE_TILES), FabricRecipeProvider.conditionsFromItem(ModBlocks.CANDY_CANE_TILES))
                    .offerTo(consumer);

            RecipeProvider.getWallRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NORTH_POLE, Ingredient.ofItems(ModBlocks.CANDY_CANE_TILES))
                    .criterion(FabricRecipeProvider.hasItem(ModBlocks.CANDY_CANE_TILES), FabricRecipeProvider.conditionsFromItem(ModBlocks.CANDY_CANE_TILES))
                    .offerTo(consumer);

            // Gingerbread
            RecipeProvider.offer2x2CompactingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GINGERBREAD_BLOCK, ModItems.GINGERBREAD_COOKIE);
            RecipeProvider.createStairsRecipe(ModBlocks.GINGERBREAD_BLOCK_STAIRS, Ingredient.ofItems(ModBlocks.GINGERBREAD_BLOCK))
                    .criterion(FabricRecipeProvider.hasItem(ModBlocks.GINGERBREAD_BLOCK), FabricRecipeProvider.conditionsFromItem(ModBlocks.GINGERBREAD_BLOCK))
                    .offerTo(consumer);
            RecipeProvider.createSlabRecipe(
                            RecipeCategory.BUILDING_BLOCKS,
                            ModBlocks.GINGERBREAD_BLOCK_SLAB, Ingredient.ofItems(ModBlocks.GINGERBREAD_BLOCK))
                    .criterion(FabricRecipeProvider.hasItem(ModBlocks.GINGERBREAD_BLOCK), FabricRecipeProvider.conditionsFromItem(ModBlocks.GINGERBREAD_BLOCK))
                    .offerTo(consumer);

            offer2x2ConversionRecipe(consumer, ModBlocks.GINGERBREAD_BRICKS, ModBlocks.GINGERBREAD_BLOCK);
            RecipeProvider.createStairsRecipe(ModBlocks.GINGERBREAD_BRICK_STAIRS, Ingredient.ofItems(ModBlocks.GINGERBREAD_BRICKS))
                    .criterion(FabricRecipeProvider.hasItem(ModBlocks.GINGERBREAD_BRICKS), FabricRecipeProvider.conditionsFromItem(ModBlocks.GINGERBREAD_BRICKS))
                    .offerTo(consumer);
            RecipeProvider.createSlabRecipe(
                            RecipeCategory.BUILDING_BLOCKS,
                            ModBlocks.GINGERBREAD_BRICK_SLAB, Ingredient.ofItems(ModBlocks.GINGERBREAD_BRICKS))
                    .criterion(FabricRecipeProvider.hasItem(ModBlocks.GINGERBREAD_BRICKS), FabricRecipeProvider.conditionsFromItem(ModBlocks.GINGERBREAD_BRICKS))
                    .offerTo(consumer);


            offer2x2ConversionRecipe(consumer, ModBlocks.GINGERBREAD_SHINGLES, ModBlocks.GINGERBREAD_BRICKS);
            RecipeProvider.createStairsRecipe(ModBlocks.GINGERBREAD_SHINGLE_STAIRS, Ingredient.ofItems(ModBlocks.GINGERBREAD_SHINGLES))
                    .criterion(FabricRecipeProvider.hasItem(ModBlocks.GINGERBREAD_SHINGLES), FabricRecipeProvider.conditionsFromItem(ModBlocks.GINGERBREAD_SHINGLES))
                    .offerTo(consumer);
            RecipeProvider.createSlabRecipe(
                            RecipeCategory.BUILDING_BLOCKS,
                            ModBlocks.GINGERBREAD_SHINGLE_SLAB, Ingredient.ofItems(ModBlocks.GINGERBREAD_SHINGLES))
                    .criterion(FabricRecipeProvider.hasItem(ModBlocks.GINGERBREAD_SHINGLES), FabricRecipeProvider.conditionsFromItem(ModBlocks.GINGERBREAD_SHINGLES))
                    .offerTo(consumer);

            // Hard Candy Item
            CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(Items.SUGAR), RecipeCategory.FOOD, ModItems.HARD_CANDY, 0f, 200)
                    .criterion(FabricRecipeProvider.hasItem(Items.SUGAR), FabricRecipeProvider.conditionsFromItem(Items.SUGAR))
                    .offerTo(consumer);
            CookingRecipeJsonBuilder.createSmoking(Ingredient.ofItems(Items.SUGAR), RecipeCategory.FOOD, ModItems.HARD_CANDY, 0f, 100)
                    .criterion(FabricRecipeProvider.hasItem(Items.SUGAR), FabricRecipeProvider.conditionsFromItem(Items.SUGAR))
                    .offerTo(consumer, "hard_candy_smoking");

            ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.GINGERBREAD_COOKIE, 8)
                    .pattern(" # ")
                    .pattern("$%$")
                    .input('#', ModItems.WARM_SPICES)
                    .input('$', Items.WHEAT)
                    .input('%', Items.SUGAR)
                    .criterion(FabricRecipeProvider.hasItem(Items.SUGAR), FabricRecipeProvider.conditionsFromItem(Items.SUGAR))
                    .offerTo(consumer);

            ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CHOCOLATE_MILK_BUCKET)
                    .input(Items.COCOA_BEANS)
                    .input(Items.MILK_BUCKET)
                    .input(Items.SUGAR)
                    .criterion(FabricRecipeProvider.hasItem(Items.MILK_BUCKET), FabricRecipeProvider.conditionsFromItem(Items.MILK_BUCKET))
                    .offerTo(consumer);

            CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(ModItems.CHOCOLATE_MILK_BUCKET), RecipeCategory.FOOD, ModItems.HOT_CHOCOLATE_BUCKET,
                    0f, 200)
                    .criterion(FabricRecipeProvider.hasItem(ModItems.CHOCOLATE_MILK_BUCKET), FabricRecipeProvider.conditionsFromItem(ModItems.CHOCOLATE_MILK_BUCKET))
                    .offerTo(consumer);
            CookingRecipeJsonBuilder.createSmoking(Ingredient.ofItems(ModItems.CHOCOLATE_MILK_BUCKET), RecipeCategory.FOOD, ModItems.HOT_CHOCOLATE_BUCKET,
                            0f, 100)
                    .criterion(FabricRecipeProvider.hasItem(ModItems.CHOCOLATE_MILK_BUCKET), FabricRecipeProvider.conditionsFromItem(ModItems.CHOCOLATE_MILK_BUCKET))
                    .offerTo(consumer, "hot_chocolate_bucket_smoking");

            createFestiveHatRecipe(ModItems.WHITE_FESTIVE_HAT.asItem(), Items.WHITE_DYE).offerTo(consumer);
            createFestiveHatRecipe(ModItems.LIGHT_GRAY_FESTIVE_HAT.asItem(), Items.LIGHT_GRAY_DYE).offerTo(consumer);
            createFestiveHatRecipe(ModItems.GRAY_FESTIVE_HAT.asItem(), Items.GRAY_DYE).offerTo(consumer);
            createFestiveHatRecipe(ModItems.BLACK_FESTIVE_HAT.asItem(), Items.BLACK_DYE).offerTo(consumer);
            createFestiveHatRecipe(ModItems.BROWN_FESTIVE_HAT.asItem(), Items.BROWN_DYE).offerTo(consumer);
            createFestiveHatRecipe(ModItems.RED_FESTIVE_HAT.asItem(), Items.RED_DYE).offerTo(consumer);
            createFestiveHatRecipe(ModItems.ORANGE_FESTIVE_HAT.asItem(), Items.ORANGE_DYE).offerTo(consumer);
            createFestiveHatRecipe(ModItems.YELLOW_FESTIVE_HAT.asItem(), Items.YELLOW_DYE).offerTo(consumer);
            createFestiveHatRecipe(ModItems.LIME_FESTIVE_HAT.asItem(), Items.LIME_DYE).offerTo(consumer);
            createFestiveHatRecipe(ModItems.GREEN_FESTIVE_HAT.asItem(), Items.GREEN_DYE).offerTo(consumer);
            createFestiveHatRecipe(ModItems.CYAN_FESTIVE_HAT.asItem(), Items.CYAN_DYE).offerTo(consumer);
            createFestiveHatRecipe(ModItems.LIGHT_BLUE_FESTIVE_HAT.asItem(), Items.LIGHT_BLUE_DYE).offerTo(consumer);
            createFestiveHatRecipe(ModItems.BLUE_FESTIVE_HAT.asItem(), Items.BLUE_DYE).offerTo(consumer);
            createFestiveHatRecipe(ModItems.PURPLE_FESTIVE_HAT.asItem(), Items.PURPLE_DYE).offerTo(consumer);
            createFestiveHatRecipe(ModItems.MAGENTA_FESTIVE_HAT.asItem(), Items.MAGENTA_DYE).offerTo(consumer);
            createFestiveHatRecipe(ModItems.PINK_FESTIVE_HAT.asItem(), Items.PINK_DYE).offerTo(consumer);

            createDyedOrnamentRecipe(ModBlocks.WHITE_ORNAMENT.asItem(), Items.WHITE_DYE).offerTo(consumer);
            createDyedOrnamentRecipe(ModBlocks.LIGHT_GRAY_ORNAMENT.asItem(), Items.LIGHT_GRAY_DYE).offerTo(consumer);
            createDyedOrnamentRecipe(ModBlocks.GRAY_ORNAMENT.asItem(), Items.GRAY_DYE).offerTo(consumer);
            createDyedOrnamentRecipe(ModBlocks.BLACK_ORNAMENT.asItem(), Items.BLACK_DYE).offerTo(consumer);
            createDyedOrnamentRecipe(ModBlocks.BROWN_ORNAMENT.asItem(), Items.BROWN_DYE).offerTo(consumer);
            createDyedOrnamentRecipe(ModBlocks.RED_ORNAMENT.asItem(), Items.RED_DYE).offerTo(consumer);
            createDyedOrnamentRecipe(ModBlocks.ORANGE_ORNAMENT.asItem(), Items.ORANGE_DYE).offerTo(consumer);
            createDyedOrnamentRecipe(ModBlocks.YELLOW_ORNAMENT.asItem(), Items.YELLOW_DYE).offerTo(consumer);
            createDyedOrnamentRecipe(ModBlocks.LIME_ORNAMENT.asItem(), Items.LIME_DYE).offerTo(consumer);
            createDyedOrnamentRecipe(ModBlocks.GREEN_ORNAMENT.asItem(), Items.GREEN_DYE).offerTo(consumer);
            createDyedOrnamentRecipe(ModBlocks.CYAN_ORNAMENT.asItem(), Items.CYAN_DYE).offerTo(consumer);
            createDyedOrnamentRecipe(ModBlocks.LIGHT_BLUE_ORNAMENT.asItem(), Items.LIGHT_BLUE_DYE).offerTo(consumer);
            createDyedOrnamentRecipe(ModBlocks.BLUE_ORNAMENT.asItem(), Items.BLUE_DYE).offerTo(consumer);
            createDyedOrnamentRecipe(ModBlocks.PURPLE_ORNAMENT.asItem(), Items.PURPLE_DYE).offerTo(consumer);
            createDyedOrnamentRecipe(ModBlocks.MAGENTA_ORNAMENT.asItem(), Items.MAGENTA_DYE).offerTo(consumer);
            createDyedOrnamentRecipe(ModBlocks.PINK_ORNAMENT.asItem(), Items.PINK_DYE).offerTo(consumer);

            createDyedGarlandRecipe(ModBlocks.WHITE_GARLAND.asItem(), Items.WHITE_DYE).offerTo(consumer);
            createDyedGarlandRecipe(ModBlocks.LIGHT_GRAY_GARLAND.asItem(), Items.LIGHT_GRAY_DYE).offerTo(consumer);
            createDyedGarlandRecipe(ModBlocks.GRAY_GARLAND.asItem(), Items.GRAY_DYE).offerTo(consumer);
            createDyedGarlandRecipe(ModBlocks.BLACK_GARLAND.asItem(), Items.BLACK_DYE).offerTo(consumer);
            createDyedGarlandRecipe(ModBlocks.BROWN_GARLAND.asItem(), Items.BROWN_DYE).offerTo(consumer);
            createDyedGarlandRecipe(ModBlocks.RED_GARLAND.asItem(), Items.RED_DYE).offerTo(consumer);
            createDyedGarlandRecipe(ModBlocks.ORANGE_GARLAND.asItem(), Items.ORANGE_DYE).offerTo(consumer);
            createDyedGarlandRecipe(ModBlocks.YELLOW_GARLAND.asItem(), Items.YELLOW_DYE).offerTo(consumer);
            createDyedGarlandRecipe(ModBlocks.LIME_GARLAND.asItem(), Items.LIME_DYE).offerTo(consumer);
            createDyedGarlandRecipe(ModBlocks.GREEN_GARLAND.asItem(), Items.GREEN_DYE).offerTo(consumer);
            createDyedGarlandRecipe(ModBlocks.CYAN_GARLAND.asItem(), Items.CYAN_DYE).offerTo(consumer);
            createDyedGarlandRecipe(ModBlocks.LIGHT_BLUE_GARLAND.asItem(), Items.LIGHT_BLUE_DYE).offerTo(consumer);
            createDyedGarlandRecipe(ModBlocks.BLUE_GARLAND.asItem(), Items.BLUE_DYE).offerTo(consumer);
            createDyedGarlandRecipe(ModBlocks.PURPLE_GARLAND.asItem(), Items.PURPLE_DYE).offerTo(consumer);
            createDyedGarlandRecipe(ModBlocks.MAGENTA_GARLAND.asItem(), Items.MAGENTA_DYE).offerTo(consumer);
            createDyedGarlandRecipe(ModBlocks.PINK_GARLAND.asItem(), Items.PINK_DYE).offerTo(consumer);

            createGarlandFromHolidayLeavesRecipe(ModBlocks.WHITE_GARLAND.asItem(), ModBlocks.WHITE_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "white_garland_from_holiday_leaves");
            createGarlandFromHolidayLeavesRecipe(ModBlocks.LIGHT_GRAY_GARLAND.asItem(), ModBlocks.LIGHT_GRAY_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "light_gray_garland_from_holiday_leaves");
            createGarlandFromHolidayLeavesRecipe(ModBlocks.GRAY_GARLAND.asItem(), ModBlocks.GRAY_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "gray_garland_from_holiday_leaves");
            createGarlandFromHolidayLeavesRecipe(ModBlocks.BLACK_GARLAND.asItem(), ModBlocks.BLACK_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "black_garland_from_holiday_leaves");
            createGarlandFromHolidayLeavesRecipe(ModBlocks.BROWN_GARLAND.asItem(), ModBlocks.BROWN_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "brown_garland_from_holiday_leaves");
            createGarlandFromHolidayLeavesRecipe(ModBlocks.RED_GARLAND.asItem(), ModBlocks.RED_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "red_garland_from_holiday_leaves");
            createGarlandFromHolidayLeavesRecipe(ModBlocks.ORANGE_GARLAND.asItem(), ModBlocks.ORANGE_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "orange_garland_from_holiday_leaves");
            createGarlandFromHolidayLeavesRecipe(ModBlocks.YELLOW_GARLAND.asItem(), ModBlocks.YELLOW_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "yellow_garland_from_holiday_leaves");
            createGarlandFromHolidayLeavesRecipe(ModBlocks.LIME_GARLAND.asItem(), ModBlocks.LIME_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "lime_garland_from_holiday_leaves");
            createGarlandFromHolidayLeavesRecipe(ModBlocks.GREEN_GARLAND.asItem(), ModBlocks.GREEN_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "green_garland_from_holiday_leaves");
            createGarlandFromHolidayLeavesRecipe(ModBlocks.CYAN_GARLAND.asItem(), ModBlocks.CYAN_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "cyan_garland_from_holiday_leaves");
            createGarlandFromHolidayLeavesRecipe(ModBlocks.LIGHT_BLUE_GARLAND.asItem(), ModBlocks.LIGHT_BLUE_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "light_blue_garland_from_holiday_leaves");
            createGarlandFromHolidayLeavesRecipe(ModBlocks.BLUE_GARLAND.asItem(), ModBlocks.BLUE_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "blue_garland_from_holiday_leaves");
            createGarlandFromHolidayLeavesRecipe(ModBlocks.PURPLE_GARLAND.asItem(), ModBlocks.PURPLE_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "purple_garland_from_holiday_leaves");
            createGarlandFromHolidayLeavesRecipe(ModBlocks.MAGENTA_GARLAND.asItem(), ModBlocks.MAGENTA_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "magenta_garland_from_holiday_leaves");
            createGarlandFromHolidayLeavesRecipe(ModBlocks.PINK_GARLAND.asItem(), ModBlocks.PINK_HOLIDAY_LEAVES.asItem()).offerTo(consumer, "pink_garland_from_holiday_leaves");

            createDyedHolidayLeavesRecipe(ModBlocks.RAINBOW_HOLIDAY_LEAVES.asItem(), Items.AMETHYST_SHARD).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.WHITE_HOLIDAY_LEAVES.asItem(), Items.WHITE_DYE).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.LIGHT_GRAY_HOLIDAY_LEAVES.asItem(), Items.LIGHT_GRAY_DYE).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.GRAY_HOLIDAY_LEAVES.asItem(), Items.GRAY_DYE).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.BLACK_HOLIDAY_LEAVES.asItem(), Items.BLACK_DYE).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.BROWN_HOLIDAY_LEAVES.asItem(), Items.BROWN_DYE).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.RED_HOLIDAY_LEAVES.asItem(), Items.RED_DYE).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.ORANGE_HOLIDAY_LEAVES.asItem(), Items.ORANGE_DYE).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.YELLOW_HOLIDAY_LEAVES.asItem(), Items.YELLOW_DYE).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.LIME_HOLIDAY_LEAVES.asItem(), Items.LIME_DYE).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.GREEN_HOLIDAY_LEAVES.asItem(), Items.GREEN_DYE).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.CYAN_HOLIDAY_LEAVES.asItem(), Items.CYAN_DYE).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.LIGHT_BLUE_HOLIDAY_LEAVES.asItem(), Items.LIGHT_BLUE_DYE).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.BLUE_HOLIDAY_LEAVES.asItem(), Items.BLUE_DYE).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.PURPLE_HOLIDAY_LEAVES.asItem(), Items.PURPLE_DYE).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.MAGENTA_HOLIDAY_LEAVES.asItem(), Items.MAGENTA_DYE).offerTo(consumer);
            createDyedHolidayLeavesRecipe(ModBlocks.PINK_HOLIDAY_LEAVES.asItem(), Items.PINK_DYE).offerTo(consumer);

            createChristmasLightsRecipe(ModBlocks.MULTICOLOR_CHRISTMAS_LIGHTS.asItem(), Items.AMETHYST_SHARD).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.WHITE_CHRISTMAS_LIGHTS.asItem(), Items.WHITE_DYE).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.LIGHT_GRAY_CHRISTMAS_LIGHTS.asItem(), Items.LIGHT_GRAY_DYE).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.GRAY_CHRISTMAS_LIGHTS.asItem(), Items.GRAY_DYE).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.BLACK_CHRISTMAS_LIGHTS.asItem(), Items.BLACK_DYE).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.BROWN_CHRISTMAS_LIGHTS.asItem(), Items.BROWN_DYE).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.RED_CHRISTMAS_LIGHTS.asItem(), Items.RED_DYE).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.ORANGE_CHRISTMAS_LIGHTS.asItem(), Items.ORANGE_DYE).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.YELLOW_CHRISTMAS_LIGHTS.asItem(), Items.YELLOW_DYE).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.LIME_CHRISTMAS_LIGHTS.asItem(), Items.LIME_DYE).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.GREEN_CHRISTMAS_LIGHTS.asItem(), Items.GREEN_DYE).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.CYAN_CHRISTMAS_LIGHTS.asItem(), Items.CYAN_DYE).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.LIGHT_BLUE_CHRISTMAS_LIGHTS.asItem(), Items.LIGHT_BLUE_DYE).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.BLUE_CHRISTMAS_LIGHTS.asItem(), Items.BLUE_DYE).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.PURPLE_CHRISTMAS_LIGHTS.asItem(), Items.PURPLE_DYE).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.MAGENTA_CHRISTMAS_LIGHTS.asItem(), Items.MAGENTA_DYE).offerTo(consumer);
            createChristmasLightsRecipe(ModBlocks.PINK_CHRISTMAS_LIGHTS.asItem(), Items.PINK_DYE).offerTo(consumer);
        }

        public ShapedRecipeJsonBuilder createFestiveHatRecipe(Item hat, Item dye) {
            return ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, hat)
                    .pattern("###")
                    .pattern("#$#")
                    .input('#', ItemTags.WOOL)
                    .input('$', dye)
                    .criterion("has_wool", FabricRecipeProvider.conditionsFromTag(ItemTags.WOOL))
                    .criterion(FabricRecipeProvider.hasItem(dye), FabricRecipeProvider.conditionsFromItem(dye));
        }

        public ShapedRecipeJsonBuilder createDyedOrnamentRecipe(Item ornament, Item dye) {
            return ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ornament, 4)
                    .pattern(" # ")
                    .pattern("#X#")
                    .pattern(" # ")
                    .input('#', ModItemTags.ORNAMENT)
                    .input('X', dye)
                    .criterion(FabricRecipeProvider.hasItem(ModBlocks.CLEAR_ORNAMENT), FabricRecipeProvider.conditionsFromItem(ModBlocks.CLEAR_ORNAMENT));
        }

        public ShapedRecipeJsonBuilder createDyedGarlandRecipe(Item garland, Item dye) {
            return ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, garland, 6)
                    .pattern("$X$")
                    .pattern("###")
                    .input('#', ItemTags.LEAVES)
                    .input('$', Items.STRING)
                    .input('X', dye)
                    .criterion("has_leaves", FabricRecipeProvider.conditionsFromTag(ItemTags.LEAVES));
        }

        public ShapedRecipeJsonBuilder createGarlandFromHolidayLeavesRecipe(Item garland, Item holidayLeaves) {
            return ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, garland, 6)
                    .pattern("$ $")
                    .pattern("###")
                    .input('#', holidayLeaves)
                    .input('$', Items.STRING)
                    .criterion(FabricRecipeProvider.hasItem(holidayLeaves), FabricRecipeProvider.conditionsFromItem(holidayLeaves));
        }

        public ShapedRecipeJsonBuilder createDyedHolidayLeavesRecipe(Item leaves, Item dye) {
            return ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, leaves, 8)
                    .pattern("###")
                    .pattern("#X#")
                    .pattern("###")
                    .input('#', ItemTags.LEAVES)
                    .input('X', dye)
                    .criterion("has_leaves", FabricRecipeProvider.conditionsFromTag(ItemTags.LEAVES));
        }

        public ShapedRecipeJsonBuilder createChristmasLightsRecipe(Item lights, Item dye) {
            return ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, lights, 6)
                    .pattern("$X$")
                    .pattern("###")
                    .input('#', Items.GLASS_BOTTLE)
                    .input('X', dye)
                    .input('$', Items.STRING)
                    .criterion(FabricRecipeProvider.hasItem(dye), FabricRecipeProvider.conditionsFromItem(dye))
                    .criterion(FabricRecipeProvider.hasItem(Items.STRING), FabricRecipeProvider.conditionsFromItem(Items.STRING))
                    .criterion(FabricRecipeProvider.hasItem(Items.GLASS_BOTTLE), FabricRecipeProvider.conditionsFromItem(Items.GLASS_BOTTLE));
        }
    }

    public static class ModBlockLootTables extends FabricBlockLootTableProvider {
        protected ModBlockLootTables(FabricDataOutput dataOutput) {
            super(dataOutput);
        }

        @Override
        public void generate() {
            addDrop(ModBlocks.CHRISTMAS_TREE_STAND);
            addDrop(ModBlocks.MISTLETOE);
            addDrop(ModBlocks.WREATH);
            addDrop(ModBlocks.FAUX_SNOW);
            addDrop(ModBlocks.STAR_TOPPER);
            addDrop(ModBlocks.ICICLE);

            addDrop(ModBlocks.HARD_CANDY_BLOCK);
            addDrop(ModBlocks.HARD_CANDY_TILES);
            addDrop(ModBlocks.HARD_CANDY_TILE_SLAB, slabDrops(ModBlocks.HARD_CANDY_TILE_SLAB));
            addDrop(ModBlocks.HARD_CANDY_TILE_STAIRS);
            addDrop(ModBlocks.HARD_CANDY_PILLAR);

            addDrop(ModBlocks.CANDY_CANE_TILES);
            addDrop(ModBlocks.CANDY_CANE_TILE_SLAB, slabDrops(ModBlocks.CANDY_CANE_TILE_SLAB));
            addDrop(ModBlocks.CANDY_CANE_TILE_STAIRS);
            addDrop(ModBlocks.CANDY_CANE_PILLAR);
            addDrop(ModBlocks.NORTH_POLE);

            addDrop(ModBlocks.GINGERBREAD_BLOCK);
            addDrop(ModBlocks.GINGERBREAD_BLOCK_SLAB, slabDrops(ModBlocks.GINGERBREAD_BLOCK_SLAB));
            addDrop(ModBlocks.GINGERBREAD_BLOCK_STAIRS);
            addDrop(ModBlocks.GINGERBREAD_BRICKS);
            addDrop(ModBlocks.GINGERBREAD_BRICK_SLAB, slabDrops(ModBlocks.GINGERBREAD_BRICK_SLAB));
            addDrop(ModBlocks.GINGERBREAD_BRICK_STAIRS);
            addDrop(ModBlocks.GINGERBREAD_SHINGLES);
            addDrop(ModBlocks.GINGERBREAD_SHINGLE_SLAB, slabDrops(ModBlocks.GINGERBREAD_SHINGLE_SLAB));
            addDrop(ModBlocks.GINGERBREAD_SHINGLE_STAIRS);


            ModBlocks.ORNAMENTS.forEach(this::addDrop);
            ModBlocks.GARLANDS.forEach(this::addDrop);
            ModBlocks.CHRISTMAS_LIGHTS.forEach(this::addDrop);
            ModBlocks.HOLIDAY_LEAVES.forEach(this::addDrop);
        }
    }

    public static class ModChestLootTables extends SimpleFabricLootTableProvider {
        public ModChestLootTables(FabricDataOutput output) {
            super(output, LootContextTypes.CHEST);
        }

        @Override
        public void accept(BiConsumer<Identifier, LootTable.Builder> exporter) {
            exporter.accept(Identifier.of(RainbowChristmas.MOD_ID, "gingerbread_house"),
                    LootTable.builder().pool(LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(24.0f))
                            .with(ItemEntry.builder(ModItems.WARM_SPICES))
                            .with(ItemEntry.builder(ModItems.CANDY_CANE))
                            .with(ItemEntry.builder(ModItems.GINGERBREAD_COOKIE))));
            exporter.accept(Identifier.of(RainbowChristmas.MOD_ID, "gingerbread_house_festive_hat_materials"),
                    LootTable.builder().pool(LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(15.0f))
                            .with(ItemEntry.builder(Items.WHITE_WOOL).weight(4))
                            .with(ItemEntry.builder(Items.RED_DYE))
                            .with(ItemEntry.builder(Items.GREEN_DYE))
                            .with(ItemEntry.builder(Items.WHITE_DYE))));
        }
    }
}
