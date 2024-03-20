package dev.efekos.fancyhealthbar.client;

import dev.efekos.fancyhealthbar.client.config.FancyHealthBarConfig;
import dev.efekos.fancyhealthbar.client.hud.FancyHealthHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class FancyHealthBarClient implements ClientModInitializer {

    public static final FancyHealthHud FANCY_HEALTH_HUD = new FancyHealthHud();

    public static final String MOD_ID = "fancyhealthbar";

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(FANCY_HEALTH_HUD);

        FancyHealthBarConfig.CONFIG_KEY = KeyBindingHelper.registerKeyBinding(FancyHealthBarConfig.CONFIG_KEY);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(FancyHealthBarConfig.CONFIG_KEY.wasPressed()){
                client.setScreen(FancyHealthBarConfig.createScreen());
            }
        });
    }
}
