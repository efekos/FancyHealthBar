package dev.efekos.fancyhealthbar.client.options;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import dev.efekos.fancyhealthbar.client.rendering.HealthBarRendering;
import dev.efekos.fancyhealthbar.client.screen.RangedSliderWidget;
import net.minecraft.client.gui.widget.GridWidget;
import org.joml.Vector2i;

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

    default Vector2i readVector(JsonObject object,String key){
        return new Vector2i(object.get(key+"X").getAsInt(),object.get(key+"Y").getAsInt());
    }

    default void writeVector(JsonObject object,String key,Vector2i vector2i){
        object.addProperty(key+"X",vector2i.x());
        object.addProperty(key+"Y",vector2i.y());
    }

    default Range<Integer> readIntRange(JsonObject object, String key){
        return new IntRange(object.get(key+"Min").getAsInt(),object.get(key+"Max").getAsInt());
    }

    default Range<Double> readDoubleRange(JsonObject object, String key){
        return new RangedSliderWidget.Range(object.get(key+"Min").getAsDouble(),object.get(key+"Max").getAsDouble());
    }

    default void writeRange(JsonObject object, String key, Range<?> range){
        object.addProperty(key+"Min",range.getMin());
        object.addProperty(key+"Max",range.getMax());
    }

}