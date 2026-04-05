package dev.efekos.fancyhealthbar.client.mixin;

import dev.efekos.fancyhealthbar.client.accessor.ClearMethod;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.world.Clearable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GridLayout.RowHelper.class)
public abstract class GridAdderMixin implements ClearMethod {

    @Shadow
    private int index;

    @Shadow
    public abstract GridLayout getGrid();

    @Override
    public void clear() {
        ((ClearMethod) this.getGrid()).clear();
        this.index = 0;
    }

}
