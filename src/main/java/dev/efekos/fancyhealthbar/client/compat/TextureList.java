package dev.efekos.fancyhealthbar.client.compat;

import java.util.ArrayList;
import java.util.List;

public interface TextureList {

    static List<Texture> verticalStrip(Texture texture,int frames){
        List<Texture> list = new ArrayList<>();
        int size = texture.atlasPosition().v2() - texture.atlasPosition().v1();
        for (int i = 1; i <= frames; i++) {
            Texture texture1 = new Texture(texture.baseId().withSuffixedPath("/" + i), texture.atlasId(),
                    texture.atlasPosition().add(0, (i - 1) * size),
                    texture.nineSlice());
            list.add(texture1);
        }
        return list;
    }

}