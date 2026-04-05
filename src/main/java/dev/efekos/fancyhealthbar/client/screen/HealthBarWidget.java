package dev.efekos.fancyhealthbar.client.screen;
//~if>=26.1 'GuiGraphics' -> 'GuiGraphicsExtractor' {
import dev.efekos.fancyhealthbar.client.entity.HudEntityManager;
import dev.efekos.fancyhealthbar.client.rendering.HealthBarRendering;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.util.RandomSource;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class HealthBarWidget implements LayoutElement, Renderable {

    private int x;
    private int y;
    private int healthValue;
    private HealthBarRendering rendering;
    private int width;
    private int height;
    private boolean hardcore;
    private final Supplier<Boolean> blinkingSupplier;
    private int lastHealth;
    private final RandomSource random = RandomSource.create();
    private final HudEntityManager entityManager;

    public HealthBarWidget(HudEntityManager entityManager,Supplier<Boolean> blinkingSupplier) {
        this.entityManager = entityManager;
        setWidth(80);
        setHeight(9);
        this.blinkingSupplier = blinkingSupplier;
    }

    public void setRendering(HealthBarRendering rendering) {
        this.rendering = rendering;
    }

    @Override
    public void /*?>=26.1{*/extractRenderState/*?}else{*//*render*//*?}*/(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        rendering.drawPreview(random,context,x,y,1,lastHealth,healthValue,blinkingSupplier.get(),hardcore);
    }

    public boolean isHardcore() {
        return hardcore;
    }

    public void setHardcore(boolean hardcore) {
        this.hardcore = hardcore;
    }

    public void tick(){
        if(lastHealth!=healthValue){
            if(healthValue<lastHealth)rendering.onDamage(entityManager,x,y,1,-1,20f,lastHealth,healthValue,0,false);
            lastHealth=healthValue;
        }
    }

    public HealthBarWidget setHealthValue(int healthValue) {
        this.healthValue = healthValue;
        return this;
    }

    public int getHealthValue() {
        return healthValue;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void visitWidgets(Consumer<AbstractWidget> consumer) {

    }
}
//~}