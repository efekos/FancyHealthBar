package dev.efekos.fancyhealthbar.client.entity;

import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public class HudEntityManager {

    private final List<HudEntity> entities = new ArrayList<>();

    public void tick(){
        for (HudEntity entity : new ArrayList<>(entities)) {
            if(entity.getLifetime()<entity.getMaxLifetime())entity.tick();
            else entities.remove(entity);
        }
    }

    public void render(DrawContext context){
        for (HudEntity hud : entities) hud.render(context);
    }

    public void addEntity(HudEntity entity){
        entities.add(entity);
    }

    public void AddEntities(List<HudEntity> entities){
        this.entities.addAll(entities);
    }

    public void removeEntity(HudEntity entity){
        entities.remove(entity);
    }

    public void removeEntities(List<HudEntity> entities){
        this.entities.removeAll(entities);
    }

    public List<HudEntity> getEntities() {
        return entities;
    }

    public void reset() {
        entities.clear();
    }

}
