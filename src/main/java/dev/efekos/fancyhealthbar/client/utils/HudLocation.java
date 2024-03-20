package dev.efekos.fancyhealthbar.client.utils;

public class HudLocation {
    private int x;
    private int y;

    public HudLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public HudLocation getAbove(int step) {
        return new HudLocation(x, y + step);
    }

    public HudLocation getBelow(int step) {
        return new HudLocation(x, y - step);
    }

    public HudLocation getLeft(int step) {
        return new HudLocation(x - step, y);
    }

    public HudLocation getRight(int step) {
        return new HudLocation(x + step, y);
    }

    public HudLocation clone() {
        return new HudLocation(x, y);
    }

    public void add(HudLocation location) {
        this.x += location.x;
        this.y += location.y;
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
