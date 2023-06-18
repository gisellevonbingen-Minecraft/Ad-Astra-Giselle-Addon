package ad_astra_giselle_addon.client.screen;

import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.GravityNormalizerBlockEntity;
import ad_astra_giselle_addon.common.menu.GravityNormalizerMenu;
import ad_astra_giselle_addon.common.network.AddonNetwork;
import ad_astra_giselle_addon.common.network.GravityNormalizerMessage;
import ad_astra_giselle_addon.common.util.Vec3iUtils;
import earth.terrarium.ad_astra.client.screen.GuiUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class GravityNormalizerScreen extends AddonMachineScreen<GravityNormalizerBlockEntity, GravityNormalizerMenu>
{
	public static final ResourceLocation TEXTURE = AdAstraGiselleAddon.rl("textures/gui/container/gravity_normalizer.png");
	public static final Component VECTOR_ELEMENT_MINUS_TEXT = Component.literal("-");
	public static final Component VECTOR_ELEMENT_PLUS_TEXT = Component.literal("+");
	public static final String VECTOR_ELEMENT_TOOLTIP = ctl("gravity_normalizer.vector_element_tooltip");
	public static final Component MOVE_TO_POS_COMPONENT = Component.translatable(ctl("gravity_normalizer.move_to_pos"));
	public static final String ENERGY_USING_KEY = ctl("gravity_normalizer.energy_using");

	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("+#;-#");
	public static final int DELTA_NORMAL = 1;
	public static final int DELTA_SHIFT = 5;
	public static final Component MINUS_TOOLTIP = createVectorElementButtonTooltipComponent(-1);
	public static final Component PLUS_TOOLTIP = createVectorElementButtonTooltipComponent(+1);

	public static final int ENERGY_LEFT = 146;
	public static final int ENERGY_TOP = 22;

	protected final List<AbstractWidget> element_length_x_widgets;
	protected final List<AbstractWidget> element_length_y_widgets;
	protected final List<AbstractWidget> element_length_z_widgets;
	protected final List<AbstractWidget> element_offset_x_widgets;
	protected final List<AbstractWidget> element_offset_y_widgets;
	protected final List<AbstractWidget> element_offset_z_widgets;

	protected Button moveToPosButton;

	public GravityNormalizerScreen(GravityNormalizerMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title, TEXTURE);
		this.imageWidth = 176;
		this.imageHeight = 196;
		this.inventoryLabelY = this.imageHeight - 94;

		this.element_length_x_widgets = new ArrayList<>();
		this.element_length_y_widgets = new ArrayList<>();
		this.element_length_z_widgets = new ArrayList<>();
		this.element_offset_x_widgets = new ArrayList<>();
		this.element_offset_y_widgets = new ArrayList<>();
		this.element_offset_z_widgets = new ArrayList<>();
	}

	@Override
	protected void init()
	{
		super.init();

		int x = this.leftPos + 6;
		int y = this.topPos + 18;
		int lengthMin = GravityNormalizerBlockEntity.getMinLength();
		int lengthMax = GravityNormalizerBlockEntity.getMaxLength();
		int offsetMin = GravityNormalizerBlockEntity.getMinOffset();
		int offsetMax = GravityNormalizerBlockEntity.getMaxOffset();
		GravityNormalizerBlockEntity machine = this.getMenu().getMachine();
		Supplier<Vec3i> getLength = machine::getLength;
		Consumer<Vec3i> setLength = vec -> this.setMachineLength(machine, vec);
		Supplier<Vec3i> getOffset = machine::getOffset;
		Consumer<Vec3i> setOffset = vec -> this.setMachineOffset(machine, vec);

		int layoutY = y;
		int offset = 12;
		this.element_length_x_widgets.clear();
		this.element_length_x_widgets.addAll(this.addVectorElementComponents(x, layoutY, ctl("gravity_normalizer.length.x"), lengthMin, lengthMax, getLength, setLength, Vec3i::getX, Vec3iUtils::deriveX));

		this.element_length_y_widgets.clear();
		this.element_length_y_widgets.addAll(this.addVectorElementComponents(x, layoutY += offset, ctl("gravity_normalizer.length.y"), lengthMin, lengthMax, getLength, setLength, Vec3i::getY, Vec3iUtils::deriveY));

		this.element_length_z_widgets.clear();
		this.element_length_z_widgets.addAll(this.addVectorElementComponents(x, layoutY += offset, ctl("gravity_normalizer.length.z"), lengthMin, lengthMax, getLength, setLength, Vec3i::getZ, Vec3iUtils::deriveZ));

		this.moveToPosButton = new Button.Builder(MOVE_TO_POS_COMPONENT, b ->
		{
			this.setMachineOffset(machine, GravityNormalizerBlockEntity.offsetFromLength(getLength.get()));
		}).bounds(x, layoutY += offset, 132, 10).build();
		this.addRenderableWidget(this.moveToPosButton);

		this.element_offset_x_widgets.clear();
		this.element_offset_x_widgets.addAll(this.addVectorElementComponents(x, layoutY += offset, ctl("gravity_normalizer.offset.x"), offsetMin, offsetMax, getOffset, setOffset, Vec3i::getX, Vec3iUtils::deriveX));

		this.element_offset_y_widgets.clear();
		this.element_offset_y_widgets.addAll(this.addVectorElementComponents(x, layoutY += offset, ctl("gravity_normalizer.offset.y"), offsetMin, offsetMax, getOffset, setOffset, Vec3i::getY, Vec3iUtils::deriveY));

		this.element_offset_z_widgets.clear();
		this.element_offset_z_widgets.addAll(this.addVectorElementComponents(x, layoutY += offset, ctl("gravity_normalizer.offset.z"), offsetMin, offsetMax, getOffset, setOffset, Vec3i::getZ, Vec3iUtils::deriveZ));
	}

	protected List<AbstractWidget> addVectorElementComponents(int x, int y, String translationKey, int elementMin, int elementMax, Supplier<Vec3i> vectorGetter, Consumer<Vec3i> vectorSetter, ToIntFunction<Vec3i> elementGetter, BiFunction<Vec3i, Integer, Vec3i> elementSetter)
	{
		IntSupplier mergedGetter = this.getMergedElementGetter(vectorGetter, elementGetter);
		IntConsumer mergedSetter = this.getMergedElementSetter(vectorGetter, vectorSetter, elementSetter, elementMin, elementMax);
		List<AbstractWidget> widgets = new ArrayList<>();

		int buttonWidth = 16;
		int buttonHeight = 10;

		int sliderX = x;
		int sliderWidth = 100;
		int sliderHeight = buttonHeight;
		widgets.add(this.addRenderableWidget(new ElementSliderButton(sliderX, y, sliderWidth, sliderHeight, translationKey, mergedGetter.getAsInt(), elementMin, elementMax, mergedSetter::accept)));

		int minusButtonX = sliderX + sliderWidth;
		widgets.add(this.addRenderableWidget(new Button.Builder(VECTOR_ELEMENT_MINUS_TEXT, b ->
		{
			this.onVectorElementButtonClick(-1, mergedGetter, mergedSetter);
		}).bounds(minusButtonX, y, buttonWidth, buttonHeight).tooltip(Tooltip.create(MINUS_TOOLTIP)).build()));

		int plusButtonX = minusButtonX + buttonWidth;
		widgets.add(this.addRenderableWidget(new Button.Builder(VECTOR_ELEMENT_PLUS_TEXT, b ->
		{
			this.onVectorElementButtonClick(+1, mergedGetter, mergedSetter);
		}).bounds(plusButtonX, y, buttonWidth, buttonHeight).tooltip(Tooltip.create(PLUS_TOOLTIP)).build()));

		return widgets;
	}

	protected void onVectorElementButtonClick(int direction, IntSupplier mergedGetter, IntConsumer mergedSetter)
	{
		int delta = direction * (Screen.hasShiftDown() ? DELTA_SHIFT : DELTA_NORMAL);
		mergedSetter.accept(mergedGetter.getAsInt() + delta);
	}

	protected IntSupplier getMergedElementGetter(Supplier<Vec3i> vectorGetter, ToIntFunction<Vec3i> elementGetter)
	{
		return () ->
		{
			Vec3i vec = vectorGetter.get();
			return elementGetter.applyAsInt(vec);
		};
	}

	protected IntConsumer getMergedElementSetter(Supplier<Vec3i> vectorGetter, Consumer<Vec3i> vectorSetter, BiFunction<Vec3i, Integer, Vec3i> elementSetter, int min, int max)
	{
		return i ->
		{
			Vec3i vec = vectorGetter.get();
			Vec3i newVec = elementSetter.apply(vec, Mth.clamp(i, min, max));
			vectorSetter.accept(newVec);
		};
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(guiGraphics, partialTicks, mouseX, mouseY);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
	{
		super.render(guiGraphics, mouseX, mouseY, delta);

		GravityNormalizerMenu menu = this.getMenu();
		GravityNormalizerBlockEntity machine = menu.getMachine();

		Vec3i length = machine.getLength();
		this.setElement(this.element_length_x_widgets, length.getX());
		this.setElement(this.element_length_y_widgets, length.getY());
		this.setElement(this.element_length_z_widgets, length.getZ());

		Vec3i offset = machine.getOffset();
		this.setElement(this.element_offset_x_widgets, offset.getX());
		this.setElement(this.element_offset_y_widgets, offset.getY());
		this.setElement(this.element_offset_z_widgets, offset.getZ());

		long maxCapacity = machine.getEnergyStorage().getMaxCapacity();
		GuiUtil2.drawEnergy(guiGraphics, this.getEnergyBounds(), menu.getEnergyAmount(), maxCapacity);

		if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY))
		{
			GuiUtil.drawEnergyTooltip(guiGraphics, menu.getEnergyAmount(), maxCapacity, mouseX, mouseY);
		}

	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY)
	{
		super.renderLabels(guiGraphics, mouseX, mouseY);

		GravityNormalizerBlockEntity machine = this.getMenu().getMachine();
		long energyUsing = machine.getEnergyUsing();
		int maxTimer = machine.getMaxTimer();
		Component component = Component.translatable(ENERGY_USING_KEY, String.valueOf(energyUsing), String.valueOf(maxTimer));
		int componentWidth = this.font.width(component);
		guiGraphics.drawString(this.font, component, this.imageWidth - 6 - componentWidth, this.inventoryLabelY, this.getTextColour(), false);
	}

	protected void setMachineLength(GravityNormalizerBlockEntity blockEntity, Vec3i length)
	{
		if (!blockEntity.getLength().equals(length))
		{
			blockEntity.setLength(length);
			AddonNetwork.CHANNEL.sendToServer(new GravityNormalizerMessage.Length(blockEntity, length));
		}

	}

	protected void setMachineOffset(GravityNormalizerBlockEntity blockEntity, Vec3i offset)
	{
		if (!blockEntity.getOffset().equals(offset))
		{
			blockEntity.setOffset(offset);
			AddonNetwork.CHANNEL.sendToServer(new GravityNormalizerMessage.Offset(blockEntity, offset));
		}

	}

	protected void setElement(List<AbstractWidget> widgets, int value)
	{
		for (AbstractWidget widget : widgets)
		{
			if (widget instanceof ElementSliderButton slider)
			{
				slider.setIntValue(value);
			}

		}

	}

	public Rectangle getEnergyBounds()
	{
		return GuiUtil.getEnergyBounds(this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP);
	}

	public static Component createVectorElementButtonTooltipComponent(int direction)
	{
		return Component.translatable(VECTOR_ELEMENT_TOOLTIP, DECIMAL_FORMAT.format(direction * DELTA_NORMAL), DECIMAL_FORMAT.format(direction * DELTA_SHIFT));
	}

}
