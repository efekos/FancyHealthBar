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

public class HeartTypes {

    public static final HeartGenerator NORMAL = new HeartGenerator("full");
    public static final HeartGenerator HARDCORE_NORMAL = new HeartGenerator("hardcore_full");
    public static final HeartGenerator POISON = new HeartGenerator("poisoned_full");
    public static final HeartGenerator HARDCORE_POISON = new HeartGenerator("hardcore_poisoned_full");
    public static final HeartGenerator FROZEN = new HeartGenerator("frozen_full");
    public static final HeartGenerator HARDCORE_FROZEN = new HeartGenerator("hardcore_frozen_full");
    public static final HeartGenerator WITHER = new HeartGenerator("withered_full");
    public static final HeartGenerator HARDCORE_WITHER = new HeartGenerator("hardcore_withered_full");


    public static HeartGenerator get(boolean hardcore, boolean poison, boolean frozen, boolean wither) {
        if (hardcore) {
            return poison ? HARDCORE_POISON : (frozen ? HARDCORE_FROZEN : (wither ? HARDCORE_WITHER : HARDCORE_NORMAL));
        } else {
            return poison ? POISON : (frozen ? FROZEN : (wither ? WITHER : NORMAL));
        }
    }

}
