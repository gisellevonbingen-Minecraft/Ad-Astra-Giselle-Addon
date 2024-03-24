package ad_astra_giselle_addon.client.screen;

import java.util.function.IntConsumer;

import com.mojang.blaze3d.systems.RenderSystem;

import ad_astra_giselle_addon.client.mixin.minecraft.AbstractSliderButtonInvoker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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
	public void renderWidget(GuiGraphics guiGraphics, int p_275335_, int p_275551_, float p_275511_)
	{
		Minecraft minecraft = Minecraft.getInstance();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		AbstractSliderButtonInvoker invoker = (AbstractSliderButtonInvoker) this;
		guiGraphics.blitSprite(invoker.invokeGetSprite(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
		guiGraphics.blitSprite(invoker.invokeGetHandleSprite(), this.getX() + (int) (this.value * (this.width - 8)), this.getY(), 8, this.getHeight());
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		int i = this.active ? 16777215 : 10526880;
		this.renderScrollingString(guiGraphics, minecraft.font, 2, i | Mth.ceil(this.alpha * 255.0F) << 24);
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
