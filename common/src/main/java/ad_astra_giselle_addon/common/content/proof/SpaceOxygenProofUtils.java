package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import earth.terrarium.adastra.api.events.AdAstraEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class SpaceOxygenProofUtils extends ProofAbstractUtils
{
	public static final SpaceOxygenProofEnchantmentFunction ENCHANTMENT_FUNCTION = new SpaceOxygenProofEnchantmentFunction();
	public static final SpaceOxygenProofUtils INSTANCE = new SpaceOxygenProofUtils(AdAstraGiselleAddon.rl("space_breathing"));

	private SpaceOxygenProofUtils(ResourceLocation id)
	{
		super(id);

		this.register(ENCHANTMENT_FUNCTION);
		AdAstraEvents.OxygenTickEvent.register(this::onOxygenTick);
		AdAstraEvents.ColdTemperatureTickEvent.register(this::onColdTick);
	}

	private boolean onOxygenTick(ServerLevel level, LivingEntity living)
	{
		return !this.tryProvideProof(living);
	}

	private boolean onColdTick(ServerLevel level, LivingEntity living)
	{
		return !this.tryProvideProof(living);
	}

}
