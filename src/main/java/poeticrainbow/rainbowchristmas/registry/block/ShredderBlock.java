package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import poeticrainbow.rainbowchristmas.registry.ModBlocks;
import poeticrainbow.rainbowchristmas.registry.TooltipContainer;

public class ShredderBlock extends Block implements TooltipContainer {
    public ShredderBlock(Properties settings) {
        super(settings);
    }


    @Override
    public String getTooltipTranslationKey() {
        return "tooltip.rainbowchristmas.shredder";
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClientSide() && world instanceof ServerLevel serverWorld) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.hurtServer(serverWorld, world.damageSources().generic(), 2);
                return;
            }
            if (entity instanceof ItemEntity itemEntity && entity.tickCount % 3 == 0) {
                ItemStack entityStack = itemEntity.getItem();
                if (entityStack.is(ItemTags.WOOL)) {
                    itemEntity.getItem().shrink(1);
                    ItemStack shreddedItemStack = new ItemStack(ModBlocks.FAUX_SNOW, 8);
                    itemEntity.spawnAtLocation(serverWorld, shreddedItemStack, -1 - itemEntity.getBbHeight());
                    itemEntity.playSound(SoundEvents.GRINDSTONE_USE, 0.6f, 0.8f + world.getRandom().nextFloat() * 0.3f);
                } else {
                    itemEntity.setDeltaMovement(0d, 0.35d, 0d);
                    itemEntity.playSound(SoundEvents.ANVIL_DESTROY, 1f, 1f);
                }
            }
        }
    }
}
