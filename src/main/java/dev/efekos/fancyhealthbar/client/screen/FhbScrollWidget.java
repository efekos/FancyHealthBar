package dev.efekos.fancyhealthbar.client.screen;

//? >=1.21.9
/*import net.minecraft.client.gui.Click;*/
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.LayoutWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class FhbScrollWidget extends ClickableWidget implements Drawable {

    private final LayoutWidget wrapped;
    private boolean focused = false;
    private Element focusedElement;
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
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if(!overlap())return false;
        scrollY=MathHelper.clamp(scrollY-(int)(verticalAmount*8),0,(wrapped.getHeight()-height));
        refreshScroll();
        return true;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if(!overlap())return false;
        scrollY=MathHelper.clamp(scrollY-(int)(amount*8),0,(wrapped.getHeight()-height));
        refreshScroll();
        return true;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        wrapped.forEachChild(clickableWidget -> {
            if(clickableWidget.isMouseOver(mouseX, mouseY)) clickableWidget.mouseMoved(mouseX, mouseY);
        });
    }

    //? <1.21.9 {
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        AtomicBoolean b = new AtomicBoolean(false);

        AtomicBoolean broke = new AtomicBoolean(false);
        wrapped.forEachChild(clickableWidget -> {
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
    public boolean mouseClicked(Click click, boolean doubled) {
        AtomicBoolean b = new AtomicBoolean(false);

        AtomicBoolean broke = new AtomicBoolean(false);
        wrapped.forEachChild(clickableWidget -> {
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
    public boolean mouseReleased(Click click) {
        if(focusedElement!=null){
            focusedElement.mouseReleased(click);
            focusedElement.setFocused(false);
            focusedElement=null;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        if(focusedElement!=null)return focusedElement.mouseDragged(click,offsetX,offsetY);
        return false;
    }
    *///?}

    private boolean overlap(){
        return wrapped.getHeight()>height;
    }

    public FhbScrollWidget(int width, int height, LayoutWidget wrapped) {
        super(0,0, width, height, ScreenTexts.EMPTY);
        this.wrapped = wrapped;
    }

    @Override
    public void setX(int x) {
        super.setX(x);
        wrapped.setX(getWidth()/2- wrapped.getWidth()/2);
        wrapped.refreshPositions();
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        wrapped.setY(y-scrollY);
        wrapped.refreshPositions();
    }

    private void refreshScroll(){
        wrapped.setY(getY()-scrollY);
        wrapped.refreshPositions();
    }

    @Override
    public void /*? <1.20.3 {*//*renderButton*//*?} else {*/renderWidget/*?}*/(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(getX(),getY()-5,getX()+getWidth(),getY()+getHeight()+5, ColorHelper/*? <1.21.2 {*/.Argb/*?}*/.getArgb(128,0,0,0));

        context.enableScissor(getX(),getY(),getX()+getWidth(),getY()+getHeight());
        wrapped.forEachElement(widget -> {
            if(widget instanceof Drawable d)d.render(context,mouseX,mouseY,delta);
        });
        context.disableScissor();
    }

    @Override
    public void forEachChild(Consumer<ClickableWidget> consumer) {

    }

}
