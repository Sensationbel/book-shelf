package by.bulaukin.bookshelf.servise.impl;

import by.bulaukin.bookshelf.entity.CategoryEntity;
import by.bulaukin.bookshelf.repository.CategoryRepository;
import by.bulaukin.bookshelf.servise.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public CategoryEntity findByCategoryName(String categoryName) {
        CategoryEntity probe = new CategoryEntity();
        probe.setCategoryName(categoryName);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id");
        Example<CategoryEntity> example = Example.of(probe, matcher);
        return repository.findOne(example).orElse(null);
    }

    @Override
    public CategoryEntity save(CategoryEntity category) {
        return repository.save(category);
    }
}
