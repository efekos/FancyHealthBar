package dev.efekos.fancyhealthbar.client.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.efekos.fancyhealthbar.client.accessor.FhbOptionsAccessor;
import dev.efekos.fancyhealthbar.client.accessor.InGameHudRenderingAccessor;
import dev.efekos.fancyhealthbar.client.animation.AnimationController;
import dev.efekos.fancyhealthbar.client.entity.HudEntityManager;
import dev.efekos.fancyhealthbar.client.rendering.HealthBarRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Gui;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin implements InGameHudRenderingAccessor {

    @Shadow
    @Final
    private RandomSource random;

    @Shadow
    @Final
    private Minecraft minecraft;
    private int ticks;
    @Unique
    private final HudEntityManager manager = new HudEntityManager();
    @Unique
    private final AnimationController controller = new AnimationController();
    @Unique
    private HealthBarRendering rendering = ((FhbOptionsAccessor) Minecraft.getInstance()).fhb$getOptions().getRenderingOptions().createRendering().initialize(manager,controller, Minecraft.getInstance());

    @Override
    public void fhb$react() {
        rendering.react(manager,controller, minecraft);
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
        rendering.initialize(manager,controller, minecraft);
    }

    @Unique
    private int lastHealth = 0;

    @Inject(method = "tick()V",at=@At("TAIL"))
    public void tickTail(CallbackInfo ci){
        manager.tick();
        controller.tick();
    }

    @WrapMethod(method = "renderHearts")
    public void renderHealthBar(GuiGraphics context, Player player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int a, int b, int absorption, boolean blinking, Operation<Void> original) {
        int health = (int) Math.ceil(Minecraft.getInstance().player.getHealth());
        rendering.draw(random,context, player, x, y, lines, regeneratingHeartIndex, maxHealth, a, health, absorption, blinking);
        manager.render(context);
        controller.draw(context);

        if(this.lastHealth!=health){
            if(health<this.lastHealth) rendering.onDamage(manager,x,y,lines,regeneratingHeartIndex,maxHealth,this.lastHealth,health,absorption,blinking);
            this.lastHealth=health;
        }
    }

}