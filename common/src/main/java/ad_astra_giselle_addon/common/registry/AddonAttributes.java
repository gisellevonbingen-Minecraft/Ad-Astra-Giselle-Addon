package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class AddonAttributes
{
	public static final AttributeRegistryCollection ATTRIBUTES = new AttributeRegistryCollection(AdAstraGiselleAddon.MOD_ID);
	public static final ObjectRegistryHolder<Attribute> SPACE_OXYGEN_PROOF_DURATION = ATTRIBUTES.add("space_oxygen_proof_duration", name -> new RangedAttribute(name, 0.0D, 0.0D, 1024.0D).setSyncable(true));
	public static final ObjectRegistryHolder<Attribute> SPACE_FIRE_PROOF_DURATION = ATTRIBUTES.add("space_fire_proof_duration", name -> new RangedAttribute(name, 0.0D, 0.0D, 1024.0D).setSyncable(true));
	public static final ObjectRegistryHolder<Attribute> ACID_RAIN_PROOF_DURATION = ATTRIBUTES.add("acid_rain_proof_duration", name -> new RangedAttribute(name, 0.0D, 0.0D, 1024.0D).setSyncable(true));
}
