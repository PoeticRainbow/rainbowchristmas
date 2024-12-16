package poeticrainbow.rainbowchristmas.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.ClearAllEffectsConsumeEffect;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import poeticrainbow.rainbowchristmas.RainbowChristmas;
import poeticrainbow.rainbowchristmas.registry.item.TooltipItem;

import java.util.EnumMap;
import java.util.List;

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
    public static List<Item> FESTIVE_HATS = List.of(WHITE_FESTIVE_HAT, LIGHT_GRAY_FESTIVE_HAT, GRAY_FESTIVE_HAT, BLACK_FESTIVE_HAT,
            BROWN_FESTIVE_HAT, RED_FESTIVE_HAT, ORANGE_FESTIVE_HAT, YELLOW_FESTIVE_HAT, LIME_FESTIVE_HAT, GREEN_FESTIVE_HAT,
            CYAN_FESTIVE_HAT, LIGHT_BLUE_FESTIVE_HAT, BLUE_FESTIVE_HAT, PURPLE_FESTIVE_HAT, MAGENTA_FESTIVE_HAT, PINK_FESTIVE_HAT);

    public static final Item HARD_CANDY = register(new Item(new Item.Settings().registryKey(keyOfItem("hard_candy"))
            .component(DataComponentTypes.CONSUMABLE, ConsumableComponent.builder().consumeSeconds(0.5f).build())
            .food(new FoodComponent.Builder().nutrition(1).saturationModifier(0.2f).alwaysEdible().build())), "hard_candy");

    public static StatusEffectInstance christmasSpirit = new StatusEffectInstance(ModMisc.CHRISTMAS_SPIRIT, 300);
    public static ConsumableComponent christmasSpiritComponent = ConsumableComponent.builder().consumeSeconds(0.5f).consumeEffect(new ApplyEffectsConsumeEffect(christmasSpirit, 1.0f)).build();

    public static final Item CANDY_CANE = register(new Item(new Item.Settings().registryKey(keyOfItem("candy_cane"))
            .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f).alwaysEdible().build())
            .component(DataComponentTypes.CONSUMABLE, christmasSpiritComponent)), "candy_cane");
    public static final Item GINGERBREAD_COOKIE = register(new Item(new Item.Settings().registryKey(keyOfItem("gingerbread_cookie"))
            .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f).alwaysEdible().build())
            .component(DataComponentTypes.CONSUMABLE, christmasSpiritComponent)), "gingerbread_cookie");

    public static final Item CHOCOLATE_MILK_BUCKET = register(new Item(new Item.Settings().registryKey(keyOfItem("chocolate_milk_bucket"))
            .useRemainder(Items.BUCKET).maxCount(1)
            .component(DataComponentTypes.CONSUMABLE, ConsumableComponent.builder()
                    .consumeEffect(ClearAllEffectsConsumeEffect.INSTANCE).build())), "chocolate_milk_bucket");

    public static final Item HOT_CHOCOLATE_BUCKET = register(new Item(new Item.Settings().registryKey(keyOfItem("hot_chocolate_bucket"))
            .useRemainder(Items.BUCKET).maxCount(1)
            .component(DataComponentTypes.CONSUMABLE, ConsumableComponent.builder()
                    .consumeEffect(ClearAllEffectsConsumeEffect.INSTANCE)
                    .consumeEffect(new ApplyEffectsConsumeEffect(christmasSpirit, 1.0f)).build())), "hot_chocolate_bucket");

    public static final Item WARM_SPICES = register(new TooltipItem(new Item.Settings().registryKey(keyOfItem("warm_spices"))), "warm_spices");

    public static Item registerFestiveHat(String color) {
        ArmorMaterial festiveArmorMaterial = new ArmorMaterial(200, Util.make(new EnumMap<>(EquipmentType.class), map -> map.put(EquipmentType.HELMET, 2)), 2, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0, 0, ItemTags.WOOL, ModMisc.FESTIVE);
        return register(new ArmorItem(festiveArmorMaterial, EquipmentType.HELMET,
                        new Item.Settings()
                                .registryKey(keyOfItem(color + "_festive_hat"))),
                        color + "_festive_hat");
    }

    public static Item register(Item item, String name) {
        ItemGroupEvents.modifyEntriesEvent(RAINBOW_CHRISTMAS_GROUP_KEY).register((itemGroup) -> itemGroup.add(item));
        Identifier id = RainbowChristmas.id(name);
        return Registry.register(Registries.ITEM, id, item);
    }

    public static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, RainbowChristmas.id(name));
    }

    public static void onInitialize() {
        Registry.register(Registries.ITEM_GROUP, RAINBOW_CHRISTMAS_GROUP_KEY, RAINBOW_CHRISTMAS_GROUP);
    }
}
