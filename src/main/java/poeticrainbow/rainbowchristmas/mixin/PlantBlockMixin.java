package poeticrainbow.rainbowchristmas.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import poeticrainbow.rainbowchristmas.gen.ModDataGenerator;

@Mixin(PlantBlock.class)
public class PlantBlockMixin {
    @ModifyReturnValue(
            method = "canPlantOnTop(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Z",
            at = @At(value="TAIL"))
    private boolean rainbowchristmas$plant_on_plant_sustainable(boolean original, @Local(argsOnly = true) BlockState floor) {
        return original || floor.isIn(ModDataGenerator.ModBlockTags.PLANT_SUSTAINABLE);
    }
}
