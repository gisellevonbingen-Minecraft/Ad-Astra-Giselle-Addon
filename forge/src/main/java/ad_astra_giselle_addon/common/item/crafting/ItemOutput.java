package ad_astra_giselle_addon.common.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public abstract class ItemOutput
{
	public static ItemOutput fromJson(JsonElement element)
	{
		if (element.isJsonPrimitive())
		{
			return of(GsonHelper.convertToItem(element, "item"));
		}

		JsonObject object = GsonHelper.convertToJsonObject(element, "element");
		JsonElement tagElement = object.get("tag");

		if (tagElement != null)
		{
			TagKey<Item> tag = ItemTags.create(new ResourceLocation(GsonHelper.convertToString(tagElement, "tag")));
			int count = GsonHelper.getAsInt(object, "count", 1);
			return of(tag, count);
		}
		else
		{
			return of(CraftingHelper.getItemStack(object, true));
		}

	}

	public static ItemOutput fromNetwork(FriendlyByteBuf buffer)
	{
		ItemStack item = buffer.readItem();
		return new ItemOutput.ItemStackValue(item);
	}

	public static ItemOutput of(Item item)
	{
		return new ItemOutput.ItemStackValue(new ItemStack(item));
	}

	public static ItemOutput of(ItemStack itemStack)
	{
		return new ItemOutput.ItemStackValue(itemStack.copy());
	}

	public static ItemOutput of(TagKey<Item> tag)
	{
		return new ItemOutput.TagValue(tag, 1);
	}

	public static ItemOutput of(TagKey<Item> tag, int count)
	{
		return new ItemOutput.TagValue(tag, count);
	}

	public void toNetwork(FriendlyByteBuf buffer)
	{
		buffer.writeItem(this.getItemStack());
	}

	public abstract ItemStack getItemStack();

	public static class ItemStackValue extends ItemOutput
	{
		private final ItemStack itemStack;

		public ItemStackValue(ItemStack itemStack)
		{
			this.itemStack = itemStack;
		}

		@Override
		public ItemStack getItemStack()
		{
			return this.itemStack;
		}

	}

	public static class TagValue extends ItemOutput
	{
		private final TagKey<Item> tag;
		private final int count;

		private ItemStack cached;

		public TagValue(TagKey<Item> tag, int count)
		{
			this.tag = tag;
			this.count = count;

			this.cached = null;
		}

		@Override
		public ItemStack getItemStack()
		{
			if (this.cached == null)
			{
				this.cached = TagPreference.getPreference(this.tag).map(item -> new ItemStack(item, this.count)).orElse(ItemStack.EMPTY);
			}

			return this.cached;
		}

	}

}
