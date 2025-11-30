package dev.efekos.fancyhealthbar.client.entity;

//? >=1.21.6
/*import net.minecraft.client.gl.RenderPipelines;*/
import dev.efekos.fancyhealthbar.client.compat.Texture;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import org.joml.Vector2i;

import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;

public class PixelEntity extends HudEntity {

    private final Texture texture;
    private final int u;
    private final int v;
    private int size = 1;
    private int maxLifetime;

    public PixelEntity(double x, double y, Texture texture, int u, int v) {
        super(x, y);
        this.texture = texture;
        this.u = u;
        this.v = v;
    }

    public int getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(int maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public int getSize() {
        return size;
    }

    public PixelEntity setSize(int size) {
        this.size = size;
        return this;
    }

    @Override
    public void render(DrawContext context) {
        //? <=1.20.2 {
        /*context.drawTexture(texture.atlasId(),(int)x,(int)y,size,size,u+texture.atlasPosition().u1(),v+texture.atlasPosition().v1(),1,1,256,256);*/
        //?} else {
        Sprite sprite = context./*? >=1.21.9 {*//*spriteAtlasTexture*//*?} else {*/guiAtlasManager/*?}*/.getSprite(texture.baseId());
        float unit=(sprite.getMaxU()-sprite.getMinU())/sprite.getContents().getWidth();
        int textureSize = (int)(1/unit);
        //? <1.21.2
        context.drawTexture(sprite.getAtlasId(),(int)x,(int)y,size,size,u+sprite.getMinU()*textureSize,v+sprite.getMinV()*textureSize,1,1,textureSize,textureSize);
        //? >=1.21.2 {
        /*context.drawTexture(/^? >=1.21.6 {^//^RenderPipelines.GUI_TEXTURED^//^?} else {^/RenderLayer::getGuiTextured/^?}^/,sprite.getAtlasId(),(int)x,(int)y,u+sprite.getMinU()*textureSize,v+sprite.getMinV()*textureSize,size,size,1,1,textureSize,textureSize);
        *///?}
    }

    @Override
    public Vector2i getAreaTaken() {
        return new Vector2i(size,size);
    }

    @Override
    public boolean hasPhysics() {
        return true;
    }

}