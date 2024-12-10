package poeticrainbow.rainbowchristmas.registry.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class DrinkItem extends MilkBucketItem {
    public boolean clearEffects;

    public DrinkItem(Settings settings, boolean clearEffectsOnConsumption) {
        super(settings);
        clearEffects = clearEffectsOnConsumption;
    }

    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.INTENTIONALLY_EMPTY;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient() && clearEffects) {
            user.clearStatusEffects();
        }
        if (stack.isFood()) {
            user.eatFood(world, stack);
        }
        return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
    }
}
