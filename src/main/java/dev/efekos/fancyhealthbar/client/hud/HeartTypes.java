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

import dev.efekos.fancyhealthbar.client.hud.heart.*;
import dev.efekos.fancyhealthbar.client.utils.HeartSpawner;

public class HeartTypes {

    public static final NormalHeartType NORMAL = new NormalHeartType();
    public static final NormalHardcoreHeartType HARDCORE_NORMAL = new NormalHardcoreHeartType();
    public static final PoisonHeartType POISON = new PoisonHeartType();
    public static final PoisonHardcoreHeartType HARDCORE_POISON = new PoisonHardcoreHeartType();
    public static final FrozenHeartType FROZEN = new FrozenHeartType();
    public static final FrozenHardcoreHeartType HARDCORE_FROZEN = new FrozenHardcoreHeartType();
    public static final WitherHeartType WITHER = new WitherHeartType();
    public static final WitherHardcoreHeartType HARDCORE_WITHER = new WitherHardcoreHeartType();


    public static HeartSpawner get(boolean hardcore, boolean poison, boolean frozen, boolean wither) {
        if (hardcore) {
            return poison ? HARDCORE_POISON : (frozen ? HARDCORE_FROZEN : (wither ? HARDCORE_WITHER : HARDCORE_NORMAL));
        } else {
            return poison ? POISON : (frozen ? FROZEN : (wither ? WITHER : NORMAL));
        }
    }

}
