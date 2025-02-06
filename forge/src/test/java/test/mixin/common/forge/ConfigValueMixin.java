package test.mixin.common.forge;

import javax.annotation.CheckForNull;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

@Mixin(value = ConfigValue.class)
public abstract class ConfigValueMixin
{
	@Redirect(method = "get", remap = false, at = @At(value = "INVOKE", target = "com/google/common/base/Preconditions.checkState", remap = false))
	private void get_checkState(boolean expression, @CheckForNull Object errorMessage)
	{

	}

}
