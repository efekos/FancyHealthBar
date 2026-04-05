package dev.efekos.fancyhealthbar.client.rendering;

//~if>=26.1 'GuiGraphics' -> 'GuiGraphicsExtractor' {
import com.mojang.blaze3d.systems.RenderSystem;
import dev.efekos.fancyhealthbar.client.HeartTextureSet;
import dev.efekos.fancyhealthbar.client.Try;
import dev.efekos.fancyhealthbar.client.animation.AnimationController;
import dev.efekos.fancyhealthbar.client.compat.Texture;
import dev.efekos.fancyhealthbar.client.entity.HudEntityManager;
import dev.efekos.fancyhealthbar.client.entity.PixelEntity;
import dev.efekos.fancyhealthbar.client.options.VanillaRenderingOptions;
import net.minecraft.client.Minecraft;
//? >=1.21.6
//import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Unique;

public class VanillaHealthBarRendering implements HealthBarRendering {

    private final VanillaRenderingOptions options;

    @Override
    public VanillaHealthBarRendering initialize(HudEntityManager manager, AnimationController controller, Minecraft client) {
        return this;
    }

    @Override
    public void react(HudEntityManager manager, AnimationController controller, Minecraft client) {

    }

    public VanillaHealthBarRendering(VanillaRenderingOptions options) {
        this.options = options;
    }

    @Override
    public void drawPreview(RandomSource random, GuiGraphics context, int x, int y, int lines, int lastHealth, int health, boolean blinking, boolean hardcore) {

        //? <1.21.6
        context.pose().pushPose();
        //? >=1.21.6
        //context.pose().pushMatrix();
        context.pose().translate(options.offset().x,options.offset().y/*? <1.21.6 {*/,0/*?}*/);

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

        //? <1.21.6
        context.pose().popPose();
        //? >=1.21.6
        //context.pose().popMatrix();

    }

    @Override
    public void draw(RandomSource random, GuiGraphics context, Player player, int x, int y,
                     int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption,
                     boolean blinking) {

        //? <1.21.6
        context.pose().pushPose();
        //? >=1.21.6
        //context.pose().pushMatrix();
        context.pose().translate(options.offset().x,options.offset().y/*? <1.21.6 {*/,0/*?}*/);

        HeartTextureSet heartType = HeartTextureSet.tryGetFromPlayer(player);
        boolean hardcore = player.level().getLevelData().isHardcore();

        int maxHearts = Mth.ceil((double)maxHealth / 2.0);
        int absorptionHearts = Mth.ceil((double)absorption / 2.0);
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


        //? <1.21.6
        context.pose().popPose();
        //? >=1.21.6
        //context.pose().popMatrix();

    }

    private @NotNull Vector2i getHeartLocation(RandomSource random, int x, int y, int lines, int regeneratingHeartIndex, int lastHealth, int absorption, int i, int maxHearts) {
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
        LocalPlayer player = Minecraft.getInstance().player;
        HeartTextureSet heartType = player==null?HeartTextureSet.NORMAL:HeartTextureSet.tryGetFromPlayer(player);
        boolean hardcore = (player!=null&&options.getHardcoreHearts()!=VanillaRenderingOptions.HardcoreHearts.AUTO&&player.level().getLevelData().isHardcore())||options.getHardcoreHearts()== VanillaRenderingOptions.HardcoreHearts.ON;
        int maxHearts = Mth.ceil((double)maxHealth / 2.0);
        int hearts = Mth.ceil(health/2d);
        int lastHearts = Mth.ceil(lastHealth/2d);
        int heartDif = lastHealth-health==1?1:lastHearts-hearts;
        int healthDif = lastHealth-health;

        Texture texture = heartType.getTexture(hardcore, false, blinking);

        boolean bothLost = lastHealth % 2 != 0 && health % 2 != 0;
        boolean endHalfLost = lastHealth % 2 == 0 && health % 2 != 0;
        boolean startHalfLost = lastHealth % 2 != 0 && health % 2 == 0;
        if(bothLost){ // both halves lost
            Vector2i location = getHeartLocation(RandomSource.create(), x, y, lines, regenHeartIndex, lastHealth, absorption, hearts-1, maxHearts);
            spawnHeart(4, 9, manager, location, texture);
            Vector2i location2 = getHeartLocation(RandomSource.create(), x, y, lines, regenHeartIndex, lastHealth, absorption, hearts+heartDif-1, maxHearts);
            spawnHeart(0, 5, manager, location2, texture);
        }

        if(endHalfLost){ // end half lost
            Vector2i location = getHeartLocation(RandomSource.create(), x, y, lines, regenHeartIndex, lastHealth, absorption, hearts-1, maxHearts);
            spawnHeart(4, 9, manager, location, texture);
        }

        if(startHalfLost){ // start half lost
            Vector2i location = getHeartLocation(RandomSource.create(), x, y, lines, regenHeartIndex, lastHealth, absorption, hearts+heartDif-1, maxHearts);
            spawnHeart(0, 5, manager, location, texture);
        }

        if(healthDif>1) for (int i = 0; i < heartDif; i++) {
            if((startHalfLost||bothLost)&&i+1==heartDif)continue;
            Vector2i location = getHeartLocation(RandomSource.create(), x, y, lines, regenHeartIndex, lastHealth, absorption, hearts + i, maxHearts);
            spawnHeart(0, 9, manager, location, texture);
        }

    }

    private void spawnHeart(int us, int ul, HudEntityManager manager, Vector2i location, Texture texture) {
        for (int u = us; u < ul; u++)
            for (int v = 0; v < 9; v++)
                a(manager, location, texture, u, v);
    }

    private void a(HudEntityManager manager, Vector2i location, Texture texture, int a, int b) {
        PixelEntity entity = new PixelEntity(location.x + a + options.offset().x, location.y + b + options.offset().y, texture, a, b);
        entity.setVelocity(new Vector2d(options.getVelocityX().random(), options.getVelocityY().random()));
        entity.setAcceleration(new Vector2d(options.getAccelerationX().random(),options.getAccelerationY().random()));
        entity.setDrag(options.getDrag().random());
        entity.setMaxLifetime(options.getMaxLifetime().random());
        entity.setSize(options.getSize().random());
        entity.setX(entity.getX()-entity.getSize()/2d);
        entity.setY(entity.getY()-entity.getSize()/2d);
        entity.setFadeIn(options.getFadeInTicks().random());
        entity.setFadeOut(options.getFadeOutTicks().random());
        manager.addEntity(entity);
    }

    @Unique
    private void legacyHeart(GuiGraphics context, HeartTextureSet set, int x, int y, boolean hardcore, boolean blinking, boolean half){
        //? <1.21.5
        RenderSystem.enableBlend();
        Texture setTexture = set.getTexture(hardcore, half, blinking);
        setTexture.draw(context,x,y,9,9);
        //? <1.21.5
        RenderSystem.disableBlend();
    }

}


//~}