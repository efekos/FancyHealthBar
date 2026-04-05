package dev.efekos.fancyhealthbar.client.entity;

//~if >=26.1 'GuiGraphics' -> 'GuiGraphicsExtractor' {

//? >=1.21.6 {
/*import com.mojang.blaze3d.pipeline.RenderPipeline;
import dev.efekos.fancyhealthbar.client.compat.CompatKey;
import net.minecraft.client.renderer.RenderPipelines;
*///?}
import dev.efekos.fancyhealthbar.client.accessor.DrawWithAlphaAccessor;
import dev.efekos.fancyhealthbar.client.compat.CompatKey;
import dev.efekos.fancyhealthbar.client.compat.Texture;
import net.minecraft.client.gui.GuiGraphics;
//? <1.21.11
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
//? <1.21.6
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector2i;

//? >=1.21.2 {
/*import java.util.function.Function;
*///?}

public class PixelEntity extends HudEntity {

    private final Texture texture;
    private final int u;
    private final int v;
    private int size = 1;

    public PixelEntity(double x, double y, Texture texture, int u, int v) {
        super(x, y);
        this.texture = texture;
        this.u = u;
        this.v = v;
    }

    public int getSize() {
        return size;
    }

    public PixelEntity setSize(int size) {
        this.size = size;
        return this;
    }

    @Override
    public void render(GuiGraphics ctx) {
        DrawWithAlphaAccessor context = (DrawWithAlphaAccessor)ctx;

        //? <1.20.2 {
        /*context.drawTexture(texture.atlasId(),(int)x,(int)y,size,size,u+texture.atlasPosition().u1(),v+texture.atlasPosition().v1(),1,1,256,256,getAlpha());
        *///?} else {
        TextureAtlasSprite sprite = ctx./*? >=1.21.9 {*//*guiSprites*//*?} else {*/sprites/*?}*/.getSprite(texture.baseId().unwrap());
        float unit=(sprite.getU1()-sprite.getU0())/sprite.contents().width();
        int textureSize = (int)(1/unit);
        //? <1.21.2
        context.drawTexture(CompatKey.of(sprite.atlasLocation()),(int)x,(int)y,size,size,u+ sprite.getU0()* textureSize,v+ sprite.getV0()* textureSize,1,1, textureSize, textureSize,getAlpha());
        //? >=1.21.2 {
        /*//? >=1.21.6
        //RenderPipeline arg1 = RenderPipelines.GUI_TEXTURED;
        //? <1.21.6
        Function<ResourceLocation, RenderType> arg1 = RenderType::guiTextured;
        context.drawTexture(arg1, CompatKey.of(sprite.atlasLocation()),(int)x,(int)y,u+ sprite.getU0()* textureSize,v+ sprite.getV0()* textureSize,size,size,1,1, textureSize, textureSize,getAlpha());
        *///?}
        //?}


    }

    public Vector2i getAreaTaken() {
        return new Vector2i(size,size);
    }

    @Override
    public boolean hasPhysics() {
        return true;
    }

}
//~}