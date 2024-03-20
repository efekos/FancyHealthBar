package dev.efekos.fancyhealthbar.client.mixin;

import dev.efekos.fancyhealthbar.client.FancyHealthBarClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends LivingEntity{

    public ClientPlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "applyDamage",at = @At("HEAD"))
    public void onDamage(DamageSource damageSource, float damage, CallbackInfo ci){
        FancyHealthBarClient.FANCY_HEALTH_HUD.onDamage(MathHelper.clamp(getHealth(),0,20),MathHelper.clamp(getHealth()-damage,0,20));
    }

}
