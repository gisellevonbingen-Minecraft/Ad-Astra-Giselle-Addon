package ad_astra_giselle_addon.client.screen;

import java.util.function.IntConsumer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class ElementSliderButton extends AbstractSliderButton
{
	private String translationKey;
	private IntConsumer setter;
	private int intValue;
	private int minValue;
	private int maxValue;

	public ElementSliderButton(int x, int y, int width, int height, String translationKey, int value, int min, int max, IntConsumer setter)
	{
		super(x, y, width, height, Component.translatable(translationKey), Mth.inverseLerp(value, min, max));
		this.translationKey = translationKey;
		this.intValue = value;
		this.minValue = min;
		this.maxValue = max;
		this.setter = setter;
		this.updateMessage();
	}

	@Override
	protected void renderBg(PoseStack pPoseStack, Minecraft pMinecraft, int pMouseX, int pMouseY)
	{
		RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		int i = (this.isActive() && this.isHoveredOrFocused() ? 2 : 1) * 20;
		int cursorX = this.x + (int) (this.value * (this.width - 8));
		int cursorY = this.y;
		int cursorWidth = 4;
		int cursorHeight = this.height;
		GuiComponent.blit(pPoseStack, cursorX + 0, cursorY, cursorWidth, cursorHeight, 0, 46 + i, 4, 20, 256, 256);
		GuiComponent.blit(pPoseStack, cursorX + 4, cursorY, cursorWidth, cursorHeight, 196, 46 + i, 4, 20, 256, 256);
	}

	@Override
	protected void updateMessage()
	{
		this.setMessage(Component.translatable(this.getTranslationKey(), String.valueOf(this.getIntValue())));
	}

	@Override
	protected void applyValue()
	{
		this.applyValue(this.value);
		this.onValueChanged();
	}

	protected void onValueChanged()
	{
		this.setter.accept(this.getIntValue());
	}

	protected void applyValue(double ratio)
	{
		this.intValue = Mth.floor(Mth.clampedLerp(this.getMinValue(), this.getMaxValue(), ratio));
	}

	public String getTranslationKey()
	{
		return this.translationKey;
	}

	public int getIntValue()
	{
		return this.intValue;
	}

	public void setIntValue(int value)
	{
		if (this.getIntValue() == value)
		{
			return;
		}

		value = Mth.clamp(value, this.getMinValue(), this.getMaxValue());
		this.intValue = value;
		this.value = Mth.inverseLerp(value, this.getMinValue(), this.getMaxValue());
		this.onValueChanged();
		this.updateMessage();
	}

	public int getMinValue()
	{
		return this.minValue;
	}

	public int getMaxValue()
	{
		return this.maxValue;
	}

}
