package ad_astra_giselle_addon.common.block.entity;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.config.MachinesConfig;
import ad_astra_giselle_addon.common.menu.AutomationNasaWorkbenchMenu;
import ad_astra_giselle_addon.common.registry.AddonBlockEntityTypes;
import ad_astra_giselle_addon.common.world.ContainerUtils;
import earth.terrarium.ad_astra.common.recipe.IngredientHolder;
import earth.terrarium.ad_astra.common.recipe.NasaWorkbenchRecipe;
import earth.terrarium.ad_astra.common.util.ModUtils;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.energy.PlatformEnergyManager;
import earth.terrarium.botarium.api.energy.SimpleUpdatingEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;

public class AutomationNasaWorkbenchBlockEntity extends AddonMachineBlockEntity implements EnergyBlock
{
	public static final int RESULT_SLOT = 14;
	public static final String DATA_COOK_TIME_KEY = "cookTime";
	public static final String DATA_COOK_TIME_TOTAL_KEY = "cookTimeTotal";

	private SimpleUpdatingEnergyContainer energyStorage;

	private boolean firstTick;
	private List<RecipeCache> recipaCaches;
	private NasaWorkbenchRecipe selectedRecipe;

	private int cookTime;
	private int cookTimeTotal;

	public AutomationNasaWorkbenchBlockEntity(BlockPos pos, BlockState state)
	{
		super(AddonBlockEntityTypes.AUTOMATION_NASA_WORKBENCH.get(), pos, state);
		this.firstTick = true;
		this.recipaCaches = new ArrayList<>();
		this.selectedRecipe = null;
	}

	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
		this.cookTime = nbt.getInt(DATA_COOK_TIME_KEY);
		this.cookTimeTotal = nbt.getInt(DATA_COOK_TIME_TOTAL_KEY);
	}

	@Override
	public void saveAdditional(CompoundTag nbt)
	{
		super.saveAdditional(nbt);
		nbt.putInt(DATA_COOK_TIME_KEY, this.cookTime);
		nbt.putInt(DATA_COOK_TIME_TOTAL_KEY, this.cookTimeTotal);
	}

	@Override
	public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory inv, @NotNull Player player)
	{
		return new AutomationNasaWorkbenchMenu(syncId, inv, this);
	}

	@Override
	public void update()
	{

	}

	@Override
	public int getInventorySize()
	{
		return 15;
	}

	@Override
	public int getSideSlotLimit(int slot, @Nullable Direction directon)
	{
		return 1;
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction dir)
	{
		if (slot == RESULT_SLOT)
		{
			return false;
		}
		else
		{
			return this.recipaCaches.stream().filter(cache -> cache.testRemain(slot, stack)).findAny().isPresent();
		}

	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir)
	{
		return slot == RESULT_SLOT;
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
	public void tick()
	{
		if (!this.getLevel().isClientSide())
		{
			if (this.firstTick)
			{
				this.firstTick = false;
				this.cacheRecipes();
			}

			if (this.recipaCaches.stream().anyMatch(RecipeCache::anyAdded))
			{
				this.spawnWorkingParticles();
			}

			this.cook();
		}

	}

	public void spawnWorkingParticles()
	{
		if (this.getLevel() instanceof ServerLevel level)
		{
			BlockPos pos = this.getBlockPos();
			ModUtils.spawnForcedParticles(level, ParticleTypes.CRIT, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 10, 0.1D, 0.1, 0.1D, 0.1D);
		}

	}

	public void spawnResultParticles()
	{
		if (this.getLevel() instanceof ServerLevel level)
		{
			BlockPos pos = this.getBlockPos();
			ModUtils.spawnForcedParticles(level, ParticleTypes.TOTEM_OF_UNDYING, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 100, 0.1D, 0.1D, 0.1D, 0.7D);
			level.playSound(null, pos, SoundEvents.TOTEM_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
		}

	}

	private void cook()
	{
		NasaWorkbenchRecipe recipe = this.selectedRecipe;

		if (recipe == null)
		{
			return;
		}

		List<ItemStack> items = this.getItems();
		List<IngredientHolder> ingredients = recipe.getHolders();
		ItemStack resultItem = recipe.getResultItem();

		if (this.testIngredientCounts(recipe.getHolders(), items) && ContainerUtils.insert(this, RESULT_SLOT, resultItem, true).isEmpty() && this.extractEnergy(true))
		{
			this.setCookTime(this.getCookTime() + 1);
			this.extractEnergy(false);

			if (this.getCookTime() >= this.getCookTimeTotal())
			{
				for (int i = 0; i < RESULT_SLOT; i++)
				{
					items.get(i).shrink(ingredients.get(i).count());
				}

				this.setCookTime(0);
				ContainerUtils.insert(this, RESULT_SLOT, resultItem, false);
				this.spawnResultParticles();
			}

		}
		else
		{
			this.setCookTime(0);
		}

	}

	public boolean extractEnergy(boolean simulate)
	{
		long extracting = MachinesConfig.AUTOMATION_NASA_WORKBENCH_ENERGY_USAGE;
		PlatformEnergyManager energyManager = EnergyHooks.getBlockEnergyManager(this, null);
		return energyManager.extract(extracting, simulate) == extracting;
	}

	private void cacheRecipes()
	{
		List<ItemStack> items = this.getItems();
		List<NasaWorkbenchRecipe> recipes = NasaWorkbenchRecipe.getRecipes(this.getLevel());

		this.recipaCaches.clear();
		this.recipaCaches.addAll(recipes.stream().map(recipe -> this.cache(recipe, items)).filter(cache -> cache != null).toList());
		this.selectedRecipe = this.recipaCaches.stream().filter(cache -> this.test(cache, items)).map(RecipeCache::recipe).findFirst().orElse(null);

		if (this.selectedRecipe == null)
		{
			this.setCookTimeTotal(0);
		}
		else
		{
			this.setCookTimeTotal(MachinesConfig.AUTOMATION_NASA_WORKBENCH_COOK_TIME);
		}

	}

	private boolean test(RecipeCache cache, List<ItemStack> items)
	{
		return cache.isComplete() && this.testIngredientCounts(cache.recipe().getHolders(), items);
	}

	private boolean testIngredientCounts(List<IngredientHolder> ingredients, List<ItemStack> items)
	{
		for (int i = 0; i < ingredients.size(); i++)
		{
			ItemStack item = items.get(i);
			IngredientHolder ingredient = ingredients.get(i);

			if (item.getCount() < ingredient.count())
			{
				return false;
			}

		}

		return true;
	}

	private RecipeCache cache(NasaWorkbenchRecipe recipe, List<ItemStack> items)
	{
		List<IngredientHolder> ingredients = recipe.getHolders();
		List<SlotWithIngredient> remainIngredients = new ArrayList<>();
		boolean mismatched = false;
		boolean anyAdded = false;

		for (int i = 0; i < ingredients.size(); i++)
		{
			ItemStack item = items.get(i);
			Ingredient ingredient = ingredients.get(i).ingredient();
			boolean needAdd = false;

			if (item.isEmpty())
			{
				needAdd = true;
			}
			else if (!ingredient.test(item))
			{
				needAdd = true;
				mismatched = true;
			}
			else
			{
				anyAdded = true;
			}

			if (needAdd)
			{
				remainIngredients.add(new SlotWithIngredient(i, ingredient));
			}

		}

		if (!mismatched)
		{
			return new RecipeCache(recipe, remainIngredients, anyAdded);
		}
		else
		{
			return null;
		}

	}

	@Override
	public void setChanged()
	{
		super.setChanged();

		if (this.getLevel() instanceof ServerLevel)
		{
			this.cacheRecipes();
		}

	}

	public int getCookTime()
	{
		return this.cookTime;
	}

	public void setCookTime(int cookTime)
	{
		cookTime = Mth.clamp(cookTime, 0, this.getCookTimeTotal());

		if (this.getCookTime() != cookTime)
		{
			this.cookTime = cookTime;
		}

	}

	public int getCookTimeTotal()
	{
		return this.cookTimeTotal;
	}

	protected void setCookTimeTotal(int cookTimeTotal)
	{
		cookTimeTotal = Math.max(cookTimeTotal, 0);

		if (this.getCookTimeTotal() != cookTimeTotal)
		{
			this.cookTimeTotal = cookTimeTotal;
			this.setCookTime(this.getCookTime());
		}

	}

	private record RecipeCache(NasaWorkbenchRecipe recipe, List<SlotWithIngredient> remainIngredients, boolean anyAdded)
	{
		public boolean testRemain(int putSlot, ItemStack item)
		{
			List<SlotWithIngredient> remainIngredients = this.remainIngredients();
			return remainIngredients.stream().filter(i -> i.test(putSlot, item)).findAny().isPresent();
		}

		public boolean isComplete()
		{
			return this.remainIngredients().size() == 0;
		}

	}

	private record SlotWithIngredient(int slot, Ingredient ingredient)
	{
		public boolean test(int putSlot, ItemStack item)
		{
			return putSlot == this.slot() && this.ingredient().test(item);
		}

	}

}
