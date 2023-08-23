package ad_astra_giselle_addon.common.block;

import earth.terrarium.ad_astra.common.block.door.LocationState;
import earth.terrarium.ad_astra.common.block.door.SlidingDoorBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class SlidingDoorBlockProperties
{
	public static final DirectionProperty FACING = SlidingDoorBlock.FACING;
	public static final BooleanProperty OPEN = SlidingDoorBlock.OPEN;
	public static final BooleanProperty POWERED = SlidingDoorBlock.POWERED;
	public static final EnumProperty<LocationState> LOCATION = SlidingDoorBlock.LOCATION;

	public static final BooleanProperty LOCKED = BlockStateProperties.LOCKED;
}
