package ad_astra_giselle_addon.client.screen;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.RocketSensorBlockEntity;
import ad_astra_giselle_addon.common.menu.RocketSensorMenu;
import ad_astra_giselle_addon.common.network.AddonNetwork;
import ad_astra_giselle_addon.common.network.RocketSensorMessage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RocketSensorScreen extends AddonMachineScreen<RocketSensorBlockEntity, RocketSensorMenu>
{
	public static final ResourceLocation TEXTURE = AdAstraGiselleAddon.rl("textures/gui/container/rocket_sensor.png");
	public static String INVERTED_KEY = ctl("rocket_sensor.inverted");
	public static String CURRENT_KEY = ctl("rocket_sensor.current");

	private RocketSensingTypeList list;
	private CustomCheckbox invertedCheckBox;

	public RocketSensorScreen(RocketSensorMenu handler, Inventory inventory, Component title)
	{
		super(handler, inventory, title, TEXTURE);
		this.imageWidth = 176;
		this.imageHeight = 160;
	}

	@Override
	protected void init()
	{
		super.init();

		RocketSensorBlockEntity rocketSensor = this.getMenu().getMachine();
		int x0 = this.leftPos + 8;
		int x1 = this.leftPos + this.imageWidth - 8;
		int y0 = this.topPos + 18;
		int y1 = this.topPos + this.imageHeight - 38;

		this.list = new RocketSensingTypeList(this, rocketSensor, this.minecraft, x1 - x0, y1 - y0, y0, y1);
		this.list.setLeftPos(x0);
		this.list.select(rocketSensor.getSensingType());
		this.addRenderableWidget(this.list);

		this.invertedCheckBox = new CustomCheckbox(x0, y1 + 3, x1 - x0, 10, Component.translatable(INVERTED_KEY), rocketSensor.isInverted());
		this.addRenderableWidget(this.invertedCheckBox);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY)
	{
		super.renderBg(guiGraphics, delta, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
	{
		RocketSensorBlockEntity rocketSensor = this.getMenu().getMachine();
		guiGraphics.drawString(this.font, this.getTitle(), this.titleLabelX, this.titleLabelY, this.getTextColour(), false);
		guiGraphics.drawString(this.font, Component.translatable(CURRENT_KEY, rocketSensor.getAnalogSignal()), this.titleLabelX, this.invertedCheckBox.getY() + this.invertedCheckBox.getHeight() + 8 - this.topPos, this.getTextColour(), false);

		guiGraphics.pose().pushPose();
		guiGraphics.pose().translate(-this.getLeftPos(), -this.getTopPos(), 0.0D);
		this.list.renderTooltip(guiGraphics, mouseX, mouseY);
		guiGraphics.pose().popPose();
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
	{
		super.render(guiGraphics, mouseX, mouseY, delta);

		RocketSensorBlockEntity rocketSensor = this.getMenu().getMachine();
		this.list.select(rocketSensor.getSensingType());

		boolean newInverted = this.invertedCheckBox.selected();

		if (rocketSensor.isInverted() != newInverted)
		{
			rocketSensor.setInverted(newInverted);
			AddonNetwork.CHANNEL.sendToServer(new RocketSensorMessage.Inverted(rocketSensor, newInverted));
		}

	}

}
