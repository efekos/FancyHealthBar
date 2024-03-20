package dev.efekos.fancyhealthbar.client.hud;

import dev.efekos.fancyhealthbar.client.hud.heart.NormalHardcoreHeartType;
import dev.efekos.fancyhealthbar.client.hud.heart.NormalHeartType;
import dev.efekos.fancyhealthbar.client.utils.HeartSpawner;

public class HeartTypes {

    public static final NormalHeartType NORMAL = new NormalHeartType();
    public static final NormalHardcoreHeartType HARDCORE_NORMAL = new NormalHardcoreHeartType();

    public static HeartSpawner get(boolean hardcore) {
        return hardcore ? HARDCORE_NORMAL : NORMAL;
    }

}
