package dev.efekos.fancyhealthbar.client.hud.heart;

import dev.efekos.fancyhealthbar.client.object.HudObject;
import dev.efekos.fancyhealthbar.client.object.PixelObject;
import dev.efekos.fancyhealthbar.client.utils.Color;
import dev.efekos.fancyhealthbar.client.utils.HeartSpawner;
import dev.efekos.fancyhealthbar.client.utils.VelocityProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FrozenHardcoreHeartType implements HeartSpawner {

    public static final Color[] COLOR_PALETTE = {
            Color.of(76,86,216),  // color at the top - 0
            Color.of(167,246,254), // two pixels at 2nd row -1
            Color.of(128,229,239), // center wrap -2
            Color.of(168,247,255), // dark of center & sides of 4th row -3
            Color.of(255,252,255), // light of center -4
            Color.of(76,186,216), // center -5
            Color.of(1,190,242), // shadow -6
            Color.of(107,148,157), // hc top -7
            Color.of(103,132,140), // hc bottom -8
    };


    public List<HudObject> spawnFull(int x, int y, VelocityProvider velocityProvider) {

        Random random = new Random();


        return Arrays.asList(
                new PixelObject(x + 1, y, velocityProvider.velocity(random), COLOR_PALETTE[0]),
                new PixelObject(x + 2, y, velocityProvider.velocity(random), COLOR_PALETTE[0]),
                new PixelObject(x + 4, y, velocityProvider.velocity(random), COLOR_PALETTE[0]),
                new PixelObject(x + 5, y, velocityProvider.velocity(random), COLOR_PALETTE[0]),

                new PixelObject(x, y + 1, velocityProvider.velocity(random), COLOR_PALETTE[0]),
                new PixelObject(x + 1, y + 1, velocityProvider.velocity(random), COLOR_PALETTE[7]),
                new PixelObject(x + 2, y + 1, velocityProvider.velocity(random), COLOR_PALETTE[8]),
                new PixelObject(x + 3, y + 1, velocityProvider.velocity(random), COLOR_PALETTE[2]),
                new PixelObject(x + 4, y + 1, velocityProvider.velocity(random), COLOR_PALETTE[0]),
                new PixelObject(x + 5, y + 1, velocityProvider.velocity(random), COLOR_PALETTE[8]),
                new PixelObject(x + 6, y + 1, velocityProvider.velocity(random), COLOR_PALETTE[0]),

                new PixelObject(x, y + 2, velocityProvider.velocity(random), COLOR_PALETTE[0]),
                new PixelObject(x + 1, y + 2, velocityProvider.velocity(random), COLOR_PALETTE[0]),
                new PixelObject(x + 2, y + 2, velocityProvider.velocity(random), COLOR_PALETTE[9]),
                new PixelObject(x + 3, y + 2, velocityProvider.velocity(random), COLOR_PALETTE[3]),
                new PixelObject(x + 4, y + 2, velocityProvider.velocity(random), COLOR_PALETTE[7]),
                new PixelObject(x + 5, y + 2, velocityProvider.velocity(random), COLOR_PALETTE[8]),
                new PixelObject(x + 6, y + 2, velocityProvider.velocity(random), COLOR_PALETTE[0]),

                new PixelObject(x, y + 3, velocityProvider.velocity(random), COLOR_PALETTE[3]),
                new PixelObject(x + 1, y + 3, velocityProvider.velocity(random), COLOR_PALETTE[2]),
                new PixelObject(x + 2, y + 3, velocityProvider.velocity(random), COLOR_PALETTE[8]),
                new PixelObject(x + 3, y + 3, velocityProvider.velocity(random), COLOR_PALETTE[5]),
                new PixelObject(x + 4, y + 3, velocityProvider.velocity(random), COLOR_PALETTE[8]),
                new PixelObject(x + 5, y + 3, velocityProvider.velocity(random), COLOR_PALETTE[2]),
                new PixelObject(x + 6, y + 3, velocityProvider.velocity(random), COLOR_PALETTE[3]),

                new PixelObject(x + 1, y + 4, velocityProvider.velocity(random), COLOR_PALETTE[6]),
                new PixelObject(x + 2, y + 4, velocityProvider.velocity(random), COLOR_PALETTE[2]),
                new PixelObject(x + 3, y + 4, velocityProvider.velocity(random), COLOR_PALETTE[4]),
                new PixelObject(x + 4, y + 4, velocityProvider.velocity(random), COLOR_PALETTE[2]),
                new PixelObject(x + 5, y + 4, velocityProvider.velocity(random), COLOR_PALETTE[6]),

                new PixelObject(x + 2, y + 5, velocityProvider.velocity(random), COLOR_PALETTE[6]),
                new PixelObject(x + 3, y + 5, velocityProvider.velocity(random), COLOR_PALETTE[2]),
                new PixelObject(x + 4, y + 5, velocityProvider.velocity(random), COLOR_PALETTE[6]),

                new PixelObject(x + 3, y + 6, velocityProvider.velocity(random), COLOR_PALETTE[6])
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
