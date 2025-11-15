package dev.efekos.fancyhealthbar.client.options;

import com.google.gson.JsonObject;
import dev.efekos.fancyhealthbar.client.FancyHealthBarClient;
import dev.efekos.fancyhealthbar.client.Try;
import dev.efekos.fancyhealthbar.client.accessor.InGameHudRenderingAccessor;
import dev.efekos.fancyhealthbar.client.animation.Animation;
import dev.efekos.fancyhealthbar.client.compat.Texture;
import dev.efekos.fancyhealthbar.client.compat.TextureList;
import dev.efekos.fancyhealthbar.client.compat.TextureNineSlice;
import dev.efekos.fancyhealthbar.client.entity.HudEntityManager;
import dev.efekos.fancyhealthbar.client.rendering.HealthBarRendering;
import dev.efekos.fancyhealthbar.client.rendering.LineHealthBarRendering;
import dev.efekos.fancyhealthbar.client.screen.FhbEnumToggleWidget;
import dev.efekos.fancyhealthbar.client.screen.FhbSliderWidget;
import dev.efekos.fancyhealthbar.client.screen.FhbToggleWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.function.Supplier;

public class LineRenderingOptions implements HealthBarRenderingOptions {

    public LineRenderingOptions() {
        reset();
    }

    private boolean blinking;
    private boolean yellowDelta;
    private int notches = 0;
    private LineStyle normalLineStyle;
    private LineStyle hardcoreLineStyle;
    private DeltaBehaviour deltaBehaviour;

    @Override
    public void fillOptions(GridWidget.Adder adder) {
        adder.add(new FhbToggleWidget(Text.translatable("options.fancyhealthbar.vanilla.blinking"),blinking(),this::setBlinking));
        adder.add(new FhbToggleWidget(Text.translatable("options.fancyhealthbar.line.delta"),yellowDelta,this::setYellowDelta));
        adder.add(new FhbSliderWidget(0,5,Text.translatable("options.fancyhealthbar.line.notches"),notches,this::setNotches));
        adder.add(new FhbEnumToggleWidget<>(DeltaBehaviour.class,Text.translatable("options.fancyhealthbar.line.delta_behaviour"),deltaBehaviour,this::setDeltaBehaviour));
        adder.add(new FhbEnumToggleWidget<>(LineStyle.class,Text.translatable("options.fancyhealthbar.line.normal_style"),normalLineStyle,this::setNormalLineStyle));
        adder.add(new FhbEnumToggleWidget<>(LineStyle.class,Text.translatable("options.fancyhealthbar.line.hardcore_style"),hardcoreLineStyle,this::setHardcoreLineStyle));
    }

    public DeltaBehaviour deltaBehaviour() {
        return deltaBehaviour;
    }

    public void setDeltaBehaviour(DeltaBehaviour deltaBehaviour) {
        this.deltaBehaviour = deltaBehaviour;
    }

    public void setNormalLineStyle(LineStyle normalLineStyle) {
        this.normalLineStyle = normalLineStyle;
        Try.ignore(()->{
            ((InGameHudRenderingAccessor) MinecraftClient.getInstance().inGameHud).fhb$react();
            return null;
        });
    }

    public void setHardcoreLineStyle(LineStyle hardcoreLineStyle) {
        this.hardcoreLineStyle = hardcoreLineStyle;
        Try.ignore(()->{
            ((InGameHudRenderingAccessor) MinecraftClient.getInstance().inGameHud).fhb$react();
            return null;
        });
    }

    public LineStyle normalLineStyle() {
        return normalLineStyle;
    }

    public LineStyle hardcoreLineStyle() {
        return hardcoreLineStyle;
    }

    public int notches() {
        return notches;
    }

    public void setNotches(int notches) {
        this.notches = notches;
    }

    public boolean yellowDelta() {
        return yellowDelta;
    }

    public void setYellowDelta(boolean yellowDelta) {
        this.yellowDelta = yellowDelta;
    }

    public boolean blinking() {
        return blinking;
    }

    public void setBlinking(boolean blinking) {
        this.blinking = blinking;
    }

    @Override
    public HealthBarRendering createRendering() {
        return new LineHealthBarRendering(this);
    }

    @Override
    public void read(JsonObject object) {
        if(object.has("blinking")) blinking = object.get("blinking").getAsBoolean();
        if(object.has("delta")) yellowDelta = object.get("delta").getAsBoolean();
        if(object.has("notches")) notches = object.get("notches").getAsInt();
        if(object.has("normal_style")) normalLineStyle = Try.orElse(() -> LineStyle.valueOf(object.get("normal_style").getAsString()),LineStyle.DEFAULT);
        if(object.has("hardcore_style")) hardcoreLineStyle = Try.orElse(() -> LineStyle.valueOf(object.get("hardcore_style").getAsString()),LineStyle.HARDCORE);
        if(object.has("delta_behaviour")) deltaBehaviour = Try.orElse(()->DeltaBehaviour.valueOf(object.get("delta_behaviour").getAsString()),DeltaBehaviour.FOLLOW);
    }

