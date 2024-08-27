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

package dev.efekos.fancyhealthbar.client.hud;

import dev.efekos.fancyhealthbar.client.object.HudObject;
import dev.efekos.fancyhealthbar.client.object.PixelObject;
import dev.efekos.fancyhealthbar.client.utils.VelocityProvider;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HeartGenerator {

    private final Identifier texture;
    private final int u;
    private final int v;

    public HeartGenerator(int u, int v) {
        this.u = u;
        this.v = v;
        this.texture = new Identifier("textures/gui/icons.png");
    }

    public List<HudObject> spawnFull(int x, int y, VelocityProvider velocityProvider) {

        Random random = new Random();


        return Arrays.asList(
                //1
                new PixelObject(x + 1, y, velocityProvider.velocity(random), texture, u + 1, v),
                new PixelObject(x + 2, y, velocityProvider.velocity(random), texture, u + 2, v),
                //4
                new PixelObject(x + 4, y, velocityProvider.velocity(random), texture, u + 4, v),
                new PixelObject(x + 5, y, velocityProvider.velocity(random), texture, u + 5, v),
                //7

                new PixelObject(x, y + 1, velocityProvider.velocity(random), texture, u, v + 1),
                new PixelObject(x + 1, y + 1, velocityProvider.velocity(random), texture, u + 1, v + 1),
                new PixelObject(x + 2, y + 1, velocityProvider.velocity(random), texture, u + 2, v + 1),
                new PixelObject(x + 3, y + 1, velocityProvider.velocity(random), texture, u + 3, v + 1),
                new PixelObject(x + 4, y + 1, velocityProvider.velocity(random), texture, u + 4, v + 1),
                new PixelObject(x + 5, y + 1, velocityProvider.velocity(random), texture, u + 5, v + 1),
                new PixelObject(x + 6, y + 1, velocityProvider.velocity(random), texture, u + 6, v + 1),

                new PixelObject(x, y + 2, velocityProvider.velocity(random), texture, u, v + 2),
                new PixelObject(x + 1, y + 2, velocityProvider.velocity(random), texture, u + 1, v + 2),
                new PixelObject(x + 2, y + 2, velocityProvider.velocity(random), texture, u + 2, v + 2),
                new PixelObject(x + 3, y + 2, velocityProvider.velocity(random), texture, u + 3, v + 2),
                new PixelObject(x + 4, y + 2, velocityProvider.velocity(random), texture, u + 4, v + 2),
                new PixelObject(x + 5, y + 2, velocityProvider.velocity(random), texture, u + 5, v + 2),
                new PixelObject(x + 6, y + 2, velocityProvider.velocity(random), texture, u + 6, v + 2),

                new PixelObject(x, y + 3, velocityProvider.velocity(random), texture, u, v + 3),
                new PixelObject(x + 1, y + 3, velocityProvider.velocity(random), texture, u + 1, v + 3),
                new PixelObject(x + 2, y + 3, velocityProvider.velocity(random), texture, u + 2, v + 3),
                new PixelObject(x + 3, y + 3, velocityProvider.velocity(random), texture, u + 3, v + 3),
                new PixelObject(x + 4, y + 3, velocityProvider.velocity(random), texture, u + 4, v + 3),
                new PixelObject(x + 5, y + 3, velocityProvider.velocity(random), texture, u + 5, v + 3),
                new PixelObject(x + 6, y + 3, velocityProvider.velocity(random), texture, u + 6, v + 3),

                //1
                new PixelObject(x + 1, y + 4, velocityProvider.velocity(random), texture, u + 1, v + 4),
                new PixelObject(x + 2, y + 4, velocityProvider.velocity(random), texture, u + 2, v + 4),
                new PixelObject(x + 3, y + 4, velocityProvider.velocity(random), texture, u + 3, v + 4),
                new PixelObject(x + 4, y + 4, velocityProvider.velocity(random), texture, u + 4, v + 4),
                new PixelObject(x + 5, y + 4, velocityProvider.velocity(random), texture, u + 5, v + 4),
                //7

                //1
                //2
                new PixelObject(x + 2, y + 5, velocityProvider.velocity(random), texture, u + 2, v + 5),
                new PixelObject(x + 3, y + 5, velocityProvider.velocity(random), texture, u + 3, v + 5),
                new PixelObject(x + 4, y + 5, velocityProvider.velocity(random), texture, u + 4, v + 5),
                //6
                //7

                //1
                //2
                //3
                new PixelObject(x + 3, y + 6, velocityProvider.velocity(random), texture, u + 3, v + 6)
                //5
                //6
                //7
        );
    }

    List<HudObject> spawnEndHalf(int x, int y) {
        return new ArrayList<>(spawnFull(x, y, FancyHealthHud.HEART_VELOCITY_PROVIDER).stream().filter(hudObject -> ((PixelObject) hudObject).getU() > u+3).toList());
    }

    List<HudObject> spawnStartHalf(int x, int y) {
        return new ArrayList<>(spawnFull(x, y, FancyHealthHud.HEART_VELOCITY_PROVIDER).stream().filter(hudObject -> ((PixelObject) hudObject).getU() <= u+4).toList());
    }

}
