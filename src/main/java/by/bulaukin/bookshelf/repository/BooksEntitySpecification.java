package by.bulaukin.bookshelf.repository;

import by.bulaukin.bookshelf.entity.BooksEntity;
import by.bulaukin.bookshelf.web.model.filter.EntityFilter;
import org.springframework.data.jpa.domain.Specification;

public interface BooksEntitySpecification {

    static Specification<BooksEntity> withFilter(EntityFilter filter) {
        return Specification.where(getByCategoryName(filter.getCategoryName()));
    }

    static Specification<BooksEntity> getByCategoryName(String categoryName) {
        return ((root, query, criteriaBuilder) ->
                (categoryName == null) ? null : criteriaBuilder.equal(root.get("category").get("categoryName"), categoryName));
    }

}
