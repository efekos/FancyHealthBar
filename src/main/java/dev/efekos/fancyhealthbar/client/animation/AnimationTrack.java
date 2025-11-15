package dev.efekos.fancyhealthbar.client.animation;

import com.mojang.blaze3d.systems.RenderSystem;
//? >=1.21.6
/*import net.minecraft.client.gl.RenderPipelines;*/
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.math.MathHelper;

public class AnimationTrack {

    private final Animation animation;
    private boolean paused = true;
    private boolean loop = false;
    private int frame = 0;
    private int x;
    private int y;
    private boolean invisibleWhenNotPlaying = true;

    public AnimationTrack(Animation animation) {
        this.animation = animation;
    }

    public void draw(DrawContext context) {
        if ((!isInvisibleWhenNotPlaying() || !isPaused()) && MathHelper.clamp(frame, 0, animation.maxFrames()-1) == frame) {
            /*? <1.21.5 {*/
            //? <1.21.2
            RenderSystem.setShader(GameRenderer::getRenderTypeTranslucentProgram);
            RenderSystem.enableBlend();
            RenderSystem.enableCull();
            /*?}*/
            animation.getTexture(frame).draw(context,x,y,animation.width(),animation.height());
            /*? <1.21.5 {*/
            RenderSystem.disableBlend();
            RenderSystem.disableCull();
            /*?}*/
        }
    }

    private String createDebugInfo() {
        StringBuilder builder = new StringBuilder();
        if (paused) builder.append("P");
        if (loop) builder.append("L");
        if (invisibleWhenNotPlaying) builder.append("I");
        return builder.append(":").append(frame).append(":").append(x).append(":").append(y).toString();
    }

    public boolean isInvisibleWhenNotPlaying() {
        return invisibleWhenNotPlaying;
    }

    public void setInvisibleWhenNotPlaying(boolean invisibleWhenNotPlaying) {
        this.invisibleWhenNotPlaying = invisibleWhenNotPlaying;
    }

    public void tick() {
        if (isPaused()) return;
        if (frame >= animation.maxFrames()) {
            if (isLoop()) setFrame(0);
            else setPaused(true);
        } else setFrame(frame + 1);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = MathHelper.clamp(frame, 0, animation.maxFrames() + 1);
    }

    public boolean isPlaying() {
        return !paused;
    }

    public void setPlaying(boolean playing) {
        this.paused = !playing;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void play() {
        setFrame(0);
        setPlaying(true);
    }

    public void pause() {
        setPlaying(false);
    }

    public void resume() {
        setPlaying(true);
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void stop() {
        setPlaying(false);
    }

    public boolean isOver() {
        return isPlaying() && getFrame() >= animation.maxFrames();
    }

}
