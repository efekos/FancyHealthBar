package dev.efekos.fancyhealthbar.client.config;

import com.google.gson.GsonBuilder;
import dev.efekos.fancyhealthbar.client.FancyHealthBarClient;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.impl.controller.IntegerSliderControllerBuilderImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FancyHealthBarConfig {

    @SerialEntry
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
}
