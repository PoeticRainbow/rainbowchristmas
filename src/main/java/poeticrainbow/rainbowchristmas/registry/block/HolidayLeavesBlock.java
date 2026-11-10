package poeticrainbow.rainbowchristmas.registry.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;

public class HolidayLeavesBlock extends LeavesBlock {
    public static final MapCodec<HolidayLeavesBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
        ExtraCodecs.floatRange(0.0F, 1.0F)
              .fieldOf("leaf_particle_chance")
              .forGetter((block) -> block.leafParticleChance), propertiesCodec()
    ).apply(instance, HolidayLeavesBlock::new));

    @Override
    public MapCodec<? extends LeavesBlock> codec() {
        return CODEC;
    }

    public HolidayLeavesBlock(float leafParticleChance, Properties settings) {
        super(leafParticleChance, settings);
        registerDefaultState(getStateDefinition().any().setValue(PERSISTENT, true).setValue(WATERLOGGED, false));
    }

    @Override
    protected void spawnFallingLeavesParticle(Level world, BlockPos pos, RandomSource random) {

    }
}
