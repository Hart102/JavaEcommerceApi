package com.Hart.shoppingCartApi.service.category;

import com.Hart.shoppingCartApi.model.Category;
import java.util.List;

public interface ICategoryService {
    List<Category>getAllCategories();
    Category getCategoryByName(String name);
    Category getCategoryById(Long id);
    Category addCategory(Category category);
    Category updateCategory (Category category, Long id);
    void deleteCategoryById(Long id);
}
