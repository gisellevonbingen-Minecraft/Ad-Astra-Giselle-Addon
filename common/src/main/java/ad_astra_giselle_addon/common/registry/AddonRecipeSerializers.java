package ad_astra_giselle_addon.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.crafting.CanUpgradeRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class AddonRecipeSerializers
{
	public static final ResourcefulRegistry<RecipeSerializer<?>> RECIPE_SERIALIZERS = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_SERIALIZER, AdAstraGiselleAddon.MOD_ID);

	public static final RegistryEntry<CanUpgradeRecipe.Serializer> CAN_UPGRADING = RECIPE_SERIALIZERS.register("can_upgrading", CanUpgradeRecipe.Serializer::new);

	private AddonRecipeSerializers()
	{

	}

}
