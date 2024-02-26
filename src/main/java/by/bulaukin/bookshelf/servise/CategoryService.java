package by.bulaukin.bookshelf.servise;

import by.bulaukin.bookshelf.entity.CategoryEntity;

public interface CategoryService {
    CategoryEntity findByCategoryName(String categoryName);

    CategoryEntity save(CategoryEntity category);

}
