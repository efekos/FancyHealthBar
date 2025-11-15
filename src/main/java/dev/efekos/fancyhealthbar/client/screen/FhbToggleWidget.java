package dev.efekos.fancyhealthbar.client.screen;

import net.minecraft.client.gui.widget.ButtonWidget;
//?>=1.21.9
/*import net.minecraft.client.input.AbstractInput;*/
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FhbToggleWidget extends ButtonWidget {

    private final Consumer<Boolean> valueConsumer;
    private boolean toggled;
    private final Text text;

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public FhbToggleWidget(Text message,boolean value,Consumer<Boolean> valueConsumer) {
        super(0, 0, 150, 20, ScreenTexts.composeGenericOptionText(message,ScreenTexts.onOrOff(value)), null, Supplier::get);
        this.text = message;
        toggled = value;
        this.valueConsumer = valueConsumer;
    }

    //?>=1.21.9{
    /*
    public void onPress(AbstractInput input) {
        setToggled(!toggled);
        valueConsumer.accept(toggled);
        setMessage(ScreenTexts.composeGenericOptionText(text,ScreenTexts.onOrOff(toggled)));
    }*/
    //?} else {
    public void onPress() {
        setToggled(!toggled);
        valueConsumer.accept(toggled);
        setMessage(ScreenTexts.composeGenericOptionText(text,ScreenTexts.onOrOff(toggled)));
    }
    //?}

}
