package by.bulaukin.bookshelf.repository;

import by.bulaukin.bookshelf.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
