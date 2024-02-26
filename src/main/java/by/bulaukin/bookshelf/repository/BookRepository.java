package by.bulaukin.bookshelf.repository;

import by.bulaukin.bookshelf.entity.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends  JpaRepository<BooksEntity, Long>, JpaSpecificationExecutor<BooksEntity> {
}
