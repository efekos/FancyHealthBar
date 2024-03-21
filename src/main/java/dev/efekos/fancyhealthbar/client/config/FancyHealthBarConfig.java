package dev.efekos.fancyhealthbar.client.config;

import com.google.gson.GsonBuilder;
import dev.efekos.fancyhealthbar.client.FancyHealthBarClient;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.impl.controller.DoubleSliderControllerBuilderImpl;
import dev.isxander.yacl3.impl.controller.IntegerSliderControllerBuilderImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.text.NumberFormat;

public class FancyHealthBarConfig {

    @SerialEntry
    private static int pixelSize = 1;

    @SerialEntry
    private static double velocityMultiplier = 1;

    public static double getVelocityMultiplier() {
        return velocityMultiplier;
    }

    public static int getPixelSize() {
        return pixelSize;
    }

    public static Screen createScreen(){
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("config.fancyhealthbar.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.fancyhealthbar.category.title"))
                        .tooltip(Text.translatable("config.fancyhealthbar.category.title.tooltip"))
                        .option(Option.<Integer>createBuilder()
                                .name(Text.translatable("config.fancyhealthbar.pixel_size"))
                                .description(OptionDescription.of(Text.translatable("config.fancyhealthbar.pixel_size.description")))
                                .binding(1, () -> pixelSize, newVal -> pixelSize = newVal)
                                .controller(integerOption -> new IntegerSliderControllerBuilderImpl(integerOption).range(1, 8).step(1).formatValue(value -> Text.translatable("config.fancyhealthbar.pixel_size.format",value)))
                                .build())
                        .option(Option.<Double>createBuilder()
                                .name(Text.translatable("config.fancyhealthbar.velocity_multiplier"))
                                .description(OptionDescription.of(Text.translatable("config.fancyhealthbar.velocity_multiplier.description")))
                                .binding(1d,() -> velocityMultiplier,v -> velocityMultiplier = v)
                                .controller(doubleOption -> new DoubleSliderControllerBuilderImpl(doubleOption).range(0d,3d).step(0.1d).formatValue(value -> Text.translatable("config.fancyhealthbar.velocity_multiplier.format",NumberFormat.getNumberInstance().format(value))))
                                .build()
                        )
                        .build()
                )
                .save(HANDLER::save)
                .build()
                .generateScreen(null);
    }

    public static KeyBinding CONFIG_KEY = new KeyBinding("key.fancyhealthbar.config",76,"key.category.fancyhealthbar");

    public static ConfigClassHandler<FancyHealthBarConfig> HANDLER = ConfigClassHandler.createBuilder(FancyHealthBarConfig.class)
            .id(new Identifier(FancyHealthBarClient.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("fancyhealthbar.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    public static Screen createScreen(Screen screen) {
        return createScreen();
    }
}
