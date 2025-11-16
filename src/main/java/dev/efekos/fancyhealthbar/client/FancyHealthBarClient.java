package dev.efekos.fancyhealthbar.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

public class FancyHealthBarClient implements ClientModInitializer {

    public static final String MOD_ID = "fancyhealthbar";

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static Identifier vanillaId(String path){
        //? <1.21
        /*return Identifier.of("minecraft",path);*/
        //? >=1.21
         return Identifier.ofVanilla(path);
    }

    @Override
    public void onInitializeClient() {


    }

}