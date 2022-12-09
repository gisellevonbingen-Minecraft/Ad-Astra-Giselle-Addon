package ad_astra_giselle_addon.common.registry;

import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class DelegateObjectHolder<T> implements Supplier<T>
{
	private final ResourceLocation id;
	private final Supplier<T> initializer;
	private final ResourceKey<? extends Registry<?>> key;

	private Supplier<T> objectSupplier;

	public DelegateObjectHolder(ResourceLocation id, Supplier<T> initializer, ResourceKey<? extends Registry<?>> key)
	{
		this.id = id;
		this.initializer = initializer;
		this.key = key;

		this.objectSupplier = null;
	}

	@Override
	public String toString()
	{
		return "[key=" + this.key.location() + "][id=" + this.id.toString() + "]";
	}

	public void register(DelegateRegistry<T> registry)
	{
		if (this.objectSupplier == null)
		{
			this.objectSupplier = registry.register(this.id, this.initializer);
		}
		else
		{
			// TODO: THROW
			throw new RuntimeException();
		}

	}

	public ResourceLocation getId()
	{
		return this.id;
	}

	@Override
	public T get()
	{
		Supplier<T> objectSupplier = this.objectSupplier;

		if (objectSupplier == null)
		{
			// TODO: THROW
			throw new RuntimeException();
		}
		else
		{
			return objectSupplier.get();
		}

	}

}
