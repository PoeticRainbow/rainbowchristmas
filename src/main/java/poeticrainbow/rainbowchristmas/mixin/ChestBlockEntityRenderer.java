package poeticrainbow.rainbowchristmas.mixin;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.LocalDate;

@Mixin(net.minecraft.client.render.block.entity.ChestBlockEntityRenderer.class)
public class ChestBlockEntityRenderer {
    @Shadow
    private boolean christmas;

    @Inject(method = "<init>(Lnet/minecraft/client/render/block/entity/BlockEntityRendererFactory$Context;)V",
            at = @At(target = "Ljava/util/Calendar;getInstance()Ljava/util/Calendar;", value = "INVOKE"))
    public void rainbowchristmas$force_christmas_chests(BlockEntityRendererFactory.Context ctx, CallbackInfo ci) {
        christmas = LocalDate.now().getMonthValue() == 12;
    }
}
