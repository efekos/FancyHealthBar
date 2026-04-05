package dev.efekos.fancyhealthbar.client.compat;

import net.minecraft.resources./*? >=1.21.11 {*//*Identifier*//*?} else {*/ResourceLocation/*?}*/;

/**
 * Wraps the ID class because it has been renamed from ResourceLocation to Identifier in 1.21.11 which would cause
 * massive issues for this codebase
 */
public record CompatKey(String namespace, String key) {

    public /*? >=1.21.11 {*//*Identifier*//*?} else {*/ResourceLocation/*?}*/ unwrap(){
        return /*? >=1.21.11 {*//*Identifier*//*?} else {*/ResourceLocation/*?}*/.tryBuild(namespace,key);
    }

    public static CompatKey of(/*? >=1.21.11 {*//*Identifier*//*?} else {*/ResourceLocation/*?}*/ id){
        return new CompatKey(id.getNamespace(),id.getPath());
    }

    public static CompatKey of(String namespace, String key) {
        return new CompatKey(namespace, key);
    }

    public static CompatKey minecraft(String key) {
        return new CompatKey("minecraft", key);
    }

    public CompatKey suffixed(String key){
        return of(namespace,this.key+key);
    }

    public CompatKey prefixed(String key){
        return of(namespace,key+this.key);
    }

    @Override
    public String toString() {
        return namespace + ":" + key;
    }

}