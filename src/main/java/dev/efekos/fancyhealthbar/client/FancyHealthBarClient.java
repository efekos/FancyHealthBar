/*
 * MIT License
 *
 * Copyright (c) 2024 efekos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.efekos.fancyhealthbar.client;

import dev.efekos.fancyhealthbar.client.config.FancyHealthBarConfig;
import dev.efekos.fancyhealthbar.client.hud.FancyHealthHud;
import dev.efekos.fancyhealthbar.client.screen.HeartEditorScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;

public class FancyHealthBarClient implements ClientModInitializer {

    public static final FancyHealthHud FANCY_HEALTH_HUD = new FancyHealthHud();

    public static final String MOD_ID = "fancyhealthbar";

    public static KeyBinding EDITOR_KEY = new KeyBinding("key.fancyhealthbar.editor",79,"key.category.fancyhealthbar");

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(FANCY_HEALTH_HUD);

        FancyHealthBarConfig.CONFIG_KEY = KeyBindingHelper.registerKeyBinding(FancyHealthBarConfig.CONFIG_KEY);
        EDITOR_KEY = KeyBindingHelper.registerKeyBinding(EDITOR_KEY);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (FancyHealthBarConfig.CONFIG_KEY.wasPressed()) {
                client.setScreen(FancyHealthBarConfig.createScreen());
            }
            if(EDITOR_KEY.wasPressed()) {
                client.setScreen(new HeartEditorScreen());
            }
        });

        FancyHealthBarConfig.HANDLER.load();
    }
}
