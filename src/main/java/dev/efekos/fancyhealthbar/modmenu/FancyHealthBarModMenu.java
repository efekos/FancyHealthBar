package dev.efekos.fancyhealthbar.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.efekos.fancyhealthbar.client.config.FancyHealthBarConfig;

import java.util.Map;

public class FancyHealthBarModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return FancyHealthBarConfig::createScreen;
    }
}