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
import poeticrainbow.rainbowchristmas.registry.item.TooltipItem;

public class ModItems {
    public static final RegistryKey<ItemGroup> RAINBOW_CHRISTMAS_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, RainbowChristmas.id("rainbowchristmas"));
    public static final ItemGroup RAINBOW_CHRISTMAS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.STAR_TOPPER.asItem()))
            .displayName(Text.translatable("itemGroup.rainbowChristmas"))
            .build();

    public static final Item WHITE_FESTIVE_HAT = registerFestiveHat("white");
    public static final Item LIGHT_GRAY_FESTIVE_HAT = registerFestiveHat("light_gray");
    public static final Item GRAY_FESTIVE_HAT = registerFestiveHat("gray");
    public static final Item BLACK_FESTIVE_HAT = registerFestiveHat("black");
    public static final Item BROWN_FESTIVE_HAT = registerFestiveHat("brown");
    public static final Item RED_FESTIVE_HAT = registerFestiveHat("red");
    public static final Item ORANGE_FESTIVE_HAT = registerFestiveHat("orange");
    public static final Item YELLOW_FESTIVE_HAT = registerFestiveHat("yellow");
    public static final Item LIME_FESTIVE_HAT = registerFestiveHat("lime");
    public static final Item GREEN_FESTIVE_HAT = registerFestiveHat("green");
    public static final Item CYAN_FESTIVE_HAT = registerFestiveHat("cyan");
    public static final Item LIGHT_BLUE_FESTIVE_HAT = registerFestiveHat("light_blue");
    public static final Item BLUE_FESTIVE_HAT = registerFestiveHat("blue");
    public static final Item PURPLE_FESTIVE_HAT = registerFestiveHat("purple");
    public static final Item MAGENTA_FESTIVE_HAT = registerFestiveHat("magenta");
    public static final Item PINK_FESTIVE_HAT = registerFestiveHat("pink");

    public static final Item HARD_CANDY = register(new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).alwaysEdible().snack().build())), "hard_candy");

    public static StatusEffectInstance christmasSpirit = new StatusEffectInstance(ModMisc.CHRISTMAS_SPIRIT, 300);
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

    public static final Item WARM_SPICES = register(new TooltipItem(new FabricItemSettings()), "warm_spices");

    public static Item registerFestiveHat(String color) {
        return register(new ArmorItem(new FestiveArmorMaterial(color), ArmorItem.Type.HELMET,
                        new FabricItemSettings()), color + "_festive_hat");
    }

    public static Item register(Item item, String name) {
        ItemGroupEvents.modifyEntriesEvent(RAINBOW_CHRISTMAS_GROUP_KEY).register((itemGroup) -> itemGroup.add(item));
        Identifier id = RainbowChristmas.id(name);
        return Registry.register(Registries.ITEM, id, item);
    }

    public static void onInitialize() {
        Registry.register(Registries.ITEM_GROUP, RAINBOW_CHRISTMAS_GROUP_KEY, RAINBOW_CHRISTMAS_GROUP);
    }
}
