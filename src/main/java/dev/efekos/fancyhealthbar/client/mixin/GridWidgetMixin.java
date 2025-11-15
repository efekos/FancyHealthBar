package dev.efekos.fancyhealthbar.client.mixin;

import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.Clearable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(GridWidget.class)
public class GridWidgetMixin implements Clearable {

    @Shadow
    @Final
    private List<Widget> children;

    @Shadow
    @Final
    private List<GridWidget.Element> grids;

    @Unique
    public void clear(){
        this.children.clear();
        this.grids.clear();
    }

}