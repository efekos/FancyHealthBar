/*
 * MIT License
 *
 * Copyright (c) 2024 efekos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.efekos.fancyhealthbar.client.object;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.efekos.fancyhealthbar.client.FancyHealthBarClient;
import dev.efekos.fancyhealthbar.client.config.FancyHealthBarConfig;
import dev.efekos.fancyhealthbar.client.utils.Color;
import dev.efekos.fancyhealthbar.client.utils.HudLocation;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class PixelObject extends PhysicalHudObject {

    private Color color;
    private int size;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public PixelObject(HudLocation location, HudLocation velocity, Color color) {
        super(location, velocity);
        this.color = color;
        this.size = FancyHealthBarConfig.getPixelSize();
    }

    public PixelObject(int locX, int locY, HudLocation velocity, Color color) {
        this(locX, locY, velocity.getX(), velocity.getY(), color);
    }

    public PixelObject(int locX, int locY, int vecX, int vecY, Color color) {
        this(new HudLocation(locX, locY), new HudLocation(vecX, vecY), color);
    }

    public static final Identifier TEXTURE_ID = new Identifier(FancyHealthBarClient.MOD_ID, "pixel");

    @Override
    public void draw(DrawContext context) {
        RenderSystem.setShaderColor(color.getR() / 255f, color.getG() / 255f, color.getB() / 255f, 1f);

        context.drawGuiTexture(TEXTURE_ID, getLocation().getX(), getLocation().getY(), size, size);

        if (getVelocity().getY() < -10 && size > 1)
            context.drawGuiTexture(TEXTURE_ID, getLocation().getX(), getLocation().getY() - 2, size, 1);
        if (getVelocity().getY() < -12 && size > 2)
            context.drawGuiTexture(TEXTURE_ID, getLocation().getX(), getLocation().getY() - 4, size, 1);
        if (getVelocity().getY() < -14 && size > 3)
            context.drawGuiTexture(TEXTURE_ID, getLocation().getX(), getLocation().getY() - 8, size, 1);
        if (getVelocity().getY() < -16 && size > 4)
            context.drawGuiTexture(TEXTURE_ID, getLocation().getX(), getLocation().getY() - 12, size, 1);
        if (getVelocity().getY() < -18 && size > 5)
            context.drawGuiTexture(TEXTURE_ID, getLocation().getX(), getLocation().getY() - 16, size, 1);

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    @Override
    public int getGravity() {
        return 1;
    }

    @Override
    public double getSlipperiness() {
        return 0.99;
    }
}
