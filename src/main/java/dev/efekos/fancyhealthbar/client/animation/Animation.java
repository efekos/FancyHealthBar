package dev.efekos.fancyhealthbar.client.animation;

import dev.efekos.fancyhealthbar.client.compat.Texture;
import dev.efekos.fancyhealthbar.client.compat.TextureList;

import java.util.List;

public record Animation(List<Texture> textures, int maxFrames, int width, int height) {

    public static Animation verticalStrip(Texture texture,int frames){
        return  new Animation(TextureList.verticalStrip(texture,frames),frames,
                texture.atlasPosition().u2()-texture.atlasPosition().u1(),
                texture.atlasPosition().v2()-texture.atlasPosition().v1());
    }

    public Texture getTexture(int frame) {
        return textures.get(frame);
    }

}
