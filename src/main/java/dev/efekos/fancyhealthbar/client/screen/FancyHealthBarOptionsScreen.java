package dev.efekos.fancyhealthbar.client.screen;

import dev.efekos.fancyhealthbar.client.accessor.ClearMethod;
import dev.efekos.fancyhealthbar.client.accessor.InGameHudRenderingAccessor;
import dev.efekos.fancyhealthbar.client.animation.AnimationController;
import dev.efekos.fancyhealthbar.client.compat.BlinkingTextures;
import dev.efekos.fancyhealthbar.client.compat.Texture;
import dev.efekos.fancyhealthbar.client.entity.HudEntityManager;
import dev.efekos.fancyhealthbar.client.options.FancyHealthBarOptions;
import dev.efekos.fancyhealthbar.client.options.HealthBarRenderingOptions;
import net.minecraft.client.Minecraft;
//? >=1.21.9
/*import net.minecraft.client.input.MouseButtonEvent;*/
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Clearable;

import java.util.ArrayList;
import java.util.List;

public class FancyHealthBarOptionsScreen extends Screen {

    private static final Component TITLE_TEXT = Component.translatable("options.fancyhealthbar.title");

    private final List<String> typeSet = new ArrayList<>(HealthBarRenderingOptions.TYPES.keySet());

