package poeticrainbow.rainbowchristmas.registry.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class HolidayLeavesBlock extends LeavesBlock {
    public static final MapCodec<HolidayLeavesBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
        Codecs.rangedInclusiveFloat(0.0F, 1.0F)
              .fieldOf("leaf_particle_chance")
              .forGetter((block) -> block.leafParticleChance), createSettingsCodec()
    ).apply(instance, HolidayLeavesBlock::new));

    @Override
    public MapCodec<? extends LeavesBlock> getCodec() {
        return CODEC;
    }

    public HolidayLeavesBlock(float leafParticleChance, Settings settings) {
        super(leafParticleChance, settings);
        setDefaultState(getStateManager().getDefaultState().with(PERSISTENT, true).with(WATERLOGGED, false));
    }

    @Override
    protected void spawnLeafParticle(World world, BlockPos pos, Random random) {

    }
}
