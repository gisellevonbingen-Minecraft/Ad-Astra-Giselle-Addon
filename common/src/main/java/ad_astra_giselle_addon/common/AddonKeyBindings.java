package ad_astra_giselle_addon.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.world.entity.player.Player;

public class AddonKeyBindings
{
	private static final Map<UUID, AddonKeyBindings> PLAYER_KEYS = new HashMap<>();

	public static enum Key
	{
		JET_SUIT_POWER,
		JET_SUIT_HOVER,
	}

	public static void resetKeyDown(Player player)
	{
		PLAYER_KEYS.remove(player.getUUID());
	}

	public static AddonKeyBindings getKeyBindings(Player player)
	{
		return PLAYER_KEYS.computeIfAbsent(player.getUUID(), p -> new AddonKeyBindings());
	}

	private boolean[] isDowns;

	public AddonKeyBindings()
	{
		this.isDowns = new boolean[Key.values().length];
	}

	public boolean isKeyDown(Key key)
	{
		return this.isDowns[key.ordinal()];
	}

	public void setKeyDown(Key key, boolean isDown)
	{
		this.isDowns[key.ordinal()] = isDown;
	}

}
