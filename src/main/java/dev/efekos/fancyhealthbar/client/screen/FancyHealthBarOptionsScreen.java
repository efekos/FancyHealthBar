package dev.efekos.fancyhealthbar.client.screen;

import dev.efekos.fancyhealthbar.client.accessor.InGameHudRenderingAccessor;
import dev.efekos.fancyhealthbar.client.animation.AnimationController;
import dev.efekos.fancyhealthbar.client.compat.BlinkingTextures;
import dev.efekos.fancyhealthbar.client.compat.Texture;
import dev.efekos.fancyhealthbar.client.entity.HudEntityManager;
import dev.efekos.fancyhealthbar.client.options.FancyHealthBarOptions;
import dev.efekos.fancyhealthbar.client.options.HealthBarRenderingOptions;
import net.minecraft.client.MinecraftClient;
//?>=1.21.9
/*import net.minecraft.client.gui.Click;*/
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Clearable;

import java.util.ArrayList;
import java.util.List;

public class FancyHealthBarOptionsScreen extends Screen {

    private static final Text TITLE_TEXT = Text.translatable("options.fancyhealthbar.title");

    private final List<String> typeSet = new ArrayList<>(HealthBarRenderingOptions.TYPES.keySet());

    private GridWidget healthBars;
    private HudEntityManager manager;
    private AnimationController controller;
    private final FancyHealthBarOptions options;
    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this,40);
    private final Screen parent;

    public FancyHealthBarOptionsScreen(Screen parent,FancyHealthBarOptions options) {
        super(TITLE_TEXT);
        this.options = options;
        this.parent = parent;
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }

    protected void initTabNavigation() {
        scrollWidget.setWidth(width);
        scrollWidget.setHeight(height-80-27-24-16);
        scrollWidget.setX(0);
        layout.refreshPositions();
        healthBars.refreshPositions();
    }

    protected void refreshWidgetPositions() {
        scrollWidget.setWidth(width);
        scrollWidget.setHeight(height-80-27-24-16);
        scrollWidget.setX(0);
        layout.refreshPositions();
        healthBars.refreshPositions();
    }

    public static final BlinkingTextures LEFT_ARROW_TEXTURES = new BlinkingTextures(
            Texture.mod("widget/arrow_left","textures/gui/widgets.png",0,0,16,15),
            Texture.mod("widget/arrow_left_selected","textures/gui/widgets.png",16,0,16,15)
    );

    public static final BlinkingTextures RIGHT_ARROW_TEXTURES = new BlinkingTextures(
            Texture.mod("widget/arrow_right","textures/gui/widgets.png",32,0,16,15),
            Texture.mod("widget/arrow_right_selected","textures/gui/widgets.png",48,0,16,15)
            );

    private HealthBarWidget damage1;
    private HealthBarWidget damage2;
    private HealthBarWidget damage3;

    private TextWidget healthBarName;
    private FhbScrollWidget scrollWidget;
    private GridWidget.Adder adder;

    @Override
    protected void init() {
        manager = new HudEntityManager();
        controller = new AnimationController();

        healthBarName = new TextWidget(Text.literal("healthBarName"),textRenderer);
        healthBarName.setWidth(100);

        //? >=1.21
        /*layout.addHeader(TITLE_TEXT,textRenderer);*/
        //? <1.21
        layout.addHeader(new TextWidget(TITLE_TEXT,textRenderer));

        DirectionalLayoutWidget bodyLayout = new DirectionalLayoutWidget(0, 0, DirectionalLayoutWidget.DisplayAxis.VERTICAL).spacing(8);
        DirectionalLayoutWidget healthBarsWidget = new DirectionalLayoutWidget(0,0, DirectionalLayoutWidget.DisplayAxis.HORIZONTAL).spacing(8);
        healthBarsWidget.add(new FhbArrowButton(16,15,LEFT_ARROW_TEXTURES, this::previousRendering, Text.empty()),Positioner::alignVerticalCenter);
        healthBars = new GridWidget(0,0);
        healthBars.setColumnSpacing(8);
        healthBars.setRowSpacing(8);
        GridWidget.Adder adder = healthBars.createAdder(3);
        adder.add(new HealthBarWidget(manager,() -> false).setHealthValue(20));
        adder.add(new HealthBarWidget(manager,() -> false).setHealthValue(10));
        adder.add(new HealthBarWidget(manager,() -> false).setHealthValue(0));
        damage1 =adder.add(new HealthBarWidget(manager,this::shouldBlink).setHealthValue(20));
        damage2=adder.add(new HealthBarWidget(manager,this::shouldBlink).setHealthValue(10));
        damage3=adder.add(new HealthBarWidget(manager,this::shouldBlink).setHealthValue(5));
        healthBarsWidget.add(healthBars);
        healthBarsWidget.add(new FhbArrowButton(16,15,RIGHT_ARROW_TEXTURES, this::nextRendering, Text.empty()),Positioner::alignVerticalCenter);

        bodyLayout.add(healthBarsWidget,Positioner::alignHorizontalCenter);
        bodyLayout.add(healthBarName,Positioner::alignHorizontalCenter);
        FhbScrollWidget optionsGrid = createRenderingOptionsGrid();
        scrollWidget = optionsGrid;
        bodyLayout.add(optionsGrid);


        //? <1.20.2 {
        layout.addBody(bodyLayout,Positioner.create().alignHorizontalCenter());
        //?} else {
        /*layout.addBody(bodyLayout,Positioner::alignHorizontalCenter);
        *///?}

        layout.addFooter(ButtonWidget.builder(ScreenTexts.DONE, button -> close()).build());
        healthBars.forEachElement(this::add);
        layout.forEachChild(this::add);
        add(optionsGrid);
        updateRendering();
        //? >=1.21.6
        /*refreshWidgetPositions();*/
    }


    private FhbScrollWidget createRenderingOptionsGrid() {
        GridWidget grid = new GridWidget();
        grid.setColumnSpacing(4);
        grid.setRowSpacing(2);
        GridWidget.Adder adder = grid.createAdder(2);
        this.adder = adder;
        options.getRenderingOptions().fillOptions(adder);

        return new FhbScrollWidget(this.width,100,grid);
    }

    //? <1.21.9 {
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return this.getFocused() != null && this.isDragging() && (button == 0 || button == 1) && this.getFocused().mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for(Element element : this.children()) {
            if (element.mouseClicked(mouseX, mouseY, button)) {
                this.setFocused(element);
                if (button == 0||button==1) {
                    this.setDragging(true);
                }

                return true;
            }
        }

        return false;
    }
    //?} else {

    /*@Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        return this.getFocused() != null && this.isDragging() && (click.button()==0||click.button()==1) && this.getFocused().mouseDragged(click,offsetX,offsetY);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        for(Element element : this.children())
            if (element.mouseClicked(click,doubled)) {
                this.setFocused(element);
                if (click.button()==0||click.button()==1) this.setDragging(true);
                return true;
            }
        return false;
    }

    *///?}


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        //? <1.20.2
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        manager.render(context);
        controller.draw(context);
    }

    private void nextRendering(ButtonWidget widget) {
        int i = typeSet.indexOf(options.getSelectedRendering())+1;
        if(i>=typeSet.size())i=0;
        options.selectRendering(typeSet.get(i));
        updateRendering();
    }

    private void previousRendering(ButtonWidget widget) {
        int i = typeSet.indexOf(options.getSelectedRendering())-1;
        if(i<0)i=typeSet.size()-1;
        options.selectRendering(typeSet.get(i));
        updateRendering();
    }

    private void add(Widget widget){
        if(widget instanceof ClickableWidget cl)addSelectableChild(cl);
        if(widget instanceof Drawable d)addDrawable(d);
    }

    @Override
    public void removed() {
        options.write();
    }

    public boolean shouldBlink(){
        return this.jumpEnd > (long)this.ticks && (this.jumpEnd - (long)this.ticks) / 3L % 2L == 1L;
    }

    private int ticks;
    private int jumpEnd;

    @Override
    public void tick() {
        healthBars.forEachElement(widget -> ((HealthBarWidget) widget).tick());
        manager.tick();
        controller.tick();
        this.ticks++;
        if(this.ticks%100==0){
            damage1.setHealthValue(20);
            damage2.setHealthValue(10);
            damage3.setHealthValue(5);
            jumpEnd = this.ticks+10;
        } else if(this.ticks%50==0){
            damage1.setHealthValue(10);
            damage2.setHealthValue(5);
            damage3.setHealthValue(0);
            jumpEnd = this.ticks+20;
        }
    }

    private void updateRendering() {
        GridWidget gridWidget = adder.getGridWidget();
        gridWidget.forEachChild(this::remove);
        ((Clearable) (Object)adder).clear();
        options.getRenderingOptions().fillOptions(adder);
        layout.refreshPositions();
        manager.reset();
        controller.reset();
        healthBars.forEachElement(widget ->
            ((HealthBarWidget) widget).setRendering(this.options.getRenderingOptions().createRendering().initialize(manager,controller,client))
        );
        ((InGameHudRenderingAccessor) MinecraftClient.getInstance().inGameHud).fhb$setRendering(options.getRenderingOptions().createRendering());
        healthBarName.setMessage(Text.translatable("options.fancyhealthbar.rendering."+this.options.getSelectedRendering()));
    }

}