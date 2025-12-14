package dev.efekos.fancyhealthbar.client.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.efekos.fancyhealthbar.client.HeartTextureSet;
import dev.efekos.fancyhealthbar.client.Try;
import dev.efekos.fancyhealthbar.client.animation.AnimationController;
import dev.efekos.fancyhealthbar.client.compat.Texture;
import dev.efekos.fancyhealthbar.client.entity.HudEntityManager;
import dev.efekos.fancyhealthbar.client.entity.PixelEntity;
import dev.efekos.fancyhealthbar.client.options.VanillaRenderingOptions;
import net.minecraft.client.MinecraftClient;
//? >=1.21.6
/*import net.minecraft.client.gl.RenderPipelines;*/
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Unique;

public class VanillaHealthBarRendering implements HealthBarRendering {

    private final VanillaRenderingOptions options;

    @Override
    public VanillaHealthBarRendering initialize(HudEntityManager manager, AnimationController controller, MinecraftClient client) {
        return this;
    }

    @Override
    public void react(HudEntityManager manager, AnimationController controller, MinecraftClient client) {

    }

    public VanillaHealthBarRendering(VanillaRenderingOptions options) {
        this.options = options;
    }

    @Override
    public void drawPreview(Random random, DrawContext context, int x, int y, int lines, int lastHealth, int health, boolean blinking,boolean hardcore) {
        HeartTextureSet heartType = HeartTextureSet.NORMAL;
        int maxHearts = 10;
        int absorptionHearts = 0;
        if(options.getHardcoreHearts()== VanillaRenderingOptions.HardcoreHearts.ON) hardcore = true;
        if(options.getHardcoreHearts()== VanillaRenderingOptions.HardcoreHearts.OFF) hardcore = false;
        blinking = blinking&& options.isBlinking();

        for (int i = maxHearts + absorptionHearts - 1; i >= 0; i--) {
            Vector2i heartLoc = getHeartLocation(random, x, y, lines, -1, lastHealth, 0, i, maxHearts);

            legacyHeart(context, HeartTextureSet.CONTAINER, heartLoc.x, heartLoc.y, hardcore, blinking, false);
            int q = i * 2;

            if (blinking && q < health) {
                boolean bl4 = q + 1 == health;
                legacyHeart(context, heartType, heartLoc.x, heartLoc.y, hardcore, true, bl4);
            }

            if (q < lastHealth) {
                boolean bl4 = q + 1 == lastHealth;
                legacyHeart(context, heartType, heartLoc.x, heartLoc.y, hardcore, false, bl4);
            }
        }
    }

    @Override
    public void draw(Random random, DrawContext context, PlayerEntity player, int x, int y,
                     int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption,
                     boolean blinking) {
        HeartTextureSet heartType = HeartTextureSet.tryGetFromPlayer(player);
        //? <1.21.9 {
        boolean hardcore = player.getWorld().getLevelProperties().isHardcore();
        //?} else {
        /*boolean hardcore = player.getEntityWorld().getLevelProperties().isHardcore();
        *///?}
        int maxHearts = MathHelper.ceil((double)maxHealth / 2.0);
        int absorptionHearts = MathHelper.ceil((double)absorption / 2.0);
        int maxHealthI = maxHearts * 2;
        blinking = blinking&& options.isBlinking();
        if(options.getHardcoreHearts()== VanillaRenderingOptions.HardcoreHearts.ON) hardcore = true;
        if(options.getHardcoreHearts()== VanillaRenderingOptions.HardcoreHearts.OFF) hardcore = false;

        for (int i = maxHearts + absorptionHearts - 1; i >= 0; i--) {
            Vector2i heartLoc = getHeartLocation(random, x, y, lines, regeneratingHeartIndex, lastHealth, absorption, i, maxHearts);

            legacyHeart(context, HeartTextureSet.CONTAINER, heartLoc.x, heartLoc.y, hardcore, blinking, false);
            int q = i * 2;
            if (i >= maxHearts) {
                int r = q - maxHealthI;
                if (r < absorption) {
                    boolean bl3 = r + 1 == absorption;
                    legacyHeart(context, heartType == HeartTextureSet.WITHERED ? heartType : HeartTextureSet.ABSORBING, heartLoc.x, heartLoc.y(), hardcore, false, bl3);
                }
            }

            if (blinking && q < health) {
                boolean bl4 = q + 1 == health;
                legacyHeart(context, heartType, heartLoc.x, heartLoc.y, hardcore, true, bl4);
            }

            if (q < lastHealth) {
                boolean bl4 = q + 1 == lastHealth;
                legacyHeart(context, heartType, heartLoc.x, heartLoc.y, hardcore, false, bl4);
            }
        }
    }

