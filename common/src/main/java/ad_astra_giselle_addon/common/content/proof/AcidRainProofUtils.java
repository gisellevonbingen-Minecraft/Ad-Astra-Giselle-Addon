package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import earth.terrarium.adastra.api.events.AdAstraEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class AcidRainProofUtils extends ProofAbstractUtils
{
	public static final AcidRainProofEnchantmentFunction ENCHANTMENT_FUNCTION = new AcidRainProofEnchantmentFunction();
	public static final AcidRainProofUtils INSTANCE = new AcidRainProofUtils(AdAstraGiselleAddon.rl("acid_rain_proof"));

	private AcidRainProofUtils(ResourceLocation id)
	{
		super(id);

		this.register(ENCHANTMENT_FUNCTION);
		AdAstraEvents.AcidRainTickEvent.register(this::onAcidRainTick);
	}

	private boolean onAcidRainTick(ServerLevel level, LivingEntity living)
	{
		return !this.tryProvideProof(living);
	}

}
