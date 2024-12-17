package poeticrainbow.rainbowchristmas.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.tint.TintSource;
import net.minecraft.client.render.item.tint.TintSourceTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import poeticrainbow.rainbowchristmas.RainbowChristmas;
import poeticrainbow.rainbowchristmas.render.RainbowTintSource;

@Mixin(TintSourceTypes.class)
public class TintSourceTypesMixin {
    @Shadow @Final public static Codecs.IdMapper<Identifier, MapCodec<? extends TintSource>> ID_MAPPER;

    @Inject(method = "bootstrap()V", at = @At("TAIL"))
    private static void inject(CallbackInfo ci) {
        ID_MAPPER.put(RainbowChristmas.id("rainbow"), RainbowTintSource.CODEC);
    }
}
