/*
 * MIT License
 *
 * Copyright (c) 2024 efekos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.efekos.fancyhealthbar.client.config;

import com.google.gson.GsonBuilder;
import dev.efekos.fancyhealthbar.client.FancyHealthBarClient;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.impl.controller.DoubleSliderControllerBuilderImpl;
import dev.isxander.yacl3.impl.controller.FloatSliderControllerBuilderImpl;
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

    @SerialEntry
    private static float slipperiness = 1;

    @SerialEntry
    private static int gravity = 1;

    @SerialEntry
    private static int countMultiplier = 1;

    @SerialEntry
    private static int maximumObjects = 1024;

    @SerialEntry
    private static int maximumTicks = 100;

    @SerialEntry
    private static int updateInterval = 5;

    public static float getSlipperiness() {
        return slipperiness;
    }

    public static int getGravity() {
        return gravity;
    }

    public static double getVelocityMultiplier() {
        return velocityMultiplier;
    }

    public static int getPixelSize() {
        return pixelSize;
    }

    public static int getCountMultiplier() {
        return countMultiplier;
    }

    public static int getMaximumObjects() {
        return maximumObjects;
    }

    public static int getMaximumTicks() {
        return maximumTicks;
    }

    public static int getUpdateInterval() {
        return updateInterval;
    }

    public static Screen createScreen() {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("config.fancyhealthbar.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.fancyhealthbar.category.title"))
                        .tooltip(Text.translatable("config.fancyhealthbar.category.title.tooltip"))
                        .option(Option.<Integer>createBuilder()
                                .name(Text.translatable("config.fancyhealthbar.pixel_size"))
                                .description(OptionDescription.of(Text.translatable("config.fancyhealthbar.pixel_size.description")))
                                .binding(1, FancyHealthBarConfig::getPixelSize, newVal -> pixelSize = newVal)
                                .controller(integerOption -> new IntegerSliderControllerBuilderImpl(integerOption).range(1, 8).step(1).formatValue(value -> Text.translatable("config.fancyhealthbar.pixel_size.format", value)))
                                .build())
                        .option(Option.<Double>createBuilder()
                                .name(Text.translatable("config.fancyhealthbar.velocity_multiplier"))
                                .description(OptionDescription.of(Text.translatable("config.fancyhealthbar.velocity_multiplier.description")))
                                .binding(1d, FancyHealthBarConfig::getVelocityMultiplier, v -> velocityMultiplier = v)
                                .controller(doubleOption -> new DoubleSliderControllerBuilderImpl(doubleOption).range(0d, 3d).step(0.05d).formatValue(value -> Text.translatable("config.fancyhealthbar.velocity_multiplier.format", NumberFormat.getNumberInstance().format(value))))
                                .build()
                        )
                        .option(Option.<Float>createBuilder()
                                .name(Text.translatable("config.fancyhealthbar.slipperiness"))
                                .description(OptionDescription.of(Text.translatable("config.fancyhealthbar.slipperiness.description")))
                                .binding(1f, FancyHealthBarConfig::getSlipperiness, aFloat -> slipperiness = aFloat)
                                .controller(floatOption -> new FloatSliderControllerBuilderImpl(floatOption).range(0f, 5f).step(0.1f).formatValue(value -> Text.translatable("config.fancyhealthbar.slipperiness.format", NumberFormat.getNumberInstance().format(value))))
                                .build()
                        )
                        .option(Option.<Integer>createBuilder()
                                .name(Text.translatable("config.fancyhealthbar.gravity"))
                                .description(OptionDescription.of(Text.translatable("config.fancyhealthbar.gravity.description")))
                                .binding(1, FancyHealthBarConfig::getGravity, i -> gravity = i)
                                .controller(i -> new IntegerSliderControllerBuilderImpl(i).range(-12, 12).step(1).formatValue(value -> Text.translatable("config.fancyhealthbar.gravity.format", value)))
                                .build()
                        )
                        .option(Option.<Integer>createBuilder()
                                .name(Text.translatable("config.fancyhealthbar.count_multiplier"))
                                .description(OptionDescription.of(Text.translatable("config.fancyhealthbar.count_multiplier.description")))
                                .binding(1, FancyHealthBarConfig::getCountMultiplier, i -> countMultiplier = i)
                                .controller(i -> new IntegerSliderControllerBuilderImpl(i).range(1, 8).step(1).formatValue(value -> Text.translatable("config.fancyhealthbar.count_multiplier.format", value)))
                                .build()
                        )
                        .option(Option.<Integer>createBuilder()
                                .name(Text.translatable("config.fancyhealthbar.maximum_objects"))
                                .description(OptionDescription.of(Text.translatable("config.fancyhealthbar.maximum_objects.description")))
                                .binding(1024, FancyHealthBarConfig::getMaximumObjects, integer -> maximumObjects = integer)
                                .controller(i -> new IntegerSliderControllerBuilderImpl(i).range(64, 4096).step(64).formatValue(value -> Text.translatable("config.fancyhealthbar.maximum_objects.format", value)))
                                .build()
                        )
                        .option(Option.<Integer>createBuilder()
                                .name(Text.translatable("config.fancyhealthbar.maximum_ticks"))
                                .description(OptionDescription.of(Text.translatable("config.fancyhealthbar.maximum_ticks.description")))
                                .binding(100, FancyHealthBarConfig::getMaximumTicks, i -> maximumTicks = i)
                                .controller(i -> new IntegerSliderControllerBuilderImpl(i).range(20, 240).step(4).formatValue(value -> Text.translatable("config.fancyhealthbar.maximum_ticks.format", NumberFormat.getNumberInstance().format(value / 20d))))
                                .build()
                        )
                        .option(Option.<Integer>createBuilder()
                                .name(Text.translatable("config.fancyhealthbar.update_interval"))
                                .description(OptionDescription.of(Text.translatable("config.fancyhealthbar.update_interval.description")))
                                .binding(5, FancyHealthBarConfig::getUpdateInterval, integer -> updateInterval = integer)
                                .controller(i -> new IntegerSliderControllerBuilderImpl(i).range(2, 30).step(1).formatValue(value -> Text.translatable("config.fancyhealthbar.update_interval.format", NumberFormat.getNumberInstance().format(value / 20d))))
                                .build()
                        )
                        .build()
                )
                .save(HANDLER::save)
                .build()
                .generateScreen(null);
    }

    public static KeyBinding CONFIG_KEY = new KeyBinding("key.fancyhealthbar.config", 76, "key.category.fancyhealthbar");

    public static ConfigClassHandler<FancyHealthBarConfig> HANDLER = ConfigClassHandler.createBuilder(FancyHealthBarConfig.class)
            .id(new Identifier(FancyHealthBarClient.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("fancyhealthbar.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    public static Screen createScreen(Screen ignoredScreen) {
        return createScreen();
    }
}