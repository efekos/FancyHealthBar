package dev.efekos.fancyhealthbar.client.options;

import net.minecraft.util.math.MathHelper;

public class IntRange implements Range<Integer> {

    public IntRange(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Integer random() {
        return MathHelper.floor(MathHelper.clampedLerp(min, max, Math.random()));
    }

    private Integer min;
    private Integer max;

    @Override
    public Integer getMin() {
        return min;
    }

    @Override
    public void setMin(Integer min) {
        this.min = min;
    }

    @Override
    public Integer getMax() {
        return max;
    }

    @Override
    public void setMax(Integer max) {
        this.max = max;
    }
}
