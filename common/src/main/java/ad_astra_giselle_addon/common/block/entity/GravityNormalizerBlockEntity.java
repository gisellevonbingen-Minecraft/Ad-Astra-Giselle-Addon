package ad_astra_giselle_addon.common.block.entity;

import java.util.List;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.config.MachinesConfig;
import ad_astra_giselle_addon.common.content.proof.GravityNormalizingUtils;
import ad_astra_giselle_addon.common.menu.GravityNormalizerMenu;
import ad_astra_giselle_addon.common.util.Vec3iUtils;
import earth.terrarium.adastra.common.blockentities.base.EnergyContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class GravityNormalizerBlockEntity extends EnergyContainerMachineBlockEntity implements IWorkingAreaBlockEntity
{
	public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(//
			new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.NONE, ConstantComponents.SIDE_CONFIG_ENERGY));

	public static final String DATA_LENGTH_KEY = "length";
	public static final String DATA_OFFSET_KEY = "offset";
	public static final String DATA_TIMER_KEY = "timer";
	public static final String DATA_WORKINGAREA_VISIBLE_KEY = "workingAreaVisible";

	public static final int CONTAINER_SIZE = 1;

	private Vec3i length;
	private Vec3i offset;
	private int timer;
	private boolean workingAreaVisible;

	public GravityNormalizerBlockEntity(BlockPos pos, BlockState state)
	{
		super(pos, state, CONTAINER_SIZE);
		this.length = new Vec3i(3, 3, 3);
		this.offset = offsetFromLength(this.length);
		this.timer = 0;
		this.workingAreaVisible = false;
	}

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		this.length = Vec3i.CODEC.parse(NbtOps.INSTANCE, tag.get(DATA_LENGTH_KEY)).result().get();
		this.offset = Vec3i.CODEC.parse(NbtOps.INSTANCE, tag.get(DATA_OFFSET_KEY)).result().get();
		this.timer = tag.getInt(DATA_TIMER_KEY);
		this.workingAreaVisible = tag.getBoolean(DATA_WORKINGAREA_VISIBLE_KEY);
	}

	@Override
	public void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		tag.put(DATA_LENGTH_KEY, Vec3i.CODEC.encodeStart(NbtOps.INSTANCE, this.length).result().get());
		tag.put(DATA_OFFSET_KEY, Vec3i.CODEC.encodeStart(NbtOps.INSTANCE, this.offset).result().get());
		tag.putInt(DATA_TIMER_KEY, this.timer);
		tag.putBoolean(DATA_WORKINGAREA_VISIBLE_KEY, this.workingAreaVisible);
	}

	@Override
	@Nullable
	public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player)
	{
		return new GravityNormalizerMenu(windowId, inv, this);
	}

	@Override
	public WrappedBlockEnergyContainer getEnergyStorage()
	{
		if (this.energyContainer == null)
		{
			this.energyContainer = new WrappedBlockEnergyContainer(this, new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(MachinesConfig.GRAVITY_NORMALIZER_ENERGY_CAPACITY, MachinesConfig.GRAVITY_NORMALIZER_ENERGY_CAPACITY)));
		}

		return this.energyContainer;
	}

	@Override
	public void tick(Level level, long time, BlockState state, BlockPos pos)
	{
		super.tick(level, time, state, pos);

		if (this.canFunction())
		{
			int timer = this.getTimer();
			timer--;

			if (timer <= 0)
			{
				timer = this.getMaxTimer();
				AABB workingArea = this.getWorkingArea();
				this.doNormalize(workingArea);
				this.sync();
			}

			this.setTimer(timer);
		}

	}

	protected void doNormalize(AABB workingArea)
	{
		Level level = this.getLevel();
		GravityNormalizingUtils proof = GravityNormalizingUtils.INSTANCE;
		long energyUsing = this.getEnergyUsing(workingArea);
		WrappedBlockEnergyContainer energyStorage = this.getEnergyStorage();

		if (energyStorage.internalExtract(energyUsing, true) < energyUsing)
		{
			return;
		}

		List<LivingEntity> livings = level.getEntitiesOfClass(LivingEntity.class, workingArea);

		if (livings.size() <= 0)
		{
			return;
		}

		energyStorage.internalExtract(energyUsing, false);
		int proofDuration = this.getMaxTimer() + 1;

		for (LivingEntity entity : livings)
		{
			if (proof.getProofDuration(entity) > proofDuration)
			{
				continue;
			}

			proof.setProofDuration(entity, proofDuration);
		}

	}

	@Override
	public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter, List<ConfigurationEntry> sideConfig)
	{
		TransferUtils.pullEnergyNearby(this, pos, getEnergyStorage().maxInsert(), sideConfig.get(0), filter);
	}

	public long getEnergyUsing()
	{
		return this.getEnergyUsing(this.getWorkingArea());
	}

	public long getEnergyUsing(AABB workingArea)
	{
		double blocks = workingArea.getXsize() * workingArea.getYsize() * workingArea.getZsize();
		return (long) (MachinesConfig.GRAVITY_NORMALIZER_ENERGY_PER_BLOCKS * blocks);
	}

	public static int getMinLength()
	{
		return 1;
	}

	public static int getMaxLength()
	{
		return MachinesConfig.GRAVITY_NORMALIZER_MAX_LENGTH;
	}

	public Vec3i getLength()
	{
		return this.length;
	}

	public void setLength(Vec3i length)
	{
		length = Vec3iUtils.clamp(length, getMinLength(), getMaxLength());

		if (!this.getLength().equals(length))
		{
			this.length = length;
			this.sync();
		}

	}

	public static int half(int length)
	{
		return (length - 1) / 2;
	}

	public static int offsetFromLength(int length)
	{
		return -half(length);
	}

	public static Vec3i offsetFromLength(Vec3i length)
	{
		int x = offsetFromLength(length.getX());
		int y = 0;
		int z = offsetFromLength(length.getZ());
		return new Vec3i(x, y, z);
	}

	public static int getMinOffset()
	{
		return -half(MachinesConfig.GRAVITY_NORMALIZER_MAX_LENGTH);
	}

	public static int getMaxOffset()
	{
		return +half(MachinesConfig.GRAVITY_NORMALIZER_MAX_LENGTH);
	}

	public Vec3i getOffset()
	{
		return this.offset;
	}

	public void setOffset(Vec3i offset)
	{
		offset = Vec3iUtils.clamp(offset, getMinOffset(), getMaxOffset());

		if (!this.getOffset().equals(offset))
		{
			this.offset = offset;
			this.sync();
		}

	}

	public int getTimer()
	{
		return this.timer;
	}

	public void setTimer(int timer)
	{
		timer = Math.max(timer, 0);

		if (this.getTimer() != timer)
		{
			this.timer = timer;
		}

	}

	public int getMaxTimer()
	{
		return MachinesConfig.GRAVITY_NORMALIZER_PROOF_DURATION;
	}

	@Override
	public AABB getWorkingArea()
	{
		return getWorkingArea(this.getBlockPos());
	}

	@Override
	public AABB getWorkingArea(BlockPos pos)
	{
		Vec3i length = this.getLength();
		Vec3i offset = this.getOffset();
		double x = (length.getX() - 1.0D) / 2.0D;
		double y = (length.getY() - 1.0D) / 2.0D;
		double z = (length.getZ() - 1.0D) / 2.0D;
		return new AABB(pos).inflate(x, y, z).move(x + offset.getX(), y + offset.getY(), z + offset.getZ());
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
	public List<ConfigurationEntry> getDefaultConfig()
	{
		return SIDE_CONFIG;
	}

	@Override
	public int @NotNull [] getSlotsForFace(@NotNull Direction side)
	{
		return new int[]{};
	}

}
