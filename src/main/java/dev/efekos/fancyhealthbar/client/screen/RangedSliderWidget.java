package dev.efekos.fancyhealthbar.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.efekos.fancyhealthbar.client.FancyHealthBarClient;
import dev.efekos.fancyhealthbar.client.compat.Texture;
import dev.efekos.fancyhealthbar.client.compat.TextureNineSlice;
import net.minecraft.client.MinecraftClient;
//? >=1.21.6
/*import net.minecraft.client.gl.RenderPipelines;*/
//?>=1.21.9
/*import net.minecraft.client.gui.Click;*/
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.navigation.GuiNavigationType;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ClickableWidget;
//? <1.21.9
import net.minecraft.client.input.KeyCodes;
//?>=1.21.9
/*import net.minecraft.client.input.KeyInput;*/
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.text.NumberFormat;

public abstract class RangedSliderWidget extends ClickableWidget {
    private static final Texture TEXTURE = Texture.vanilla("widget/slider","textures/gui/widgets.png",0,46,200,20,new TextureNineSlice(3,200,20));
    private static final Texture HIGHLIGHTED_TEXTURE = Texture.vanilla("widget/slider_highlighted","textures/gui/widgets.png",0,46,200,20,new TextureNineSlice(3,200,20));;
    private static final Texture HANDLE_TEXTURE = Texture.vanilla("widget/slider_handle","textures/gui/widgets.png",0,66,200,20,new TextureNineSlice(3,200,20));
    private static final Texture HANDLE_HIGHLIGHTED_TEXTURE = Texture.vanilla("widget/slider_handle_highlighted","textures/gui/widgets.png",0,86,200,20,new TextureNineSlice(3,200,20));
    private static final Texture HANDLE_START_HIGHLIGHTED_TEXTURE = Texture.mod("widget/slider_handle_start_highlighted","textures/gui/widgets.png",69,0,5,20);
    private static final Texture HANDLE_START_TEXTURE = Texture.mod("widget/slider_handle_start","textures/gui/widgets.png",64,0,5,20);;
    private static final Texture HANDLE_END_HIGHLIGHTED_TEXTURE = Texture.mod("widget/slider_handle_end_highlighted","textures/gui/widgets.png",79,0,5,20);;
    private static final Texture HANDLE_END_TEXTURE = Texture.mod("widget/slider_handle_end","textures/gui/widgets.png",74,0,5,20);
    protected Range value;
    private boolean sliderFocused;

    public static Text rangeText(double min, double max) {
        return min==max?Text.literal(NumberFormat.getInstance().format(min)):Text.translatable("options.range",NumberFormat.getInstance().format(min),NumberFormat.getInstance().format(max));
    }

    public RangedSliderWidget(int x, int y, int width, int height, Text text, Range value) {
        super(x, y, width, height, text);
        this.value = value;
    }

    private Texture getTexture() {
        return this.isFocused() && !this.sliderFocused ? HIGHLIGHTED_TEXTURE : TEXTURE;
    }


    private Texture getHandleStartTexture() {
        return this.hovered || this.sliderFocused ? HANDLE_START_HIGHLIGHTED_TEXTURE : HANDLE_START_TEXTURE;
    }

    private Texture getHandleEndTexture() {
        return this.hovered || this.sliderFocused ? HANDLE_END_HIGHLIGHTED_TEXTURE : HANDLE_END_TEXTURE;
    }

    private Texture getHandleTexture() {
        return this.hovered || this.sliderFocused ? HANDLE_HIGHLIGHTED_TEXTURE : HANDLE_TEXTURE;
    }

    protected MutableText getNarrationMessage() {
        return Text.translatable("gui.narrate.slider", this.getMessage());
    }

