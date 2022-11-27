package ad_astra_giselle_addon.common.util;

@FunctionalInterface
public interface TriFunction<T, U, V, R>
{
	R apply(T param1T, U param1U, V param1V);
}
