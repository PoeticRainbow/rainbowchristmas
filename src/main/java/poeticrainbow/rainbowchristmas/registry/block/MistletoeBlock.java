package poeticrainbow.rainbowchristmas.registry.block;

import poeticrainbow.rainbowchristmas.registry.ModMisc;
import poeticrainbow.rainbowchristmas.registry.TooltipContainer;

import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MistletoeBlock extends Block implements TooltipContainer {
    public static VoxelShape SHAPE;

    public MistletoeBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        world.playSound(null, pos, ModMisc.BELLS, SoundSource.BLOCKS, 1f, 0.8f + random.nextFloat() * 0.4f);

        List<Entity> entities = world.getEntities(
            null, AABB.ofSize(
                pos.getCenter()
                   .add(0d, -2d, 0d), 1.5d, 3d, 1.5d
            )
        );
        Collections.shuffle(entities);
        for (int i = 0; i < entities.size() && i < 2; i++) {
            Entity entity = entities.get(i);
            if (entity instanceof Animal animal) {
                animal.setInLove(null);
                animal.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1f, 1f);
            }
        }
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0f;
    }

    static {
        SHAPE = box(1, 2, 1, 15, 16, 15);
    }

    @Override
    public String getTooltipTranslationKey() {
        return "tooltip.rainbowchristmas.mistletoe";
    }
}
