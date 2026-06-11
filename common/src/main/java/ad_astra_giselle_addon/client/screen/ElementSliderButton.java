package ad_astra_giselle_addon.client.screen;

import java.util.function.IntConsumer;

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
