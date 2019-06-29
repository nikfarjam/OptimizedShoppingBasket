package com.mehdi.optimizedbasket.factory;

import com.mehdi.optimizedbasket.builder.ItemBuilder;
import com.mehdi.optimizedbasket.builder.ItemBuilderImpl;
import com.mehdi.optimizedbasket.model.ItemValueRange;

/**
 * A thread safe singleton Factory class in charge of creating classes
 */
public class ClassFactory {

    private static String LOCK = "LOCK";
    private static ClassFactory ourInstance = null;

    private ClassFactory() {
    }

    public static ClassFactory getInstance() {
        if (ourInstance == null) {
            synchronized (LOCK) {
                if (ourInstance == null) {
                    ourInstance = new ClassFactory();
                }
            }
        }
        return ourInstance;
    }

    public ItemBuilder createItemBuilder() {
        ItemBuilder builder = new ItemBuilderImpl();
        return builder;
    }
}
