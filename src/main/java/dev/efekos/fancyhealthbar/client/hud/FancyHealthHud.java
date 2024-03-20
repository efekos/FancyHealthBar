package dev.efekos.fancyhealthbar.client.hud;

import dev.efekos.fancyhealthbar.client.object.HudObject;
import dev.efekos.fancyhealthbar.client.utils.HudLocation;
import dev.efekos.fancyhealthbar.client.utils.VelocityProvider;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
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

        if(gameTicks<40) return;

        double difference = MathHelper.clamp(oldHeart - newHeart,0,20);

        System.out.println(oldHeart + ", " + newHeart + ", " + difference);


        for (int i = 0; i < (int) (difference / 2); i++) {
            OBJECTS.addAll(HeartTypes.NORMAL.spawnFull(lastHeartStartX + ((int) (newHeart / 2) * 8) + (i * 8), lastHeartStartY
                    , random -> new HudLocation(random.nextInt(20) - 10, random.nextInt(15))));
        }

        if (difference % 2 != 0) { // so there is a half health loss that should be rendered

            if(Math.round(newHeart)%2==0) // If there is a half heart lost, but the new health doesn't contain a half heart, then there should be a startHalf heart.
                OBJECTS.addAll(HeartTypes.NORMAL.spawnStartHalf(lastHeartStartX + ((int) (newHeart / 2) * 8), lastHeartStartY,HEART_VELOCITY_PROVIDER));
            else OBJECTS.addAll(HeartTypes.NORMAL.spawnEndHalf(lastHeartStartX + ((int) (newHeart / 2) * 8), lastHeartStartY,HEART_VELOCITY_PROVIDER));

        }

    }

    public static final VelocityProvider HEART_VELOCITY_PROVIDER = (random -> new HudLocation(random.nextInt(20) - 10, random.nextInt(15)));

}
