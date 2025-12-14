package dev.efekos.fancyhealthbar.client.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.efekos.fancyhealthbar.client.compat.Texture;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.joml.Vector2d;
import org.joml.Vector2i;

public class LineJumpEntity extends HudEntity {

    private final Texture texture;
    private final int width;

    public LineJumpEntity(double x, double y, int width, Texture texture) {
        super(x, y);
        setVelocity(new Vector2d(0,5));
        setDrag(0.25f);
        this.texture = texture;
        this.width = width;
    }

    @Override
    public int getMaxLifetime() {
        return 20;
    }

    @Override
    public void render(DrawContext context) {
        texture.draw(context,(int)x,(int)y,width,9,1-(lifetime/(float)getMaxLifetime()));
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
