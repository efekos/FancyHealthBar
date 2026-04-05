package dev.efekos.fancyhealthbar.client.screen;

import dev.efekos.fancyhealthbar.client.compat.BlinkingTextures;
import dev.efekos.fancyhealthbar.client.compat.Texture;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class FhbArrowButton extends Button {
    protected final BlinkingTextures textures;

    public FhbArrowButton(int x, int y, int width, int height, BlinkingTextures textures, Button.OnPress pressAction) {
        this(x, y, width, height, textures, pressAction, CommonComponents.EMPTY);
    }

    public FhbArrowButton(int x, int y, int width, int height, BlinkingTextures textures, Button.OnPress pressAction, net.minecraft.network.chat.Component text) {
        super(x, y, width, height, text, pressAction, DEFAULT_NARRATION);
        this.textures = textures;
    }

    public FhbArrowButton(int width, int height, BlinkingTextures textures, Button.OnPress pressAction, net.minecraft.network.chat.Component text) {
        this(0, 0, width, height, textures, pressAction, text);
    }

    public void renderContents(GuiGraphics context, int mouseX, int mouseY, float delta) {
        Texture texture = this.textures.get(this.isHovered());
        texture.draw(context,this.getX(),this.getY(),this.width,this.height);
    }

}