package ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers;

import ad_astra_giselle_addon.common.compat.pneumaticcraft.AddonPNCUpgrades;
import me.desht.pneumaticcraft.api.item.PNCUpgrade;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorExtensionData;
import me.desht.pneumaticcraft.api.pneumatic_armor.ICommonArmorHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;

public class VenusAcidProofCommonHandler extends AddonArmorUpgradeCommonHandler<IArmorExtensionData>
{
	public VenusAcidProofCommonHandler(ResourceLocation id)
	{
		super(id);
	}

	@Override
	public EquipmentSlot getEquipmentSlot()
	{
		return EquipmentSlot.CHEST;
	}

	@Override
	public float getIdleAirUsage(ICommonArmorHandler arg0)
	{
		return 0;
	}

	@Override
	public PNCUpgrade[] getRequiredUpgrades()
	{
		return new PNCUpgrade[]{AddonPNCUpgrades.ACID_RAIN_PROOF.get()};
	}

}
