package dev.efekos.fancyhealthbar.client.object;

import dev.efekos.fancyhealthbar.client.utils.HudLocation;

public abstract class PhysicalHudObject implements HudObject{
    private HudLocation location;
    private HudLocation velocity;

    public abstract int getGravity();
    public abstract double getSlipperiness();


    public PhysicalHudObject(HudLocation location, HudLocation velocity) {
        this.location = location;
        this.velocity = velocity;
    }

    @Override
    public void tick() {
        location.add(velocity.getX(),-velocity.getY());

        int velocityX = velocity.getX();
        int velocityY = velocity.getY();

        velocityX = (int) (velocityX*getSlipperiness());
        velocityY -= getGravity();

        setVelocity(new HudLocation(velocityX,velocityY));

    }

    @Override
    public HudLocation getLocation() {
        return location;
    }

    public void setLocation(HudLocation location){
        this.location = location;
    }

    public HudLocation getVelocity(){
        return velocity;
    }

    public void setVelocity(HudLocation velocity) {
        this.velocity = velocity;
    }

    public void addVelocity(HudLocation velocity){
        this.velocity.add(velocity);
    }

    public void addVelocity(int x,int y){
        this.velocity.add(x,y);
    }
}
