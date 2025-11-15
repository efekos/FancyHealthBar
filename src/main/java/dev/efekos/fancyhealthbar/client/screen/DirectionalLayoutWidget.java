package dev.efekos.fancyhealthbar.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.LayoutWidget;
import net.minecraft.client.gui.widget.Positioner;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.Util;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class DirectionalLayoutWidget implements LayoutWidget {
    public final GridWidget grid;
    private final DisplayAxis axis;
    private int currentIndex;

    private DirectionalLayoutWidget(DisplayAxis axis) {
        this(0, 0, axis);
    }

    public DirectionalLayoutWidget(int x, int y, DisplayAxis axis) {
        this.currentIndex = 0;
        this.grid = new GridWidget(x, y);
        this.axis = axis;
    }

    public DirectionalLayoutWidget spacing(int spacing) {
        this.axis.setSpacing(this.grid, spacing);
        return this;
    }

    public Positioner copyPositioner() {
        return this.grid.copyPositioner();
    }

    public Positioner getMainPositioner() {
        return this.grid.getMainPositioner();
    }

    public <T extends Widget> T add(T widget, Positioner positioner) {
        return this.axis.add(this.grid, widget, this.currentIndex++, positioner);
    }

    public <T extends Widget> T add(T widget) {
        return this.add(widget, this.copyPositioner());
    }

    public <T extends Widget> T add(T widget, Consumer<Positioner> callback) {
        return this.axis.add(this.grid, widget, this.currentIndex++, Util.make(this.copyPositioner(), callback));
    }

    public void forEachElement(Consumer<Widget> consumer) {
        this.grid.forEachElement(consumer);
    }

    public void refreshPositions() {
        this.grid.refreshPositions();
    }

    public int getWidth() {
        return this.grid.getWidth();
    }

    public int getHeight() {
        return this.grid.getHeight();
    }

    public void setX(int x) {
        this.grid.setX(x);
    }

    public void setY(int y) {
        this.grid.setY(y);
    }

    public int getX() {
        return this.grid.getX();
    }

    public int getY() {
        return this.grid.getY();
    }

    public static DirectionalLayoutWidget vertical() {
        return new DirectionalLayoutWidget(DirectionalLayoutWidget.DisplayAxis.VERTICAL);
    }

    public static DirectionalLayoutWidget horizontal() {
        return new DirectionalLayoutWidget(DirectionalLayoutWidget.DisplayAxis.HORIZONTAL);
    }

    @Environment(EnvType.CLIENT)
    public enum DisplayAxis {
        HORIZONTAL,
        VERTICAL;

        void setSpacing(GridWidget grid, int spacing) {
            switch (this) {
                case HORIZONTAL -> grid.setColumnSpacing(spacing);
                case VERTICAL -> grid.setRowSpacing(spacing);
            }

        }

        public <T extends Widget> T add(GridWidget grid, T widget, int index, Positioner positioner) {
            T var10000;
            switch (this) {
                case HORIZONTAL -> var10000 = grid.add(widget, 0, index, positioner);
                case VERTICAL -> var10000 = grid.add(widget, index, 0, positioner);
                default -> throw new IncompatibleClassChangeError();
            }

            return var10000;
        }
    }
}

