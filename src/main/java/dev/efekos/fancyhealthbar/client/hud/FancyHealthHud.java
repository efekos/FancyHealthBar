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

package dev.efekos.fancyhealthbar.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.efekos.fancyhealthbar.client.FancyHealthBarClient;
import dev.efekos.fancyhealthbar.client.FancyHealthBarConfig;
import dev.efekos.fancyhealthbar.client.object.HudObject;
import dev.efekos.fancyhealthbar.client.utils.HudLocation;
import dev.efekos.fancyhealthbar.client.utils.VelocityProvider;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class FancyHealthHud {

    public static final VelocityProvider HEART_VELOCITY_PROVIDER = (random -> {

        double multiplier = FancyHealthBarConfig.getVelocityMultiplier();

        return new HudLocation((int) ((random.nextInt(21) - 10) * multiplier), (int) (Math.max(random.nextInt(16) - 5, 0) * multiplier));
    });
    public static List<HudObject> OBJECTS = new ArrayList<>();
    private static int gameTicks = 0;
    private int lastHeartStartX;
    private int lastHeartStartY;
    private int lastHealthLostTicks = 0;

    public static final Identifier LINES_TEXTURE = Identifier.of(FancyHealthBarClient.MOD_ID,"lines");

    private void add(List<HudObject> objects){
        for (HudObject o : objects)
            if(OBJECTS.size()<FancyHealthBarConfig.getMaximumObjects()) OBJECTS.add(o);
    }

    public void onDamage(float oldHeart, float newHeart) {

        if (gameTicks < 40) return;

        double difference = MathHelper.clamp(oldHeart - newHeart, 0, 20);

        System.out.println(oldHeart + ", " + newHeart + ", " + difference);

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        boolean hardcore = client.world.getLevelProperties().isHardcore();
        boolean poison = player.getStatusEffect(StatusEffects.POISON) != null;
        boolean wither = player.getStatusEffect(StatusEffects.WITHER) != null;
        boolean frozen = player.getFrozenTicks() >= 140;

        HeartGenerator spawner = HeartTypes.get(hardcore, poison, frozen, wither);

        for (int i = 0; i < (int) (difference / 2); i++)
            for (int j = 0; j < FancyHealthBarConfig.getCountMultiplier(); j++)
                add(spawner.spawnFull(lastHeartStartX + ((int) (newHeart / 2) * 8), lastHeartStartY, HEART_VELOCITY_PROVIDER));

        if (difference % 2 != 0) { // so there is a half health loss that should be rendered

            if (Math.round(newHeart) % 2 == 0) // If there is a half heart lost, but the new health doesn't contain a half heart, then there should be a startHalf heart.
                for (int i = 0; i < FancyHealthBarConfig.getCountMultiplier(); i++)
                    add(spawner.spawnStartHalf(lastHeartStartX + ((int) (newHeart / 2) * 8), lastHeartStartY));

            else
                for (int i = 0; i < FancyHealthBarConfig.getCountMultiplier(); i++)
                    add(spawner.spawnEndHalf(lastHeartStartX + ((int) (newHeart / 2) * 8), lastHeartStartY));
        }
    }

    public void render(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, float lastHealth, float health, int absorption, boolean blinking) {

        MinecraftClient client = MinecraftClient.getInstance();
        boolean notPaused = !client.isPaused();

        int height = context.getScaledWindowHeight();
        lastHeartStartY = height - 38;
        int width = context.getScaledWindowWidth();
        lastHeartStartX = (width / 2) - 90;
        if (health < lastHealth) {
            onDamage(lastHealth, health);
            lastHealthLostTicks = 0;
        }

        for (HudObject object : new ArrayList<>(OBJECTS)) {

            if (gameTicks % FancyHealthBarConfig.getUpdateInterval() == 0 && notPaused) object.tick();

            if (object.getLocation().getX() > width + 16 || object.getLocation().getY() > height + 16 ||
                    object.getLocation().getX() < -16 || object.getLocation().getY() < -128 || object.getLifetime() >= FancyHealthBarConfig.getMaximumTicks()) {
                OBJECTS.remove(object);
                continue;
            }

            if(!client.options.hudHidden) object.draw(context);
        }

        gameTicks++;
        if(lastHealthLostTicks<100) lastHealthLostTicks++;
        if(client.options.hudHidden) return;

        float v = 1f - lastHealthLostTicks / 25f;
        RenderSystem.setShaderColor(v,v,v,1f);
        context.drawGuiTexture(LINES_TEXTURE,x,y,81,9);
        RenderSystem.setShaderColor(1,1,1,1);
    }

}
