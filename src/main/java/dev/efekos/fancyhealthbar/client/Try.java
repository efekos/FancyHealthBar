package dev.efekos.fancyhealthbar.client;

import org.slf4j.Logger;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public interface Try {

    static void error(Callable<Object> action, Logger logger, String message){
        try {
            action.call();
        } catch (Exception e) {
            logger.error(message,e);
        }
    }

    static <T> T orElse(Supplier<T> supplier, T defaultValue) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    static void ignore(Callable<Object> action) {
        try {
            action.call();
        } catch (Exception ignored){

        }
    }
}
