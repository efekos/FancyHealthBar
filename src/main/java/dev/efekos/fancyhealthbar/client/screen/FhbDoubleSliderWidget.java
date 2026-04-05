package dev.efekos.fancyhealthbar.client.screen;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.function.Consumer;

public class FhbDoubleSliderWidget extends AbstractSliderButton {

    private Consumer<Double> valueConsumer;
    private final Component text;
    private double min;
    private double max;

    public FhbDoubleSliderWidget setMax(double max) {
        this.max = max;
        return this;
    }

    public FhbDoubleSliderWidget setMin(double min) {
        this.min = min;
        return this;
    }

    public double getMin() {
        return min;
    }
    public double getMax() {
        return max;
    }

    public FhbDoubleSliderWidget(double min, double max, Component text, double value, Consumer<Double> valueConsumer) {
        super(0, 0, 150, 20, CommonComponents.optionNameValue(text, Component.literal(NumberFormat.getInstance().format(Mth.clampedLerp(min, max, value)))), (value - min) /(max-min));
        this.text = text;
        this.min = min;
        this.max = max;
        this.valueConsumer = valueConsumer;
    }

    public FhbDoubleSliderWidget setValueConsumer(Consumer<Double> valueConsumer) {
        this.valueConsumer = valueConsumer;
        return this;
    }

    public Component createMessage(){
        return CommonComponents.optionNameValue(text, Component.literal(NumberFormat.getInstance().format(clamp())));
    }

    @Override
    protected void updateMessage() {
        setMessage(createMessage());
    }

    @Override
    protected void applyValue() {
        valueConsumer.accept(clamp());
    }

    private double clamp() {
        return new BigDecimal(Mth.clampedLerp(/*? <1.21.11 {*/min, max, this.value/*?} else {*//*this.value, min, max*//*?}*/)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

}
