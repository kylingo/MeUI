package com.me.ui.sample.pattern.create.singleton;

/**
 * @author studiotang on 17/4/26
 */
public class Singleton {
    private Singleton() {

    }

    private static class SingletonInstance {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonInstance.INSTANCE;
    }
}
