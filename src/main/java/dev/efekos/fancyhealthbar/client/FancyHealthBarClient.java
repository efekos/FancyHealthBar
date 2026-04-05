package dev.efekos.fancyhealthbar.client;

import dev.efekos.fancyhealthbar.client.compat.CompatKey;
import net.fabricmc.api.ClientModInitializer;

public class FancyHealthBarClient implements ClientModInitializer {

    public static final String MOD_ID = "fancyhealthbar";

    public static CompatKey id(String path) {
        return CompatKey.of(MOD_ID, path);
    }

    public static CompatKey vanillaId(String path){
         return CompatKey.minecraft(path);
    }

    @Override
    public void onInitializeClient() {


    }

}