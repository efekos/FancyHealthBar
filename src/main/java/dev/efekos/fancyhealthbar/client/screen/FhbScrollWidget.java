package dev.efekos.fancyhealthbar.client.screen;

//? >=1.21.9
/*import net.minecraft.client.input.MouseButtonEvent;*/
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.layouts.Layout;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.util.Mth;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class FhbScrollWidget extends AbstractWidget implements Renderable {

    private final Layout wrapped;
    private boolean focused = false;
    private GuiEventListener focusedElement;
    private int scrollY;

    @Override
    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean isFocused() {
        return focused;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if(!overlap())return false;
        scrollY= Mth.clamp(scrollY-(int)(verticalAmount*8),0,(wrapped.getHeight()-height));
        refreshScroll();
        return true;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if(!overlap())return false;
        scrollY= Mth.clamp(scrollY-(int)(amount*8),0,(wrapped.getHeight()-height));
        refreshScroll();
        return true;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        wrapped.visitWidgets(clickableWidget -> {
            if(clickableWidget.isMouseOver(mouseX, mouseY)) clickableWidget.mouseMoved(mouseX, mouseY);
        });
    }

    //? <1.21.9 {
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        AtomicBoolean b = new AtomicBoolean(false);

        AtomicBoolean broke = new AtomicBoolean(false);
        wrapped.visitWidgets(clickableWidget -> {
            if(broke.get())return;
            if(clickableWidget.isMouseOver(mouseX, mouseY)){
                b.set(clickableWidget.mouseClicked(mouseX, mouseY, button));
                clickableWidget.setFocused(true);
                focusedElement = clickableWidget;
                broke.set(true);
            }
        });
        return b.get();
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(focusedElement!=null){
            focusedElement.mouseReleased(mouseX, mouseY, button);
            focusedElement.setFocused(false);
            focusedElement=null;
        }
        return false;
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if(focusedElement!=null)return focusedElement.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return false;
    }
    //?} else {


    /*@Override
    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
        AtomicBoolean b = new AtomicBoolean(false);

        AtomicBoolean broke = new AtomicBoolean(false);
        wrapped.visitWidgets(clickableWidget -> {
            if(broke.get())return;
            if(clickableWidget.isMouseOver(click.x(),click.y())){
                b.set(clickableWidget.mouseClicked(click,doubled));
                clickableWidget.setFocused(true);
                focusedElement = clickableWidget;
                broke.set(true);
            }
        });
        return b.get();
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent click) {
        if(focusedElement!=null){
            focusedElement.mouseReleased(click);
            focusedElement.setFocused(false);
            focusedElement=null;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent click, double offsetX, double offsetY) {
        if(focusedElement!=null)return focusedElement.mouseDragged(click,offsetX,offsetY);
        return false;
    }
    *///?}

    private boolean overlap(){
        return wrapped.getHeight()>height;
    }

    public FhbScrollWidget(int width, int height, Layout wrapped) {
        super(0,0, width, height, CommonComponents.EMPTY);
        this.wrapped = wrapped;
    }

    @Override
    public void setX(int x) {
        super.setX(x);
        wrapped.setX(getWidth()/2- wrapped.getWidth()/2);
        wrapped.arrangeElements();
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        wrapped.setY(y-scrollY);
        wrapped.arrangeElements();
    }

    private void refreshScroll(){
        wrapped.setY(getY()-scrollY);
        wrapped.arrangeElements();
    }

    @Override
    public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
        context.fill(getX(),getY()-5,getX()+getWidth(),getY()+getHeight()+5, 128 << 24);

        context.enableScissor(getX(),getY(),getX()+getWidth(),getY()+getHeight());
        wrapped.visitChildren(widget -> {
            if(widget instanceof Renderable d)d.render(context,mouseX,mouseY,delta);
        });
        context.disableScissor();
    }

    @Override
    public void visitWidgets(Consumer<AbstractWidget> consumer) {

    }

    public void setScrollY(int y) {
        scrollY = y;
        refreshScroll();
    }

}
