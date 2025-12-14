package dev.efekos.fancyhealthbar.client.options;

import com.google.gson.JsonObject;
import dev.efekos.fancyhealthbar.client.rendering.HealthBarRendering;
import dev.efekos.fancyhealthbar.client.rendering.VanillaHealthBarRendering;
import dev.efekos.fancyhealthbar.client.screen.*;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.joml.Vector2i;

import java.util.function.Supplier;

public class VanillaRenderingOptions implements HealthBarRenderingOptions {

    public static final int VERSION = 2;

    private int jiggle;
    private int lowHealthJiggleStart;
    private boolean regenIndexAccount;
    private boolean blinking;
    private HardcoreHearts hardcoreHearts;
    private int lowHealthJiggle;
    private Range<Integer> velocityX;
    private Range<Integer> velocityY;
    private Range<Double> accelerationX;
    private Range<Double> accelerationY;
    private Range<Double> drag;
    private Range<Integer> maxLifetime;
    private Range<Integer> size;
    private Range<Integer> fadeOutTicks;
    private Range<Integer> fadeInTicks;
    private Vector2i offset;

    @Override
    public void fillOptions(GridWidget.Adder adder) {
        adder.add(new FhbSliderWidget(0,10,Text.translatable("options.fancyhealthbar.vanilla.jiggle"),this.getJiggle(),this::setJiggle));
        adder.add(new FhbSliderWidget(0,10,Text.translatable("options.fancyhealthbar.vanilla.low_health_jiggle"),this.getLowHealthJiggle(),this::setLowHealthJiggle));
        adder.add(new FhbToggleWidget(Text.translatable("options.fancyhealthbar.vanilla.blinking"),this.isBlinking(),this::setBlinking));
        adder.add(new FhbSliderWidget(1,100,Text.translatable("options.fancyhealthbar.vanilla.low_health_jiggle_start"),this.getLowHealthJiggleStart(),this::setLowHealthJiggleStart));
        adder.add(new FhbEnumToggleWidget<>(VanillaRenderingOptions.HardcoreHearts.class,Text.translatable("options.fancyhealthbar.vanilla.hardcore_hearts"),this.getHardcoreHearts(),this::setHardcoreHearts));
        adder.add(new FhbToggleWidget(Text.translatable("options.fancyhealthbar.vanilla.regen"),this.isRegenIndexAccount(),this::setRegenIndexAccount));

        adder.add(new FhbRangedSliderWidget(-50,50,Text.translatable("options.fancyhealthbar.vanilla.velocity_x"),this.getVelocityX().getMin(),this.getVelocityX().getMax(),this.getVelocityX()::setMin,this.getVelocityX()::setMax));
        adder.add(new FhbRangedSliderWidget(-50,50,Text.translatable("options.fancyhealthbar.vanilla.velocity_y"),this.getVelocityY().getMin(),this.getVelocityY().getMax(),this.getVelocityY()::setMin,this.getVelocityY()::setMax));

        adder.add(new FhbRangedDoubleSliderWidget(-1,1,Text.translatable("options.fancyhealthbar.vanilla.acceleration_x"),this.getAccelerationX()));
        adder.add(new FhbRangedDoubleSliderWidget(-1,1,Text.translatable("options.fancyhealthbar.vanilla.acceleration_y"),this.getAccelerationY()));

        adder.add(new FhbRangedDoubleSliderWidget(0,1,Text.translatable("options.fancyhealthbar.vanilla.drag"),this.getDrag()));
        adder.add(new FhbRangedSliderWidget(1,150,Text.translatable("options.fancyhealthbar.vanilla.lifetime"),this.getMaxLifetime()));

        adder.add(new FhbRangedSliderWidget(1,10,Text.translatable("options.fancyhealthbar.vanilla.size"),this.getSize()));
        adder.add(new FhbRangedSliderWidget(0,100,Text.translatable("options.fancyhealthbar.vanilla.fade_in"),this.getFadeInTicks()));
        adder.add(new FhbRangedSliderWidget(0,100,Text.translatable("options.fancyhealthbar.vanilla.fade_out"),this.getFadeOutTicks()));

        adder.add(new FhbSliderWidget(-200,200,Text.translatable("options.fancyhealthbar.generic.offset_x"),offset().x,v->offset.x = v));
        adder.add(new FhbSliderWidget(-200,200,Text.translatable("options.fancyhealthbar.generic.offset_y"),offset().y,v->offset.y = v));
    }

    public VanillaRenderingOptions() {
        reset();
    }

    @Override
    public HealthBarRendering createRendering() {
        return new VanillaHealthBarRendering(this);
    }

    @Override
    public void read(JsonObject object) {

        int version = object.has("version") ? object.get("version").getAsInt() : 1;

        jiggle = object.get("jiggle").getAsInt();
        regenIndexAccount = object.get("regenIndexAccount").getAsBoolean();
        blinking = object.get("blinking").getAsBoolean();
        lowHealthJiggle = object.get("lowHealthJiggle").getAsInt();
        hardcoreHearts = HardcoreHearts.valueOf(object.get("hardcoreHearts").getAsString());
        lowHealthJiggleStart = object.get("lowHealthJiggleStart").getAsInt();
        velocityX = readIntRange(object,"velocityX");
        velocityY = readIntRange(object,"velocityY");
        accelerationX = readDoubleRange(object,"accelerationX");
        accelerationY = readDoubleRange(object,"accelerationY");
        drag = readDoubleRange(object,"drag");
        maxLifetime = readIntRange(object,"maxLifetime");
        if(version>1){
            size=readIntRange(object,"size");
            fadeOutTicks=readIntRange(object,"fade_out");
            fadeInTicks=readIntRange(object,"fade_in");
            offset=readVector(object,"offset");
        }
    }


