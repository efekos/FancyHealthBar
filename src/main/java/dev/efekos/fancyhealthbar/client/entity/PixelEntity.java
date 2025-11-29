package dev.efekos.fancyhealthbar.client.entity;

//? >=1.21.6
/*import net.minecraft.client.gl.RenderPipelines;*/
import dev.efekos.fancyhealthbar.client.compat.Texture;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import org.joml.Vector2i;

// <1.21.6
import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;

public class PixelEntity extends HudEntity {

    private final Texture texture;
    private final int u;
    private final int v;
    private final boolean guiMode;
    private int size = 1;
    private int maxLifetime;

    public PixelEntity(double x, double y, Texture texture, int u, int v, boolean guiMode) {
        super(x, y);
        this.texture = texture;
        this.u = u;
        this.v = v;
        this.guiMode = guiMode;
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

    public void draw(DrawContext context) {
        int x1 = (int)x-1;
        int x2 = x1+size;
        int y1 = (int)y-1;
        int y2 = y1+size;


        //?>=1.20.2 {
        Sprite sprite = context.guiAtlasManager.getSprite(texture.baseId());
        Identifier id = sprite.getAtlasId();
        float perPixelV = (sprite.getMaxV() - sprite.getMinV()) / (float) sprite.getContents().getHeight();
        float perPixelU = (sprite.getMaxU() - sprite.getMinU()) / (float) sprite.getContents().getWidth();
        float u1 = sprite.getMinU() + perPixelU * u;
        float v1 = sprite.getMinV() + perPixelV * v;
        drawTexturedQuad(context,id,x1,x2,y1,y2,0, u1,u1+perPixelU,v1,v1+perPixelV);
        //?} else {
        /*float perPixelV = 1/256f;
        float perPixelU = 1/256f;
        float u1 = perPixelU * u + texture.atlasPosition().u1()*perPixelU;
        float v1 = perPixelV * v + texture.atlasPosition().v1()*perPixelV;
        drawTexturedQuad(context,texture.atlasId(),x1,x2,y1,y2,0, u1,u1+perPixelU,v1,v1+perPixelV);*/
        //?}
    }

    void drawTexturedQuad(DrawContext context,Identifier texture, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2) {
        //? >=1.21.6 {
        /*context.drawTexturedQuad(texture,x1,y1,x2,y2,u1,u2,v1,v2);
        *///?} else if 1.21.5 {
        /*context.drawTexturedQuad(RenderLayer::getGuiTextured,texture,x1,x2,y1,y2,u1,u2,v1,v2,Colors.WHITE);
        *///?} else {
        RenderSystem.setShaderTexture(0, texture);
        //? <1.21.2
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        Matrix4f matrix4f = context.getMatrices().peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance()./*? >=1.21 {*/begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);/*?} else {*//*getBuffer();*//*?}*/
        bufferBuilder.vertex(matrix4f, x1, y1, z).texture(u1, v1);
        bufferBuilder.vertex(matrix4f, x1, y2, z).texture(u1, v2);
        bufferBuilder.vertex(matrix4f, x2, y2, z).texture(u2, v2);
        bufferBuilder.vertex(matrix4f, x2, y1, z).texture(u2, v1);
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        //?}
    }

    @Override
    public void render(DrawContext context) {

        if(guiMode){
            //? <1.20.2 {
            /*context.drawRepeatingTexture(texture.atlasId(),(int)x,(int)y,1,1,texture.atlasPosition().u1()+u,texture.atlasPosition().v1()+v,1,1);
            *///?} else {
            Sprite spr = context./*? <1.21.9 {*/guiAtlasManager/*?} else {*//*spriteAtlasTexture*//*?}*/.getSprite(texture.baseId());
            context.drawSpriteTiled(
                    /*? >=1.21.6 {*//*RenderPipelines.GUI_TEXTURED,*//*?} else if >=1.21.2 {*//*RenderLayer::getGuiTextured,*//*?}*/
                    spr,
                    (int)x,(int)y,/*? <1.21.3 {*/0,/*?}*/
                    size,size,u,v,
                    1,1,spr.getContents().getWidth(),spr.getContents().getHeight()
                    /*? >=1.21.3 {*//*, Colors.WHITE*//*?}*/
                    );
            //?}
        } else draw(context);

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