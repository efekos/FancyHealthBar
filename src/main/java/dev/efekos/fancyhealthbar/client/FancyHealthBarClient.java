package dev.efekos.fancyhealthbar.client;

import dev.efekos.fancyhealthbar.client.hud.FancyHealthHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class FancyHealthBarClient implements ClientModInitializer {

    public static final FancyHealthHud FANCY_HEALTH_HUD = new FancyHealthHud();

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(FANCY_HEALTH_HUD);
    }
}
