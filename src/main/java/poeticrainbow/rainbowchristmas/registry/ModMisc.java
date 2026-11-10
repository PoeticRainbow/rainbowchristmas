package poeticrainbow.rainbowchristmas.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.equipment.EquipmentAsset;
import poeticrainbow.rainbowchristmas.RainbowChristmas;
import poeticrainbow.rainbowchristmas.registry.effect.ChristmasSpiritStatusEffect;

public class ModMisc {
    public static final SoundEvent BELLS = registerSound("bells");
    public static final Holder<MobEffect> CHRISTMAS_SPIRIT = registerStatusEffect("christmas_spirit", new ChristmasSpiritStatusEffect());

    public static final ResourceKey<? extends Registry<EquipmentAsset>> EQUIPMENT_REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));
    public static final ResourceKey<EquipmentAsset> FESTIVE = registerEquipment("festive_hat");

    public static SoundEvent registerSound(String name) {
        Identifier identifier = RainbowChristmas.id(name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier));
    }

    public static Holder.Reference<MobEffect> registerStatusEffect(String name, MobEffect effect) {
        Identifier identifier = RainbowChristmas.id(name);
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, identifier, effect);
    }

    public static ResourceKey<EquipmentAsset> registerEquipment(String name) {
        return ResourceKey.create(EQUIPMENT_REGISTRY_KEY, RainbowChristmas.id(name));
    }

    public static void onInitialize() {
    }
}
