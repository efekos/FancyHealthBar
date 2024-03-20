package dev.efekos.fancyhealthbar.client.hud.heart;

import dev.efekos.fancyhealthbar.client.object.HudObject;
import dev.efekos.fancyhealthbar.client.object.PixelObject;
import dev.efekos.fancyhealthbar.client.utils.Color;
import dev.efekos.fancyhealthbar.client.utils.HeartSpawner;
import dev.efekos.fancyhealthbar.client.utils.VelocityProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WitherHeartType implements HeartSpawner {

    public List<HudObject> spawnFull(int x, int y, VelocityProvider velocityProvider) {

        Color mainColor = new Color(43,43,43);
        Color shadowColor = new Color(32,32,32);
        Color borderColor = new Color(59,19,19);
        Color lightDot = Color.of(203,203,203);

        Random random = new Random();


        return Arrays.asList(
                new PixelObject(x + 1, y, velocityProvider.velocity(random), borderColor),
                new PixelObject(x + 2, y, velocityProvider.velocity(random), borderColor),
                //3
                new PixelObject(x + 4, y, velocityProvider.velocity(random), borderColor),
                new PixelObject(x + 5, y, velocityProvider.velocity(random), borderColor),
                //6
                //7

                new PixelObject(x, y + 1, velocityProvider.velocity(random), borderColor),
                new PixelObject(x + 1, y + 1, velocityProvider.velocity(random), lightDot),
                new PixelObject(x + 2, y + 1, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 3, y + 1, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 4, y + 1, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 5, y + 1, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 6, y + 1, velocityProvider.velocity(random), borderColor),

                new PixelObject(x, y + 2, velocityProvider.velocity(random), borderColor),
                new PixelObject(x + 1, y + 2, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 2, y + 2, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 3, y + 2, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 4, y + 2, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 5, y + 2, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 6, y + 2, velocityProvider.velocity(random), borderColor),

                new PixelObject(x, y + 3, velocityProvider.velocity(random), borderColor),
                new PixelObject(x + 1, y + 3, velocityProvider.velocity(random), shadowColor),
                new PixelObject(x + 2, y + 3, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 3, y + 3, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 4, y + 3, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 5, y + 3, velocityProvider.velocity(random), shadowColor),
                new PixelObject(x + 6, y + 3, velocityProvider.velocity(random), borderColor),

                //1
                new PixelObject(x + 1, y + 4, velocityProvider.velocity(random), borderColor),
                new PixelObject(x + 2, y + 4, velocityProvider.velocity(random), shadowColor),
                new PixelObject(x + 3, y + 4, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 4, y + 4, velocityProvider.velocity(random), shadowColor),
                new PixelObject(x + 5, y + 4, velocityProvider.velocity(random), borderColor),
                //7

                //1
                //2
                new PixelObject(x + 2, y + 5, velocityProvider.velocity(random), borderColor),
                new PixelObject(x + 3, y + 5, velocityProvider.velocity(random), shadowColor),
                new PixelObject(x + 4, y + 5, velocityProvider.velocity(random), borderColor),
                //6
                //7

                //1
                //2
                //3
                new PixelObject(x + 3, y + 6, velocityProvider.velocity(random), borderColor)
                //5
                //6
                //7
        );
    }

    @Override
    public List<HudObject> spawnEndHalf(int x, int y, VelocityProvider provider) {
        return spawnFull(x,y,provider).stream().filter(hudObject -> hudObject.getLocation().getX()>=x+4).toList();
    }

    @Override
    public List<HudObject> spawnStartHalf(int x, int y, VelocityProvider provider) {
        return spawnFull(x,y,provider).stream().filter(hudObject -> hudObject.getLocation().getX()<=x+3).toList();
    }

}
