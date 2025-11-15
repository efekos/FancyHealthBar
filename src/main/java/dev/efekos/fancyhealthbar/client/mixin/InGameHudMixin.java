package dev.efekos.fancyhealthbar.client.mixin;

import dev.efekos.fancyhealthbar.client.accessor.FhbOptionsAccessor;
import dev.efekos.fancyhealthbar.client.accessor.InGameHudRenderingAccessor;
import dev.efekos.fancyhealthbar.client.animation.AnimationController;
import dev.efekos.fancyhealthbar.client.entity.HudEntityManager;
import dev.efekos.fancyhealthbar.client.rendering.HealthBarRendering;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin implements InGameHudRenderingAccessor {

    @Shadow
    @Final
    private Random random;

    @Shadow
    @Final
    private MinecraftClient client;
    private int ticks;
    @Unique
    private final HudEntityManager manager = new HudEntityManager();
    @Unique
    private final AnimationController controller = new AnimationController();
    @Unique
    private HealthBarRendering rendering = ((FhbOptionsAccessor) MinecraftClient.getInstance()).fhb$getOptions().getRenderingOptions().createRendering().initialize(manager,controller,MinecraftClient.getInstance());

    @Override
    public void fhb$react() {
        rendering.react(manager,controller,client);
    }

    @Override
    public HealthBarRendering fhb$getRendering() {
        return rendering;
    }

    @Override
    public void fhb$setRendering(HealthBarRendering rendering) {
        this.rendering = rendering;
        manager.reset();
        controller.reset();
        rendering.initialize(manager,controller,client);
    }

    @Unique
    private int lastHealth = 0;

    @Inject(method = "tick()V",at=@At("TAIL"))
    public void tickTail(CallbackInfo ci){
        manager.tick();
        controller.tick();
    }

    /**
     * @author efekos
     * @reason To completely remake the health bar. Go to <a href="https://github.com/efekos/FancyHealthBar/issues">FancyHealthBar/issues</a> to complain
     */
    @Overwrite
    private void renderHealthBar(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int a, int b, int absorption, boolean blinking) {
        int health = (int) Math.ceil(MinecraftClient.getInstance().player.getHealth());
        rendering.draw(random,context, player, x, y, lines, regeneratingHeartIndex, maxHealth, a, health, absorption, blinking);
        manager.render(context);
        controller.draw(context);

        if(this.lastHealth!=health){
            if(health<this.lastHealth) rendering.onDamage(manager,x,y,lines,regeneratingHeartIndex,maxHealth,this.lastHealth,health,absorption,blinking);
            this.lastHealth=health;
        }
    }

}