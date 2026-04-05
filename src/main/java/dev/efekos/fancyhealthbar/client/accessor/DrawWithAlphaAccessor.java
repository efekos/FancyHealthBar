package dev.efekos.fancyhealthbar.client.accessor;

//~if >=26.1 'GuiGraphics' -> 'GuiGraphicsExtractor' {

//? >=1.21.6
//import com.mojang.blaze3d.pipeline.RenderPipeline;
import dev.efekos.fancyhealthbar.client.compat.CompatKey;
import net.minecraft.client.gui.GuiGraphics;
//? <1.21.11
import net.minecraft.client.renderer.RenderType;
//? <1.21.6
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.function.Function;

public interface DrawWithAlphaAccessor {

    //? <1.21.2
    void drawTexture(CompatKey texture, int x, int y, int width, int height, float u, float v, int regionWidth, int regionHeight, int textureWidth, int textureHeight, float alpha);
    //? >=1.21.2 && <1.21.6 {
    /*default void drawTexture(Function<ResourceLocation, RenderType> layer, CompatKey texture, int x, int y, float u, float v, int width, int height, int regionWidth, int regionHeight, int textureWidth, int textureHeight, float alpha){
        if(!(this instanceof GuiGraphics drawContext))return;
        drawContext.blit(layer,texture.unwrap(),x,y,u,v,width,height,regionWidth,regionHeight,textureWidth,textureHeight, (Math.round(alpha * 255) << 24) | 0xFFFFFF);
    }
    *///?}
    //? >=1.21.6 {
    /*default void drawTexture(RenderPipeline layer, CompatKey texture, int x, int y, float u, float v, int width, int height, int regionWidth, int regionHeight, int textureWidth, int textureHeight, float alpha){
        if(!(this instanceof GuiGraphics drawContext))return;
        drawContext.blit(layer,texture.unwrap(),x,y,u,v,width,height,regionWidth,regionHeight,textureWidth,textureHeight, (Math.round(alpha * 255) << 24) | 0xFFFFFF);
    }
    *///?}

}

//~}