package com.mehdi.optimizedbasket.factory;

import com.mehdi.optimizedbasket.builder.ItemBuilder;
import com.mehdi.optimizedbasket.builder.ItemBuilderImpl;
import com.mehdi.optimizedbasket.model.ItemRange;
import com.mehdi.optimizedbasket.service.ConsoleReportGeneratorImpl;
import com.mehdi.optimizedbasket.service.GreedyPickerImpl;
import com.mehdi.optimizedbasket.service.ItemPicker;
import com.mehdi.optimizedbasket.service.ReportGenerator;

/**
 * A thread safe singleton Factory class in charge of creating classes
 */
public class ClassFactory {

    public static final String ITEM_PICKER_GREEDY = "ITEM_PICKER_GREEDY";

    private static final String LOCK = "LOCK";
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

    public ItemBuilder createItemBuilder(ItemRange valueRange) {
        ItemBuilder builder = new ItemBuilderImpl();
        builder.setItemRange(valueRange);
        return builder;
    }

    public ItemPicker getItemPicker(String key) {
        if (ITEM_PICKER_GREEDY.equals(key)) {
            return new GreedyPickerImpl();
        }
        return null;
    }

    public ReportGenerator getReportGenerator() {
        return new ConsoleReportGeneratorImpl();
    }
}
