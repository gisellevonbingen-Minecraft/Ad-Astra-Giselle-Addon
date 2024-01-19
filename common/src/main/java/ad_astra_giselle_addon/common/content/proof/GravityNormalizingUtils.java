package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import earth.terrarium.adastra.api.events.AdAstraEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class GravityNormalizingUtils extends ProofAbstractUtils
{
	public static final GravityNormalizingEnchantmentFunction ENCHANTMENT_FUNCTION = new GravityNormalizingEnchantmentFunction();
	public static final GravityNormalizingUtils INSTANCE = new GravityNormalizingUtils(AdAstraGiselleAddon.rl("gravity_noramlizing"));

	private GravityNormalizingUtils(ResourceLocation id)
	{
		super(id);

		this.register(ENCHANTMENT_FUNCTION);
		AdAstraEvents.EntityGravityEvent.register(this::onEntityGravity);
	}

	private float onEntityGravity(Entity entity, float gravity)
	{
		if (gravity != 1.0F && entity instanceof LivingEntity living)
		{
			if (this.tryProvideProof(living))
			{
				return 1.0F;
			}

		}

		return gravity;
	}

}
