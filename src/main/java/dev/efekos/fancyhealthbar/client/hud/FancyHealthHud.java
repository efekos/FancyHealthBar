package dev.efekos.fancyhealthbar.client.hud;

import dev.efekos.fancyhealthbar.client.config.FancyHealthBarConfig;
import dev.efekos.fancyhealthbar.client.object.HudObject;
import dev.efekos.fancyhealthbar.client.utils.HeartSpawner;
import dev.efekos.fancyhealthbar.client.utils.HudLocation;
import dev.efekos.fancyhealthbar.client.utils.VelocityProvider;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class FancyHealthHud implements HudRenderCallback {

    public static List<HudObject> OBJECTS = new ArrayList<>();

    private static int gameTicks = 0;

    private int lastHeartStartX;
    private int lastHeartStartY;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {

        MinecraftClient client = MinecraftClient.getInstance();
        boolean notPaused = !client.isPaused();

        lastHeartStartY = drawContext.getScaledWindowHeight() - 38;
        lastHeartStartX = (drawContext.getScaledWindowWidth() / 2) - 90;

        for (HudObject object : new ArrayList<>(OBJECTS)) {

            if (gameTicks % 5 == 0 && notPaused) object.tick();

            if (object.getLocation().getX() > drawContext.getScaledWindowWidth() + 5 || object.getLocation().getY() > drawContext.getScaledWindowHeight() + 5) {
                OBJECTS.remove(object);
                continue;
            }

            object.draw(drawContext);
        }

        gameTicks++;
    }

    public void onDamage(float oldHeart, float newHeart) {

        if (gameTicks < 40) return;

        double difference = MathHelper.clamp(oldHeart - newHeart, 0, 20);

        System.out.println(oldHeart + ", " + newHeart + ", " + difference);

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        boolean hardcore = client.world.getLevelProperties().isHardcore();
        boolean poison = player.getStatusEffect(StatusEffects.POISON)!=null;
        boolean wither = player.getStatusEffect(StatusEffects.WITHER)!=null;
        boolean frozen = player.getFrozenTicks() >= 140;

        HeartSpawner spawner = HeartTypes.get(hardcore,poison,frozen,wither);

        for (int i = 0; i < (int) (difference / 2); i++) {
            OBJECTS.addAll(spawner.spawnFull(lastHeartStartX + ((int) (newHeart / 2) * 8) + (i * 8), lastHeartStartY,HEART_VELOCITY_PROVIDER));
        }

        if (difference % 2 != 0) { // so there is a half health loss that should be rendered

            if (Math.round(newHeart) % 2 == 0) // If there is a half heart lost, but the new health doesn't contain a half heart, then there should be a startHalf heart.
                OBJECTS.addAll(spawner.spawnStartHalf(lastHeartStartX + ((int) (newHeart / 2) * 8), lastHeartStartY, HEART_VELOCITY_PROVIDER));
            else
                OBJECTS.addAll(spawner.spawnEndHalf(lastHeartStartX + ((int) (newHeart / 2) * 8), lastHeartStartY, HEART_VELOCITY_PROVIDER));

        }
    }

    public static final VelocityProvider HEART_VELOCITY_PROVIDER = (random -> new HudLocation(random.nextInt(21) - 10, Math.max(random.nextInt(16)-5,0)));

}
