package ad_astra_giselle_addon.common.content.oxygen;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;

public interface IOxygenCharger
{
	public default List<IChargeMode> getAvailableChargeModes()
	{
		return Arrays.asList(ChargeMode.values());
	}

	@NotNull
	public IChargeMode getChargeMode();

	public void setChargeMode(@Nullable IChargeMode mode);

	public long getTransferAmount();

	public UniveralFluidHandler getFluidHandler();
}
