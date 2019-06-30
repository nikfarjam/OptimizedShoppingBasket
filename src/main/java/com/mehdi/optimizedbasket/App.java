package com.mehdi.optimizedbasket;

import com.mehdi.optimizedbasket.builder.ItemBuilder;
import com.mehdi.optimizedbasket.factory.ClassFactory;
import com.mehdi.optimizedbasket.model.Category;
import com.mehdi.optimizedbasket.model.Item;
import com.mehdi.optimizedbasket.model.ItemValueRange;
import com.mehdi.optimizedbasket.service.Basket;
import com.mehdi.optimizedbasket.service.ItemPicker;
import com.mehdi.optimizedbasket.service.ReportGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mehdi.optimizedbasket.factory.ClassFactory.ITEM_PICKER_GREEDY;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        final BigDecimal totalCostLimit = BigDecimal.valueOf(50);
        final ItemValueRange config = new ItemValueRange()
                .setMinPrice(BigDecimal.ONE)
                .setMaxPrice(BigDecimal.valueOf(20))
                .setMinShoppingCost(BigDecimal.valueOf(2))
                .setMaxShoppingCost(BigDecimal.valueOf(5))
                .setMinRate(1)
                .setMaxRate(5);

        ItemBuilder builder = ClassFactory.getInstance().createItemBuilder(config);
        ItemPicker picker = ClassFactory.getInstance().getItemPicker(ITEM_PICKER_GREEDY);
        ReportGenerator report = ClassFactory.getInstance().getReportGenerator();

        List<Category> categories = builder.getCategoriesAndItems(20, 10);
        logger.info("Generated categories are:");
        for (Category category : categories) {
            logger.info(category);
        }

        List<Item> items = categories.stream()
                .map(Category::getItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Optional<Basket> result = picker.selectItems(items, totalCostLimit);
        result.ifPresent(basket -> report.generate(basket, categories));
    }
}
