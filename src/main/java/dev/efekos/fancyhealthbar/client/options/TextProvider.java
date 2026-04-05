package dev.efekos.fancyhealthbar.client.options;

import net.minecraft.network.chat.Component;

@FunctionalInterface
public interface TextProvider {

    Component text();

}