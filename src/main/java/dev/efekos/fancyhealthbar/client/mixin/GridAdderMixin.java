package dev.efekos.fancyhealthbar.client.mixin;

import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.util.Clearable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GridWidget.Adder.class)
public abstract class GridAdderMixin implements Clearable {

    @Shadow
    private int totalOccupiedColumns;

    @Shadow
    public abstract GridWidget getGridWidget();

    @Override
    public void clear() {
        ((Clearable) this.getGridWidget()).clear();
        this.totalOccupiedColumns = 0;
    }

}
