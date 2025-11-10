package poeticrainbow.rainbowchristmas.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import poeticrainbow.rainbowchristmas.RainbowChristmas;
import poeticrainbow.rainbowchristmas.registry.effect.ChristmasSpiritStatusEffect;

import java.util.*;

public class ModMisc {
    public static final SoundEvent BELLS = registerSound("bells");
    public static final RegistryEntry<StatusEffect> CHRISTMAS_SPIRIT = registerStatusEffect("christmas_spirit", new ChristmasSpiritStatusEffect());

    public static List<String> DYE_COLORS = List.of("black", "blue", "brown", "cyan", "gray", "green", "light_blue", "light_gray",
        "lime", "magenta", "orange", "pink", "purple", "red", "white", "yellow");
    public static final Map<String, RegistryEntry.Reference<ArmorMaterial>> FESTIVE_MATERIALS = new HashMap<>();
    static {
        registerEquipment();
    }

    public static SoundEvent registerSound(String name) {
        Identifier identifier = RainbowChristmas.id(name);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static RegistryEntry.Reference<StatusEffect> registerStatusEffect(String name, StatusEffect effect) {
        Identifier identifier = RainbowChristmas.id(name);
        return Registry.registerReference(Registries.STATUS_EFFECT, identifier, effect);
    }

    public static void registerEquipment() {
        for (String color : DYE_COLORS) {
            var id = RainbowChristmas.id(color + "_festive_hat");
            var armorMaterial = new ArmorMaterial(
                Util.make(new EnumMap<>(ArmorItem.Type.class), map -> map.put(ArmorItem.Type.HELMET, 2)),
                2,
                SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
                () -> Ingredient.fromTag(ItemTags.WOOL),
                Collections.singletonList(new ArmorMaterial.Layer(id)),
                0,
                0
            );
            RegistryKey.of(RegistryKeys.ARMOR_MATERIAL, id);
            FESTIVE_MATERIALS.put(color + "_festive_hat", Registry.registerReference(Registries.ARMOR_MATERIAL, id, armorMaterial));
        }
    }

    public static void onInitialize() {
    }
}
