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
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

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

    private PixelWidget cp(int x, int y, int id, PixelWidget.ColorChangeAction action,Color def){
        PixelWidget widget = new PixelWidget(x, y, 20, 20, this, def, id);
        widget.setColorChangeAction(action);
        return widget;
    }

    private static Color color1 = Color.of(255,255,255);
    private static Color color2 = Color.of(255,255,255);
    private static Color color3 = Color.of(255,255,255);
    private static Color color4 = Color.of(255,255,255);
    private static Color color5 = Color.of(255,255,255);
    private static Color color6 = Color.of(255,255,255);
    private static Color color7 = Color.of(255,255,255);
    private static Color color8 = Color.of(255,255,255);
    private static Color color9 = Color.of(255,255,255);
    private static Color color10 = Color.of(255,255,255);
    private static Color color11 = Color.of(255,255,255);
    private static Color color12 = Color.of(255,255,255);
    private static Color color13 = Color.of(255,255,255);
    private static Color color14 = Color.of(255,255,255);
    private static Color color15 = Color.of(255,255,255);
    private static Color color16 = Color.of(255,255,255);
    private static Color color17 = Color.of(255,255,255);
    private static Color color18 = Color.of(255,255,255);
    private static Color color19 = Color.of(255,255,255);
    private static Color color20 = Color.of(255,255,255);
    private static Color color21 = Color.of(255,255,255);
    private static Color color22 = Color.of(255,255,255);
    private static Color color23 = Color.of(255,255,255);
    private static Color color24 = Color.of(255,255,255);
    private static Color color25 = Color.of(255,255,255);
    private static Color color26 = Color.of(255,255,255);
    private static Color color27 = Color.of(255,255,255);
    private static Color color28 = Color.of(255,255,255);
    private static Color color29 = Color.of(255,255,255);
    private static Color color30 = Color.of(255,255,255);
    private static Color color31 = Color.of(255,255,255);
    private static Color color32 = Color.of(255,255,255);
    private static Color color33 = Color.of(255,255,255);
    private static Color color34 = Color.of(255,255,255);

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
    private TextFieldWidget colorInput;
    private PixelWidget selectedPixel;

    public PixelWidget getSelectedPixel() {
        return selectedPixel;
    }

    public void setSelectedPixel(PixelWidget selectedPixel) {
        this.selectedPixel = selectedPixel;
    }

    @Override
    protected void init() {
        super.init();

        int x = width/2;
        int y = height/2;

        //1st row
        pixel1 = cp(x-50,y-80,1,c -> color1 = c,color1);
        pixel2 = cp(x-30,y-80,2,c -> color2 = c,color2);
        pixel3 = cp(x+10,y-80,3,c -> color3 = c,color3);
        pixel4 = cp(x+30,y-80,4,c -> color4 = c,color4);

        //2nd row
        pixel5 = cp(x-70,y-60,5,c -> color5 = c,color5);
        pixel6 = cp(x-50,y-60,6,c -> color6 = c,color6);
        pixel7 = cp(x-30,y-60,7,c -> color7 = c,color7);
        pixel8 = cp(x-10,y-60,8,c -> color8 = c,color8);
        pixel9 = cp(x+10,y-60,9,c -> color9 = c,color9);
        pixel10 = cp(x+30,y-60,10,c -> color10 = c,color10);
        pixel11 = cp(x+50,y-60,11,c -> color11 = c,color11);

        //3rd row
        pixel12 = cp(x-70,y-40,12,c -> color12 = c,color12);
        pixel13 = cp(x-50,y-40,13,c -> color13 = c,color13);
        pixel14 = cp(x-30,y-40,14,c -> color14 = c,color14);
        pixel15 = cp(x-10,y-40,15,c -> color15 = c,color15);
        pixel16 = cp(x+10,y-40,16,c -> color16 = c,color16);
        pixel17 = cp(x+30,y-40,17,c -> color17 = c,color17);
        pixel18 = cp(x+50,y-40,18,c -> color18 = c,color18);

        //4th row
        pixel19 = cp(x-70,y-20,19,c -> color19 = c,color19);
        pixel20 = cp(x-50,y-20,20,c -> color20 = c,color20);
        pixel21 = cp(x-30,y-20,21,c -> color21 = c,color21);
        pixel22 = cp(x-10,y-20,22,c -> color22 = c,color22);
        pixel23 = cp(x+10,y-20,23,c -> color23 = c,color23);
        pixel24 = cp(x+30,y-20,24,c -> color24 = c,color24);
        pixel25 = cp(x+50,y-20,25,c -> color25 = c,color25);

        //5th row
        pixel26 = cp(x-50,y,26,c -> color26 = c,color26);
        pixel27 = cp(x-30,y,27,c -> color27 = c,color27);
        pixel28 = cp(x-10,y,28,c -> color28 = c,color28);
        pixel29 = cp(x+10,y,29,c -> color29 = c,color29);
        pixel30 = cp(x+30,y,30,c -> color30 = c,color30);

        //6th row
        pixel31 = cp(x-30,y+20,31,c -> color31 = c,color31);
        pixel32 = cp(x-10,y+20,32,c -> color32 = c,color32);
        pixel33 = cp(x+10,y+20,33,c -> color33 = c,color33);

        //7th row
        pixel34 = cp(x-10,y+40,34,c -> color34 = c,color34);

        colorInput = new TextFieldWidget(client.textRenderer,x-100,y+90,200,20,Text.literal("#ffffff"));
        colorInput.setChangedListener(s -> {
            if(HEX_PATTERN.matcher(s).matches()&&getSelectedPixel() !=null) getSelectedPixel().setColor(HexToColor(s));
        });
        colorInput.setMaxLength(7);

        for (PixelWidget widget : Arrays.asList(pixel1, pixel2, pixel3, pixel4, pixel5, pixel6, pixel7, pixel8, pixel9, pixel10, pixel11, pixel12, pixel13, pixel14, pixel15, pixel16, pixel17, pixel18, pixel19, pixel20, pixel21, pixel22
                , pixel23, pixel24, pixel25, pixel26, pixel27, pixel28, pixel29, pixel30, pixel31, pixel32, pixel33, pixel34)) addDrawableChild(widget);

        addDrawableChild(colorInput);
    }

    public static Color HexToColor(String hex)
    {
        int hexColor = Integer.decode(hex.replace("#", "0x"));

        int r = (hexColor & 0xFF0000) >> 16;
        int g = (hexColor & 0xFF00) >> 8;
        int b = (hexColor & 0xFF);

        return new Color(r, g, b);
    }

    public static final Pattern HEX_PATTERN = Pattern.compile("^#[a-fA-F0-9]{6}$");

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        PixelWidget pix = getSelectedPixel();
        if(pix!=null) {
            context.drawGuiTexture(PixelWidget.FOCUS, pix.getX()-1,pix.getY()-1,pix.getWidth()+2,pix.getHeight()+2);

            int x = width/2;
            int y = height/2;

            String s = "Pixel #" + pix.getId();
            context.drawText(client.textRenderer,Text.literal(s),x-(textRenderer.getWidth(s)/2),y+80,-1,true);
        }

    }
}
