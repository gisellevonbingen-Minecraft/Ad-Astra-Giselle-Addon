package ad_astra_giselle_addon.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils
{
	public static Method getDeclaredAcessibleMethod(Class<?> klass, String name, Class<?>... parameterTypes)
	{
		try
		{
			Method method = klass.getDeclaredMethod(name, parameterTypes);

			if (method != null)
			{
				method.setAccessible(true);
				return method;
			}

		}
		catch (NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static Field getDeclaredAccessibleField(Class<?> klass, String name)
	{
		try
		{
			Field field = klass.getDeclaredField(name);

			if (field != null)
			{
				field.setAccessible(true);
				return field;
			}

		}
		catch (NoSuchFieldException | SecurityException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private ReflectionUtils()
	{

	}

}
