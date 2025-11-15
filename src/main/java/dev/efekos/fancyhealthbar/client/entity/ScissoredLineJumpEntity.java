package dev.efekos.fancyhealthbar.client.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.efekos.fancyhealthbar.client.compat.Texture;
import net.minecraft.client.gui.DrawContext;
import org.joml.Vector2d;
import org.joml.Vector2i;

public class ScissoredLineJumpEntity extends HudEntity {

    private final Texture texture;
    private final int width;
    private final int startX;

    public ScissoredLineJumpEntity(double x, double y, int startX,int width, Texture texture) {
        super(x, y);
        setVelocity(new Vector2d(0,5));
        setDrag(0.25f);
        this.texture = texture;
        this.width = width;
        this.startX = startX;
    }

    @Override
    public int getMaxLifetime() {
        return 20;
    }

    @Override
    public void render(DrawContext context) {
        /*? <1.21.5 {*/
        RenderSystem.setShaderColor(1,1,1,1-(lifetime/(float)getMaxLifetime()));
        RenderSystem.enableBlend();
        /*?}*/
        context.enableScissor(startX,(int)y,startX+width,(int)y+9);
        texture.draw(context,(int)x,(int)y,90,9);
        context.disableScissor();
        /*? <1.21.5 {*/
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.disableBlend();
        /*?}*/
    }

    @Override
    public Vector2i getAreaTaken() {
        return new Vector2i((int)x+width,(int)y+9);
    }

    @Override
    public boolean hasPhysics() {
        return true;
    }

}
