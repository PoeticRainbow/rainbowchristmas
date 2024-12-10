package poeticrainbow.rainbowchristmas.render;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

import java.awt.*;

public class MulticolorProvider {
    private static final Random RANDOM;
    private static final PerlinNoiseSampler NOISE;

    public static int getRainbowColorFromBlockPos(BlockPos pos, float horizonalScale, float verticalScale) {
        return Color.HSBtoRGB((float) NOISE.sample(
                        (float) pos.getX() / horizonalScale,
                        (float) pos.getY() / verticalScale,
                        (float) pos.getZ() / horizonalScale),
                0.7f, 0.95f);
    }

    public static int holidayLeavesBlockColorProvider(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex) {
        if (pos != null) {
            return MulticolorProvider.getRainbowColorFromBlockPos(pos, 32f, 8f);
        }
        return 0xFF0000;
    }

    public static int rainbowItemColorProvider(ItemStack stack, int tintIndex) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.world == null) {
            return 0xEE0000;
        }
        return Color.HSBtoRGB((client.world.getTime() / 240f) % 1f, 0.7f, 0.95f);
    }

    static {
        RANDOM = Random.create(21492397L);
        NOISE = new PerlinNoiseSampler(RANDOM);
    }
}