    private @NotNull Vector2i getHeartLocation(Random random, int x, int y, int lines, int regeneratingHeartIndex, int lastHealth, int absorption, int i, int maxHearts) {
        int m = i / 10;
        int n = i % 10;
        int heartX = x + n * 8;
        int heartY = y - m * lines;
        if(options.getJiggle()>0)heartY += random.nextInt(options.getJiggle());

        if (options.getLowHealthJiggle()>0&&lastHealth + absorption <= options.getLowHealthJiggleStart())
            heartY += random.nextInt(options.getLowHealthJiggle());

        if (i < maxHearts && i == regeneratingHeartIndex&& options.isRegenIndexAccount())
            heartY -= 2;

        return new Vector2i(heartX, heartY);
    }

    @Override
    public boolean shouldTick() {
        return false;
    }

    @Override
    public void onDamage(HudEntityManager manager, int x, int y, int lines, int regenHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        HeartTextureSet heartType = player==null?HeartTextureSet.NORMAL:HeartTextureSet.tryGetFromPlayer(player);
        boolean hardcore = (player!=null&&options.getHardcoreHearts()!=VanillaRenderingOptions.HardcoreHearts.AUTO&&player/*? <1.21.9 {*/.getWorld()/*?} else {*//*.getEntityWorld()*//*?}*/.getLevelProperties().isHardcore())||options.getHardcoreHearts()== VanillaRenderingOptions.HardcoreHearts.ON;
        int maxHearts = MathHelper.ceil((double)maxHealth / 2.0);
        int hearts = MathHelper.ceil(health/2d);
        int lastHearts = MathHelper.ceil(lastHealth/2d);
        int heartDif = lastHealth-health==1?1:lastHearts-hearts;
        int healthDif = lastHealth-health;

        Texture texture = heartType.getTexture(hardcore, false, blinking);

        boolean bothLost = lastHealth % 2 != 0 && health % 2 != 0;
        boolean endHalfLost = lastHealth % 2 == 0 && health % 2 != 0;
        boolean startHalfLost = lastHealth % 2 != 0 && health % 2 == 0;
        if(bothLost){ // both halves lost
            Vector2i location = getHeartLocation(Random.create(), x, y, lines, regenHeartIndex, lastHealth, absorption, hearts-1, maxHearts);
            spawnHeart(4, 9, manager, location, texture);
            Vector2i location2 = getHeartLocation(Random.create(), x, y, lines, regenHeartIndex, lastHealth, absorption, hearts+heartDif-1, maxHearts);
            spawnHeart(0, 5, manager, location2, texture);
        }

        if(endHalfLost){ // end half lost
            Vector2i location = getHeartLocation(Random.create(), x, y, lines, regenHeartIndex, lastHealth, absorption, hearts-1, maxHearts);
            spawnHeart(4, 9, manager, location, texture);
        }

        if(startHalfLost){ // start half lost
            Vector2i location = getHeartLocation(Random.create(), x, y, lines, regenHeartIndex, lastHealth, absorption, hearts+heartDif-1, maxHearts);
            spawnHeart(0, 5, manager, location, texture);
        }

        if(healthDif>1) for (int i = 0; i < heartDif; i++) {
            if((startHalfLost||bothLost)&&i+1==heartDif)continue;
            Vector2i location = getHeartLocation(Random.create(), x, y, lines, regenHeartIndex, lastHealth, absorption, hearts + i, maxHearts);
            spawnHeart(0, 9, manager, location, texture);
        }

    }

    private void spawnHeart(int us, int ul, HudEntityManager manager, Vector2i location, Texture texture) {
        for (int u = us; u < ul; u++)
            for (int v = 0; v < 9; v++)
                a(manager, location, texture, u, v);
    }

    private void a(HudEntityManager manager, Vector2i location, Texture texture, int a, int b) {
        PixelEntity entity = new PixelEntity(location.x + a, location.y + b, texture, a, b);
        entity.setVelocity(new Vector2d(options.getVelocityX().random(), options.getVelocityY().random()));
        entity.setAcceleration(new Vector2d(options.getAccelerationX().random(),options.getAccelerationY().random()));
        entity.setDrag(options.getDrag().random());
        entity.setMaxLifetime(options.getMaxLifetime().random());
        entity.setSize(options.getSize().random());
        entity.setFadeIn(options.getFadeInTicks().random());
        entity.setFadeOut(options.getFadeOutTicks().random());
        manager.addEntity(entity);
    }

    @Unique
    private void legacyHeart(DrawContext context,HeartTextureSet set,int x,int y,boolean hardcore,boolean blinking,boolean half){
        //? <1.21.5
        RenderSystem.enableBlend();
        Texture setTexture = set.getTexture(hardcore, half, blinking);
        setTexture.draw(context,x,y,9,9);
        //? <1.21.5
        RenderSystem.disableBlend();
    }

}
