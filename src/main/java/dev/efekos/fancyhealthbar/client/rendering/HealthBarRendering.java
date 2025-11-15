package dev.efekos.fancyhealthbar.client.rendering;

import dev.efekos.fancyhealthbar.client.animation.AnimationController;
import dev.efekos.fancyhealthbar.client.entity.HudEntityManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.random.Random;

public interface HealthBarRendering {

    void drawPreview(Random random,DrawContext context,int x,int y,int lines,int lastHealth,int health,boolean blinking,boolean hardcore);
    void draw(Random random, DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking);
    void react(HudEntityManager manager,AnimationController controller,MinecraftClient client);
    boolean shouldTick();
    default void tick(){}
    HealthBarRendering initialize(HudEntityManager manager, AnimationController controller, MinecraftClient client);

    void onDamage(HudEntityManager manager, int x, int y, int lines, int regeneratingHeartIndex,float maxHealth, int lastHealth, int health, int absorption, boolean blinking);

}