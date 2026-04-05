package dev.efekos.fancyhealthbar.client.mixin;
//~if >=26.1 'GuiGraphics' -> 'GuiGraphicsExtractor' {
import dev.efekos.fancyhealthbar.client.accessor.DrawWithAlphaAccessor;
import net.minecraft.client.gui.GuiGraphicsExtractor;
//? <1.21.11
//import net.minecraft.client.renderer.RenderType;
//? <1.21.11
//import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiGraphicsExtractor.class)
public abstract class DrawContextMixin implements DrawWithAlphaAccessor {

    //? <1.21.2 {
    
    /*@Shadow
    abstract void innerBlit(ResourceLocation par1, int par2, int par3, int par4, int par5, int par6, float par7, float par8, float par9, float par10, float par11, float par12, float par13, float par14);

    @Unique
    public void drawTexture(ResourceLocation texture, int x, int y, int width, int height, float u, float v, int regionWidth, int regionHeight, int textureWidth, int textureHeight, float alpha) {
        this.drawTexture(texture, x, x + width, y, y + height, 0, regionWidth, regionHeight, u, v, textureWidth, textureHeight,alpha);
    }

    @Unique
    void drawTexture(ResourceLocation texture, int x1, int x2, int y1, int y2, int z, int regionWidth, int regionHeight, float u, float v, int textureWidth, int textureHeight, float alpha) {
        this.innerBlit(texture, x1, x2, y1, y2, z, (u + 0.0F) / (float)textureWidth, (u + (float)regionWidth) / (float)textureWidth, (v + 0.0F) / (float)textureHeight, (v + (float)regionHeight) / (float)textureHeight,1f,1f,1f,alpha);
    }
    *///?}

}
//~}