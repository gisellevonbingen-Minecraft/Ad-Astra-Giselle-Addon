package ad_astra_giselle_addon.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CustomCheckbox extends AbstractButton
{
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");
	private final boolean showLabel;
	private boolean selected;

	public CustomCheckbox(int pX, int pY, int pWidth, int pHeight, Component pMessage, boolean pSelected)
	{
		this(pX, pY, pWidth, pHeight, pMessage, pSelected, true);
	}

	public CustomCheckbox(int pX, int pY, int pWidth, int pHeight, Component pMessage, boolean pSelected, boolean pShowLabel)
	{
		super(pX, pY, pWidth, pHeight, pMessage);
		this.selected = pSelected;
		this.showLabel = pShowLabel;
	}

	@Override
	public void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick)
	{
		Minecraft minecraft = Minecraft.getInstance();
		RenderSystem.enableDepthTest();
		Font font = minecraft.font;
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		int checkerWidth = this.height;
		int checkerHeight = this.height;
		float textureU = this.isHoveredOrFocused() ? 20.0F : 0.0F;
		float textureV = this.selected() ? 20.0F : 0.0F;
		guiGraphics.blit(TEXTURE, this.getX(), this.getY(), checkerWidth, checkerHeight, textureU, textureV, 20, 20, 64, 64);

		if (this.isShowLabel())
		{
			guiGraphics.drawString(font, this.getMessage(), this.getX() + this.height + 2, this.getY() + (this.height - 8) / 2, 0x303030 | Mth.ceil(this.alpha * 255.0F) << 24, false);
		}

	}

	public boolean selected()
	{
		return this.selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public boolean isShowLabel()
	{
		return this.showLabel;
	}

	@Override
	public void onPress()
	{
		this.setSelected(!this.selected());
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput)
	{
		pNarrationElementOutput.add(NarratedElementType.TITLE, this.createNarrationMessage());

		if (this.active)
		{
			if (this.isFocused())
			{
				pNarrationElementOutput.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.focused"));
			}
			else
			{
				pNarrationElementOutput.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.hovered"));
			}

		}

	}

}
