package ad_astra_giselle_addon.common.network;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.AddonKeyBindings;
import ad_astra_giselle_addon.common.AddonKeyHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class KeyBindingMessage implements Packet<KeyBindingMessage>
{
	public static final ResourceLocation ID = AdAstraGiselleAddon.rl("key_binding");
	public static final Handler HANDLER = new Handler();

	private AddonKeyBindings.Key key;
	private boolean isDown;

	public KeyBindingMessage()
	{
		this.key = AddonKeyBindings.Key.JET_SUIT_POWER;
		this.isDown = false;
	}

	public KeyBindingMessage(AddonKeyBindings.Key key, boolean isDown)
	{
		this.key = key;
		this.isDown = isDown;
	}

	public AddonKeyBindings.Key getKey()
	{
		return this.key;
	}

	public void setKey(AddonKeyBindings.Key key)
	{
		this.key = key;
	}

	public boolean isDown()
	{
		return this.isDown;
	}

	public void setDown(boolean isDown)
	{
		this.isDown = isDown;
	}

	@Override
	public ResourceLocation getID()
	{
		return ID;
	}

	@Override
	public PacketHandler<KeyBindingMessage> getHandler()
	{
		return HANDLER;
	}

	public static class Handler implements PacketHandler<KeyBindingMessage>
	{
		@Override
		public void encode(KeyBindingMessage message, FriendlyByteBuf buffer)
		{
			buffer.writeEnum(message.getKey());
			buffer.writeBoolean(message.isDown());
		}

		@Override
		public KeyBindingMessage decode(FriendlyByteBuf buffer)
		{
			KeyBindingMessage message = new KeyBindingMessage();
			message.setKey(buffer.readEnum(AddonKeyBindings.Key.class));
			message.setDown(buffer.readBoolean());
			return message;
		}

		@Override
		public PacketContext handle(KeyBindingMessage message)
		{
			return (player, level) ->
			{
				AddonKeyBindings.getKeyBindings(player).setKeyDown(message.getKey(), message.isDown());
				AddonKeyHandler.onKeyChanged(player, message.getKey(), message.isDown());
			};

		}

	}

}
