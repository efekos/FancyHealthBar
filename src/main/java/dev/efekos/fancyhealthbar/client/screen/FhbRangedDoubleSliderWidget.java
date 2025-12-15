package dev.efekos.fancyhealthbar.client.screen;

import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Consumer;

public class FhbRangedDoubleSliderWidget extends RangedSliderWidget {

    private Consumer<Double> minConsumer;
    private Consumer<Double> maxConsumer;
    private final Text text;
    private double min;
    private double max;

    public FhbRangedDoubleSliderWidget(int min, int max, Text text, dev.efekos.fancyhealthbar.client.options.Range<Double> range) {
        this(min,max,text,range.getMin(),range.getMax(),range::setMin,range::setMax);
    }

    public FhbRangedDoubleSliderWidget setMax(double max) {
        this.max = max;
        return this;
    }

    public FhbRangedDoubleSliderWidget setMin(double min) {
        this.min = min;
        return this;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public FhbRangedDoubleSliderWidget(double min, double max, Text text, double vMin, double vMax, Consumer<Double> minConsumer, Consumer<Double> maxConsumer) {
        super(0, 0, 150, 20, ScreenTexts.composeGenericOptionText(text,RangedSliderWidget.rangeText(vMin,vMax)), new Range(inverseLerp(min,max,vMin), inverseLerp(min,max,vMax)));
        this.text = text;
        this.min = min;
        this.max = max;
        this.value = new Range(inverseLerp(min,max,vMin), inverseLerp(min,max,vMax));
        this.minConsumer = minConsumer;
        this.maxConsumer = maxConsumer;
    }

    private static double inverseLerp(double min, double max, double value) {
        return (value - min) / (max - min);
    }

    public void setMinConsumer(Consumer<Double> minConsumer) {
        this.minConsumer = minConsumer;
    }

    public void setMaxConsumer(Consumer<Double> maxConsumer) {
        this.maxConsumer = maxConsumer;
    }

    public Text createMessage(){
        return ScreenTexts.composeGenericOptionText(text,rangeText(clamp(this.value.min),clamp(this.value.max)));
    }

    @Override
    protected void updateMessage() {
        setMessage(createMessage());
    }

    @Override
    protected void applyValue() {
        minConsumer.accept(clamp(this.value.min));
        maxConsumer.accept(clamp(this.value.max));
    }

    private double clamp(double v) {
        return new BigDecimal(MathHelper.clampedLerp(/*? <1.21.11 {*/min, max, v/*?} else {*//*v, min, max*//*?}*/)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

}
