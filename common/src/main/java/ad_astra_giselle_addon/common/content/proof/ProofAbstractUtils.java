package ad_astra_giselle_addon.common.content.proof;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.fluid.FluidHooks2;
import ad_astra_giselle_addon.common.registry.AddonAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

public abstract class ProofAbstractUtils
{
	public static final int GENERAL_PROOF_INTERVAL = 10;
	public static final int OXYGEN_PROOF_INTERVAL = 30;
	public static final long OXYGEN_PROOF_USING = FluidHooks2.MILLI_BUCKET;

	public static void reduceProofDuration(LivingEntity living)
	{
		for (Attribute attribute : AddonAttributes.ATTRIBUTES.getValues())
		{
			AttributeInstance instance = living.getAttribute(attribute);

			if (instance == null)
			{
				continue;
			}
			
			double baseValue = instance.getBaseValue();

			if (baseValue > 0.0D)
			{
				instance.setBaseValue(attribute.sanitizeValue(baseValue - 1.0D));
			}

		}

	}

	private final ResourceLocation id;
	private final Supplier<Attribute> attribute;

	public ProofAbstractUtils(ResourceLocation id, Supplier<Attribute> attribute)
	{
		this.id = id;
		this.attribute = attribute;
	}

	public ResourceLocation getId()
	{
		return this.id;
	}

	public Attribute getAttribute()
	{
		return this.attribute.get();
	}

	public int getProofDuration(LivingEntity living)
	{
		return (int) living.getAttribute(this.getAttribute()).getBaseValue();
	}

	public void setProofDuration(LivingEntity living, int proofDuration)
	{
		Attribute attribute = this.getAttribute();
		AttributeInstance instance = living.getAttribute(attribute);
		instance.setBaseValue(attribute.sanitizeValue(proofDuration));
	}

	public boolean tryProvideProof(LivingEntity living)
	{
		if (this.getProofDuration(living) > 0)
		{
			return true;
		}
		else
		{
			List<Function<LivingEntity, ProofSession>> list = new ArrayList<>();
			LivingProofProvidingEvent event = this.createEvent(living, list);
			AdAstraGiselleAddon.eventBus().post(event);

			for (Function<LivingEntity, ProofSession> function : list)
			{
				ProofSession session = function.apply(living);

				if (session == null)
				{
					continue;
				}

				int proofDuration = session.provide();

				if (proofDuration <= 0)
				{
					continue;
				}
				else
				{
					this.setProofDuration(living, proofDuration);
					return true;
				}

			}

			return false;
		}

	}

	public abstract LivingProofProvidingEvent createEvent(LivingEntity entity, List<Function<LivingEntity, ProofSession>> function);
}
