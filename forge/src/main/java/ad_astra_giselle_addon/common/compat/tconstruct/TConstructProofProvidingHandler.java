package ad_astra_giselle_addon.common.compat.tconstruct;

import ad_astra_giselle_addon.common.config.EnchantmentsConfig;
import ad_astra_giselle_addon.common.content.proof.OxygenProofEnchantmentFunction;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class TConstructProofProvidingHandler
{
	public TConstructProofProvidingHandler()
	{
	}

	public int onOxygenProof(Entity entity)
	{
		if (entity instanceof LivingEntity living)
		{
			Tuple tuple = findFirst(living, AddonTConstructModifiers.OXYGEN_PROOF.get());

			if (tuple != null)
			{
				if (OxygenProofEnchantmentFunction.consumeOxygen(living, ProofAbstractUtils.OXYGEN_PROOF_USING, false))
				{
					return ProofAbstractUtils.OXYGEN_PROOF_INTERVAL;
				}

			}

		}

		return 0;
	}

	public int onHotTemperatureProof(Entity entity)
	{
		return provideProof(entity, AddonTConstructModifiers.HOT_TEMPERATURE_PROOF.get(), EnchantmentsConfig.HOT_TEMPERATURE_PROOF_DURABILITY_USING, EnchantmentsConfig.HOT_TEMPERATURE_PROOF_DURABILITY_DURATION);
	}

	public int onAcidRainProof(Entity entity)
	{
		return provideProof(entity, AddonTConstructModifiers.ACID_RAIN_PROOF.get(), EnchantmentsConfig.ACID_RAIN_PROOF_DURABILITY_USING, EnchantmentsConfig.ACID_RAIN_PROOF_DURABILITY_DURATION);
	}

	public int onGravityProof(Entity entity)
	{
		return provideProof(entity, AddonTConstructModifiers.GRAVITY_PROOF.get(), EnchantmentsConfig.GRAVITY_PROOF_DURABILITY_USING, EnchantmentsConfig.GRAVITY_PROOF_DURABILITY_DURATION);
	}

	public static int provideProof(Entity entity, Modifier modifier, int durabilityUsing, int duration)
	{
		if (entity instanceof LivingEntity living)
		{
			Tuple tuple = findFirst(living, modifier);

			if (tuple != null)
			{
				ToolDamageUtil.damage(tuple.tool(), durabilityUsing, living, living.getItemBySlot(tuple.slot()));
				return duration;
			}

		}

		return 0;
	}

	public static Tuple findFirst(LivingEntity living, Modifier modifier)
	{
		for (EquipmentSlot slot : EquipmentSlot.values())
		{
			ToolStack tool = Modifier.getHeldTool(living, slot);

			if (tool == null)
			{
				continue;
			}
			else if (tool.getModifierLevel(modifier) > 0)
			{
				return new Tuple(slot, tool);
			}

		}

		return null;
	}

	public record Tuple(EquipmentSlot slot, ToolStack tool)
	{

	}

}
