package poeticrainbow.rainbowchristmas.registry.block;

import net.minecraft.block.LeavesBlock;

public class HolidayLeavesBlock extends LeavesBlock {
    public HolidayLeavesBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(PERSISTENT, true).with(WATERLOGGED, false));
    }
}
