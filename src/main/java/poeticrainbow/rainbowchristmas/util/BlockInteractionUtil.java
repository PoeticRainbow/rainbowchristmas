package poeticrainbow.rainbowchristmas.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

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
    public static Direction getDirectionOfClosestSideFromHitPosition(Vec3 hitPos, BlockPos blockPos) {
        Direction side = Direction.DOWN;
        Vec3 hitPosWithinBlock = hitPos.add(blockPos.getCenter().reverse());

        double hitX = hitPosWithinBlock.x();
        double hitZ = hitPosWithinBlock.z();

        // If the distance on the Z axis is more significant, NS; else EW
        if (Mth.abs((float) hitZ) > Mth.abs((float) hitX)) {
            if (hitZ > 0) side = Direction.NORTH;
            if (hitZ < 0) side = Direction.SOUTH;
        } else {
            if (hitX > 0) side = Direction.WEST;
            if (hitX < 0) side = Direction.EAST;
        }

        return side;
    }
}
