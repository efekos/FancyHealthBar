package dev.efekos.fancyhealthbar.client.screen;

//~if>=26.1 'GuiGraphics' -> 'GuiGraphicsExtractor' {
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
//? >=1.21.9
//import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FhbEnumToggleWidget<T extends Enum<T> & Supplier<Component>> extends Button {

    private final Consumer<T> valueConsumer;
    private T value;
    private final Class<T> clazz;
    private final Component text;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        valueConsumer.accept(value);
    }

    public FhbEnumToggleWidget(Class<T> clazz, Component message, T value, Consumer<T> valueConsumer) {
        super(0, 0, 150, 20, CommonComponents.optionNameValue(message,value.get()), null, Supplier::get);
        this.value = value;
        this.text = message;
        this.clazz = clazz;
        this.valueConsumer = valueConsumer;
    }

    //? >=1.21.11 {
    /*//~ if>=26.1 'render' -> 'extract'{
    @Override
    protected void renderContents(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
        renderDefaultSprite(context);
        renderScrollingStringOverContents(context.textRenderer(),message,2);
    }
    //~ }
    *///?}

    public void onPress(/*? >=1.21.9 {*//*InputWithModifiers input*//*?}*/) {
        int ordinal = value.ordinal()+1;
        T[] constants = clazz.getEnumConstants();
        if(ordinal>=constants.length) ordinal=0;
        value=constants[ordinal];
        valueConsumer.accept(value);
        setMessage(CommonComponents.optionNameValue(text,value.get()));
    }

}

//~}