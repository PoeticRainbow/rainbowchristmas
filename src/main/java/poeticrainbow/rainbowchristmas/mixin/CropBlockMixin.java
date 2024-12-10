package poeticrainbow.rainbowchristmas.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import poeticrainbow.rainbowchristmas.gen.ModDataGenerator;

@Mixin(CropBlock.class)
public class CropBlockMixin {
    @ModifyReturnValue(method = "canPlantOnTop(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Z",
    at = @At("TAIL"))
    public boolean rainbowchristmas$crop_on_plant_sustainable(boolean original, @Local(argsOnly = true) BlockState floor) {
        return original || floor.isIn(ModDataGenerator.ModBlockTags.PLANT_SUSTAINABLE);
    }
}
