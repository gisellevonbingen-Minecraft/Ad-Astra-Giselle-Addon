package ad_astra_giselle_addon.common.util;

@FunctionalInterface
public interface TriConsumer<T, U, V>
{
	void accept(T param1T, U param1U, V param1V);
}
