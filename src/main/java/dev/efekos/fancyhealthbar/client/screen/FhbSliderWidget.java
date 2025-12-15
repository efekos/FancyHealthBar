package dev.efekos.fancyhealthbar.client.screen;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.function.Consumer;

public class FhbSliderWidget extends SliderWidget {

    private Consumer<Integer> valueConsumer;
    private final Text text;
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

    public FhbSliderWidget(int min, int max,Text text, int value, Consumer<Integer> valueConsumer) {
        super(0, 0, 150, 20, ScreenTexts.composeGenericOptionText(text,Text.literal(value+"")), (double) (value - min) /(max-min));
        this.text = text;
        this.min = min;
        this.max = max;
        this.valueConsumer = valueConsumer;
    }

    public FhbSliderWidget setValueConsumer(Consumer<Integer> valueConsumer) {
        this.valueConsumer = valueConsumer;
        return this;
    }

    public Text createMessage(){
        return ScreenTexts.composeGenericOptionText(text,Text.literal(clamp()+""));
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
        return MathHelper.floor(MathHelper.clampedLerp(/*? <1.21.11 {*/min, max, this.value/*?} else {*//*this.value, min, max*//*?}*/));
    }

}
