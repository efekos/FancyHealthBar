package dev.efekos.fancyhealthbar.client.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.efekos.fancyhealthbar.client.Try;
import dev.efekos.fancyhealthbar.client.animation.AnimationController;
import dev.efekos.fancyhealthbar.client.animation.AnimationTrack;
import dev.efekos.fancyhealthbar.client.compat.Texture;
import dev.efekos.fancyhealthbar.client.entity.HudEntityManager;
import dev.efekos.fancyhealthbar.client.entity.LineJumpEntity;
import dev.efekos.fancyhealthbar.client.entity.ScissoredLineJumpEntity;
import dev.efekos.fancyhealthbar.client.options.LineRenderingOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

import java.util.Objects;

public class LineHealthBarRendering implements HealthBarRendering {

    private final LineRenderingOptions options;
    private HudEntityManager manager;

    @Override
    public void react(HudEntityManager manager,AnimationController controller,MinecraftClient client) {
        controller.reset();
        initialize(manager,controller,client);
    }

    public LineHealthBarRendering(LineRenderingOptions options) {
        this.options = options;
    }

    private AnimationTrack regenTrack;
    private AnimationTrack fulledTrack;
    private int healthDeltaW = -1;

    @Override
    public LineHealthBarRendering initialize(HudEntityManager manager, AnimationController controller, MinecraftClient client) {
        //? <1.21.9 {
        boolean hardcore = Try.orElse(()->client.player.getWorld().getLevelProperties().isHardcore(),false);
        //?} else {
        /*boolean hardcore = Try.orElse(()->client.player.getEntityWorld().getLevelProperties().isHardcore(),false);
        *///?}

        LineRenderingOptions.LineStyle lineStyle = hardcore ? options.hardcoreLineStyle() : options.normalLineStyle();
        regenTrack = controller.createTrack(lineStyle.regenAnim());
        regenTrack.setLoop(true);
        fulledTrack = controller.createTrack(lineStyle.fulledAnim());
        this.manager = manager;
        controller.addTrack(regenTrack);
        controller.addTrack(fulledTrack);
        return this;
    }

    @Override
    public void drawPreview(Random random, DrawContext context, int x, int y, int lines, int lastHealth, int health, boolean blinking, boolean hardcore) {
        LineRenderingOptions.LineStyle lineStyle = hardcore ? options.hardcoreLineStyle() : options.normalLineStyle();
        int healthW = (int) Math.floor((health / 20d)*90);
        if (this.healthDeltaW == -1) this.healthDeltaW = healthW;
        lineStyle.empty().draw(context,x,y,90,9);
        updatePositions(x,y);
        if(options.yellowDelta()&&health>0&&(options.deltaBehaviour()!= LineRenderingOptions.DeltaBehaviour.BLINK||blinking))
            drawTxtr(context,lineStyle.delta(), x, y,this.healthDeltaW, lineStyle);
        if(lineStyle.scissor()){
            context.enableScissor(x,y,x+healthW,y+9);
            lineStyle.full().draw(context,x,y,90,9);
            context.disableScissor();
        } else
            lineStyle.full().draw(context,x,y,healthW,9);

        if(options.blinking()&&blinking)
            lineStyle.blink().draw(context,x,y,90,9);
        if(options.notches()>0){
            Texture texture = lineStyle.notches(MathHelper.clamp(options.notches(), 1, 5));
            //? <1.21.5
            RenderSystem.enableBlend();
            texture.draw(context,x,y,90,9);
            //? <1.21.5
            RenderSystem.disableBlend();
        }

        updateAnimationState(health,20f);
        updateDeltaWidth(health,20f, healthW);
        updateLastHealth(health,20f,lineStyle);
    }

    private float lastHealth = -1;
    private float healthLossTicks = 0;
    private float lastHealthL = -1; // still lastHealth but lerped

