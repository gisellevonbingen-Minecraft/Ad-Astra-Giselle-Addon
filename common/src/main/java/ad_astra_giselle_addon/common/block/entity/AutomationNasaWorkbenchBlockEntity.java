package ad_astra_giselle_addon.common.block.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.config.MachinesConfig;
import ad_astra_giselle_addon.common.item.SidedItemContainerBlock;
import ad_astra_giselle_addon.common.menu.AutomationNasaWorkbenchMenu;
import earth.terrarium.adastra.common.blockentities.base.RecipeMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.recipes.machines.NasaWorkbenchRecipe;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.adastra.common.utils.ItemUtils;
import earth.terrarium.adastra.common.utils.ModUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AutomationNasaWorkbenchBlockEntity extends RecipeMachineBlockEntity<NasaWorkbenchRecipe> implements SidedItemContainerBlock
{
	public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(//
			new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_SLOTS), //
			new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_SLOTS), //

			new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.NONE, ConstantComponents.SIDE_CONFIG_ENERGY));

	public static final int[] INPUT_SLOTS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
	public static final int[] OUTPUT_SLOTS = {15};
	public static final int[] SLOTS_FOR_FACE = ArrayUtils.addAll(INPUT_SLOTS, OUTPUT_SLOTS);
	public static final int CONTAINER_SIZE = 1 + INPUT_SLOTS.length + OUTPUT_SLOTS.length;

	private VirtualInputOnlyContainer recipeContainer;
	private boolean firstTick;
	private List<RecipeCache> recipeCaches;

	public AutomationNasaWorkbenchBlockEntity(BlockPos pos, BlockState state)
	{
		super(pos, state, CONTAINER_SIZE, ModRecipeTypes.NASA_WORKBENCH);
		this.recipeContainer = new VirtualInputOnlyContainer(this, INPUT_SLOTS[0], INPUT_SLOTS.length);
		this.firstTick = true;
		this.recipeCaches = new ArrayList<>();
	}

	@Override
	public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory inv, @NotNull Player player)
	{
		return new AutomationNasaWorkbenchMenu(syncId, inv, this);
	}

	@Override
	public WrappedBlockEnergyContainer getEnergyStorage(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity entity, @Nullable Direction direction)
	{
		if (this.energyContainer == null)
		{
			this.energyContainer = new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(MachinesConfig.AUTOMATION_NASA_WORKBENCH_ENERGY_CAPACITY, MachinesConfig.AUTOMATION_NASA_WORKBENCH_ENERGY_CAPACITY));
		}

		return this.energyContainer;
	}

	@Override
	public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter, List<ConfigurationEntry> sideConfig)
	{
		super.tickSideInteractions(pos, filter, sideConfig);

		TransferUtils.pullItemsNearby(this, pos, INPUT_SLOTS, sideConfig.get(0), filter);
		TransferUtils.pushItemsNearby(this, pos, OUTPUT_SLOTS, sideConfig.get(1), filter);

		TransferUtils.pushEnergyNearby(this, pos, this.getEnergyStorage().maxExtract(), sideConfig.get(2), filter);
		TransferUtils.pullEnergyNearby(this, pos, this.getEnergyStorage().maxInsert(), sideConfig.get(2), filter);
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction dir)
	{
		if (ArrayUtils.contains(OUTPUT_SLOTS, slot))
		{
			return false;
		}
		else
		{
			return this.recipeCaches.stream().filter(cache -> cache.testRemain(this.recipeContainer.toRecipeSlot(slot), stack)).findAny().isPresent();
		}

	}

	@Override
	public boolean canTakeItemThroughFace(int slot, @NotNull ItemStack stack, @NotNull Direction dir)
	{
		return ArrayUtils.contains(OUTPUT_SLOTS, slot);
	}

	@Override
	public void recipeTick(ServerLevel level, WrappedBlockEnergyContainer energyStroage)
	{
		if (this.recipe == null)
		{
			return;
		}
		else if (!this.canCraft())
		{
			this.clearRecipe();
			return;
		}

		energyStroage.internalExtract(MachinesConfig.AUTOMATION_NASA_WORKBENCH_ENERGY_USAGE, false);
		this.cookTime++;

		if (this.cookTime < this.cookTimeTotal)
		{
			return;
		}

		this.craft();
	}

	@Override
	public boolean canCraft()
	{
		return this.recipe != null && this.recipe.matches(this.recipeContainer, this.getLevel()) && ItemUtils.canAddItem(this, this.recipe.result(), OUTPUT_SLOTS);
	}

	@Override
	public void craft()
	{
		if (this.recipe == null)
		{
			return;
		}

		this.spawnResultParticles();

		for (int element : INPUT_SLOTS)
		{
			this.getItem(element).shrink(1);
		}

		ItemUtils.addItem(this, this.recipe.result(), OUTPUT_SLOTS);
		this.clearRecipe();
	}

	@Override
	public void update()
	{
		super.update();

		if (!this.getLevel().isClientSide())
		{
			this.cacheRecipes();
		}

	}

	@Override
	public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos)
	{
		super.serverTick(level, time, state, pos);

		if (this.firstTick)
		{
			this.firstTick = false;
			this.cacheRecipes();
		}

		if (this.recipeCaches.stream().anyMatch(RecipeCache::anyAdded))
		{
			this.spawnWorkingParticles();
		}

	}

	public void spawnWorkingParticles()
	{
		if (this.getLevel() instanceof ServerLevel level)
		{
			BlockPos pos = this.getBlockPos();
			ModUtils.sendParticles(level, ParticleTypes.CRIT, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 10, 0.1D, 0.1, 0.1D, 0.1D);
		}

	}

	public void spawnResultParticles()
	{
		if (this.getLevel() instanceof ServerLevel level)
		{
			BlockPos pos = this.getBlockPos();
			ModUtils.sendParticles(level, ParticleTypes.TOTEM_OF_UNDYING, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 100, 0.1D, 0.1D, 0.1D, 0.7D);
			level.playSound(null, pos, SoundEvents.TOTEM_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
		}

	}

	private void cacheRecipes()
	{
		VirtualInputOnlyContainer container = this.recipeContainer;
		List<RecipeHolder<NasaWorkbenchRecipe>> recipes = this.getLevel().getRecipeManager().getAllRecipesFor(ModRecipeTypes.NASA_WORKBENCH.get());

		this.recipeCaches.clear();
		this.recipeCaches.addAll(recipes.stream().map(recipe -> this.cache(recipe.value(), container)).filter(cache -> cache != null).toList());
		this.recipe = this.recipeCaches.stream().filter(cache -> cache.isComplete()).map(RecipeCache::recipe).findFirst().orElse(null);

		if (this.recipe == null)
		{
			this.cookTimeTotal = 0;
		}
		else
		{
			this.cookTimeTotal = MachinesConfig.AUTOMATION_NASA_WORKBENCH_COOK_TIME;
		}

	}

	private RecipeCache cache(NasaWorkbenchRecipe recipe, VirtualInputOnlyContainer container)
	{
		List<Ingredient> ingredients = recipe.ingredients();
		List<SlotWithIngredient> remainIngredients = new ArrayList<>();
		boolean mismatched = false;
		boolean anyAdded = false;

		for (int i = 0; i < ingredients.size(); i++)
		{
			ItemStack item = container.getItem(i);
			Ingredient ingredient = ingredients.get(i);
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

	@Override
	public List<ConfigurationEntry> getDefaultConfig()
	{
		return SIDE_CONFIG;
	}

	@Override
	public int[] getSlotsForFace(Direction pSide)
	{
		return SLOTS_FOR_FACE;
	}

	@Override
	public int getSideSlotLimit(int slot, @Nullable Direction directon)
	{
		return directon == null ? this.getMaxStackSize() : 1;
	}

	@Override
	public WorldlyContainer getContainer()
	{
		return this;
	}

}
