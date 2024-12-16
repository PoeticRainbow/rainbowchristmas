package poeticrainbow.rainbowchristmas.registry.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import poeticrainbow.rainbowchristmas.RainbowChristmas;

public class ChristmasSpiritStatusEffect extends StatusEffect {
    public ChristmasSpiritStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xBC1F1C);
        Identifier attributeId = RainbowChristmas.id("christmas_spirit_effects");
        addAttributeModifier(EntityAttributes.LUCK, attributeId,
                0.2f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(EntityAttributes.MOVEMENT_SPEED, attributeId,
                0.2f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(EntityAttributes.ATTACK_SPEED, attributeId,
                0.5f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(EntityAttributes.ATTACK_DAMAGE, attributeId,
                1.0f, EntityAttributeModifier.Operation.ADD_VALUE);
    }
}
