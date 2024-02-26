package by.bulaukin.bookshelf.aspect;

import by.bulaukin.bookshelf.entity.BooksEntity;
import by.bulaukin.bookshelf.entity.CategoryEntity;
import by.bulaukin.bookshelf.servise.CategoryService;
import by.bulaukin.bookshelf.web.model.request.UpsertBooksEntityRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CategoryCheckerAspect {

    private final CategoryService service;

    @Around(value = "@annotation(CategoryChecker)")
    public BooksEntity around(ProceedingJoinPoint point) throws Throwable {
        UpsertBooksEntityRequest request = (UpsertBooksEntityRequest) Arrays.stream(point.getArgs()).filter(o -> o.getClass()
                        .equals(UpsertBooksEntityRequest.class))
                .findFirst()
                .get();

        String categoryName = request.getCategoryName();
        CategoryEntity actualCategory = service.findByCategoryName(categoryName);
        BooksEntity proceed = (BooksEntity) point.proceed();

        if (actualCategory == null) {
            CategoryEntity savedCategory = new CategoryEntity();
            savedCategory.setCategoryName(categoryName);
            proceed.setCategory(service.save(savedCategory));
        } else {
            proceed.setCategory(actualCategory);
        }

        return proceed;

    }
}
