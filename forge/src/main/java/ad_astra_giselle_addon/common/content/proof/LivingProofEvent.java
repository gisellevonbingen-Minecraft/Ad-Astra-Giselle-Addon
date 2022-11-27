package ad_astra_giselle_addon.common.content.proof;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventListener;

public abstract class LivingProofEvent extends LivingEvent
{
	public static <E extends LivingProofEvent> E postUntilDuration(E event)
	{
		MinecraftForge.EVENT_BUS.post(event, LivingProofEvent::dispatch);
		return event;
	}

	public static void dispatch(IEventListener listener, Event event)
	{
		if (event instanceof LivingProofEvent event2 && event2.getProofDuration() > 0)
		{
			return;
		}

		listener.invoke(event);
	}

	private int proofDuration;

	public LivingProofEvent(LivingEntity entity)
	{
		super(entity);
	}

	public int getProofDuration()
	{
		return this.proofDuration;
	}

	public void setProofDuration(int proofDuration)
	{
		this.proofDuration = proofDuration;
	}

}
