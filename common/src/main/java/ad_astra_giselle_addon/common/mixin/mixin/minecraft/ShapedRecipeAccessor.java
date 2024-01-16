package ad_astra_giselle_addon.common.mixin.mixin.minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;

@Mixin(value = ShapedRecipe.class)
public interface ShapedRecipeAccessor
{
	@Accessor
	ItemStack getResult();
}
