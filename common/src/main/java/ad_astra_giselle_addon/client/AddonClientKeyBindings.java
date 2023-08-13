package ad_astra_giselle_addon.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.lwjgl.glfw.GLFW;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.AddonKeyBindings;
import ad_astra_giselle_addon.common.network.AddonNetwork;
import ad_astra_giselle_addon.common.network.KeyBindingMessage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class AddonClientKeyBindings
{
	private static final Map<AddonKeyBindings.Key, KeyMapping> KEY_MAPPINGS = new HashMap<>();
	private static final AddonKeyBindings CACHE = new AddonKeyBindings();

	public static void register(Consumer<KeyMapping> register)
	{
		register(register, AddonKeyBindings.Key.JET_SUIT_POWER, GLFW.GLFW_KEY_COMMA);
		register(register, AddonKeyBindings.Key.JET_SUIT_HOVER, GLFW.GLFW_KEY_PERIOD);
	}

	private static void register(Consumer<KeyMapping> register, AddonKeyBindings.Key key, int keyCode)
	{
		KeyMapping keyMapping = new KeyMapping("key." + AdAstraGiselleAddon.MOD_ID + "." + key.name().toLowerCase(), keyCode, "key.categories." + AdAstraGiselleAddon.MOD_ID);
		KEY_MAPPINGS.put(key, keyMapping);
		register.accept(keyMapping);
	}

	public static void updateKeys()
	{
		Minecraft minecraft = Minecraft.getInstance();
		boolean noGUI = minecraft.screen == null;

		for (Entry<AddonKeyBindings.Key, KeyMapping> entry : KEY_MAPPINGS.entrySet())
		{
			AddonKeyBindings.Key key = entry.getKey();
			boolean prev = CACHE.isKeyDown(key);
			boolean next = entry.getValue().isDown() && noGUI;

			if (prev != next)
			{
				CACHE.setKeyDown(key, next);
				AddonNetwork.CHANNEL.sendToServer(new KeyBindingMessage(key, next));
			}

		}

	}

	private AddonClientKeyBindings()
	{

	}

}
