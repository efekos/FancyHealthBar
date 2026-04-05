package dev.efekos.fancyhealthbar.client.mixin;

import dev.efekos.fancyhealthbar.client.accessor.ClearMethod;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.world.Clearable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(GridLayout.class)
public class GridLayoutMixin implements ClearMethod {

    @Shadow
    @Final
    private List<GridLayout> children;

    @Shadow
    @Final
    private List<GridLayout.CellInhabitant> cellInhabitants;

    @Unique
    public void clear(){
        this.children.clear();
        this.cellInhabitants.clear();
    }

}