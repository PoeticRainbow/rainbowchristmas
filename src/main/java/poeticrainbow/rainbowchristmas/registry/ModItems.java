package poeticrainbow.rainbowchristmas.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import poeticrainbow.rainbowchristmas.RainbowChristmas;
import poeticrainbow.rainbowchristmas.registry.armor.FestiveArmorMaterial;
import poeticrainbow.rainbowchristmas.registry.item.DrinkItem;

public class ModItems {
    public static final RegistryKey<ItemGroup> RAINBOW_CHRISTMAS_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, RainbowChristmas.id("rainbowchristmas"));
    public static final ItemGroup RAINBOW_CHRISTMAS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.RED_ORNAMENT.asItem()))
            .displayName(Text.translatable("itemGroup.rainbowChristmas"))
            .build();

    public static final Item WHITE_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("white"), ArmorItem.Type.HELMET,
                    new FabricItemSettings()), "white_festive_hat");
    public static final Item LIGHT_GRAY_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("light_gray"), ArmorItem.Type.HELMET,
                    new FabricItemSettings()), "light_gray_festive_hat");
    public static final Item GRAY_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("gray"), ArmorItem.Type.HELMET,
                    new FabricItemSettings()), "gray_festive_hat");
    public static final Item BLACK_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("black"), ArmorItem.Type.HELMET,
                    new FabricItemSettings()), "black_festive_hat");
    public static final Item BROWN_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("brown"), ArmorItem.Type.HELMET,
                    new FabricItemSettings()), "brown_festive_hat");
    public static final Item RED_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("red"), ArmorItem.Type.HELMET,
                    new FabricItemSettings()), "red_festive_hat");
    public static final Item ORANGE_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("orange"), ArmorItem.Type.HELMET,
            new FabricItemSettings()), "orange_festive_hat");
    public static final Item YELLOW_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("yellow"), ArmorItem.Type.HELMET,
                    new FabricItemSettings()), "yellow_festive_hat");
    public static final Item LIME_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("lime"), ArmorItem.Type.HELMET,
                    new FabricItemSettings()), "lime_festive_hat");
    public static final Item GREEN_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("green"), ArmorItem.Type.HELMET,
                    new FabricItemSettings()), "green_festive_hat");
    public static final Item CYAN_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("cyan"), ArmorItem.Type.HELMET,
                    new FabricItemSettings()), "cyan_festive_hat");
    public static final Item LIGHT_BLUE_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("light_blue"), ArmorItem.Type.HELMET,
                    new FabricItemSettings()), "light_blue_festive_hat");
    public static final Item BLUE_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("blue"), ArmorItem.Type.HELMET,
                    new FabricItemSettings()), "blue_festive_hat");
    public static final Item PURPLE_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("purple"), ArmorItem.Type.HELMET,
                    new FabricItemSettings()), "purple_festive_hat");
    public static final Item MAGENTA_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("magenta"), ArmorItem.Type.HELMET,
                    new FabricItemSettings()), "magenta_festive_hat");
    public static final Item PINK_FESTIVE_HAT = register(
            new ArmorItem(new FestiveArmorMaterial("pink"), ArmorItem.Type.HELMET,
                    new FabricItemSettings()), "pink_festive_hat");

    public static StatusEffectInstance christmasSpirit = new StatusEffectInstance(ModMisc.CHRISTMAS_SPIRIT, 300);
    public static final Item HARD_CANDY = register(new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).alwaysEdible().snack().build())), "hard_candy");
    public static final Item CANDY_CANE = register(new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.6f).alwaysEdible().snack()
            .statusEffect(christmasSpirit, 1.0f).build())), "candy_cane");
    public static final Item GINGERBREAD_COOKIE = register(new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.6f).alwaysEdible().snack()
            .statusEffect(christmasSpirit, 1.0f).build())), "gingerbread_cookie");
    public static final Item CHOCOLATE_MILK_BUCKET = register(new DrinkItem(new FabricItemSettings().food(new FoodComponent.Builder().build())
            .recipeRemainder(Items.BUCKET).maxCount(1), true), "chocolate_milk_bucket");
    public static final Item HOT_CHOCOLATE_BUCKET = register(new DrinkItem(new FabricItemSettings().food(new FoodComponent.Builder()
            .statusEffect(christmasSpirit, 1.0f).build()).recipeRemainder(Items.BUCKET).maxCount(1), false), "hot_chocolate_bucket");

    public static final Item WARM_SPICES = register(new Item(new FabricItemSettings()), "warm_spices");

    public static Item register(Item item, String name) {
        ItemGroupEvents.modifyEntriesEvent(RAINBOW_CHRISTMAS_GROUP_KEY).register((itemGroup) -> itemGroup.add(item));
        Identifier id = RainbowChristmas.id(name);
        return Registry.register(Registries.ITEM, id, item);
    }

    public static void onInitialize() {
        Registry.register(Registries.ITEM_GROUP, RAINBOW_CHRISTMAS_GROUP_KEY, RAINBOW_CHRISTMAS_GROUP);
    }
}
