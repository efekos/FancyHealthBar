package dev.efekos.fancyhealthbar.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
//? >=1.21.9
/*import net.minecraft.client.input.InputWithModifiers;*/
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FhbToggleWidget extends Button {

    private final Consumer<Boolean> valueConsumer;
    private boolean toggled;
    private final Component text;

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public FhbToggleWidget(Component message,boolean value,Consumer<Boolean> valueConsumer) {
        super(0, 0, 150, 20, CommonComponents.optionNameValue(message, CommonComponents.optionStatus(value)), null, Supplier::get);
        this.text = message;
        toggled = value;
        this.valueConsumer = valueConsumer;
    }

    //? >=1.21.11 {
    /*@Override
    protected void renderContents(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
        renderDefaultSprite(context);
        renderScrollingStringOverContents(context.textRenderer(),message,2);
    }
    *///?}

    public void onPress(/*? >=1.21.9 {*//*InputWithModifiers input*//*?}*/) {
        setToggled(!toggled);
        valueConsumer.accept(toggled);
        setMessage(CommonComponents.optionNameValue(text, CommonComponents.optionStatus(toggled)));
    }

}
