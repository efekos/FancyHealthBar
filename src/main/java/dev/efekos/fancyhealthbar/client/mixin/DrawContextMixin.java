package dev.efekos.fancyhealthbar.client.mixin;

import dev.efekos.fancyhealthbar.client.accessor.DrawWithAlphaAccessor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Function;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin implements DrawWithAlphaAccessor {

    //? <1.21.2 {
    
    @Shadow
    abstract void drawTexturedQuad(Identifier par1, int par2, int par3, int par4, int par5, int par6, float par7, float par8, float par9, float par10, float par11, float par12, float par13, float par14);

    @Unique
    public void drawTexture(Identifier texture, int x, int y, int width, int height, float u, float v, int regionWidth, int regionHeight, int textureWidth, int textureHeight,float alpha) {
        this.drawTexture(texture, x, x + width, y, y + height, 0, regionWidth, regionHeight, u, v, textureWidth, textureHeight,alpha);
    }

    @Unique
    void drawTexture(Identifier texture, int x1, int x2, int y1, int y2, int z, int regionWidth, int regionHeight, float u, float v, int textureWidth, int textureHeight,float alpha) {
        this.drawTexturedQuad(texture, x1, x2, y1, y2, z, (u + 0.0F) / (float)textureWidth, (u + (float)regionWidth) / (float)textureWidth, (v + 0.0F) / (float)textureHeight, (v + (float)regionHeight) / (float)textureHeight,1f,1f,1f,alpha);
    }
    //?}

}