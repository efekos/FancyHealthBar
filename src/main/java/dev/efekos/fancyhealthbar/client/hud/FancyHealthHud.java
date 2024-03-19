package dev.efekos.fancyhealthbar.client.hud;

import dev.efekos.fancyhealthbar.client.FancyHealthBarClient;
import dev.efekos.fancyhealthbar.client.object.HudObject;
import dev.efekos.fancyhealthbar.client.object.PixelObject;
import dev.efekos.fancyhealthbar.client.utils.Color;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FancyHealthHud implements HudRenderCallback {

    public static List<HudObject> OBJECTS = new ArrayList<>();

    private static int gameTicks = 0;

    private int lastHeartStartX;
    private int lastHeartStartY;
    private int lastHealthLost;

    public static final Identifier HEART_BASE_TEXTURE = new Identifier(FancyHealthBarClient.MOD_ID, "base_hearts");

    public static final Identifier HEART_TEXTURE = new Identifier("hud/heart/full");
    public static final Identifier HALF_HEART_TEXTURE = new Identifier("hud/heart/half");

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {

        MinecraftClient client = MinecraftClient.getInstance();
        boolean notPaused = !client.isPaused();
        float health = client.player.getHealth();

        lastHeartStartY = drawContext.getScaledWindowHeight() - 38;
        lastHeartStartX = (drawContext.getScaledWindowWidth() / 2) - 90;

        drawContext.drawGuiTexture(HEART_BASE_TEXTURE, lastHeartStartX - 1, lastHeartStartY - 1, 81, 9);

        for (int i = 0; i < 10; i++) {

            if((i*2)+1<health) drawContext.drawGuiTexture(HEART_TEXTURE,lastHeartStartX+(i*8)-1,lastHeartStartY-1,9,9);
            else if ((i*2)+1==health) drawContext.drawGuiTexture(HALF_HEART_TEXTURE,lastHeartStartX+(i*8)-1,lastHeartStartY-1,9,9);

        }


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

        double difference = oldHeart - newHeart;

        System.out.println(oldHeart + ", " + newHeart + ", " + difference);

        if (difference <= 0) return;

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
                new PixelObject(x + 1, y, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 2, y, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                //3
                new PixelObject(x + 4, y, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 5, y, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                //6
                //7

                new PixelObject(x, y + 1, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 1, y + 1, random.nextInt(15) - 7, random.nextInt(10), lightColor),
                new PixelObject(x + 2, y + 1, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 3, y + 1, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 4, y + 1, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 5, y + 1, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 6, y + 1, random.nextInt(15) - 7, random.nextInt(10), mainColor),

                new PixelObject(x, y + 2, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 1, y + 2, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 2, y + 2, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 3, y + 2, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 4, y + 2, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 5, y + 2, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 6, y + 2, random.nextInt(15) - 7, random.nextInt(10), mainColor),

                new PixelObject(x, y + 3, random.nextInt(15) - 7, random.nextInt(10), shadowColor),
                new PixelObject(x + 1, y + 3, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 2, y + 3, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 3, y + 3, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 4, y + 3, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 5, y + 3, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 6, y + 3, random.nextInt(15) - 7, random.nextInt(10), shadowColor),

                //1
                new PixelObject(x + 1, y + 4, random.nextInt(15) - 7, random.nextInt(10), shadowColor),
                new PixelObject(x + 2, y + 4, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 3, y + 4, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 4, y + 4, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 5, y + 4, random.nextInt(15) - 7, random.nextInt(10), shadowColor),
                //7

                //1
                //2
                new PixelObject(x + 2, y + 5, random.nextInt(15) - 7, random.nextInt(10), shadowColor),
                new PixelObject(x + 3, y + 5, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 4, y + 5, random.nextInt(15) - 7, random.nextInt(10), shadowColor),
                //6
                //7

                //1
                //2
                //3
                new PixelObject(x + 3, y + 6, random.nextInt(15) - 7, random.nextInt(10), shadowColor)
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
                new PixelObject(x + 4, y, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 5, y, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                //6
                //7

                new PixelObject(x + 4, y + 1, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 5, y + 1, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 6, y + 1, random.nextInt(15) - 7, random.nextInt(10), mainColor),

                new PixelObject(x + 4, y + 2, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 5, y + 2, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 6, y + 2, random.nextInt(15) - 7, random.nextInt(10), mainColor),

                new PixelObject(x + 4, y + 3, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 5, y + 3, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 6, y + 3, random.nextInt(15) - 7, random.nextInt(10), shadowColor),

                new PixelObject(x + 4, y + 4, random.nextInt(15) - 7, random.nextInt(10), mainColor),
                new PixelObject(x + 5, y + 4, random.nextInt(15) - 7, random.nextInt(10), shadowColor),
                //7

                new PixelObject(x + 4, y + 5, random.nextInt(15) - 7, random.nextInt(10), shadowColor)
                //6
                //7
        );

        OBJECTS.addAll(list);
    }
}
