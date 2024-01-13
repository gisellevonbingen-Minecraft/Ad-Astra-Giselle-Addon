package ad_astra_giselle_addon.common.block.entity;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.config.MachinesConfig;
import ad_astra_giselle_addon.common.menu.RocketSensorMenu;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Redstone;
import net.minecraft.world.phys.AABB;

public class RocketSensorBlockEntity extends ContainerMachineBlockEntity implements IRangedWorkingAreaBlockEntity
{
	public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(//
			new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.NONE, ConstantComponents.SIDE_CONFIG_ENERGY));

	public static final String DATA_WORKINGAREA_VISIBLE_KEY = "workingAreaVisible";
	public static final String DATA_SENSING_TYPE_KEY = "sensingType";
	public static final String DATA_INVERTED_KEY = "inverted";
	public static final String DATA_ANALOG_SIGNAL_KEY = "analogSignal";

	public static final int CONTAINER_SIZE = 0;

	private boolean workingAreaVisible;
	private IRocketSensingType sensingType;
	private boolean inverted;
	private int analogSignal;

	private Rocket cachedTarget;

	public RocketSensorBlockEntity(BlockPos pos, BlockState state)
	{
		super(pos, state, CONTAINER_SIZE);
		this.workingAreaVisible = false;
		this.sensingType = RocketSensingType.DISABLED;
		this.inverted = false;
		this.analogSignal = Redstone.SIGNAL_NONE;
	}

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		this.workingAreaVisible = tag.getBoolean(DATA_WORKINGAREA_VISIBLE_KEY);
		this.sensingType = IRocketSensingType.readNBT(tag.get(DATA_SENSING_TYPE_KEY));
		this.inverted = tag.getBoolean(DATA_INVERTED_KEY);
		this.analogSignal = tag.getInt(DATA_ANALOG_SIGNAL_KEY);
	}

	@Override
	public void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		tag.putBoolean(DATA_WORKINGAREA_VISIBLE_KEY, this.workingAreaVisible);
		tag.put(DATA_SENSING_TYPE_KEY, IRocketSensingType.writeNBT(this.sensingType));
		tag.putBoolean(DATA_INVERTED_KEY, this.inverted);
		tag.putInt(DATA_ANALOG_SIGNAL_KEY, this.analogSignal);
	}

	@Override
	@Nullable
	public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player)
	{
		return new RocketSensorMenu(windowId, inv, this);
	}

	@Override
	public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos)
	{
		super.serverTick(level, time, state, pos);

		Rocket newTarget = this.findRocket();
		int analogSignal = newTarget != null ? this.getSensingType().getAnalogSignal(newTarget) : Redstone.SIGNAL_NONE;

		if (this.isInverted())
		{
			analogSignal = Redstone.SIGNAL_MAX - analogSignal;
		}

		if (this.getCachedTarget() != newTarget)
		{
			this.cachedTarget = newTarget;
			this.analogSignal = analogSignal;
			this.sync();
		}
		else if (this.getAnalogSignal() != analogSignal)
		{
			this.analogSignal = analogSignal;
			this.sync();
		}

	}

	public Rocket findRocket()
	{
		Level level = this.getLevel();
		AABB workingArea = this.getWorkingArea();
		return level.getEntitiesOfClass(Rocket.class, workingArea).stream().findFirst().orElse(null);
	}

	@Override
	public boolean isWorkingAreaVisible()
	{
		return this.workingAreaVisible;
	}

	@Override
	public void setWorkingAreaVisible(boolean visible)
	{
		if (this.isWorkingAreaVisible() != visible)
		{
			this.workingAreaVisible = visible;
			this.sync();
		}

	}

	@Override
	public int getWorkingRange()
	{
		return MachinesConfig.ROCKET_SENSOR_WORKING_RANGE;
	}

	@Override
	public AABB getWorkingArea(double range)
	{
		return this.getWorkingArea(this.getBlockPos(), range);
	}

	public Rocket getCachedTarget()
	{
		return this.cachedTarget;
	}

	@NotNull
	public IRocketSensingType getSensingType()
	{
		return this.sensingType;
	}

	public void setSensingType(@Nullable IRocketSensingType sensingType)
	{
		if (sensingType == null)
		{
			sensingType = RocketSensingType.DISABLED;
		}

		if (this.getSensingType() != sensingType)
		{
			this.sensingType = sensingType;
			this.sync();
		}

	}

	public boolean isInverted()
	{
		return inverted;
	}

	public void setInverted(boolean inverted)
	{
		if (this.isInverted() != inverted)
		{
			this.inverted = inverted;
			this.sync();
		}

	}

	public int getAnalogSignal()
	{
		return this.analogSignal;
	}

	@Override
	public List<ConfigurationEntry> getDefaultConfig()
	{
		return SIDE_CONFIG;
	}

	@Override
	public int[] getSlotsForFace(Direction pSide)
	{
		return new int[]{};
	}

}
