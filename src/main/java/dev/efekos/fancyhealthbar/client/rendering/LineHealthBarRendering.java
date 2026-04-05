package dev.efekos.fancyhealthbar.client.rendering;

//~if>=26.1 'GuiGraphics' -> 'GuiGraphicsExtractor' {
import com.mojang.blaze3d.systems.RenderSystem;
import dev.efekos.fancyhealthbar.client.Try;
import dev.efekos.fancyhealthbar.client.animation.AnimationController;
import dev.efekos.fancyhealthbar.client.animation.AnimationTrack;
import dev.efekos.fancyhealthbar.client.compat.Texture;
import dev.efekos.fancyhealthbar.client.entity.HudEntityManager;
import dev.efekos.fancyhealthbar.client.entity.LineJumpEntity;
import dev.efekos.fancyhealthbar.client.entity.ScissoredLineJumpEntity;
import dev.efekos.fancyhealthbar.client.options.LineRenderingOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

import java.util.Objects;

public class LineHealthBarRendering implements HealthBarRendering {

    private final LineRenderingOptions options;
    private HudEntityManager manager;

    @Override
    public void react(HudEntityManager manager, AnimationController controller, Minecraft client) {
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
    public LineHealthBarRendering initialize(HudEntityManager manager, AnimationController controller, Minecraft client) {
        boolean hardcore = Try.orElse(()->client.player.level().getLevelData().isHardcore(),false);

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
    public void drawPreview(RandomSource random, GuiGraphics context, int x, int y, int lines, int lastHealth, int health, boolean blinking, boolean hardcore) {

        //? <1.21.6
        context.pose().pushPose();
        //? >=1.21.6
        //context.pose().pushMatrix();
        context.pose().translate(options.offset().x,options.offset().y/*? <1.21.6 {*/,0/*?}*/);

        LineRenderingOptions.LineStyle lineStyle = hardcore ? options.hardcoreLineStyle() : options.normalLineStyle();
        int healthW = (int) Math.floor((health / 20d)*90);
        if (this.healthDeltaW == -1) this.healthDeltaW = healthW;
        lineStyle.empty().draw(context,x,y,90,9);
        updatePositions(x,y);
        if(options.yellowDelta()&&health>0&&(options.deltaBehaviour()!= LineRenderingOptions.DeltaBehaviour.BLINK||blinking))
            drawTxtr(context,lineStyle.delta(), x, y,this.healthDeltaW, lineStyle);
        drawTxtr(context,lineStyle.full(),x,y,healthW,lineStyle);

        if(options.blinking()&&blinking)
            lineStyle.blink().draw(context,x,y,90,9);
        if(options.notches()>0){
            Texture texture = lineStyle.notches(Mth.clamp(options.notches(), 1, 5));
            //? <1.21.5
            RenderSystem.enableBlend();
            texture.draw(context,x,y,90,9);
            //? <1.21.5
            RenderSystem.disableBlend();
        }

        //? <1.21.6
        context.pose().popPose();
        //? >=1.21.6
        //context.pose().popMatrix();

        updateAnimationState(health,20f);
        updateDeltaWidth(health,20f, healthW);
        updateLastHealth(health,20f,lineStyle);
    }

    private float lastHealth = -1;
    private float healthLossTicks = 0;
    private float lastHealthL = -1; // still lastHealth but lerped

    @Override
    public void draw(RandomSource random, GuiGraphics context, Player player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int a, int health, int absorption, boolean blinking) {


        //? <1.21.6
        context.pose().pushPose();
        //? >=1.21.6
        //context.pose().pushMatrix();
        context.pose().translate(options.offset().x,options.offset().y/*? <1.21.6 {*/,0/*?}*/);

        boolean hardcore = Try.orElse(()->player.level().getLevelData().isHardcore(),false);

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
            Texture notches = lineStyle.notches(Mth.clamp(options.notches(), 1,5));
            //? <1.21.5
            RenderSystem.enableBlend();
            notches.draw(context,x,y,90,9);
            //? <1.21.5
            RenderSystem.disableBlend();
        }


        //? <1.21.6
        context.pose().popPose();
        //? >=1.21.6
        //context.pose().popMatrix();

        updateAnimationState(player.getHealth(),player.getMaxHealth());
        updateDeltaWidth(player.getHealth(),player.getMaxHealth(), healthW);
        updateLastHealth(player.getHealth(),player.getMaxHealth(),lineStyle);
    }

    private void updatePositions(int x, int y) {
        regenTrack.setX(x+options.offset().x);
        regenTrack.setY(y+options.offset().y);
        fulledTrack.setX(x+options.offset().x);
        fulledTrack.setY(y+options.offset().y);
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
            this.lastHealthL = (float) Mth.lerp(0.1, lastHealthL, health);
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

    private static Texture determineTexture(Player player, LineRenderingOptions.LineStyle lineStyle) {
        return player.isFullyFrozen() ? lineStyle.freeze() : (player.isOnFire() || player.isInLava()) ? lineStyle.fire() : lineStyle.full();
    }

    private void drawTxtr(GuiGraphics context, Texture texture, int x, int y, int width, LineRenderingOptions.LineStyle lineStyle) {
        if(lineStyle.scissor()){
            context.enableScissor(x+options.offset().x, y+options.offset().y, x+options.offset().x+width, y+options.offset().y +9);
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
//~}
