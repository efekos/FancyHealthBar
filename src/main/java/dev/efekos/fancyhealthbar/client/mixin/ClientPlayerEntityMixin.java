/*
 * MIT License
 *
 * Copyright (c) 2024 efekos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.efekos.fancyhealthbar.client.mixin;

import dev.efekos.fancyhealthbar.client.FancyHealthBarClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends LivingEntity {

    @Shadow public abstract boolean isInvulnerableTo(DamageSource damageSource);

    @Shadow public abstract float getAbsorptionAmount();

    public ClientPlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "applyDamage", at = @At("HEAD"))
    public void onDamage(DamageSource damageSource, float damage, CallbackInfo ci) {
        if(isInvulnerableTo(damageSource)) return;
        if(getAbsorptionAmount()!=0){
            if(getAbsorptionAmount()-damage<=0) FancyHealthBarClient.FANCY_HEALTH_HUD.onDamage(MathHelper.clamp(getHealth(), 0, 20), MathHelper.clamp(getHealth() - (-(getAbsorptionAmount()-damage)), 0, 20));

        } else
        FancyHealthBarClient.FANCY_HEALTH_HUD.onDamage(MathHelper.clamp(getHealth(), 0, 20), MathHelper.clamp(getHealth() - damage, 0, 20));

    }

}
