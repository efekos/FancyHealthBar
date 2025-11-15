package dev.efekos.fancyhealthbar.client.accessor;

import dev.efekos.fancyhealthbar.client.rendering.HealthBarRendering;

public interface InGameHudRenderingAccessor {

    void fhb$setRendering(HealthBarRendering rendering);
    HealthBarRendering fhb$getRendering();
    void fhb$react();

}
