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

package dev.efekos.fancyhealthbar.client.screen;

import dev.efekos.fancyhealthbar.client.screen.widget.PixelWidget;
import dev.efekos.fancyhealthbar.client.utils.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class HeartEditorScreen extends Screen {

    public HeartEditorScreen() {
        super(Text.literal("Heart Editor"));
    }

    @Override
    public Optional<Element> hoveredElement(double mouseX, double mouseY) {
        return super.hoveredElement(mouseX, mouseY);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    private PixelWidget cp(int x,int y){
        return new PixelWidget(x,y,20,20,this,Color.of(255,255,255));
    }

    private PixelWidget pixel1;
    private PixelWidget pixel2;
    private PixelWidget pixel3;
    private PixelWidget pixel4;
    private PixelWidget pixel5;
    private PixelWidget pixel6;
    private PixelWidget pixel7;
    private PixelWidget pixel8;
    private PixelWidget pixel9;
    private PixelWidget pixel10;
    private PixelWidget pixel11;
    private PixelWidget pixel12;
    private PixelWidget pixel13;
    private PixelWidget pixel14;
    private PixelWidget pixel15;
    private PixelWidget pixel16;
    private PixelWidget pixel17;
    private PixelWidget pixel18;
    private PixelWidget pixel19;
    private PixelWidget pixel20;
    private PixelWidget pixel21;
    private PixelWidget pixel22;
    private PixelWidget pixel23;
    private PixelWidget pixel24;
    private PixelWidget pixel25;
    private PixelWidget pixel26;
    private PixelWidget pixel27;
    private PixelWidget pixel28;
    private PixelWidget pixel29;
    private PixelWidget pixel30;
    private PixelWidget pixel31;
    private PixelWidget pixel32;
    private PixelWidget pixel33;
    private PixelWidget pixel34;

    @Override
    protected void init() {
        super.init();

        int x = width/2;
        int y = height/2;

        //1st row
        pixel1 = cp(x-50,y-80);
        pixel2 = cp(x-30,y-80);
        pixel3 = cp(x+10,y-80);
        pixel4 = cp(x+30,y-80);

        //2nd row
        pixel5 = cp(x-70,y-60);
        pixel6 = cp(x-50,y-60);
        pixel7 = cp(x-30,y-60);
        pixel8 = cp(x-10,y-60);
        pixel9 = cp(x+10,y-60);
        pixel10 = cp(x+30,y-60);
        pixel11 = cp(x+50,y-60);

        //3rd row
        pixel12 = cp(x-70,y-40);
        pixel13 = cp(x-50,y-40);
        pixel14 = cp(x-30,y-40);
        pixel15 = cp(x-10,y-40);
        pixel16 = cp(x+10,y-40);
        pixel17 = cp(x+30,y-40);
        pixel18 = cp(x+50,y-40);

        //4th row
        pixel19 = cp(x-70,y-20);
        pixel20 = cp(x-50,y-20);
        pixel21 = cp(x-30,y-20);
        pixel22 = cp(x-10,y-20);
        pixel23 = cp(x+10,y-20);
        pixel24 = cp(x+30,y-20);
        pixel25 = cp(x+50,y-20);

        //5th row
        pixel26 = cp(x-50,y);
        pixel27 = cp(x-30,y);
        pixel28 = cp(x-10,y);
        pixel29 = cp(x+10,y);
        pixel30 = cp(x+30,y);

        //6th row
        pixel31 = cp(x-30,y+20);
        pixel32 = cp(x-10,y+20);
        pixel33 = cp(x+10,y+20);

        //7th row
        pixel34 = cp(x-10,y+40);

        for (PixelWidget widget : Arrays.asList(pixel1, pixel2, pixel3, pixel4, pixel5, pixel6, pixel7, pixel8, pixel9, pixel10, pixel11, pixel12, pixel13, pixel14, pixel15, pixel16, pixel17, pixel18, pixel19, pixel20, pixel21, pixel22
                , pixel23, pixel24, pixel25, pixel26, pixel27, pixel28, pixel29, pixel30, pixel31, pixel32, pixel33, pixel34)) addDrawableChild(widget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        Element elm = getFocused();
        if(elm instanceof PixelWidget pix) {
            context.drawGuiTexture(PixelWidget.FOCUS, pix.getX()-1,pix.getY()-1,pix.getWidth()+2,pix.getHeight()+2);

            int x = width/2;
            int y = height/2;

            context.drawText(client.textRenderer,Text.literal("Pixel #1"),x-(textRenderer.getWidth("Pixel #1")/2),y-15,-1,true);
        }

    }
}
