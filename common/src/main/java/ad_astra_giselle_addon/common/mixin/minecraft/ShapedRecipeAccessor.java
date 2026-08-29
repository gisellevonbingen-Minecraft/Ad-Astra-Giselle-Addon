package ad_astra_giselle_addon.common.mixin.minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

@Mixin(value = ShapedRecipe.class)
public interface ShapedRecipeAccessor
{
	@Accessor(value = "pattern", remap = true)
	ShapedRecipePattern getPattern();

	@Accessor(value = "result", remap = true)
	ItemStack getResult();
}
