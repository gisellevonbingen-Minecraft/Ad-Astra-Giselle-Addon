package ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers;

import ad_astra_giselle_addon.common.compat.pneumaticcraft.AddonPNCUpgrades;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorExtensionData;
import me.desht.pneumaticcraft.api.pneumatic_armor.ICommonArmorHandler;
import me.desht.pneumaticcraft.api.upgrade.PNCUpgrade;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;

public class OxygenProofCommonHandler extends AddonArmorUpgradeCommonHandler<IArmorExtensionData>
{
	public OxygenProofCommonHandler(ResourceLocation id)
	{
		super(id);
	}

	@Override
	public EquipmentSlot getEquipmentSlot()
	{
		return EquipmentSlot.HEAD;
	}

	@Override
	public float getIdleAirUsage(ICommonArmorHandler arg0)
	{
		return 0;
	}

	@Override
	public PNCUpgrade[] getRequiredUpgrades()
	{
		return new PNCUpgrade[]{AddonPNCUpgrades.OXYGEN_PROOF.get()};
	}

}