    @Override
    public void write(JsonObject object) {
        object.addProperty("blinking",blinking);
        object.addProperty("delta",yellowDelta);
        object.addProperty("notches",notches);
        object.addProperty("normal_style",normalLineStyle.name());
        object.addProperty("hardcore_style",hardcoreLineStyle.name());
        object.addProperty("delta_behaviour",deltaBehaviour.name());
    }

    @Override
    public void reset() {
        notches = 0;
        blinking = true;
        yellowDelta = true;
        normalLineStyle = LineStyle.DEFAULT;
        hardcoreLineStyle = LineStyle.HARDCORE;
        deltaBehaviour = DeltaBehaviour.FOLLOW;
    }

    public enum DeltaBehaviour implements Supplier<Text> {
        FOLLOW(Text.translatable("options.fancyhealthbar.line.delta_behaviour.follow"),true),
        LERP(Text.translatable("options.fancyhealthbar.line.delta_behaviour.lerp"),true),
        BLINK(Text.translatable("options.fancyhealthbar.line.delta_behaviour.blink"),false),
        JUMP(Text.translatable("options.fancyhealthbar.line.delta_behaviour.jump"),false),
        ;
        private final boolean liveUpdates;

        public boolean liveUpdates() {
            return liveUpdates;
        }

        DeltaBehaviour(Text name,boolean liveUpdates) {
            this.name = name;
            this.liveUpdates = liveUpdates;
        }

        private final Text name;

        @Override
        public Text get() {
            return name;
        }

    }

