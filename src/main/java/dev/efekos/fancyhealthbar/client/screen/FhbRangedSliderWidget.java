package dev.efekos.fancyhealthbar.client.screen;

import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.function.Consumer;

public class FhbRangedSliderWidget extends RangedSliderWidget {

    private Consumer<Integer> minConsumer;
    private Consumer<Integer> maxConsumer;
    private final Text text;
    private int min;
    private int max;

    public FhbRangedSliderWidget(int min, int max, Text text, dev.efekos.fancyhealthbar.client.options.Range<Integer> range) {
        this(min,max,text,range.getMin(), range.getMax(), range::setMin,range::setMax);
    }

    public FhbRangedSliderWidget setMax(int max) {
        this.max = max;
        return this;
    }

    public FhbRangedSliderWidget setMin(int min) {
        this.min = min;
        return this;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public FhbRangedSliderWidget(int min, int max, Text text, int vMin,int vMax, Consumer<Integer> minConsumer, Consumer<Integer> maxConsumer) {
        super(0, 0, 150, 20, ScreenTexts.composeGenericOptionText(text,RangedSliderWidget.rangeText(vMin,vMax)), new Range(inverseLerp(min,max,vMin), inverseLerp(min,max,vMax)));
        this.text = text;
        this.min = min;
        this.max = max;
        this.value = new Range(inverseLerp(min,max,vMin), inverseLerp(min,max,vMax));
        this.minConsumer = minConsumer;
        this.maxConsumer = maxConsumer;
    }

    private static double inverseLerp(int min, int max, int value) {
        return ((double) (value - min) / (max - min));
    }

    public void setMinConsumer(Consumer<Integer> minConsumer) {
        this.minConsumer = minConsumer;
    }

    public void setMaxConsumer(Consumer<Integer> maxConsumer) {
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

    private int clamp(double v) {
        return MathHelper.floor(MathHelper.clampedLerp(/*? <1.21.11 {*/min, max, v/*?} else {*//*v, min, max*//*?}*/));
    }

}
