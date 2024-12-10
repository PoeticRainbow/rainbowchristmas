package poeticrainbow.rainbowchristmas.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BlockInteractionUtil {

    /*
        ^ Z
      < - > X
        V

            S
            ^
            |
      W < - 0 - > E
            |
            V
            N
    **/
    public static Direction getDirectionOfClosestSideFromHitPosition(Vec3d hitPos, BlockPos blockPos) {
        Direction side = Direction.DOWN;
        Vec3d hitPosWithinBlock = hitPos.add(blockPos.toCenterPos().negate());

        double hitX = hitPosWithinBlock.getX();
        double hitZ = hitPosWithinBlock.getZ();

        // If the distance on the Z axis is more significant, NS; else EW
        if (MathHelper.abs((float) hitZ) > MathHelper.abs((float) hitX)) {
            if (hitZ > 0) side = Direction.NORTH;
            if (hitZ < 0) side = Direction.SOUTH;
        } else {
            if (hitX > 0) side = Direction.WEST;
            if (hitX < 0) side = Direction.EAST;
        }

        return side;
    }
}