    private GridLayout healthBars;
    private HudEntityManager manager;
    private AnimationController controller;
    private final FancyHealthBarOptions options;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this,40);
    private final Screen parent;

    public FancyHealthBarOptionsScreen(Screen parent, FancyHealthBarOptions options) {
        super(TITLE_TEXT);
        this.options = options;
        this.parent = parent;
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(parent);
    }

    protected void repositionElements() {
        scrollWidget.setWidth(width);
        scrollWidget.setHeight(height-80-27-24-16);
        scrollWidget.setX(0);
        layout.arrangeElements();
        healthBars.arrangeElements();
    }

    protected void refreshWidgetPositions() {
        scrollWidget.setWidth(width);
        scrollWidget.setHeight(height-80-27-24-16);
        scrollWidget.setX(0);
        layout.arrangeElements();
        healthBars.arrangeElements();
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

    private StringWidget healthBarName;
    private FhbScrollWidget scrollWidget;
    private GridLayout.RowHelper adder;

    @Override
    protected void init() {
        manager = new HudEntityManager();
        controller = new AnimationController();

        healthBarName = new StringWidget(Component.literal("healthBarName"),font);
        healthBarName.setWidth(100);

        //? >=1.21
        layout.addTitleHeader(TITLE_TEXT,font);
        //? <1.21
        /*layout.addToHeader(new StringWidget(TITLE_TEXT,font));*/

        DirectionalLayoutWidget bodyLayout = new DirectionalLayoutWidget(0, 0, DirectionalLayoutWidget.Orientation.VERTICAL).spacing(8);
        DirectionalLayoutWidget healthBarsWidget = new DirectionalLayoutWidget(0,0, DirectionalLayoutWidget.Orientation.HORIZONTAL).spacing(8);
        healthBarsWidget.addChild(new FhbArrowButton(16,15,LEFT_ARROW_TEXTURES, this::previousRendering, Component.empty()),LayoutSettings::alignVerticallyMiddle);
        healthBars = new GridLayout(0,0);
        healthBars.columnSpacing(8);
        healthBars.rowSpacing(8);
        GridLayout.RowHelper adder = healthBars.createRowHelper(3);
        adder.addChild(new HealthBarWidget(manager,() -> false).setHealthValue(20));
        adder.addChild(new HealthBarWidget(manager,() -> false).setHealthValue(10));
        adder.addChild(new HealthBarWidget(manager,() -> false).setHealthValue(0));
        damage1 =adder.addChild(new HealthBarWidget(manager,this::shouldBlink).setHealthValue(20));
        damage2=adder.addChild(new HealthBarWidget(manager,this::shouldBlink).setHealthValue(10));
        damage3=adder.addChild(new HealthBarWidget(manager,this::shouldBlink).setHealthValue(5));
        healthBarsWidget.addChild(healthBars);
        healthBarsWidget.addChild(new FhbArrowButton(16,15,RIGHT_ARROW_TEXTURES, this::nextRendering, Component.empty()),LayoutSettings::alignVerticallyMiddle);

        bodyLayout.addChild(healthBarsWidget, LayoutSettings::alignHorizontallyCenter);
        bodyLayout.addChild(healthBarName,LayoutSettings::alignHorizontallyCenter);
        FhbScrollWidget optionsGrid = createRenderingOptionsGrid();
        scrollWidget = optionsGrid;
        bodyLayout.addChild(optionsGrid);


        //? <1.20.2 {
        /*layout.addToContents(bodyLayout,LayoutSettings.defaults().alignHorizontallyCenter());
        *///?} else {
        layout.addToContents(bodyLayout,LayoutSettings::alignHorizontallyCenter);
        //?}

        layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> onClose()).build());
        healthBars.visitChildren(this::add);
        layout.visitWidgets(this::add);
        add(optionsGrid);
        updateRendering();
        //? >=1.21.6
        /*refreshWidgetPositions();*/
    }


    private FhbScrollWidget createRenderingOptionsGrid() {
        GridLayout grid = new GridLayout();
        grid.columnSpacing(4);
        grid.rowSpacing(2);
        GridLayout.RowHelper adder = grid.createRowHelper(2);
        this.adder = adder;
        options.getRenderingOptions().fillOptions(adder);

        return new FhbScrollWidget(this.width,100,grid);
    }

    //? <1.21.9 {
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return this.getFocused() != null && this.isDragging() && (button == 0 || button == 1) && this.getFocused().mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for(GuiEventListener element : this.children()) {
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
    public boolean mouseDragged(MouseButtonEvent click, double offsetX, double offsetY) {
        return this.getFocused() != null && this.isDragging() && (click.button()==0||click.button()==1) && this.getFocused().mouseDragged(click,offsetX,offsetY);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
        for(GuiEventListener element : this.children())
            if (element.mouseClicked(click,doubled)) {
                this.setFocused(element);
                if (click.button()==0||click.button()==1) this.setDragging(true);
                return true;
            }
        return false;
    }

    *///?}


    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        //? <1.20.2
        /*this.renderBackground(context);*/
        super.render(context, mouseX, mouseY, delta);
        manager.render(context);
        controller.draw(context);
    }

    private void nextRendering(Button widget) {
        int i = typeSet.indexOf(options.getSelectedRendering())+1;
        if(i>=typeSet.size())i=0;
        options.selectRendering(typeSet.get(i));
        updateRendering();
    }

    private void previousRendering(Button widget) {
        int i = typeSet.indexOf(options.getSelectedRendering())-1;
        if(i<0)i=typeSet.size()-1;
        options.selectRendering(typeSet.get(i));
        updateRendering();
    }

    private void add(LayoutElement widget){
        if(widget instanceof AbstractWidget cl)addWidget(cl);
        if(widget instanceof Renderable d)addRenderableOnly(d);
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
        healthBars.visitChildren(widget -> ((HealthBarWidget) widget).tick());
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
        GridLayout gridWidget = adder.getGrid();
        gridWidget.visitWidgets(this::removeWidget);
        ((ClearMethod) (Object)adder).clear();
        options.getRenderingOptions().fillOptions(adder);
        layout.arrangeElements();
        manager.reset();
        controller.reset();
        healthBars.visitChildren(widget ->
            ((HealthBarWidget) widget).setRendering(this.options.getRenderingOptions().createRendering().initialize(manager,controller,minecraft))
        );
        ((InGameHudRenderingAccessor) Minecraft.getInstance().gui).fhb$setRendering(options.getRenderingOptions().createRendering());
        healthBarName.setMessage(Component.translatable("options.fancyhealthbar.rendering."+this.options.getSelectedRendering()));
        scrollWidget.setScrollY(0);
    }

}