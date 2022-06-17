package com.red.tourtweaks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.text.LiteralText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TourTweaks implements ModInitializer {
    public static final Logger logger = LogManager.getLogger("tourtweaks");

    public static boolean SPECTATOR_TELEPORT = true;
    public static boolean GAMMA_OVERRIDE = false;
    public static boolean DISABLE_FOG = false;

    private static double GAMMA_VALUE_ORIGINAL = 0f;

    @Override
    public void onInitialize() {
        logger.info("TourTweaks Loaded.");
        registerCommands();
    }

    public void registerCommands() {
        ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal("tourtweaks")
            .then(ClientCommandManager.literal("spectatorteleport").executes(context -> {
                SPECTATOR_TELEPORT = !SPECTATOR_TELEPORT;
                context.getSource().sendFeedback(new LiteralText(String.format("%s Spectator Teleport", SPECTATOR_TELEPORT ? "Enabled" : "Disabled")));

                return 1;
            }))
            .then(ClientCommandManager.literal("gammaoverride").executes(context -> {
                GAMMA_OVERRIDE = !GAMMA_OVERRIDE;
                context.getSource().sendFeedback(new LiteralText(String.format("%s Gamma Override", GAMMA_OVERRIDE ? "Enabled" : "Disabled")));

                if (context.getSource().getClient().options.gamma <= 1.0f)
                    GAMMA_VALUE_ORIGINAL = context.getSource().getClient().options.gamma;
                context.getSource().getClient().options.gamma = GAMMA_OVERRIDE ? 16f : GAMMA_VALUE_ORIGINAL;

                return 1;
            }))
            .then(ClientCommandManager.literal("fog").executes(context -> {
                DISABLE_FOG = !DISABLE_FOG;
                context.getSource().sendFeedback(new LiteralText(String.format("%s Render Distance and Nether Fog", DISABLE_FOG ? "Disabled" : "Enabled")));

                return 1;
            }))
        );
    }
}
