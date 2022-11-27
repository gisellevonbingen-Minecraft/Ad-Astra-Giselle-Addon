package ad_astra_giselle_addon.common.item;

public class ItemUsableResourceResult
{
	private final ItemUsableResource resource;
	private final int usedAmount;
	private final boolean result;

	public ItemUsableResourceResult(ItemUsableResource resource, int usedAmount, boolean result)
	{
		this.resource = resource;
		this.usedAmount = usedAmount;
		this.result = result;
	}

	public ItemUsableResource getResource()
	{
		return this.resource;
	}

	public int getUsedAmount()
	{
		return this.usedAmount;
	}

	public boolean getResult()
	{
		return this.result;
	}

}