    public enum LineStyle implements Supplier<Text> {
        DEFAULT(Text.translatable("options.fancyhealthbar.line.style.default"),false,"default",
                Texture.mod("hud/health_bar/line/default/full","textures/gui/line_bar_default.png",60,9,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/default/freeze","textures/gui/line_bar_default.png",45,9,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/default/fire","textures/gui/line_bar_default.png",30,9,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/default/empty","textures/gui/line_bar_default.png",15,9,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/default/delta","textures/gui/line_bar_default.png",0,9,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/default/blink","textures/gui/line_bar_default.png",0,0,90,9),
                TextureList.verticalStrip(Texture.mod("hud/health_bar/line/default/notches","textures/gui/line_bar_default.png",0,52,90,9),5),
                Animation.verticalStrip(Texture.mod("hud/health_bar/line/default/regenerating","textures/gui/line_bar_default.png",0,97,90,9),11),
                Animation.verticalStrip(Texture.mod("hud/health_bar/line/default/fulled","textures/gui/line_bar_default.png",90,0,90,9),14)
                ),
        HARDCORE(Text.translatable("options.fancyhealthbar.line.style.hardcore"),false,"hardcore",
                Texture.mod("hud/health_bar/line/hardcore/full","textures/gui/line_bar_default.png",54,33,18,new TextureNineSlice(6,18,18)),
                Texture.mod("hud/health_bar/line/hardcore/freeze","textures/gui/line_bar_default.png",36,33,18,new TextureNineSlice(6,18,18)),
                Texture.mod("hud/health_bar/line/hardcore/fire","textures/gui/line_bar_default.png",18,33,18,new TextureNineSlice(6,18,18)),
                Texture.mod("hud/health_bar/line/hardcore/empty","textures/gui/line_bar_default.png",15,9,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/hardcore/delta","textures/gui/line_bar_default.png",0,33,18,new TextureNineSlice(6,18,18)),
                Texture.mod("hud/health_bar/line/hardcore/blink","textures/gui/line_bar_default.png",0,24,90,9),
                TextureList.verticalStrip(Texture.mod("hud/health_bar/line/hardcore/notches","textures/gui/line_bar_default.png",0,52,90,9),5),
                Animation.verticalStrip(Texture.mod("hud/health_bar/line/hardcore/regenerating","textures/gui/line_bar_default.png",0,97,90,9),11),
                Animation.verticalStrip(Texture.mod("hud/health_bar/line/hardcore/fulled","textures/gui/line_bar_default.png",90,0,90,9),14)
                ),

        FLAT(Text.translatable("options.fancyhealthbar.line.style.flat"),false,"flat",0,
                Texture.mod("hud/health_bar/line/flat/full","textures/gui/line_bar_flat.png",60,9,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/flat/freeze","textures/gui/line_bar_flat.png",45,9,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/flat/fire","textures/gui/line_bar_flat.png",30,9,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/flat/empty","textures/gui/line_bar_flat.png",15,9,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/flat/delta","textures/gui/line_bar_flat.png",0,9,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/flat/blink","textures/gui/line_bar_flat.png",0,0,90,9),
                TextureList.verticalStrip(Texture.mod("hud/health_bar/line/flat/notches","textures/gui/line_bar_flat.png",0,147,90,9),5),
                Animation.verticalStrip(Texture.mod("hud/health_bar/line/flat/regenerating","textures/gui/line_bar_flat.png",0,48,90,9),10),
                Animation.verticalStrip(Texture.mod("hud/health_bar/line/flat/fulled","textures/gui/line_bar_flat.png",90,0,90,9),14)),

        FLAT_HARDCORE(Text.translatable("options.fancyhealthbar.line.style.flat_hardcore"),false,"flat_hardcore",0,
                Texture.mod("hud/health_bar/line/flat_hardcore/full","textures/gui/line_bar_flat.png",60,33,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/flat_hardcore/freeze","textures/gui/line_bar_flat.png",45,33,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/flat_hardcore/fire","textures/gui/line_bar_flat.png",30,33,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/flat_hardcore/empty","textures/gui/line_bar_flat.png",15,33,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/flat_hardcore/delta","textures/gui/line_bar_flat.png",0,33,15,new TextureNineSlice(5,15,15)),
                Texture.mod("hud/health_bar/line/flat_hardcore/blink","textures/gui/line_bar_flat.png",0,0,90,9),
                TextureList.verticalStrip(Texture.mod("hud/health_bar/line/flat_hardcore/notches","textures/gui/line_bar_flat.png",0,147,90,9),5),
                Animation.verticalStrip(Texture.mod("hud/health_bar/line/flat_hardcore/regenerating","textures/gui/line_bar_flat.png",0,48,90,9),11),
                Animation.verticalStrip(Texture.mod("hud/health_bar/line/flat_hardcore/fulled","textures/gui/line_bar_flat.png",90,0,90,9),14)),

        PIXELATED(Text.translatable("options.fancyhealthbar.line.style.pixelated"),true,"pixelated",0,
                Texture.mod("hud/health_bar/line/pixelated/full","textures/gui/line_bar_pixelated.png",0,45,90,9),
                Texture.mod("hud/health_bar/line/pixelated/freeze","textures/gui/line_bar_pixelated.png",0,36,90,9),
                Texture.mod("hud/health_bar/line/pixelated/fire","textures/gui/line_bar_pixelated.png",0,27,90,9),
                Texture.mod("hud/health_bar/line/pixelated/empty","textures/gui/line_bar_pixelated.png",0,18,90,9),
                Texture.mod("hud/health_bar/line/pixelated/delta","textures/gui/line_bar_pixelated.png",0,9,90,9),
                Texture.mod("hud/health_bar/line/pixelated/blink","textures/gui/line_bar_pixelated.png",0,0,90,9),
                TextureList.verticalStrip(Texture.mod("hud/health_bar/line/pixelated/notches","textures/gui/line_bar_pixelated.png",0,153,90,9),5),
                Animation.verticalStrip(Texture.mod("hud/health_bar/line/pixelated/regenerating","textures/gui/line_bar_pixelated.png",0,54,90,9),11),
                Animation.verticalStrip(Texture.mod("hud/health_bar/line/pixelated/fulled","textures/gui/line_bar_pixelated.png",90,0,90,9),9)),
        ;

        private final Text text;
        private final String id;

        private final int jumpOffset;
        private final Animation regenAnim;
        private final Animation fulledAnim;
        private final boolean scissor;
        private final Texture full;
        private final Texture freeze;
        private final Texture fire;
        private final Texture empty;
        private final Texture delta;
        private final Texture blink;
        private final List<Texture> notches;

        LineStyle(Text text,boolean scissor, String id,int offset,Texture full,Texture freeze,Texture fire,Texture empty,Texture delta,Texture blink,List<Texture> notches,Animation regenAnim,Animation fulledAnim) {
            this.text = text;
            this.id = id;
            this.scissor = scissor;
            this.regenAnim = regenAnim;
            this.fulledAnim = fulledAnim;
            this.jumpOffset = offset;
            this.full = full;
            this.fire = fire;
            this.empty = empty;
            this.delta = delta;
            this.blink = blink;
            this.notches = notches;
            this.freeze = freeze;
        }

        LineStyle(Text text,boolean scissor, String id,Texture full,Texture freeze,Texture fire,Texture empty,Texture delta,Texture blink,List<Texture> notches,Animation regenAnim,Animation fulledAnim) {
            this.text = text;
            this.id = id;
            this.scissor = scissor;
            this.regenAnim = regenAnim;
            this.fulledAnim = fulledAnim;
            this.jumpOffset = 5;
            this.full = full;
            this.fire = fire;
            this.empty = empty;
            this.delta = delta;
            this.blink = blink;
            this.notches = notches;
            this.freeze = freeze;
        }

        public int jumpOffset() {
            return jumpOffset;
        }

        public Texture full(){
            return full;
        }

        public Texture freeze(){
            return freeze;
        }

        public Texture fire(){
            return fire;
        }

        public Texture empty(){
            return empty;
        }

        public Texture delta(){
            return delta;
        }

        public Texture blink(){
            return blink;
        }

        public Texture notches(int notches){
            return this.notches.get(notches-1);
        }

        public Animation fulledAnim() {
            return fulledAnim;
        }

        public Animation regenAnim() {
            return regenAnim;
        }

        public boolean scissor() {
            return scissor;
        }

        public Text text(){
            return text;
        }

        @Override
        public Text get() {
            return text;
        }

    }

}
