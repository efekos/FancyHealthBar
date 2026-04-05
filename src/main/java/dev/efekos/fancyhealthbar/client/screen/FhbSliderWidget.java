package dev.efekos.fancyhealthbar.client.screen;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.Consumer;

public class FhbSliderWidget extends AbstractSliderButton {

    private Consumer<Integer> valueConsumer;
    private final Component text;
    private int min;
    private int max;

    public FhbSliderWidget setMax(int max) {
        this.max = max;
        return this;
    }

    public FhbSliderWidget setMin(int min) {
        this.min = min;
        return this;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public FhbSliderWidget(int min, int max, Component text, int value, Consumer<Integer> valueConsumer) {
        super(0, 0, 150, 20, CommonComponents.optionNameValue(text, Component.literal(value+"")), (double) (value - min) /(max-min));
        this.text = text;
        this.min = min;
        this.max = max;
        this.valueConsumer = valueConsumer;
    }

    public FhbSliderWidget setValueConsumer(Consumer<Integer> valueConsumer) {
        this.valueConsumer = valueConsumer;
        return this;
    }

    public Component createMessage(){
        return CommonComponents.optionNameValue(text, Component.literal(clamp()+""));
    }

    @Override
    protected void updateMessage() {
        setMessage(createMessage());
    }

    @Override
    protected void applyValue() {
        valueConsumer.accept(clamp());
    }

    private int clamp() {
        return Mth.floor(Mth.clampedLerp(/*? <1.21.11 {*/min, max, this.value/*?} else {*//*this.value, min, max*//*?}*/));
    }

}
