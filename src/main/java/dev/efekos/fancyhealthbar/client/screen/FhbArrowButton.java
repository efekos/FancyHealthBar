package dev.efekos.fancyhealthbar.client.screen;

import dev.efekos.fancyhealthbar.client.compat.BlinkingTextures;
import dev.efekos.fancyhealthbar.client.compat.Texture;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class FhbArrowButton extends ButtonWidget {
    protected final BlinkingTextures textures;

    public FhbArrowButton(int x, int y, int width, int height, BlinkingTextures textures, ButtonWidget.PressAction pressAction) {
        this(x, y, width, height, textures, pressAction, ScreenTexts.EMPTY);
    }

    public FhbArrowButton(int x, int y, int width, int height, BlinkingTextures textures, ButtonWidget.PressAction pressAction, Text text) {
        super(x, y, width, height, text, pressAction, DEFAULT_NARRATION_SUPPLIER);
        this.textures = textures;
    }

    public FhbArrowButton(int width, int height, BlinkingTextures textures, ButtonWidget.PressAction pressAction, Text text) {
        this(0, 0, width, height, textures, pressAction, text);
    }

    public void /*? <1.20.3 {*/renderButton/*?} else {*//*renderWidget*//*?}*/(DrawContext context, int mouseX, int mouseY, float delta) {
        Texture texture = this.textures.get(this.isSelected());
        texture.draw(context,this.getX(),this.getY(),this.width,this.height);
    }

}