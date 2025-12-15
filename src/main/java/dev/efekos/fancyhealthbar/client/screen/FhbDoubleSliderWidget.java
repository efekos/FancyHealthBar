package dev.efekos.fancyhealthbar.client.screen;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.function.Consumer;

public class FhbDoubleSliderWidget extends SliderWidget {

    private Consumer<Double> valueConsumer;
    private final Text text;
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

    public FhbDoubleSliderWidget(double min, double max, Text text, double value, Consumer<Double> valueConsumer) {
        super(0, 0, 150, 20, ScreenTexts.composeGenericOptionText(text, Text.literal(NumberFormat.getInstance().format(MathHelper.clampedLerp(min, max, value)))), (value - min) /(max-min));
        this.text = text;
        this.min = min;
        this.max = max;
        this.valueConsumer = valueConsumer;
    }

    public FhbDoubleSliderWidget setValueConsumer(Consumer<Double> valueConsumer) {
        this.valueConsumer = valueConsumer;
        return this;
    }

    public Text createMessage(){
        return ScreenTexts.composeGenericOptionText(text,Text.literal(NumberFormat.getInstance().format(clamp())));
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
        return new BigDecimal(MathHelper.clampedLerp(/*? <1.21.11 {*/min, max, this.value/*?} else {*//*this.value, min, max*//*?}*/)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

}
