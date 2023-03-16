package ad_astra_giselle_addon.client.compat.pneumaticcraft.pneumatic_armor.handlers;

import com.mojang.blaze3d.vertex.PoseStack;

import ad_astra_giselle_addon.client.compat.pneumaticcraft.pneumatic_armor.options.SpaceBreathingOption;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.AddonPNCUpgrade;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.AddonPNCUpgrades;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.registry.AddonItems;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import me.desht.pneumaticcraft.api.client.IGuiAnimatedStat;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IGuiScreen;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IOptionPage;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.StatPanelLayout;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorUpgradeHandler;
import me.desht.pneumaticcraft.api.pneumatic_armor.ICommonArmorHandler;
import me.desht.pneumaticcraft.client.pneumatic_armor.ClientArmorRegistry;
import me.desht.pneumaticcraft.common.pneumatic_armor.CommonArmorHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SpaceBreathingClientHandler<T extends IArmorUpgradeHandler<?>> extends AddonSimpleToggleableHandler<T>
{
	private static final StatPanelLayout DEFAULT_STAT_LAYOUT = new StatPanelLayout(0.5F, 0.005F, false);
	private static final ItemStack ICON = new ItemStack(AddonItems.OXYGEN_CAN.get());

	private IGuiAnimatedStat stat;
	private Component oxygenComponent;

	public SpaceBreathingClientHandler(T commonHandler)
	{
		super(commonHandler);
	}

	@Override
	public IOptionPage getGuiOptionsPage(IGuiScreen screen)
	{
		return new SpaceBreathingOption<>(screen, this);
	}

	@Override
	public void tickClient(ICommonArmorHandler armorHandler, boolean isEnabled)
	{
		super.tickClient(armorHandler, isEnabled);

		if (isEnabled && this.stat.isStatOpen())
		{
			Player player = armorHandler.getPlayer();
			double ratio = OxygenChargerUtils.getExtractableStoredRatio(player).orElse(0.0D);
			this.oxygenComponent = TranslationUtils.formatPercent(ratio);
		}
		else
		{
			this.oxygenComponent = null;
		}

	}

	@Override
	public void render2D(PoseStack matrixStack, float partialTicks, boolean armorPieceHasPressure)
	{
		super.render2D(matrixStack, partialTicks, armorPieceHasPressure);

		if (this.stat != null && this.oxygenComponent != null)
		{
			Minecraft instance = Minecraft.getInstance();
			Font fr = instance.font;
			int xl = this.stat.getBaseX() + 4;
			int yt = this.stat.getBaseY() + 16;

			if (this.stat.isLeftSided())
			{
				xl -= stat.getStatWidth();
			}

			int iconWidth = 16;
			int iconX = xl;
			instance.getItemRenderer().renderGuiItem(matrixStack, ICON, iconX, yt);

			int componentX = iconX + iconWidth + 2;
			int componentY = yt + 4;
			fr.drawShadow(matrixStack, this.oxygenComponent, componentX, componentY, 0xFFFFFF);
		}

	}

	@Override
	public IGuiAnimatedStat getAnimatedStat()
	{
		if (this.stat == null)
		{
			AddonPNCUpgrade upgrade = AddonPNCUpgrades.SPACE_BREATHING.get();
			CommonArmorHandler handler = CommonArmorHandler.getHandlerForPlayer();
			int tier = Math.max(1, handler.getUpgradeCount(this.getCommonHandler().getEquipmentSlot(), upgrade));
			ItemStack stack = new ItemStack(upgrade.getItem(tier));

			this.stat = ClientArmorRegistry.getInstance().makeHUDStatPanel(Component.translatable(this.getCommonHandler().getTranslationKey()), stack, this);
			this.stat.setMinimumContractedDimensions(0, 0);
			this.stat.setMinimumExpandedDimensions(60, 30);
		}

		return this.stat;
	}

	@Override
	public void onResolutionChanged()
	{
		this.stat = null;
	}

	@Override
	public StatPanelLayout getDefaultStatLayout()
	{
		return DEFAULT_STAT_LAYOUT;
	}

}