    public void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.TITLE, this.getNarrationMessage());
        if (this.active) {
            if (this.isFocused()) {
                builder.put(NarrationPart.USAGE, Text.translatable("narration.slider.usage.focused"));
            } else {
                builder.put(NarrationPart.USAGE, Text.translatable("narration.slider.usage.hovered"));
            }
        }

    }

    public void /*? <1.20.3 {*/renderButton/*?} else {*//*renderWidget*//*?}*/(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        //? <1.21.5 {

        //? <1.21.2
        context.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        //?}
        getTexture().draw(context,getX(),getY(),getWidth(),getHeight());
        if(value.min==value.max){
            getHandleTexture().draw(context,getX()+(int)(this.value.min * (double)(this.width - 8)),this.getY(), 8, this.getHeight());
        } else {
            getHandleStartTexture().draw(context,this.getX() + (int)(this.value.min * (double)(this.width - 5)), this.getY(), 5, this.getHeight());
            getHandleEndTexture().draw(context, this.getX() + (int)(this.value.max * (double)(this.width - 5)), this.getY(), 5, this.getHeight());
        }
        //? <1.21.2
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.active ? 16777215 : 10526880;
        this.drawScrollableText(context, minecraftClient.textRenderer, 2, i | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    public void setFocused(boolean focused) {
        super.setFocused(focused);
        if (!focused) {
            this.sliderFocused = false;
        } else {
            GuiNavigationType guiNavigationType = MinecraftClient.getInstance().getNavigationType();
            if (guiNavigationType == GuiNavigationType.MOUSE || guiNavigationType == GuiNavigationType.KEYBOARD_TAB) {
                this.sliderFocused = true;
            }

        }
    }


    //? >=1.21.9 {
    /*public boolean keyPressed(KeyInput input) {
        if (input.isEnterOrSpace()) {
            this.sliderFocused = !this.sliderFocused;
            return true;
        } else {
            if (this.sliderFocused) {
                boolean bl = input.isLeft();
                boolean bl2 = input.isRight();
                if (bl || bl2) {
                    float f = bl ? -1.0F : 1.0F;
                    this.setValue(this.value.min + (double)(f / (float)(this.width - 8)));
                    return true;
                }
            }

            return false;
        }
    }

    *///?} else {
    
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (KeyCodes.isToggle(keyCode)) {
            this.sliderFocused = !this.sliderFocused;
            return true;
        } else {
            if (this.sliderFocused) {
                boolean bl = keyCode == 263;
                if (bl || keyCode == 262) {
                    float f = bl ? -1.0F : 1.0F;
                    this.setValue(this.value.min + (double)(f / (float)(this.width - 8)));
                    return true;
                }
            }

            return false;
        }
    }
    //?}

    private void setValueFromMouse(double mouseX) {
        this.setValue((mouseX - (double)(this.getX() + 4)) / (double)(this.width - 8));
    }

    private void setMaxValueFromMouse(double mouseX) {
        this.setMaxValue((mouseX - (double)(this.getX() + 4)) / (double)(this.width - 8));
    }

    private void setMaxValue(double value) {
        double d = this.value.max;
        this.value.setMax(MathHelper.clamp(value, this.value.min, 1.0F));
        if (d != this.value.max) {
            this.applyValue();
        }

        this.updateMessage();
    }


    private void setValue(double value) {
        double d = this.value.min;
        this.value.set(MathHelper.clamp(value, 0.0F, 1.0F));
        if (d != this.value.min) {
            this.applyValue();
        }

        this.updateMessage();
    }


    public void onRightClick(double mouseX, double mouseY) {
        this.setMaxValueFromMouse(mouseX);
    }

    public void onClick(double mouseX, double mouseY) {
        this.setValueFromMouse(mouseX);
    }

    //? <1.21.9 {
    protected void onRightDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        this.setMaxValueFromMouse(mouseX);
        super.onDrag(mouseX, mouseY, deltaX, deltaY);
    }


    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        this.setValueFromMouse(mouseX);
        super.onDrag(mouseX, mouseY, deltaX, deltaY);
    }
    //?} else {

    /*protected void onRightDrag(Click click, double offsetX, double offsetY) {
        setMaxValueFromMouse(click.x());
        super.onDrag(click, offsetX, offsetY);
    }

    @Override
    protected void onDrag(Click click, double offsetX, double offsetY) {
        setValueFromMouse(click.x());
        super.onDrag(click, offsetX, offsetY);
    }

    *///?}



    public void onRelease(double mouseX, double mouseY) {
        super.playDownSound(MinecraftClient.getInstance().getSoundManager());
    }

    public void playDownSound(SoundManager soundManager) {
    }

    protected abstract void updateMessage();
    protected abstract void applyValue();

    public static class Range implements dev.efekos.fancyhealthbar.client.options.Range<Double> {
        double min;
        double max;

        @Override
        public Double random() {
            return MathHelper.clampedLerp(min, max, Math.random());
        }

        @Override
        public Double getMin() {
            return min;
        }

        @Override
        public Double getMax() {
            return max;
        }

        @Override
        public void setMin(Double min) {
            this.min = min;
        }

        @Override
        public void setMax(Double max) {
            this.max = max;
        }

        public Range(double min, double max) {
            this.min = min;
            this.max = max;
        }

        public void set(double v) {
            min = v;
            max = v;
        }

    }


    protected boolean isValidClickButton(int button) {
        return button==0||button==1;
    }

    //?>=1.21.9{
    /*public boolean mouseClicked(Click click, boolean doubled) {
        return mouseClicked(click.x(),click.y(),click.button());
    }*/
    //?}

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.active && this.visible) {
            if (this.isValidClickButton(button)) {
                //? >=1.21.4 {
                /*boolean bl = super.isMouseOver(mouseX, mouseY);
                *///?} else {
                boolean bl = this.clicked(mouseX, mouseY);
                //?}
                if (bl) {
                    this.playDownSound(MinecraftClient.getInstance().getSoundManager());
                    if(button==0) this.onClick(mouseX, mouseY);
                    else this.onRightClick(mouseX,mouseY);
                    return true;
                }
            }

        }
        return false;
    }

    //? <1.21.9 {
    
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.isValidClickButton(button)) {
            if(button==0) this.onDrag(mouseX, mouseY, deltaX, deltaY);
            else this.onRightDrag(mouseX,mouseY,deltaX,deltaY);
            return true;
        } else {
            return false;
        }
    }
    //?} else {
    /*public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        if(click.button()==1||click.button()==0){
            if(click.button()==0)onDrag(click,offsetX,offsetY);
            else onRightDrag(click,offsetX,offsetY);
            return true;
        }
        return false;
    }
    *///?}

}
