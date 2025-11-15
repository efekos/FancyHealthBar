package dev.efekos.fancyhealthbar.client.compat;

public record BlinkingTextures(Texture normal,Texture blink) {

    public Texture get(boolean blinking){
        return blinking ? blink : normal;
    }

}