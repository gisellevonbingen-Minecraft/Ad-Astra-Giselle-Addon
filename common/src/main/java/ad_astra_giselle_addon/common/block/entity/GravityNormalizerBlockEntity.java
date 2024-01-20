package ad_astra_giselle_addon.common.block.entity;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.config.MachinesConfig;
import ad_astra_giselle_addon.common.content.proof.GravityProofUtils;
import ad_astra_giselle_addon.common.menu.GravityNormalizerMenu;
import ad_astra_giselle_addon.common.registry.AddonBlockEntityTypes;
import ad_astra_giselle_addon.common.registry.AddonProofs;
import ad_astra_giselle_addon.common.util.Vec3iUtils;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.energy.PlatformEnergyManager;
import earth.terrarium.botarium.api.energy.SimpleUpdatingEnergyContainer;
import net.minecraft.core.BlockPos;
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

public class GravityNormalizerBlockEntity extends AddonMachineBlockEntity implements EnergyBlock, IWorkingAreaBlockEntity
{
	public static final String DATA_LENGTH_KEY = "length";
	public static final String DATA_OFFSET_KEY = "offset";
	public static final String DATA_TIMER_KEY = "timer";
	public static final String DATA_WORKINGAREA_VISIBLE_KEY = "workingAreaVisible";

	private SimpleUpdatingEnergyContainer energyStorage;
	private Vec3i length;
	private Vec3i offset;
	private int timer;
	private boolean workingAreaVisible;

	public GravityNormalizerBlockEntity(BlockPos pos, BlockState state)
	{
		super(AddonBlockEntityTypes.GRAVITY_NORMALIZER.get(), pos, state);
		this.length = new Vec3i(3, 3, 3);
		this.offset = offsetFromLength(this.length);
		this.timer = 0;
		this.workingAreaVisible = false;
	}

	@Override
	@Nullable
	public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player)
	{
		return new GravityNormalizerMenu(windowId, inv, this);
	}

	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
		this.length = Vec3i.CODEC.parse(NbtOps.INSTANCE, nbt.get(DATA_LENGTH_KEY)).result().get();
		this.offset = Vec3i.CODEC.parse(NbtOps.INSTANCE, nbt.get(DATA_OFFSET_KEY)).result().get();
		this.timer = nbt.getInt(DATA_TIMER_KEY);
		this.workingAreaVisible = nbt.getBoolean(DATA_WORKINGAREA_VISIBLE_KEY);
	}

	@Override
	public void saveAdditional(CompoundTag nbt)
	{
		super.saveAdditional(nbt);
		nbt.put(DATA_LENGTH_KEY, Vec3i.CODEC.encodeStart(NbtOps.INSTANCE, this.length).result().get());
		nbt.put(DATA_OFFSET_KEY, Vec3i.CODEC.encodeStart(NbtOps.INSTANCE, this.offset).result().get());
		nbt.putInt(DATA_TIMER_KEY, this.timer);
		nbt.putBoolean(DATA_WORKINGAREA_VISIBLE_KEY, this.workingAreaVisible);
	}

	@Override
	public SimpleUpdatingEnergyContainer getEnergyStorage()
	{
		if (this.energyStorage == null)
		{
			this.energyStorage = new SimpleUpdatingEnergyContainer(this, MachinesConfig.GRAVITY_NORMALIZER_ENERGY_CAPACITY)
			{
				@Override
				public long maxExtract()
				{
					return this.getMaxCapacity();
				}

				@Override
				public long maxInsert()
				{
					return this.getMaxCapacity();
				}
			};
		}

		return this.energyStorage;
	}

	@Override
	public void update()
	{

	}

	@Override
	public void tick()
	{
		int timer = this.getTimer();
		timer--;

		if (timer <= 0)
		{
			timer = this.getMaxTimer();
			AABB workingArea = this.getWorkingArea();
			this.doNormalize(workingArea);
			this.setChanged();
		}

		this.setTimer(timer);
	}

	protected void doNormalize(AABB workingArea)
	{
		Level level = this.getLevel();
		GravityProofUtils proof = AddonProofs.GRAVITY;
		long energyUsing = this.getEnergyUsing(workingArea);
		PlatformEnergyManager energyManager = EnergyHooks.getBlockEnergyManager(this, null);

		if (energyManager.extract(energyUsing, true) < energyUsing)
		{
			return;
		}

		List<LivingEntity> livings = level.getEntitiesOfClass(LivingEntity.class, workingArea);

		if (livings.size() <= 0)
		{
			return;
		}

		energyManager.extract(energyUsing, false);
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
			this.setChanged();
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
			this.setChanged();
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
			this.setChanged();
		}

	}

}
