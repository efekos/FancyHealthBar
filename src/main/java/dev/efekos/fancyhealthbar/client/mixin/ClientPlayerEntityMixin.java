package dev.efekos.fancyhealthbar.client.mixin;

import com.mojang.authlib.GameProfile;
import dev.efekos.fancyhealthbar.client.FancyHealthBarClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "damage",at = @At("HEAD"))
    public void damageMixin(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        FancyHealthBarClient.FANCY_HEALTH_HUD.onDamage(getHealth(),getHealth()-amount);
    }

}
