package ad_astra_giselle_addon.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CustomCheckbox extends Checkbox
{
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");
	private final boolean showLabel;

	public CustomCheckbox(int pX, int pY, int pWidth, int pHeight, Component pMessage, boolean pSelected)
	{
		this(pX, pY, pWidth, pHeight, pMessage, pSelected, true);
	}

	public CustomCheckbox(int pX, int pY, int pWidth, int pHeight, Component pMessage, boolean pSelected, boolean pShowLabel)
	{
		super(pX, pY, pWidth, pHeight, pMessage, pSelected, pShowLabel);
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
		float textureU = this.isFocused() ? 20.0F : 0.0F;
		float textureV = this.selected() ? 20.0F : 0.0F;
		guiGraphics.blit(TEXTURE, this.getX(), this.getY(), checkerWidth, checkerHeight, textureU, textureV, 20, 20, 64, 64);
		super.renderWidget(guiGraphics, pMouseX, pMouseY, pPartialTick);

		if (this.isShowLabel())
		{
			guiGraphics.drawString(font, this.getMessage(), this.getX() + this.height + 2, this.getY() + (this.height - 8) / 2, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
		}

	}

	public boolean isShowLabel()
	{
		return this.showLabel;
	}

}
