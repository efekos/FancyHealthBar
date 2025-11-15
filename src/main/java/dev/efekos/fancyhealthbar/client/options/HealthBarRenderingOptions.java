package dev.efekos.fancyhealthbar.client.options;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import dev.efekos.fancyhealthbar.client.rendering.HealthBarRendering;
import net.minecraft.client.gui.widget.GridWidget;

import java.util.List;
import java.util.Map;

public interface HealthBarRenderingOptions {

    Map<String,HealthBarRenderingOptions> TYPES = ImmutableMap.<String,HealthBarRenderingOptions>builder()
            .put("vanilla",new VanillaRenderingOptions())
            .put("line",new LineRenderingOptions())
            .build();

    void fillOptions(GridWidget.Adder adder);
    HealthBarRendering createRendering();
    void read(JsonObject object);
    void write(JsonObject object);
    void reset();

}