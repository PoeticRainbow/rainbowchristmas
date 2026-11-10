package poeticrainbow.rainbowchristmas.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import poeticrainbow.rainbowchristmas.gen.ModDataGenerator;

@Mixin(VegetationBlock.class)
public class PlantBlockMixin {
    @ModifyReturnValue(
            method = "mayPlaceOn(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z",
            at = @At(value="TAIL"))
    private boolean rainbowchristmas$plant_on_plant_sustainable(boolean original, @Local(argsOnly = true) BlockState floor) {
        return original || floor.is(ModDataGenerator.ModBlockTags.PLANT_SUSTAINABLE);
    }
}
