package dev.efekos.fancyhealthbar.client.entity;

import net.minecraft.client.gui.DrawContext;
import org.joml.Vector2d;
import org.joml.Vector2i;

public abstract class HudEntity {

    protected Vector2d velocity = new Vector2d(0,0);
    protected Vector2d acceleration = new Vector2d(0,0);
    protected double x;
    protected double y;
    protected int lifetime;
    protected int maxLifetime = 100;
    private double drag;
    private int fadeIn;
    private int fadeOut;

    public HudEntity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public abstract void render(DrawContext context);

    public void tick(){
        lifetime++;
        if(hasPhysics()){
            x+=velocity.x;
            y-=velocity.y;

            velocity.x=velocity.x*acceleration.x;
            velocity.y+=acceleration.y;

            double d = (1-drag);
            acceleration.x*=d;
            acceleration.y*=d;
            velocity.x*=d;
            velocity.y*=d;

            if(Math.abs(acceleration.x)<=0.1)acceleration.x=0;
            if(Math.abs(acceleration.y)<=0.1)acceleration.y=0;
            if(Math.abs(velocity.x)<=0.1)velocity.x=0;
            if(Math.abs(velocity.y)<=0.1)velocity.y=0;
        }
    }

    public int getLifetime() {
        return lifetime;
    }

    public abstract Vector2i getAreaTaken();
    public abstract boolean hasPhysics();

    public int getFadeIn() {
        return fadeIn;
    }

    public int getFadeOut() {
        return fadeOut;
    }

    protected float getAlpha(){
        if(fadeIn==0&&fadeOut==0)return 1;
        if(lifetime<=fadeIn)return lifetime/(float)fadeIn;
        else if((maxLifetime-lifetime)<=fadeOut)return (maxLifetime-lifetime)/(float)fadeOut;
        else return 1;
    }

    public void setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
    }

    public void setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
    }

    public void setMaxLifetime(int maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public int getMaxLifetime() {
        return maxLifetime;
    }

    public double getDrag() {
        return drag;
    }

    public void setDrag(double drag) {
        this.drag = drag;
    }

    public boolean isVisible(int screenWidth, int screenHeight){
        Vector2i area = getAreaTaken();
        return x+area.x<=screenWidth || y+area.y<=screenHeight;
    }

    public void setAcceleration(Vector2d acceleration) {
        this.acceleration = acceleration;
    }

    public Vector2d getVelocity() {
        return velocity;
    }

    public HudEntity setVelocity(Vector2d velocity) {
        this.velocity = velocity;
        return this;
    }

    public void addVelocity(double x,double y){
        this.velocity.add(x,y);
    }

    public void addVelocity(Vector2d v){
        this.velocity.add(v);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}