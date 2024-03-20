package dev.efekos.fancyhealthbar.client.object;

import dev.efekos.fancyhealthbar.client.utils.HudLocation;
import net.minecraft.client.gui.DrawContext;

public interface HudObject {

    void draw(DrawContext context);

    HudLocation getLocation();

    void tick();
}
