package by.bulaukin.bookshelf.aspect;

import by.bulaukin.bookshelf.entity.BooksEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryChecker {
}
