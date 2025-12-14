package dev.efekos.fancyhealthbar.client.compat;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.efekos.fancyhealthbar.client.FancyHealthBarClient;
//? >=1.21.6
/*import net.minecraft.client.gl.RenderPipelines;*/
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public record Texture(Identifier baseId, Identifier atlasId, SquareRegion atlasPosition, TextureNineSlice nineSlice) {


    public static Texture mod(String base,String atlas,int u,int v,int size,TextureNineSlice nineSlice) {
        return new Texture(FancyHealthBarClient.id(base),FancyHealthBarClient.id(atlas),new SquareRegion(u,u+size,v,v+size),nineSlice);
    }

    public static Texture mod(String base,String atlas,int u,int v,int width,int height){
        return new Texture(FancyHealthBarClient.id(base),FancyHealthBarClient.id(atlas),new SquareRegion(u,u+width,v,v+height),null);
    }

    public static Texture mod(String base,String atlas,int u,int v,int size){
        return new Texture(FancyHealthBarClient.id(base),FancyHealthBarClient.id(atlas),new SquareRegion(u,u+size,v,v+size),null);
    }

    public static Texture vanilla(String base,String atlas,int u,int v,int size,TextureNineSlice nineSlice) {
        return new Texture(FancyHealthBarClient.vanillaId(base),FancyHealthBarClient.vanillaId(atlas),new SquareRegion(u,u+size,v,v+size),nineSlice);
    }

    public static Texture vanilla(String base,String atlas,int u,int v,int width,int height,TextureNineSlice nineSlice) {
        return new Texture(FancyHealthBarClient.vanillaId(base),FancyHealthBarClient.vanillaId(atlas),new SquareRegion(u,u+width,v,v+height),nineSlice);
    }
    public static Texture vanilla(String base,String atlas,int u,int v,int size){
        return new Texture(FancyHealthBarClient.vanillaId(base),FancyHealthBarClient.vanillaId(atlas),new SquareRegion(u,u+size,v,v+size),null);
    }

    public void draw(DrawContext context,int x,int y,int width,int height,float alpha){
        /*? <1.21.5 {*/
        RenderSystem.setShaderColor(1,1,1,alpha);
        RenderSystem.enableBlend();
        draw(context,x,y,width,height);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.disableBlend();
        /*?}*/
        /*? >=1.21.6 {*/
        /*context.drawGuiTexture(/^? >=1.21.6 {^//^RenderPipelines.GUI_TEXTURED,^//^?} else if >=1.21.2 {^//^RenderLayer::getGuiTextured,^//^?}^/ baseId,x,y,width,height,alpha);
        *//*?}*/
    }

    public void draw(DrawContext context,int x, int y,int width,int height){
        //? <1.20.2 {
        /*if(nineSlice==null)
            context.drawTexture(atlasId,x,y,atlasPosition.u1(),atlasPosition.v1(),width,height);
        else {
            int br = nineSlice.borderLength();
            if(width<=br||height<=br)return;
            context.drawNineSlicedTexture(atlasId,x,y,width,height, br, nineSlice.width(),nineSlice.height(), atlasPosition.u1(), atlasPosition.v1());
        }
        *//*?} else {*/
        context.drawGuiTexture(/*? >=1.21.6 {*//*RenderPipelines.GUI_TEXTURED,*//*?} else if >=1.21.2 {*//*RenderLayer::getGuiTextured,*//*?}*/ baseId,x,y,width,height);
        /*?}*/
    }

}