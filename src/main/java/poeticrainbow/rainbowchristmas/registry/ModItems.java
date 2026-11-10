package poeticrainbow.rainbowchristmas.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Util;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ClearAllStatusEffectsConsumeEffect;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import poeticrainbow.rainbowchristmas.RainbowChristmas;
import poeticrainbow.rainbowchristmas.registry.item.TooltipItem;

import java.util.EnumMap;
import java.util.List;

public class ModItems {
    public static final ResourceKey<CreativeModeTab> RAINBOW_CHRISTMAS_GROUP_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, RainbowChristmas.id("rainbowchristmas"));
    public static final CreativeModeTab RAINBOW_CHRISTMAS_GROUP = FabricItemGroup.builder()
                                                                           .icon(() -> new ItemStack(ModBlocks.STAR_TOPPER.asItem()))
                                                                           .title(Component.translatable("itemGroup.rainbowChristmas"))
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
    public static List<Item> FESTIVE_HATS = List.of(
        WHITE_FESTIVE_HAT, LIGHT_GRAY_FESTIVE_HAT, GRAY_FESTIVE_HAT, BLACK_FESTIVE_HAT,
        BROWN_FESTIVE_HAT, RED_FESTIVE_HAT, ORANGE_FESTIVE_HAT, YELLOW_FESTIVE_HAT, LIME_FESTIVE_HAT, GREEN_FESTIVE_HAT,
        CYAN_FESTIVE_HAT, LIGHT_BLUE_FESTIVE_HAT, BLUE_FESTIVE_HAT, PURPLE_FESTIVE_HAT, MAGENTA_FESTIVE_HAT, PINK_FESTIVE_HAT
    );

    public static final Item HARD_CANDY = register(
        new Item(new Item.Properties().setId(keyOfItem("hard_candy"))
                                    .component(
                                        DataComponents.CONSUMABLE, Consumable.builder()
                                                                                          .consumeSeconds(0.5f)
                                                                                          .build()
                                    )
                                    .food(new FoodProperties.Builder().nutrition(1)
                                                                     .saturationModifier(0.2f)
                                                                     .alwaysEdible()
                                                                     .build())), "hard_candy"
    );

    public static MobEffectInstance christmasSpirit = new MobEffectInstance(ModMisc.CHRISTMAS_SPIRIT, 300);
    public static Consumable christmasSpiritComponent = Consumable.builder()
                                                                                    .consumeSeconds(0.5f)
                                                                                    .onConsume(new ApplyStatusEffectsConsumeEffect(christmasSpirit, 1.0f))
                                                                                    .build();

    public static final Item CANDY_CANE = register(
        new Item(new Item.Properties().setId(keyOfItem("candy_cane"))
                                    .food(new FoodProperties.Builder().nutrition(6)
                                                                     .saturationModifier(0.6f)
                                                                     .alwaysEdible()
                                                                     .build())
                                    .component(DataComponents.CONSUMABLE, christmasSpiritComponent)), "candy_cane"
    );
    public static final Item GINGERBREAD_COOKIE = register(
        new Item(new Item.Properties().setId(keyOfItem("gingerbread_cookie"))
                                    .food(new FoodProperties.Builder().nutrition(6)
                                                                     .saturationModifier(0.6f)
                                                                     .alwaysEdible()
                                                                     .build())
                                    .component(DataComponents.CONSUMABLE, christmasSpiritComponent)), "gingerbread_cookie"
    );

    public static final Item CHOCOLATE_MILK_BUCKET = register(
        new Item(new Item.Properties().setId(keyOfItem("chocolate_milk_bucket"))
                                    .usingConvertsTo(Items.BUCKET)
                                    .stacksTo(1)
                                    .component(
                                        DataComponents.CONSUMABLE, Consumable.builder()
                                                                                          .hasConsumeParticles(false)
                                                                                          .sound(SoundEvents.GENERIC_DRINK)
                                                                                          .onConsume(ClearAllStatusEffectsConsumeEffect.INSTANCE)
                                                                                          .build()
                                    )), "chocolate_milk_bucket"
    );

    public static final Item HOT_CHOCOLATE_BUCKET = register(
        new Item(new Item.Properties().setId(keyOfItem("hot_chocolate_bucket"))
                                    .usingConvertsTo(Items.BUCKET)
                                    .stacksTo(1)
                                    .component(
                                        DataComponents.CONSUMABLE, Consumable.builder()
                                                                                          .hasConsumeParticles(false)
                                                                                          .sound(SoundEvents.GENERIC_DRINK)
                                                                                          .onConsume(ClearAllStatusEffectsConsumeEffect.INSTANCE)
                                                                                          .onConsume(new ApplyStatusEffectsConsumeEffect(christmasSpirit, 1.0f))
                                                                                          .build()
                                    )), "hot_chocolate_bucket"
    );

    public static final Item WARM_SPICES = register(new TooltipItem(new Item.Properties().setId(keyOfItem("warm_spices"))), "warm_spices");

    public static Item registerFestiveHat(String color) {
        ArmorMaterial festiveArmorMaterial = new ArmorMaterial(
            200, Util.make(new EnumMap<>(ArmorType.class), map -> map.put(ArmorType.HELMET, 2)), 2, SoundEvents.ARMOR_EQUIP_LEATHER, 0, 0, ItemTags.WOOL,
            ModMisc.registerEquipment(color + "_festive_hat")
        );
        return register(
            new Item(
                new Item.Properties().setId(keyOfItem(color + "_festive_hat"))
                                   .component(
                                       DataComponents.EQUIPPABLE,
                                       Equippable.builder(EquipmentSlot.HEAD).setAsset(ModMisc.FESTIVE).build()
                                   )
                                   .humanoidArmor(festiveArmorMaterial, ArmorType.HELMET)
            ),
            color + "_festive_hat"
        );
    }

    public static Item register(Item item, String name) {
        ItemGroupEvents.modifyEntriesEvent(RAINBOW_CHRISTMAS_GROUP_KEY).register((itemGroup) -> itemGroup.accept(item));
        Identifier id = RainbowChristmas.id(name);
        return Registry.register(BuiltInRegistries.ITEM, id, item);
    }

    public static ResourceKey<Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, RainbowChristmas.id(name));
    }

    public static void onInitialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, RAINBOW_CHRISTMAS_GROUP_KEY, RAINBOW_CHRISTMAS_GROUP);
    }
}
