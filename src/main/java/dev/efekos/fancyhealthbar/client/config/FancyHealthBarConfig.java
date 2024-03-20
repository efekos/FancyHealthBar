package dev.efekos.fancyhealthbar.client.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.impl.controller.IntegerSliderControllerBuilderImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class FancyHealthBarConfig {

    private static int pixelSize;

    public static void openConfigScreen(){
        Screen screen = YetAnotherConfigLib.createBuilder()
                .title(Text.literal("Fancy health bar configuration screen."))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("FancyHealthBar"))
                        .tooltip(Text.literal("Main configuration."))
                        .option(Option.<Integer>createBuilder()
                                .name(Text.literal("Pixel Size"))
                                .description(OptionDescription.of(Text.literal("Size of the pixels.")))
                                .binding(1, () -> pixelSize, newVal -> pixelSize = newVal)
                                .controller(integerOption -> new IntegerSliderControllerBuilderImpl(integerOption).range(1,8))
                                .build())
                        .build())
                .build()
                .generateScreen(null);
    }

}
