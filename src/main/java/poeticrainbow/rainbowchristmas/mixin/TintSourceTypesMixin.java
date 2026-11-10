package poeticrainbow.rainbowchristmas.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import poeticrainbow.rainbowchristmas.RainbowChristmas;
import poeticrainbow.rainbowchristmas.render.RainbowTintSource;

@Mixin(ItemTintSources.class)
public class TintSourceTypesMixin {
    @Shadow @Final public static ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends ItemTintSource>> ID_MAPPER;

    @Inject(method = "bootstrap()V", at = @At("TAIL"))
    private static void inject(CallbackInfo ci) {
        ID_MAPPER.put(RainbowChristmas.id("rainbow"), RainbowTintSource.CODEC);
    }
}
