package ad_astra_giselle_addon.common.item.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public interface IRecyclingVanillaRecipe<P extends Recipe<C>, C extends Container> extends Recipe<C>
{
	@Override
	public default ItemStack assemble(Container container)
	{
		return this.getOutput().getItemStack().copy();
	}

	@Override
	public default ItemStack getResultItem()
	{
		return this.getOutput().getItemStack().copy();
	}

	@Override
	public default ItemStack getToastSymbol()
	{
		return this.getParent().getToastSymbol();
	}

	public P getParent();

	public ItemOutput getOutput();
}
