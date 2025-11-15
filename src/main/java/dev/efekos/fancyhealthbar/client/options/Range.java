package dev.efekos.fancyhealthbar.client.options;

public interface Range<T extends Number> {

    T random();
    T getMin();
    T getMax();
    void setMin(T min);
    void setMax(T max);

}
