package com.red.tourtweaks.mixin;

import com.google.common.collect.Ordering;
import com.red.tourtweaks.TourTweaks;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuCommand;
import net.minecraft.client.gui.hud.spectator.TeleportSpectatorMenu;
import net.minecraft.client.gui.hud.spectator.TeleportToSpecificPlayerSpectatorCommand;
import net.minecraft.client.network.PlayerListEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;

@Mixin(TeleportSpectatorMenu.class)
public abstract class MixinTeleportSpectatorMenu
{
    @Shadow
    @Final
    private static Ordering<PlayerListEntry> ORDERING;
    @Shadow
    @Final
    private List<SpectatorMenuCommand> elements;

    @Inject(method = "<init>(Ljava/util/Collection;)V", at = @At("RETURN"))
    private void allowSpectatorTeleport(Collection<PlayerListEntry> profiles, CallbackInfo ci)
    {
        if (TourTweaks.SPECTATOR_TELEPORT)
        {
            this.elements.clear();

            for (PlayerListEntry info : ORDERING.sortedCopy(profiles))
            {
                this.elements.add(new TeleportToSpecificPlayerSpectatorCommand(info.getProfile()));
            }
        }
    }
}
