package dev.efekos.fancyhealthbar.client.object;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.efekos.fancyhealthbar.FancyHealthBar;
import dev.efekos.fancyhealthbar.client.utils.Color;
import dev.efekos.fancyhealthbar.client.utils.HudLocation;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class PixelObject extends PhysicalHudObject{

    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public PixelObject(HudLocation location, HudLocation velocity, Color color) {
        super(location, velocity);
        this.color = color;
    }

    public PixelObject(int locX,int locY, int vecX,int vecY, Color color) {
        super(new HudLocation(locX,locY),new HudLocation(vecX,vecY));
        this.color = color;
    }

    public static final Identifier TEXTURE_ID = new Identifier(FancyHealthBar.MOD_ID,"pixel");

    @Override
    public void draw(DrawContext context) {
        RenderSystem.setShaderColor(color.getR()/255f,color.getG()/255f,color.getB()/255f,1f);
        context.drawGuiTexture(TEXTURE_ID,getLocation().getX(),getLocation().getY(),1,1);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
    }

    @Override
    public int getGravity() {
        return 1;
    }

    @Override
    public double getSlipperiness() {
        return 0.9;
    }
}
