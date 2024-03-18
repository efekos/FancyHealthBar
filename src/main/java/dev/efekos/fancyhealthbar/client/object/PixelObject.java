package dev.efekos.fancyhealthbar.client.object;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.efekos.fancyhealthbar.FancyHealthBar;
import dev.efekos.fancyhealthbar.client.utils.Color;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class PixelObject extends PhysicalHudObject{

    private Color color;

    public static final Identifier TEXTURE_ID = new Identifier(FancyHealthBar.MOD_ID,"sprite/pixel");

    @Override
    public void draw(DrawContext context) {
        RenderSystem.setShaderColor(color.getR()/255f,color.getG()/255f,color.getB()/255f,1f);
        context.drawTexture(TEXTURE_ID,getLocation().getX(),getLocation().getY(),0,0,1,1);
    }

    @Override
    public int getGravity() {
        return 1;
    }

    @Override
    public double getSlipperiness() {
        return 0.5;
    }
}
