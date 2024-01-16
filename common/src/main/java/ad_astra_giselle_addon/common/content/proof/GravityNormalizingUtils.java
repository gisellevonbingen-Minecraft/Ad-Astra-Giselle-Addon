package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import earth.terrarium.adastra.api.events.AdAstraEvents;
import earth.terrarium.adastra.api.systems.GravityApi;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GravityNormalizingUtils extends ProofAbstractUtils
{
	public static final GravityNormalizingEnchantmentFunction ENCHANTMENT_FUNCTION = new GravityNormalizingEnchantmentFunction();
	public static final GravityNormalizingUtils INSTANCE = new GravityNormalizingUtils(AdAstraGiselleAddon.rl("gravity_noramlizing"));

	private GravityNormalizingUtils(ResourceLocation id)
	{
		super(id);

		this.register(ENCHANTMENT_FUNCTION);
		AdAstraEvents.GravityTickEvent.register(this::onGravityTick);
	}

	private boolean onGravityTick(Level level, LivingEntity living, Vec3 travelVector, BlockPos movementAffectingPos)
	{
		float gravity = GravityApi.API.getGravity(living);

		if (gravity != 1.0F && this.tryProvideProof(living))
		{
			return false;
		}

		return true;
	}

}
