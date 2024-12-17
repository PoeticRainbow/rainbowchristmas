package poeticrainbow.rainbowchristmas.render;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.render.item.tint.TintSource;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.Nullable;

public record RainbowTintSource(int defaultColor) implements TintSource {
    public static final MapCodec<RainbowTintSource> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(Codecs.RGB.fieldOf("default").forGetter(RainbowTintSource::defaultColor)).apply(instance, RainbowTintSource::new));

    public RainbowTintSource() {
        this(0xFFFFFF);
    }

    @Override
    public MapCodec<? extends TintSource> getCodec() {
        return CODEC;
    }

    @Override
    public int getTint(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user) {
        return MulticolorProvider.rainbowItemColorProvider();
    }
}
