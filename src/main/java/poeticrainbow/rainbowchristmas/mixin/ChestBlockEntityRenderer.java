package poeticrainbow.rainbowchristmas.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.time.LocalDate;

@Mixin(net.minecraft.client.render.block.entity.ChestBlockEntityRenderer.class)
public class ChestBlockEntityRenderer {
    @ModifyReturnValue(method = "isAroundChristmas()Z", at = @At("RETURN"))
    private static boolean rainbowchristmas$force_christmas_chests(boolean original) {
        return original || LocalDate.now().getMonthValue() == 12;
    }
}
