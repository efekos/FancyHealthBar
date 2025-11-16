package dev.efekos.fancyhealthbar.client.screen;

import net.minecraft.client.gui.widget.ButtonWidget;
//? >=1.21.9
/*import net.minecraft.client.input.AbstractInput;*/
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FhbEnumToggleWidget<T extends Enum<T> & Supplier<Text>> extends ButtonWidget {

    private final Consumer<T> valueConsumer;
    private T value;
    private final Class<T> clazz;
    private final Text text;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        valueConsumer.accept(value);
    }

    public FhbEnumToggleWidget(Class<T> clazz,Text message, T value, Consumer<T> valueConsumer) {
        super(0, 0, 150, 20, ScreenTexts.composeGenericOptionText(message,value.get()), null, Supplier::get);
        this.value = value;
        this.text = message;
        this.clazz = clazz;
        this.valueConsumer = valueConsumer;
    }

    //? >=1.21.9 {
    /*@Override
    public void onPress(AbstractInput input) {
        onPress();
    }*/
    //?}

    public void onPress() {
        int ordinal = value.ordinal()+1;
        T[] constants = clazz.getEnumConstants();
        if(ordinal>=constants.length) ordinal=0;
        value=constants[ordinal];
        valueConsumer.accept(value);
        setMessage(ScreenTexts.composeGenericOptionText(text,value.get()));
    }

}
