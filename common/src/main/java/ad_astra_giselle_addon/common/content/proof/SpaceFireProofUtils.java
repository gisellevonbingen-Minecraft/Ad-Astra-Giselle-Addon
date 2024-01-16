package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import earth.terrarium.adastra.api.events.AdAstraEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class SpaceFireProofUtils extends ProofAbstractUtils
{
	public static final SpaceFireProofEnchantmentFunction ENCHANTMENT_FUNCTION = new SpaceFireProofEnchantmentFunction();
	public static final SpaceFireProofUtils INSTANCE = new SpaceFireProofUtils(AdAstraGiselleAddon.rl("space_fire_proof"));

	private SpaceFireProofUtils(ResourceLocation id)
	{
		super(id);

		this.register(ENCHANTMENT_FUNCTION);
		AdAstraEvents.HotTemperatureTickEvent.register(this::onHotTemperatureTick);
	}

	private boolean onHotTemperatureTick(ServerLevel level, LivingEntity living)
	{
		return !this.tryProvideProof(living);
	}

}
