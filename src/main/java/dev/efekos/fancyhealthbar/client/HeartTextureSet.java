package dev.efekos.fancyhealthbar.client;

import dev.efekos.fancyhealthbar.client.compat.Texture;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public record HeartTextureSet(Texture full, Texture fullBlinking, Texture half, Texture halfBlinking,
                              Texture hardcoreFull, Texture hardcoreFullBlinking, Texture hardcoreHalf,
                              Texture hardcoreHalfBlinking) {

    public static final HeartTextureSet CONTAINER = new HeartTextureSet(
            Texture.vanilla("hud/heart/container","textures/gui/icons.png",16,0,9),
            Texture.vanilla("hud/heart/container_blinking","textures/gui/icons.png",25,0,9),
            Texture.vanilla("hud/heart/container","textures/gui/icons.png",16,0,9),
            Texture.vanilla("hud/heart/container_blinking","textures/gui/icons.png",25,0,9),
            Texture.vanilla("hud/heart/container_hardcore","textures/gui/icons.png",16,45,9),
            Texture.vanilla("hud/heart/container_hardcore_blinking","textures/gui/icons.png",25,45,9),
            Texture.vanilla("hud/heart/container_hardcore","textures/gui/icons.png",16,45,9),
            Texture.vanilla("hud/heart/container_hardcore_blinking","textures/gui/icons.png",25,45,9)
    );
    public static final HeartTextureSet NORMAL = new HeartTextureSet(
            Texture.vanilla("hud/heart/full","textures/gui/icons.png",52,0,9),
            Texture.vanilla("hud/heart/full_blinking","textures/gui/icons.png",70,0,9),
            Texture.vanilla("hud/heart/half","textures/gui/icons.png",61,0,9),
            Texture.vanilla("hud/heart/half_blinking","textures/gui/icons.png",79,0,9),
            Texture.vanilla("hud/heart/hardcore_full","textures/gui/icons.png",52,45,9),
            Texture.vanilla("hud/heart/hardcore_full_blinking","textures/gui/icons.png",70,45,9),
            Texture.vanilla("hud/heart/hardcore_half","textures/gui/icons.png",61,45,9),
            Texture.vanilla("hud/heart/hardcore_half_blinking","textures/gui/icons.png",79,45,9)
    );
    public static final HeartTextureSet POISONED = new HeartTextureSet(
            Texture.vanilla("hud/heart/poisoned_full","textures/gui/icons.png",88,0,9),
            Texture.vanilla("hud/heart/poisoned_full_blinking","textures/gui/icons.png",106,0,9),
            Texture.vanilla("hud/heart/poisoned_half","textures/gui/icons.png",97,0,9),
            Texture.vanilla("hud/heart/poisoned_half_blinking","textures/gui/icons.png",115,0,9),
            Texture.vanilla("hud/heart/poisoned_hardcore_full","textures/gui/icons.png",88,45,9),
            Texture.vanilla("hud/heart/poisoned_hardcore_full_blinking","textures/gui/icons.png",106,45,9),
            Texture.vanilla("hud/heart/poisoned_hardcore_half","textures/gui/icons.png",97,45,9),
            Texture.vanilla("hud/heart/poisoned_hardcore_half_blinking","textures/gui/icons.png",115,45,9)
    );
    public static final HeartTextureSet WITHERED = new HeartTextureSet(
            Texture.vanilla("hud/heart/withered_full","textures/gui/icons.png",124,0,9),
            Texture.vanilla("hud/heart/withered_full_blinking","textures/gui/icons.png",142,0,9),
            Texture.vanilla("hud/heart/withered_half","textures/gui/icons.png",133,0,9),
            Texture.vanilla("hud/heart/withered_half_blinking","textures/gui/icons.png",151,0,9),
            Texture.vanilla("hud/heart/withered_hardcore_full","textures/gui/icons.png",124,45,9),
            Texture.vanilla("hud/heart/withered_hardcore_full_blinking","textures/gui/icons.png",142,45,9),
            Texture.vanilla("hud/heart/withered_hardcore_half","textures/gui/icons.png",133,45,9),
            Texture.vanilla("hud/heart/withered_hardcore_half_blinking","textures/gui/icons.png",151,45,9)
    );
    public static final HeartTextureSet ABSORBING = new HeartTextureSet(
            Texture.vanilla("hud/heart/absorbing_full","textures/gui/icons.png",160,0,9),
            Texture.vanilla("hud/heart/absorbing_full_blinking","textures/gui/icons.png",160,0,9),
            Texture.vanilla("hud/heart/absorbing_half","textures/gui/icons.png",169,0,9),
            Texture.vanilla("hud/heart/absorbing_half_blinking","textures/gui/icons.png",169,0,9),
            Texture.vanilla("hud/heart/absorbing_hardcore_full","textures/gui/icons.png",160,45,9),
            Texture.vanilla("hud/heart/absorbing_hardcore_full_blinking","textures/gui/icons.png",160,45,9),
            Texture.vanilla("hud/heart/absorbing_hardcore_half","textures/gui/icons.png",169,45,9),
            Texture.vanilla("hud/heart/absorbing_hardcore_half_blinking","textures/gui/icons.png",169,45,9)
    );
    public static final HeartTextureSet FROZEN = new HeartTextureSet(
            Texture.vanilla("hud/heart/frozen_full","textures/gui/icons.png",178,0,9),
            Texture.vanilla("hud/heart/frozen_full_blinking","textures/gui/icons.png",178,0,9),
            Texture.vanilla("hud/heart/frozen_half","textures/gui/icons.png",187,0,9),
            Texture.vanilla("hud/heart/frozen_half_blinking","textures/gui/icons.png",187,0,9),
            Texture.vanilla("hud/heart/frozen_hardcore_full","textures/gui/icons.png",178,45,9),
            Texture.vanilla("hud/heart/frozen_hardcore_full_blinking","textures/gui/icons.png",178,45,9),
            Texture.vanilla("hud/heart/frozen_hardcore_half","textures/gui/icons.png",187,45,9),
            Texture.vanilla("hud/heart/frozen_hardcore_half_blinking","textures/gui/icons.png",187,45,9)
    );

    public static Map<String,HeartTextureSet> knownSets = new HashMap<>();

    public static HeartTextureSet[] values(){
        return new HeartTextureSet[]{CONTAINER,NORMAL,POISONED,WITHERED,ABSORBING,FROZEN};
    }

    public static HeartTextureSet tryGetFromPlayer(PlayerEntity player) {
        InGameHud.HeartType type = InGameHud.HeartType.fromPlayerState(player);
        return switch (type){
            case CONTAINER->CONTAINER;
            case NORMAL->NORMAL;
            case POISONED->POISONED;
            case WITHERED->WITHERED;
            case ABSORBING->ABSORBING;
            case FROZEN->FROZEN;
        };
    }

    public Texture getTexture(boolean hardcore, boolean half, boolean blinking) {
        if(blinking){
            return hardcore? (half?hardcoreHalfBlinking:hardcoreFullBlinking):(half?halfBlinking:fullBlinking);
        } else
            return hardcore? (half?hardcoreHalf:hardcoreFull):(half?half():full);

    }

}