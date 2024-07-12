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

import dev.efekos.fancyhealthbar.client.config.FancyHealthBarConfig;
import dev.efekos.fancyhealthbar.client.utils.HudLocation;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class PixelObject extends PhysicalHudObject {

    private final Identifier texture;
    private final int u;
    private final int v;
    private final int size;

    public PixelObject(HudLocation location, HudLocation velocity, Identifier texture, int u, int v) {
        super(location, velocity);
        this.u = u;
        this.v = v;
        this.size = FancyHealthBarConfig.getPixelSize();
        this.texture = texture;
    }

    public PixelObject(int x, int y, HudLocation velocity, Identifier texture, int u, int v) {
        this(new HudLocation(x, y), velocity, texture, u, v);
    }

    public PixelObject(int locX, int locY, int vecX, int vecY, Identifier texture, int u, int v) {
        this(new HudLocation(locX, locY), new HudLocation(vecX, vecY), texture, u, v);
    }

    @Override
    public void draw(DrawContext context) {
        //TODO fix texture not stretching
        int x = getLocation().getX();
        int y = getLocation().getY();
        context.drawGuiTexture(texture, 9, 9, u, v, x, y, size, size);
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
