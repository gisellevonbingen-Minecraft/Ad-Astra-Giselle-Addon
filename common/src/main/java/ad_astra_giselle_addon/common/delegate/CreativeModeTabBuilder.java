package ad_astra_giselle_addon.common.delegate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CreativeModeTabBuilder
{
	private final ResourceLocation id;
	private final List<Supplier<List<ItemStack>>> items;

	private Supplier<ItemStack> icon;

	public CreativeModeTabBuilder(ResourceLocation id)
	{
		this.id = id;
		this.items = new ArrayList<>();
	}

	public ResourceLocation id()
	{
		return this.id;
	}

	public CreativeModeTabBuilder fillItem(Supplier<ItemStack> item)
	{
		this.items.add(() -> Collections.singletonList(item.get()));
		return this;
	}

	public CreativeModeTabBuilder fillItems(Supplier<List<ItemStack>> items)
	{
		this.items.add(items);
		return this;
	}

	public void appendItems(List<ItemStack> list)
	{
		for (Supplier<List<ItemStack>> supplier : this.items())
		{
			list.addAll(supplier.get());
		}

	}

	public List<Supplier<List<ItemStack>>> items()
	{
		return this.items;
	}

	public CreativeModeTabBuilder icon(Supplier<ItemStack> icon)
	{
		this.icon = icon;
		return this;
	}

	public Supplier<ItemStack> icon()
	{
		return this.icon;
	}

}
