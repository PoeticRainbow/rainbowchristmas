package poeticrainbow.rainbowchristmas.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import poeticrainbow.rainbowchristmas.RainbowChristmas;
import poeticrainbow.rainbowchristmas.registry.effect.ChristmasSpiritStatusEffect;

public class ModMisc {
    public static final SoundEvent BELLS = registerSound("bells");
    public static final RegistryEntry<StatusEffect> CHRISTMAS_SPIRIT = registerStatusEffect("christmas_spirit", new ChristmasSpiritStatusEffect());

    public static final RegistryKey<? extends Registry<EquipmentAsset>> EQUIPMENT_REGISTRY_KEY = RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset"));
    public static final RegistryKey<EquipmentAsset> FESTIVE = registerEquipment("festive_hat");

    public static SoundEvent registerSound(String name) {
        Identifier identifier = RainbowChristmas.id(name);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static RegistryEntry.Reference<StatusEffect> registerStatusEffect(String name, StatusEffect effect) {
        Identifier identifier = RainbowChristmas.id(name);
        return Registry.registerReference(Registries.STATUS_EFFECT, identifier, effect);
    }

    public static RegistryKey<EquipmentAsset> registerEquipment(String name) {
        return RegistryKey.of(EQUIPMENT_REGISTRY_KEY, RainbowChristmas.id(name));
    }

    public static void onInitialize() {
    }
}
