package com.red.tourtweaks.mixin;

import com.red.tourtweaks.TourTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.render.DimensionEffects;

@Mixin(DimensionEffects.Nether.class)
public abstract class MixinDimensionEffects_Nether extends DimensionEffects
{
    private MixinDimensionEffects_Nether(float cloudsHeight, boolean alternateSkyColor, SkyType skyType, boolean shouldRenderSky, boolean darkened)
    {
        super(cloudsHeight, alternateSkyColor, skyType, shouldRenderSky, darkened);
    }

    @Inject(method = "useThickFog", at = @At("HEAD"), cancellable = true)
    private void disableNetherFog(int x, int z, CallbackInfoReturnable<Boolean> cir)
    {
        if (TourTweaks.DISABLE_FOG)
        {
            cir.setReturnValue(false);
        }
    }
}
