package ad_astra_giselle_addon.common.block.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.config.MachinesConfig;
import ad_astra_giselle_addon.common.menu.RocketSensorMenu;
import ad_astra_giselle_addon.common.registry.AddonBlockEntityTypes;
import earth.terrarium.ad_astra.common.entity.vehicle.Rocket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Redstone;
import net.minecraft.world.phys.AABB;

public class RocketSensorBlockEntity extends AddonMachineBlockEntity implements IRangedWorkingAreaBlockEntity
{
	public static final String DATA_WORKINGAREA_VISIBLE_KEY = "workingAreaVisible";
	public static final String DATA_SENSING_TYPE_KEY = "sensingType";
	public static final String DATA_INVERTED_KEY = "inverted";
	public static final String DATA_ANALOG_SIGNAL_KEY = "analogSignal";

	private boolean workingAreaVisible;
	private IRocketSensingType sensingType;
	private boolean inverted;
	private int analogSignal;

	private Rocket cachedTarget;

	public RocketSensorBlockEntity(BlockPos pos, BlockState state)
	{
		super(AddonBlockEntityTypes.ROCKET_SENSOR.get(), pos, state);
		this.workingAreaVisible = false;
		this.sensingType = RocketSensingType.DISABLED;
		this.inverted = false;
		this.analogSignal = Redstone.SIGNAL_NONE;
	}

	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
		this.workingAreaVisible = nbt.getBoolean(DATA_WORKINGAREA_VISIBLE_KEY);
		this.sensingType = IRocketSensingType.readNBT(nbt.get(DATA_SENSING_TYPE_KEY));
		this.inverted = nbt.getBoolean(DATA_INVERTED_KEY);
		this.analogSignal = nbt.getInt(DATA_ANALOG_SIGNAL_KEY);
	}

	@Override
	public void saveAdditional(CompoundTag nbt)
	{
		super.saveAdditional(nbt);
		nbt.putBoolean(DATA_WORKINGAREA_VISIBLE_KEY, this.workingAreaVisible);
		nbt.put(DATA_SENSING_TYPE_KEY, IRocketSensingType.writeNBT(this.sensingType));
		nbt.putBoolean(DATA_INVERTED_KEY, this.inverted);
		nbt.putInt(DATA_ANALOG_SIGNAL_KEY, this.analogSignal);
	}

	@Override
	@Nullable
	public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player)
	{
		return new RocketSensorMenu(windowId, inv, this);
	}

	@Override
	public void tick()
	{
		Level level = this.getLevel();

		if (!level.isClientSide())
		{
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
				this.setChanged();
			}
			else if (this.getAnalogSignal() != analogSignal)
			{
				this.analogSignal = analogSignal;
				this.setChanged();
			}

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
			this.setChanged();
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
			this.setChanged();
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
			this.setChanged();
		}

	}

	public int getAnalogSignal()
	{
		return this.analogSignal;
	}

}
