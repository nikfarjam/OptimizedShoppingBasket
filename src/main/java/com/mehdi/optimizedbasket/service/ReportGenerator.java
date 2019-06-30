package com.mehdi.optimizedbasket.service;

import com.mehdi.optimizedbasket.model.Category;

import java.util.List;

public interface ReportGenerator {
    void generate(Basket basket, List<Category> categories);
}
