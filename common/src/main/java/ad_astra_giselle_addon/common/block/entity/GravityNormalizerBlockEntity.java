package ad_astra_giselle_addon.common.block.entity;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.config.MachinesConfig;
import ad_astra_giselle_addon.common.content.proof.GravityNormalizingUtils;
import ad_astra_giselle_addon.common.menu.GravityNormalizerMenu;
import ad_astra_giselle_addon.common.registry.AddonBlockEntityTypes;
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
	public static final String DATA_WORKINGAREA_VISIBLE_KEY = "workingAreaVisible";

	private SimpleUpdatingEnergyContainer energyStorage;
	private Vec3i length;
	private Vec3i offset;
	private boolean workingAreaVisible;

	public GravityNormalizerBlockEntity(BlockPos pos, BlockState state)
	{
		super(AddonBlockEntityTypes.GRAVITY_NORMALIZER.get(), pos, state);
		this.length = Vec3i.ZERO;
		this.offset = Vec3i.ZERO;
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
		this.workingAreaVisible = nbt.getBoolean(DATA_WORKINGAREA_VISIBLE_KEY);
	}

	@Override
	public void saveAdditional(CompoundTag nbt)
	{
		super.saveAdditional(nbt);
		nbt.put(DATA_LENGTH_KEY, Vec3i.CODEC.encodeStart(NbtOps.INSTANCE, this.length).result().get());
		nbt.put(DATA_OFFSET_KEY, Vec3i.CODEC.encodeStart(NbtOps.INSTANCE, this.offset).result().get());
		nbt.putBoolean(DATA_WORKINGAREA_VISIBLE_KEY, this.isWorkingAreaVisible());
	}

	@Override
	public SimpleUpdatingEnergyContainer getEnergyStorage()
	{
		if (this.energyStorage == null)
		{
			this.energyStorage = new SimpleUpdatingEnergyContainer(this, MachinesConfig.AUTOMATION_NASA_WORKBENCH_ENERGY_CAPACITY);
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
		AABB workingArea = this.getWorkingArea();
		this.doNormalize(workingArea);
	}

	protected void doNormalize(AABB workingArea)
	{
		Level level = this.getLevel();
		GravityNormalizingUtils proof = GravityNormalizingUtils.INSTANCE;
		long blocks = (long) (workingArea.getXsize() * workingArea.getYsize() * workingArea.getZsize());
		long energyUsing = MachinesConfig.GRAVITY_NORMALIZER_ENERGY_PER_BLOCKS * blocks;
		PlatformEnergyManager energyManager = EnergyHooks.getBlockEnergyManager(this, null);

		if (energyManager.extract(energyUsing, true) >= energyUsing)
		{
			return;
		}

		List<LivingEntity> livings = level.getEntitiesOfClass(LivingEntity.class, workingArea);

		for (LivingEntity entity : livings)
		{
			if (proof.getProofDuration(entity) > 0)
			{
				continue;
			}

			energyManager.extract(energyUsing, false);
			proof.setProofDuration(entity, MachinesConfig.GRAVITY_NORMALIZER_PROOF_DURATION);

			if (energyManager.extract(energyUsing, true) >= energyUsing)
			{
				break;
			}

		}

	}

	public Vec3i getLength()
	{
		return this.length;
	}

	public void setLength(Vec3i length)
	{
		if (!this.getLength().equals(length))
		{
			this.length = length;
			this.setChanged();
		}

	}

	public Vec3i getOffset()
	{
		return this.offset;
	}

	public void setOffset(Vec3i offset)
	{
		if (!this.getOffset().equals(offset))
		{
			this.offset = offset;
			this.setChanged();
		}

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
		return new AABB(pos).inflate(length.getX(), length.getY(), length.getZ()).move(offset.getX(), offset.getY(), offset.getZ());
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