    @Override
    public void write(JsonObject object) {
        object.addProperty("jiggle", jiggle);
        object.addProperty("regenIndexAccount", regenIndexAccount);
        object.addProperty("blinking", blinking);
        object.addProperty("lowHealthJiggle", lowHealthJiggle);
        object.addProperty("lowHealthJiggleStart", lowHealthJiggleStart);
        object.addProperty("hardcoreHearts", hardcoreHearts.name());
        writeRange(object, "velocityX", velocityX);
        writeRange(object, "velocityY", velocityY);
        writeRange(object, "accelerationX", accelerationX);
        writeRange(object, "accelerationY", accelerationY);
        writeRange(object, "drag", drag);
        writeRange(object, "maxLifetime", maxLifetime);
        writeRange(object,"size",size);
        writeRange(object,"fade_out", fadeOutTicks);
        writeRange(object,"fade_in", fadeInTicks);
        writeVector(object,"offset",offset);
        object.addProperty("version", VERSION);
    }

    @Override
    public void reset() {
        jiggle = 0;
        blinking = true;
        hardcoreHearts = HardcoreHearts.AUTO;
        lowHealthJiggle = 2;
        lowHealthJiggleStart = 4;
        regenIndexAccount = false;
        velocityX = new IntRange(-40,40);
        velocityY = new IntRange(-20,20);
        accelerationX = new RangedSliderWidget.Range(0.99,0.99);
        accelerationY = new RangedSliderWidget.Range(-1,-1);
        drag = new RangedSliderWidget.Range(0,0);
        maxLifetime = new IntRange(100,100);
        size = new IntRange(1,1);
        fadeOutTicks = new IntRange(0,0);
        fadeInTicks = new IntRange(0,0);
        offset = new Vector2i(0,0);
    }

    public enum HardcoreHearts implements Supplier<Text> {
        OFF(ScreenTexts.OFF),
        ON(ScreenTexts.ON),
        AUTO(Text.translatable("options.guiScale.auto"));

        private final Text text;

        HardcoreHearts(Text text) {
            this.text = text;
        }

        public Text text(){
            return text;
        }

        @Override
        public Text get() {
            return text;
        }

    }

    public Range<Integer> getFadeInTicks() {
        return fadeInTicks;
    }

    public Range<Integer> getFadeOutTicks() {
        return fadeOutTicks;
    }

    public Range<Integer> getSize() {
        return size;
    }

    public void setSize(Range<Integer> size) {
        this.size = size;
    }

    public void setRegenIndexAccount(boolean regenIndexAccount) {
        this.regenIndexAccount = regenIndexAccount;
    }

    public int getLowHealthJiggleStart() {
        return lowHealthJiggleStart;
    }

    public void setLowHealthJiggleStart(int lowHealthJiggleStart) {
        this.lowHealthJiggleStart = lowHealthJiggleStart;
    }

    public void setLowHealthJiggle(int lowHealthJiggle) {
        this.lowHealthJiggle = lowHealthJiggle;
    }

    public void setHardcoreHearts(HardcoreHearts hardcoreHearts) {
        this.hardcoreHearts = hardcoreHearts;
    }

    public void setBlinking(boolean blinking) {
        this.blinking = blinking;
    }

    public void setJiggle(int jiggle) {
        this.jiggle = jiggle;
    }

    public boolean isRegenIndexAccount() {
        return regenIndexAccount;
    }

    public int getLowHealthJiggle() {
        return lowHealthJiggle;
    }

    public HardcoreHearts getHardcoreHearts() {
        return hardcoreHearts;
    }

    public boolean isBlinking() {
        return blinking;
    }

    public int getJiggle() {
        return jiggle;
    }

    public Range<Integer> getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(Range<Integer> velocityX) {
        this.velocityX = velocityX;
    }

    public Range<Integer> getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(Range<Integer> velocityY) {
        this.velocityY = velocityY;
    }

    public void setOffset(Vector2i offset) {
        this.offset = offset;
    }

    public Vector2i offset() {
        return offset;
    }

    public Range<Double> getAccelerationX() {
        return accelerationX;
    }

    public void setAccelerationX(Range<Double> accelerationX) {
        this.accelerationX = accelerationX;
    }

    public Range<Double> getAccelerationY() {
        return accelerationY;
    }

    public void setAccelerationY(Range<Double> accelerationY) {
        this.accelerationY = accelerationY;
    }

    public Range<Double> getDrag() {
        return drag;
    }

    public void setDrag(Range<Double> drag) {
        this.drag = drag;
    }

    public Range<Integer> getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(Range<Integer> maxLifetime) {
        this.maxLifetime = maxLifetime;
    }
}