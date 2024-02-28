package by.bulaukin.bookshelf.servise;

import by.bulaukin.bookshelf.entity.BooksEntity;
import by.bulaukin.bookshelf.web.model.filter.EntityFilter;
import by.bulaukin.bookshelf.web.model.request.UpsertAuthorAndBookNameRequest;

import java.util.List;

public interface BooksService {

    BooksEntity findById(Long id);
    BooksEntity createBookEntity(BooksEntity book);
    BooksEntity getByAuthorAndName(UpsertAuthorAndBookNameRequest request);
    List<BooksEntity> getAllByCategory(EntityFilter filter);
    BooksEntity update(BooksEntity books);
    void deleteById(Long id);
}
