package dev.efekos.fancyhealthbar.client.accessor;

//? >=1.21.6
/*import com.mojang.blaze3d.pipeline.RenderPipeline;*/
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

import java.awt.*;
import java.util.function.Function;

public interface DrawWithAlphaAccessor {

    //? <1.21.2
    void drawTexture(Identifier texture, int x, int y, int width, int height, float u, float v, int regionWidth, int regionHeight, int textureWidth, int textureHeight, float alpha);
    //? >=1.21.2 && <1.21.6 {
    /*default void drawTexture(Function<Identifier, RenderLayer> layer, Identifier texture, int x, int y, float u, float v,int width, int height, int regionWidth, int regionHeight, int textureWidth, int textureHeight, float alpha){
        if(!(this instanceof DrawContext))return;
        DrawContext drawContext = (DrawContext) this;
        drawContext.drawTexture(layer,texture,x,y,u,v,width,height,regionWidth,regionHeight,textureWidth,textureHeight, ColorHelper.getArgb((int)(alpha*255),255,255,255));
    }
    *///?}
    //? >=1.21.6 {
    /*default void drawTexture(RenderPipeline layer, Identifier texture, int x, int y, float u, float v, int width, int height, int regionWidth, int regionHeight, int textureWidth, int textureHeight, float alpha){
        if(!(this instanceof DrawContext drawContext))return;
        drawContext.drawTexture(layer,texture,x,y,u,v,width,height,regionWidth,regionHeight,textureWidth,textureHeight, ColorHelper.getArgb((int)(alpha*255),255,255,255));
    }
    *///?}

}