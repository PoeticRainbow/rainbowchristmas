package poeticrainbow.rainbowchristmas.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import poeticrainbow.rainbowchristmas.RainbowChristmas;
import poeticrainbow.rainbowchristmas.registry.effect.ChristmasSpiritStatusEffect;

public class ModMisc {
    public static final SoundEvent BELLS = registerSound("bells");
    public static final StatusEffect CHRISTMAS_SPIRIT = registerStatusEffect("christmas_spirit", new ChristmasSpiritStatusEffect());

    public static SoundEvent registerSound(String name) {
        Identifier identifier = RainbowChristmas.id(name);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static StatusEffect registerStatusEffect(String name, StatusEffect effect) {
        Identifier identifier = RainbowChristmas.id(name);
        return Registry.register(Registries.STATUS_EFFECT, identifier, effect);
    }

    public static void onInitialize() {
    }
}
