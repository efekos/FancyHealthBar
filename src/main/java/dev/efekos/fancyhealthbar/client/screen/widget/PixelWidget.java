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

package dev.efekos.fancyhealthbar.client.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.efekos.fancyhealthbar.client.FancyHealthBarClient;
import dev.efekos.fancyhealthbar.client.object.PixelObject;
import dev.efekos.fancyhealthbar.client.screen.HeartEditorScreen;
import dev.efekos.fancyhealthbar.client.utils.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PixelWidget extends ClickableWidget {

    private final HeartEditorScreen parent;
    private Color color;
    public static final Identifier FOCUS = Identifier.of(FancyHealthBarClient.MOD_ID,"widgets/pixel_hover");
    private final int id;

    public PixelWidget(int x, int y, int width, int height, HeartEditorScreen parent, Color color, int id) {
        super(x, y, width, height, Text.empty());
        this.parent = parent;
        this.color = color;
        this.id = id;
    }

    public Screen getParent() {
        return parent;
    }

    public int getId() {
        return id;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        super.onClick(mouseX, mouseY);
        parent.setSelectedPixel(this);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderSystem.setShaderColor(color.getR()/255f,color.getG()/255f,color.getB()/255f,1f);
        context.drawGuiTexture(PixelObject.TEXTURE_ID,getX(),getY(),getWidth(),getHeight());
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
    }

    public Color getColor() {
        return color;
    }

    private ColorChangeAction colorChangeAction = color1 -> {};

    public ColorChangeAction getColorChangeAction() {
        return colorChangeAction;
    }

    public void setColorChangeAction(ColorChangeAction colorChangeAction) {
        this.colorChangeAction = colorChangeAction;
    }

    public void setColor(Color color) {
        if(!color.equals(this.color)) colorChangeAction.onColorChange(color);
        this.color = color;
    }

    @FunctionalInterface
    public interface ColorChangeAction {
        void onColorChange(Color color);
    }
}
