/*
 * MIT License
 *
 * Copyright (c) 2025 efekos
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

    public HeartGenerator(String texture) {
        this.texture = Identifier.ofVanilla("textures/gui/sprites/hud/heart/"+texture+".png");
    }

    public List<HudObject> spawnFull(int x, int y, VelocityProvider velocityProvider) {

        Random random = new Random();


        return Arrays.asList(
                //1
                new PixelObject(x + 1, y, velocityProvider.velocity(random), texture, 1, 0),
                new PixelObject(x + 2, y, velocityProvider.velocity(random), texture, 2, 0),
                //4
                new PixelObject(x + 4, y, velocityProvider.velocity(random), texture, 4, 0),
                new PixelObject(x + 5, y, velocityProvider.velocity(random), texture, 5, 0),
                //7

                new PixelObject(x, y + 1, velocityProvider.velocity(random), texture, 0, 1),
                new PixelObject(x + 1, y + 1, velocityProvider.velocity(random), texture, 1, 1),
                new PixelObject(x + 2, y + 1, velocityProvider.velocity(random), texture, 2, 1),
                new PixelObject(x + 3, y + 1, velocityProvider.velocity(random), texture, 3, 1),
                new PixelObject(x + 4, y + 1, velocityProvider.velocity(random), texture, 4, 1),
                new PixelObject(x + 5, y + 1, velocityProvider.velocity(random), texture, 5, 1),
                new PixelObject(x + 6, y + 1, velocityProvider.velocity(random), texture, 6, 1),

                new PixelObject(x, y + 2, velocityProvider.velocity(random), texture, 0, 2),
                new PixelObject(x + 1, y + 2, velocityProvider.velocity(random), texture, 1, 2),
                new PixelObject(x + 2, y + 2, velocityProvider.velocity(random), texture, 2, 2),
                new PixelObject(x + 3, y + 2, velocityProvider.velocity(random), texture, 3, 2),
                new PixelObject(x + 4, y + 2, velocityProvider.velocity(random), texture, 4, 2),
                new PixelObject(x + 5, y + 2, velocityProvider.velocity(random), texture, 5, 2),
                new PixelObject(x + 6, y + 2, velocityProvider.velocity(random), texture, 6, 2),

                new PixelObject(x, y + 3, velocityProvider.velocity(random), texture, 0, 3),
                new PixelObject(x + 1, y + 3, velocityProvider.velocity(random), texture, 1, 3),
                new PixelObject(x + 2, y + 3, velocityProvider.velocity(random), texture, 2, 3),
                new PixelObject(x + 3, y + 3, velocityProvider.velocity(random), texture, 3, 3),
                new PixelObject(x + 4, y + 3, velocityProvider.velocity(random), texture, 4, 3),
                new PixelObject(x + 5, y + 3, velocityProvider.velocity(random), texture, 5, 3),
                new PixelObject(x + 6, y + 3, velocityProvider.velocity(random), texture, 6, 3),

                //1
                new PixelObject(x + 1, y + 4, velocityProvider.velocity(random), texture, 1, 4),
                new PixelObject(x + 2, y + 4, velocityProvider.velocity(random), texture, 2, 4),
                new PixelObject(x + 3, y + 4, velocityProvider.velocity(random), texture, 3, 4),
                new PixelObject(x + 4, y + 4, velocityProvider.velocity(random), texture, 4, 4),
                new PixelObject(x + 5, y + 4, velocityProvider.velocity(random), texture, 5, 4),
                //7

                //1
                //2
                new PixelObject(x + 2, y + 5, velocityProvider.velocity(random), texture, 2, 5),
                new PixelObject(x + 3, y + 5, velocityProvider.velocity(random), texture, 3, 5),
                new PixelObject(x + 4, y + 5, velocityProvider.velocity(random), texture, 4, 5),
                //6
                //7

                //1
                //2
                //3
                new PixelObject(x + 3, y + 6, velocityProvider.velocity(random), texture, 3, 6)
                //5
                //6
                //7
        );
    }

    List<HudObject> spawnEndHalf(int x, int y) {
        return new ArrayList<>(spawnFull(x, y, FancyHealthHud.HEART_VELOCITY_PROVIDER).stream().filter(hudObject -> ((PixelObject) hudObject).getU() > 4).toList());
    }

    List<HudObject> spawnStartHalf(int x, int y) {
        return new ArrayList<>(spawnFull(x, y, FancyHealthHud.HEART_VELOCITY_PROVIDER).stream().filter(hudObject -> ((PixelObject) hudObject).getU() <= 4).toList());
    }

}
