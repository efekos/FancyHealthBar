package dev.efekos.fancyhealthbar.client.hud.heart;

import dev.efekos.fancyhealthbar.client.object.HudObject;
import dev.efekos.fancyhealthbar.client.object.PixelObject;
import dev.efekos.fancyhealthbar.client.utils.Color;
import dev.efekos.fancyhealthbar.client.utils.HeartSpawner;
import dev.efekos.fancyhealthbar.client.utils.VelocityProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NormalHardcoreHeartType implements HeartSpawner {

    public List<HudObject> spawnFull(int x, int y, VelocityProvider velocityProvider) {

        Color mainColor = new Color(255, 19, 19);
        Color shadowColor = new Color(187, 19, 19);
        Color darkColor = new Color(96, 7, 7);

        Random random = new Random();


        return Arrays.asList(
                new PixelObject(x + 1, y, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 2, y, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 4, y, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 5, y, velocityProvider.velocity(random), mainColor),

                new PixelObject(x, y + 1, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 1, y + 1, velocityProvider.velocity(random), darkColor),
                new PixelObject(x + 2, y + 1, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 3, y + 1, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 4, y + 1, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 5, y + 1, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 6, y + 1, velocityProvider.velocity(random), mainColor),

                new PixelObject(x, y + 2, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 1, y + 2, velocityProvider.velocity(random), darkColor),
                new PixelObject(x + 2, y + 2, velocityProvider.velocity(random), darkColor),
                new PixelObject(x + 3, y + 2, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 4, y + 2, velocityProvider.velocity(random), darkColor),
                new PixelObject(x + 5, y + 2, velocityProvider.velocity(random), darkColor),
                new PixelObject(x + 6, y + 2, velocityProvider.velocity(random), mainColor),

                new PixelObject(x, y + 3, velocityProvider.velocity(random), shadowColor),
                new PixelObject(x + 1, y + 3, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 2, y + 3, velocityProvider.velocity(random), darkColor),
                new PixelObject(x + 3, y + 3, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 4, y + 3, velocityProvider.velocity(random), darkColor),
                new PixelObject(x + 5, y + 3, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 6, y + 3, velocityProvider.velocity(random), shadowColor),

                new PixelObject(x + 1, y + 4, velocityProvider.velocity(random), shadowColor),
                new PixelObject(x + 2, y + 4, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 3, y + 4, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 4, y + 4, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 5, y + 4, velocityProvider.velocity(random), shadowColor),

                new PixelObject(x + 2, y + 5, velocityProvider.velocity(random), shadowColor),
                new PixelObject(x + 3, y + 5, velocityProvider.velocity(random), mainColor),
                new PixelObject(x + 4, y + 5, velocityProvider.velocity(random), shadowColor),

                new PixelObject(x + 3, y + 6, velocityProvider.velocity(random), shadowColor)
        );
    }

    @Override
    public List<HudObject> spawnEndHalf(int x, int y, VelocityProvider provider) {
        return spawnFull(x, y, provider).stream().filter(hudObject -> hudObject.getLocation().getX() >= x + 4).toList();
    }

    @Override
    public List<HudObject> spawnStartHalf(int x, int y, VelocityProvider provider) {
        return spawnFull(x, y, provider).stream().filter(hudObject -> hudObject.getLocation().getX() <= x + 3).toList();
    }
}
