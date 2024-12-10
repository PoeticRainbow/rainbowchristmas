package poeticrainbow.rainbowchristmas.registry.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ChristmasSpiritStatusEffect extends StatusEffect {
    public ChristmasSpiritStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xBC1F1C);
        addAttributeModifier(EntityAttributes.GENERIC_LUCK, "1e7a10b1-bf3a-4460-8fec-6e7741503438",
                0.2f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "79d71111-3cee-4951-bfe2-78f6dfa8c256",
                0.2f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "a451f576-f31d-415e-afc1-3a486bad2dd0",
                0.5f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "84de266f-ae62-4749-90ac-cfda1899fb87",
                1.0f, EntityAttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return super.canApplyUpdateEffect(duration, amplifier);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
    }
}