    @Override
    public void draw(Random random, DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int a, int health, int absorption, boolean blinking) {
        //? <1.21.9 {
        boolean hardcore = Try.orElse(()->player.getWorld().getLevelProperties().isHardcore(),false);
        //?} else {
        /*boolean hardcore = Try.orElse(()->player.getEntityWorld().getLevelProperties().isHardcore(),false);
        *///?}

        LineRenderingOptions.LineStyle lineStyle = hardcore ? options.hardcoreLineStyle() : options.normalLineStyle();
        int healthW = (int) Math.floor((player.getHealth() / player.getMaxHealth()) * 90);
        if(this.lastHealth==-1)this.lastHealth = player.getHealth();
        if (this.healthDeltaW == -1) this.healthDeltaW = healthW;
        lineStyle.empty().draw(context,x,y,90,9);
        if (regenTrack.getY()!=y) updatePositions(x,y);
        if(options.yellowDelta()&&health>0&&(options.deltaBehaviour()!= LineRenderingOptions.DeltaBehaviour.BLINK||blinking))
            drawTxtr(context,lineStyle.delta(), x, y,this.healthDeltaW, lineStyle);
        drawTxtr(context,determineTexture(player, lineStyle),x,y,healthW,lineStyle);

        if(options.blinking()&&blinking)
            lineStyle.blink().draw(context,x,y,90,9);

        if(options.notches()>0){
            Texture notches = lineStyle.notches(MathHelper.clamp(options.notches(), 1,5));
            //? <1.21.5
            RenderSystem.enableBlend();
            notches.draw(context,x,y,90,9);
            //? <1.21.5
            RenderSystem.disableBlend();
        }

        updateAnimationState(player.getHealth(),player.getMaxHealth());
        updateDeltaWidth(player.getHealth(),player.getMaxHealth(), healthW);
        updateLastHealth(player.getHealth(),player.getMaxHealth(),lineStyle);
    }

    private void updatePositions(int x, int y) {
        regenTrack.setX(x);
        regenTrack.setY(y);
        fulledTrack.setX(x);
        fulledTrack.setY(y);
    }

    private void updateLastHealth(float health,float maxHealth,LineRenderingOptions.LineStyle lineStyle) {
        if(this.lastHealth>health) {
            healthLossTicks = 60;
            if(options.deltaBehaviour()== LineRenderingOptions.DeltaBehaviour.JUMP){
                int oldWidth =(int)((this.lastHealth/maxHealth)*90);
                int newWidth =(int)((health/maxHealth)*90);
                int diff = oldWidth-newWidth;
                if(lineStyle.scissor())
                    manager.addEntity(new ScissoredLineJumpEntity(regenTrack.getX(), regenTrack.getY(), regenTrack.getX()+newWidth, diff, lineStyle.delta()));
                else
                    manager.addEntity(new LineJumpEntity(Math.max(regenTrack.getX(),regenTrack.getX() + newWidth-lineStyle.jumpOffset()), regenTrack.getY(), diff+(newWidth==0?0:lineStyle.jumpOffset()), lineStyle.delta()));
            }
        }
        if (Objects.requireNonNull(options.deltaBehaviour()) == LineRenderingOptions.DeltaBehaviour.LERP)
            this.lastHealthL = (float) MathHelper.lerp(0.1, lastHealthL, health);
        this.lastHealth = health;
    }

    private void updateAnimationState(float health,float maxHealth) {
        if (health > lastHealth && regenTrack.isPaused()) {
            regenTrack.play();
        } else if (regenTrack.isOver()) regenTrack.stop();

        if (health== maxHealth&& health!=lastHealth) {
            if(regenTrack.isPlaying())regenTrack.stop();
            fulledTrack.play();
        }
    }

    private static Texture determineTexture(PlayerEntity player, LineRenderingOptions.LineStyle lineStyle) {
        return player.isFrozen() ? lineStyle.freeze() : (player.isOnFire() || player.isInLava()) ? lineStyle.fire() : lineStyle.full();
    }

    private void drawTxtr(DrawContext context, Texture texture, int x, int y, int width, LineRenderingOptions.LineStyle lineStyle) {
        if(lineStyle.scissor()){
            context.enableScissor(x, y, x +width, y +9);
            texture.draw(context,x,y,90,9);
            context.disableScissor();
        } else
            texture.draw(context,x,y,width,9);
    }

    private void updateDeltaWidth(float health,float maxHealth, int healthW) {
        switch (options.deltaBehaviour()){
            case FOLLOW -> {
                if (healthDeltaW > healthW) healthDeltaW--;
                if (healthDeltaW < healthW) healthDeltaW++;
            }
            case LERP -> healthDeltaW = (int)((this.lastHealthL/ maxHealth)*90);
            case BLINK -> {
                if(healthLossTicks<=0&&lastHealth<=health)healthDeltaW = healthW;
                else healthLossTicks--;
            }
            case JUMP -> {
                healthDeltaW = 0;
            }
        }
    }

    @Override
    public boolean shouldTick() {
        return false;
    }

    @Override
    public void onDamage(HudEntityManager manager, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking) {

    }

}
