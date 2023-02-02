package ad_astra_giselle_addon.client.screen;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.ToIntFunction;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamresourceful.resourcefulconfig.client.options.LongSlider;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.GravityNormalizerBlockEntity;
import ad_astra_giselle_addon.common.menu.GravityNormalizerMenu;
import ad_astra_giselle_addon.common.util.Vec3iUtils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class GravityNormalizerScreen extends AddonMachineScreen<GravityNormalizerBlockEntity, GravityNormalizerMenu>
{
	public static final ResourceLocation TEXTURE = AdAstraGiselleAddon.rl("textures/gui/container/gravity_normalizer.png");
	public static final Component VECTOR_ELEMENT_MINUS_COMPONENT = Component.literal("-");
	public static final Component VECTOR_ELEMENT_PLUS_COMPONENT = Component.literal("+");
	public static final Component VECTOR_ELEMENT_LENGTH_X_COMPONENT = Component.literal("Length.X");
	public static final Component VECTOR_ELEMENT_LENGTH_Y_COMPONENT = Component.literal("Length.Y");
	public static final Component VECTOR_ELEMENT_LENGTH_Z_COMPONENT = Component.literal("Length.Z");
	public static final Component VECTOR_ELEMENT_OFFSET_X_COMPONENT = Component.literal("Offset.X");
	public static final Component VECTOR_ELEMENT_OFFSET_Y_COMPONENT = Component.literal("Offset.Y");
	public static final Component VECTOR_ELEMENT_OFFSET_Z_COMPONENT = Component.literal("Offset.Z");

	public GravityNormalizerScreen(GravityNormalizerMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title, TEXTURE);
		this.imageWidth = 176;
		this.imageHeight = 182;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	protected void init()
	{
		super.init();

	}

	protected void addVectorElementComponents(int x, int y, Component sliderComponent, Function<GravityNormalizerBlockEntity, Vec3i> vectorGetter, BiConsumer<GravityNormalizerBlockEntity, Vec3i> vectorSetter, ToIntFunction<Vec3i> elementGetter, BiFunction<Vec3i, Integer, Vec3i> elementSetter)
	{
		int elementMin = 0;
		int elementMax = 32;
		IntSupplier mergedGetter = this.getElementGetter(vectorGetter, elementGetter);
		IntConsumer mergedSetter = this.getElementSetter(vectorGetter, vectorSetter, elementSetter, elementMin, elementMax);

		int minusButtonX = x;
		int buttonWidth = 10;
		int buttonHeight = buttonWidth;
		this.addWidget(new Button(minusButtonX, y, buttonWidth, buttonHeight, VECTOR_ELEMENT_MINUS_COMPONENT, b ->
		{
			mergedSetter.accept(mergedGetter.getAsInt() - 1);
		}));

		int sliderX = minusButtonX + buttonWidth;
		int sliderWidth = 100;
		int sliderHeight = buttonHeight;
		this.addWidget(new LongSlider(sliderX, y, sliderWidth, sliderHeight, sliderComponent, mergedGetter.getAsInt(), elementMin, elementMax, i ->
		{
			mergedSetter.accept((int) i);
		}));

		int plusButtonX = sliderX + sliderWidth;
		this.addWidget(new Button(plusButtonX, y, buttonWidth, buttonHeight, VECTOR_ELEMENT_PLUS_COMPONENT, b ->
		{
			mergedSetter.accept(mergedGetter.getAsInt() + 1);
		}));
	}

	protected IntSupplier getElementGetter(Function<GravityNormalizerBlockEntity, Vec3i> vectorGetter, ToIntFunction<Vec3i> elementGetter)
	{
		return () ->
		{
			GravityNormalizerBlockEntity machine = this.getMenu().getMachine();
			Vec3i vec = vectorGetter.apply(machine);
			return elementGetter.applyAsInt(vec);
		};
	}

	protected IntConsumer getElementSetter(Function<GravityNormalizerBlockEntity, Vec3i> vectorGetter, BiConsumer<GravityNormalizerBlockEntity, Vec3i> vectorSetter, BiFunction<Vec3i, Integer, Vec3i> elementSetter, int min, int max)
	{
		return i ->
		{
			GravityNormalizerBlockEntity machine = this.getMenu().getMachine();
			Vec3i vec = vectorGetter.apply(machine);
			Vec3i newVec = elementSetter.apply(vec, Mth.clamp(i, min, max));
			vectorSetter.accept(machine, newVec);
		};
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(stack, partialTicks, mouseX, mouseY);
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float delta)
	{
		super.render(stack, mouseX, mouseY, delta);
	}

}
