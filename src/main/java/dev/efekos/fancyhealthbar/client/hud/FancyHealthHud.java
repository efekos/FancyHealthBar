package dev.efekos.fancyhealthbar.client.hud;

import dev.efekos.fancyhealthbar.client.object.HudObject;
import dev.efekos.fancyhealthbar.client.object.PixelObject;
import dev.efekos.fancyhealthbar.client.utils.Color;
import dev.efekos.fancyhealthbar.client.utils.HudLocation;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FancyHealthHud implements HudRenderCallback {

    public static List<HudObject> OBJECTS = new ArrayList<>();

    private static int gameTicks = 0;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {

        for (HudObject object : OBJECTS) {

            if(gameTicks%20==0) object.tick();

            if(object.getLocation().getX()>drawContext.getScaledWindowWidth()+5||object.getLocation().getY()>drawContext.getScaledWindowHeight()+5){
                OBJECTS.remove(object);
                continue;
            }

            object.draw(drawContext);
        }

        if(gameTicks%5==0) {

            Random random = new Random();

            PixelObject object = new PixelObject(
                    new HudLocation(drawContext.getScaledWindowWidth()/2, drawContext.getScaledWindowWidth()/2),
                    new HudLocation(random.nextInt(15)-7,random.nextInt(10)),
                    new Color(255,255,255)
            );

            OBJECTS.add(object);
        }


        gameTicks++;
    }
}
