package poeticrainbow.rainbowchristmas.registry.effect;

import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import poeticrainbow.rainbowchristmas.RainbowChristmas;

public class ChristmasSpiritStatusEffect extends MobEffect {
    public ChristmasSpiritStatusEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xBC1F1C);
        Identifier attributeId = RainbowChristmas.id("christmas_spirit_effects");
        addAttributeModifier(Attributes.LUCK, attributeId,
                0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, attributeId,
                0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.ATTACK_SPEED, attributeId,
                0.5f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, attributeId,
                1.0f, AttributeModifier.Operation.ADD_VALUE);
    }
}
