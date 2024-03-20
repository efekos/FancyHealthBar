package dev.efekos.fancyhealthbar.client.utils;


import java.util.Random;

@FunctionalInterface
public interface VelocityProvider {

    HudLocation velocity(Random random);

}
