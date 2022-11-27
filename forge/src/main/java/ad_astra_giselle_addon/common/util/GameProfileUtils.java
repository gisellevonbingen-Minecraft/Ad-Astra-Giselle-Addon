package ad_astra_giselle_addon.common.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.function.Consumer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.world.level.block.entity.SkullBlockEntity;

public class GameProfileUtils
{
	public static final String WHITELIST_HOST = "textures.minecraft.net";

	public static String toWhitelistUrl(String hash)
	{
		return "http://" + WHITELIST_HOST + "/texture/" + hash;
	}

	public static void fillTextures(GameProfile gameProfile, String input, Consumer<GameProfile> callback)
	{
		try
		{
			URL url = new URL(input);

			if (url.getHost().equalsIgnoreCase(WHITELIST_HOST))
			{
				fillTexturesFromUrl(gameProfile, input);
			}

			callback.accept(gameProfile);
		}
		catch (MalformedURLException e)
		{
			try
			{
				String json = new String(Base64.getDecoder().decode(input));
				new Gson().fromJson(json, JsonObject.class);
				fillTexturesFromBase64(gameProfile, input);
				callback.accept(gameProfile);
			}
			catch (Exception e2)
			{
				SkullBlockEntity.updateGameprofile(gameProfile, fromNickname ->
				{
					if (fromNickname.getProperties().size() > 0)
					{
						callback.accept(fromNickname);
					}
					else
					{
						fillTexturesFromUrl(gameProfile, toWhitelistUrl(input));
						callback.accept(gameProfile);
					}

				});

			}

		}

	}

	public static void fillTexturesFromUrl(GameProfile gameProfile, String url)
	{
		String str = "{\"textures\":{\"SKIN\":{\"url\":\"" + url + "\"}}}";
		String base64 = Base64.getEncoder().encodeToString(str.getBytes());
		fillTexturesFromBase64(gameProfile, base64);
	}

	public static void fillTexturesFromBase64(GameProfile gameProfile, String base64)
	{
		Property property = new Property("textures", base64);
		gameProfile.getProperties().put("textures", property);
	}

	private GameProfileUtils()
	{

	}

}
