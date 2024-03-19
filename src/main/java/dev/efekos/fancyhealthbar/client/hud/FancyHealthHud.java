package dev.efekos.fancyhealthbar.client.hud;

import dev.efekos.fancyhealthbar.client.object.HudObject;
import dev.efekos.fancyhealthbar.client.object.PixelObject;
import dev.efekos.fancyhealthbar.client.utils.Color;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

    public void onDamage(double oldHeart, double newHeart) {

        if(gameTicks<40) return;

        double difference = oldHeart - newHeart;

        if (difference <= 0) return;

        System.out.println(oldHeart + ", " + newHeart + ", " + difference);


        for (int i = 0; i < (int) (difference / 2); i++) {
            summonHeart(lastHeartStartX + ((int) (newHeart / 2) * 8) + (i * 8), lastHeartStartY);
        }

        if (difference % 2 != 0) { // so there is a half health loss that should be rendered
            summonHalfHeart(lastHeartStartX + ((int) (newHeart / 2) * 8), lastHeartStartY);
        }

    }


    public void summonHeart(int x, int y) {

        Color mainColor = new Color(255, 19, 19);
        Color shadowColor = new Color(187, 19, 19);
        Color lightColor = new Color(255, 200, 200);

        Random random = new Random();


        List<PixelObject> list = Arrays.asList(
                new PixelObject(x + 1, y, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 2, y, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                //3
                new PixelObject(x + 4, y, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 5, y, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                //6
                //7

                new PixelObject(x, y + 1, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 1, y + 1, random.nextInt(30) - 15, random.nextInt(20)-5, lightColor),
                new PixelObject(x + 2, y + 1, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 3, y + 1, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 4, y + 1, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 5, y + 1, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 6, y + 1, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),

                new PixelObject(x, y + 2, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 1, y + 2, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 2, y + 2, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 3, y + 2, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 4, y + 2, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 5, y + 2, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 6, y + 2, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),

                new PixelObject(x, y + 3, random.nextInt(30) - 15, random.nextInt(20)-5, shadowColor),
                new PixelObject(x + 1, y + 3, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 2, y + 3, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 3, y + 3, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 4, y + 3, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 5, y + 3, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 6, y + 3, random.nextInt(30) - 15, random.nextInt(20)-5, shadowColor),

                //1
                new PixelObject(x + 1, y + 4, random.nextInt(30) - 15, random.nextInt(20)-5, shadowColor),
                new PixelObject(x + 2, y + 4, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 3, y + 4, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 4, y + 4, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 5, y + 4, random.nextInt(30) - 15, random.nextInt(20)-5, shadowColor),
                //7

                //1
                //2
                new PixelObject(x + 2, y + 5, random.nextInt(30) - 15, random.nextInt(20)-5, shadowColor),
                new PixelObject(x + 3, y + 5, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 4, y + 5, random.nextInt(30) - 15, random.nextInt(20)-5, shadowColor),
                //6
                //7

                //1
                //2
                //3
                new PixelObject(x + 3, y + 6, random.nextInt(30) - 15, random.nextInt(20)-5, shadowColor)
                //5
                //6
                //7
        );

        OBJECTS.addAll(list);
    }

    public void summonHalfHeart(int x, int y) {

        Color mainColor = new Color(255, 19, 19);
        Color shadowColor = new Color(187, 19, 19);

        Random random = new Random();


        List<PixelObject> list = Arrays.asList(
                new PixelObject(x + 4, y, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 5, y, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                //6
                //7

                new PixelObject(x + 4, y + 1, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 5, y + 1, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 6, y + 1, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),

                new PixelObject(x + 4, y + 2, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 5, y + 2, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 6, y + 2, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),

                new PixelObject(x + 4, y + 3, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 5, y + 3, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 6, y + 3, random.nextInt(30) - 15, random.nextInt(20)-5, shadowColor),

                new PixelObject(x + 4, y + 4, random.nextInt(30) - 15, random.nextInt(20)-5, mainColor),
                new PixelObject(x + 5, y + 4, random.nextInt(30) - 15, random.nextInt(20)-5, shadowColor),
                //7

                new PixelObject(x + 4, y + 5, random.nextInt(30) - 15, random.nextInt(20)-5, shadowColor)
                //6
                //7
        );

        OBJECTS.addAll(list);
    }
}
