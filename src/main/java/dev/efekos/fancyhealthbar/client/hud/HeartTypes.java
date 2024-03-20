package dev.efekos.fancyhealthbar.client.hud;

import dev.efekos.fancyhealthbar.client.hud.heart.*;
import dev.efekos.fancyhealthbar.client.utils.HeartSpawner;

public class HeartTypes {

    public static final NormalHeartType NORMAL = new NormalHeartType();
    public static final NormalHardcoreHeartType HARDCORE_NORMAL = new NormalHardcoreHeartType();
    public static final PoisonHeartType POISON = new PoisonHeartType();
    public static final PoisonHardcoreHeartType HARDCORE_POISON = new PoisonHardcoreHeartType();
    public static final FrozenHeartType FROZEN = new FrozenHeartType();


    public static HeartSpawner get(boolean hardcore,boolean poison,boolean frozen) {
        if(hardcore){
            return poison ? HARDCORE_POISON : HARDCORE_NORMAL;
        } else {
            return poison ? POISON : (frozen?FROZEN:NORMAL);
        }
    }

}
