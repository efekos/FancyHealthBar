package dev.efekos.fancyhealthbar.client.mixin;

import dev.efekos.fancyhealthbar.client.FancyHealthBarClient;
import dev.efekos.fancyhealthbar.client.hud.FancyHealthHud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class ClientPlayerEntityMixin {

    @Shadow public abstract float getHealth();

    @Shadow public abstract float getMaxHealth();

    @Inject(method = "setHealth",at = @At("HEAD"))
    public void damageMixin(float health, CallbackInfo ci){
        float newHealth = MathHelper.clamp(health, 0, getMaxHealth());
        FancyHealthBarClient.FANCY_HEALTH_HUD.onDamage(getHealth(),newHealth);
    }

}
