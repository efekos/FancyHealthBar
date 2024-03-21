package dev.efekos.fancyhealthbar.client.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.impl.controller.IntegerSliderControllerBuilderImpl;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;

public class FancyHealthBarConfig {

    private static int pixelSize = 1;

    public static int getPixelSize() {
        return pixelSize;
    }

    public static Screen createScreen(){
        return YetAnotherConfigLib.createBuilder()
                .title(Text.literal("Fancy health bar configuration screen."))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("FancyHealthBar"))
                        .tooltip(Text.literal("Main configuration."))
                        .option(Option.<Integer>createBuilder()
                                .name(Text.literal("Pixel Size"))
                                .description(OptionDescription.of(Text.literal("Size of the pixels.")))
                                .binding(1, () -> pixelSize, newVal -> pixelSize = newVal)
                                .controller(integerOption -> new IntegerSliderControllerBuilderImpl(integerOption).range(1, 8).step(1).formatValue(value -> Text.literal(value + " Pixels")))
                                .build())
                        .build())
                .build()
                .generateScreen(null);
    }

    public static KeyBinding CONFIG_KEY = new KeyBinding("key.fancyhealthbar.config",76,"key.category.fancyhealthbar");

}
