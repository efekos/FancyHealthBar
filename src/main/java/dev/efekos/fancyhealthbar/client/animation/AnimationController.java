package dev.efekos.fancyhealthbar.client.animation;

import net.minecraft.client.gui./*?>=26.1{*/GuiGraphicsExtractor/*?}else{*//*GuiGraphics*//*?}*/;

import java.util.ArrayList;
import java.util.List;

public class AnimationController {

    private final List<AnimationTrack> tracks = new ArrayList<AnimationTrack>();

    public void tick() {
        for (AnimationTrack track : tracks) track.tick();
    }

    public void draw(/*?>=26.1{*/GuiGraphicsExtractor/*?}else{*//*GuiGraphics*//*?}*/ context) {
        for (AnimationTrack track : tracks) track.draw(context);
    }

    public void addTrack(AnimationTrack track) {
        tracks.add(track);
    }

    public AnimationTrack createTrack(Animation animation) {
        return new AnimationTrack(animation);
    }

    public void reset() {
        tracks.clear();
    }

}
