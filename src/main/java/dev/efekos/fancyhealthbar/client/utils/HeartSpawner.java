package dev.efekos.fancyhealthbar.client.utils;

import dev.efekos.fancyhealthbar.client.object.HudObject;

import java.util.List;

public interface HeartSpawner {

    List<HudObject> spawnFull(int x, int y, VelocityProvider provider);
    List<HudObject> spawnHalf(int x, int y, VelocityProvider provider);

}
