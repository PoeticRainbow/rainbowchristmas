package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import poeticrainbow.rainbowchristmas.registry.ModBlocks;

import java.util.List;

public class ShredderBlock extends Block {
    public ShredderBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.rainbowchristmas.shredder").setStyle(Style.EMPTY.withColor(Formatting.GRAY)));
        super.appendTooltip(stack, context, tooltip, options);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.damage(serverWorld, world.getDamageSources().generic(), 2);
                return;
            }
            if (entity instanceof ItemEntity itemEntity && entity.age % 3 == 0) {
                ItemStack entityStack = itemEntity.getStack();
                if (entityStack.isIn(ItemTags.WOOL)) {
                    itemEntity.getStack().decrement(1);
                    ItemStack shreddedItemStack = new ItemStack(ModBlocks.FAUX_SNOW, 8);
                    itemEntity.dropStack(serverWorld, shreddedItemStack, -1 - itemEntity.getHeight());
                    itemEntity.playSound(SoundEvents.BLOCK_GRINDSTONE_USE, 0.6f, 0.8f + world.getRandom().nextFloat() * 0.3f);
                } else {
                    itemEntity.setVelocity(0d, 0.35d, 0d);
                    itemEntity.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, 1f, 1f);
                }
            }
        }
    }
}
