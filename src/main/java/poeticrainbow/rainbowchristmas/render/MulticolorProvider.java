package poeticrainbow.rainbowchristmas.render;

import java.awt.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;

public class MulticolorProvider {
    private static final RandomSource RANDOM;
    private static final ImprovedNoise NOISE;

    public static int getRainbowColorFromBlockPos(BlockPos pos, float horizonalScale, float verticalScale) {
        return Color.HSBtoRGB((float) NOISE.noise(
                        (float) pos.getX() / horizonalScale,
                        (float) pos.getY() / verticalScale,
                        (float) pos.getZ() / horizonalScale),
                0.7f, 0.95f);
    }

    public static int holidayLeavesBlockColorProvider(BlockState state, BlockAndTintGetter world, BlockPos pos, int tintIndex) {
        if (pos != null) {
            return MulticolorProvider.getRainbowColorFromBlockPos(pos, 32f, 8f);
        }
        return 0xFF0000;
    }

    public static int rainbowItemColorProvider() {
        Minecraft client = Minecraft.getInstance();
        if (client == null || client.level == null) {
            return 0xEE0000;
        }
        return Color.HSBtoRGB((client.level.getGameTime() / 240f) % 1f, 0.7f, 0.95f);
    }

    static {
        RANDOM = RandomSource.create(21492397L);
        NOISE = new ImprovedNoise(RANDOM);
    }
}
