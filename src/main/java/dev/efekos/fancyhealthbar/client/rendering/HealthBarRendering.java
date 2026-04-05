package dev.efekos.fancyhealthbar.client.rendering;

//~if>=26.1 'GuiGraphics' -> 'GuiGraphicsExtractor' {
import dev.efekos.fancyhealthbar.client.animation.AnimationController;
import dev.efekos.fancyhealthbar.client.entity.HudEntityManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.RandomSource;

public interface HealthBarRendering {

    void drawPreview(RandomSource random, GuiGraphics context, int x, int y, int lines, int lastHealth, int health, boolean blinking, boolean hardcore);
    void draw(RandomSource random, GuiGraphics context, Player player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking);
    void react(HudEntityManager manager, AnimationController controller, Minecraft client);
    boolean shouldTick();
    default void tick(){}
    HealthBarRendering initialize(HudEntityManager manager, AnimationController controller, Minecraft client);

    void onDamage(HudEntityManager manager, int x, int y, int lines, int regeneratingHeartIndex,float maxHealth, int lastHealth, int health, int absorption, boolean blinking);

}
//~}