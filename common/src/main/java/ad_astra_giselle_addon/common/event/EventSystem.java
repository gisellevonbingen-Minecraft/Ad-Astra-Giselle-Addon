package ad_astra_giselle_addon.common.event;

import java.util.ArrayList;
import java.util.List;

public class EventSystem<LISTENER>
{
	private final List<LISTENER> listeners = new ArrayList<>();

	public void register(LISTENER listener)
	{
		this.listeners.add(listener);
	}

	public boolean unregister(LISTENER listener)
	{
		return this.listeners.remove(listener);
	}

	public List<LISTENER> getListeners()
	{
		return new ArrayList<>(this.listeners);
	}

}
