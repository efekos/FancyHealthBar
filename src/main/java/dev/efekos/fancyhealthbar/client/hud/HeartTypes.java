package dev.efekos.fancyhealthbar.client.hud;

import dev.efekos.fancyhealthbar.client.hud.heart.NormalHardcoreHeartType;
import dev.efekos.fancyhealthbar.client.hud.heart.NormalHeartType;
import dev.efekos.fancyhealthbar.client.hud.heart.PoisonHardcoreHeartType;
import dev.efekos.fancyhealthbar.client.hud.heart.PoisonHeartType;
import dev.efekos.fancyhealthbar.client.utils.HeartSpawner;

public class HeartTypes {

    public static final NormalHeartType NORMAL = new NormalHeartType();
    public static final NormalHardcoreHeartType HARDCORE_NORMAL = new NormalHardcoreHeartType();
    public static final PoisonHeartType POISON = new PoisonHeartType();
    public static final PoisonHardcoreHeartType HARDCORE_POISON = new PoisonHardcoreHeartType();

    public static HeartSpawner get(boolean hardcore,boolean poison) {
        if(hardcore){
            return poison ? HARDCORE_POISON : HARDCORE_NORMAL;
        } else {
            return poison ? POISON : NORMAL;
        }
    }

}
