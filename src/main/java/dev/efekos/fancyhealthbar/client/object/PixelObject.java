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
import dev.efekos.fancyhealthbar.client.FancyHealthBarConfig;
import dev.efekos.fancyhealthbar.client.utils.HudLocation;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class PixelObject extends PhysicalHudObject {

    private final Identifier texture;
    private final int u;
    private final int v;
    private final int size;

    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }

    public PixelObject(HudLocation location, HudLocation velocity, Identifier texture, int u, int v) {
        super(location, velocity);
        this.u = u;
        this.v = v;
        this.size = randomSize();
        this.texture = texture;
    }

    private static int randomSize() {
        return FancyHealthBarConfig.getMinimumPixelSize() + (int) (Math.random() * (FancyHealthBarConfig.getMaximumPixelSize() - FancyHealthBarConfig.getMinimumPixelSize()));
    }

    public PixelObject(int x, int y, HudLocation velocity, Identifier texture, int u, int v) {
        this(new HudLocation(x, y), velocity, texture, u, v);
    }

    public PixelObject(int locX, int locY, int vecX, int vecY, Identifier texture, int u, int v) {
        this(new HudLocation(locX, locY), new HudLocation(vecX, vecY), texture, u, v);
    }

    @Override
    public void draw(DrawContext context) {
        int x = getLocation().getX();
        int y = getLocation().getY();
        context.drawRepeatingTexture(texture,x,y,size,size,u,v,1,1);
    }

    void drawTexture(DrawContext context,Identifier texture, int x1, int x2, int y1, int y2, int z, int regionWidth, int regionHeight, float u, float v, int textureWidth, int textureHeight) {
        this.drawTexturedQuad(context,texture, x1, x2, y1, y2, z, (u + 0.0f) / (float)textureWidth, (u + (float)regionWidth) / (float)textureWidth, (v + 0.0f) / (float)textureHeight, (v + (float)regionHeight) / (float)textureHeight);
    }

    void drawTexturedQuad(DrawContext context,Identifier texture, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2) {
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        Matrix4f matrix4f = context.getMatrices().peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix4f, x1, y1, z).texture(u1, v1);
        bufferBuilder.vertex(matrix4f, x1, y2, z).texture(u1, v2);
        bufferBuilder.vertex(matrix4f, x2, y2, z).texture(u2, v2);
        bufferBuilder.vertex(matrix4f, x2, y1, z).texture(u2, v1);
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }


    @Override
    public int getGravity() {
        return FancyHealthBarConfig.getGravity();
    }

    @Override
    public double getSlipperiness() {
        return 0.99 * FancyHealthBarConfig.getSlipperiness();
    }
}
