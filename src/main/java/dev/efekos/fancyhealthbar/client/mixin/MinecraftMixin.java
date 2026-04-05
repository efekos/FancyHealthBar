package dev.efekos.fancyhealthbar.client.mixin;

import dev.efekos.fancyhealthbar.client.accessor.FhbOptionsAccessor;
import dev.efekos.fancyhealthbar.client.options.FancyHealthBarOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.nio.file.Path;

@Mixin(Minecraft.class)
public class MinecraftMixin implements FhbOptionsAccessor {

    @Shadow
    @Final
    public File gameDirectory;
    @Unique
    private FancyHealthBarOptions fancyHealthBarOptions;

    @Override
    public FancyHealthBarOptions fhb$getOptions() {
        return fancyHealthBarOptions;
    }

    @Inject(method = "<init>",at = @At(value = "INVOKE", target = "Ljava/io/File;toPath()Ljava/nio/file/Path;",shift = At.Shift.AFTER))
    public void initInvoke(GameConfig args, CallbackInfo ci){
        fancyHealthBarOptions = new FancyHealthBarOptions(Path.of(this.gameDirectory.getAbsolutePath(),"config","fancyhealthbar").toFile());
    }

}