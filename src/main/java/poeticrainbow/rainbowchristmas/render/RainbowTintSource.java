package poeticrainbow.rainbowchristmas.render;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public record RainbowTintSource(int defaultColor) implements ItemTintSource {
    public static final MapCodec<RainbowTintSource> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default").forGetter(RainbowTintSource::defaultColor)).apply(instance, RainbowTintSource::new));

    public RainbowTintSource() {
        this(0xFFFFFF);
    }

    @Override
    public @NonNull MapCodec<? extends ItemTintSource> type() {
        return CODEC;
    }

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity user) {
        return MulticolorProvider.rainbowItemColorProvider();
    }
}
