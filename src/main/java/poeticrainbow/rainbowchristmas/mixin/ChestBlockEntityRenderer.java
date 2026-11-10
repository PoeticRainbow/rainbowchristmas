package poeticrainbow.rainbowchristmas.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.time.LocalDate;

@Mixin(net.minecraft.client.renderer.blockentity.ChestRenderer.class)
public class ChestBlockEntityRenderer {
    @ModifyReturnValue(method = "xmasTextures()Z", at = @At("RETURN"))
    private static boolean rainbowchristmas$force_christmas_chests(boolean original) {
        return original || LocalDate.now().getMonthValue() == 12;
    }
}
