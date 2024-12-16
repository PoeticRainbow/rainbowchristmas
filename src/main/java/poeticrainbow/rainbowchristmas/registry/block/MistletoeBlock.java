package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import poeticrainbow.rainbowchristmas.registry.ModMisc;

import java.util.Collections;
import java.util.List;

public class MistletoeBlock extends Block {
    public static VoxelShape SHAPE;

    public MistletoeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.rainbowchristmas.mistletoe").setStyle(Style.EMPTY.withColor(Formatting.GRAY)));
        super.appendTooltip(stack, context, tooltip, options);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.playSound(null, pos, ModMisc.BELLS, SoundCategory.BLOCKS, 1f, 0.8f + random.nextFloat() * 0.4f);

        List<Entity> entities = world.getOtherEntities(null, Box.of(pos.toCenterPos().add(0d, -2d, 0d), 1.5d, 3d, 1.5d));
        Collections.shuffle(entities);
        for (int i = 0; i < entities.size() && i < 2; i++) {
            Entity entity = entities.get(i);
            if (entity instanceof AnimalEntity animal) {
                animal.lovePlayer(null);
                animal.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
            }
        }
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }

    static {
        SHAPE = createCuboidShape(1, 2, 1, 15, 16, 15);
    }
}
