package dev.efekos.fancyhealthbar.client.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.efekos.fancyhealthbar.client.accessor.FhbOptionsAccessor;
import dev.efekos.fancyhealthbar.client.screen.FancyHealthBarOptionsScreen;
import net.minecraft.client.Minecraft;

public class FancyHealthBarModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return s->new FancyHealthBarOptionsScreen(s,((FhbOptionsAccessor) Minecraft.getInstance()).fhb$getOptions());
    }

}