package by.bulaukin.bookshelf.mapper;

import by.bulaukin.bookshelf.aspect.CategoryChecker;
import by.bulaukin.bookshelf.entity.BooksEntity;
import by.bulaukin.bookshelf.web.model.request.UpsertBooksEntityRequest;
import by.bulaukin.bookshelf.web.model.response.BooksEntityResponse;

public abstract class BooksMapperDelegate implements BooksMapper {


    @Override
    @CategoryChecker
    public BooksEntity requestToBooksEntity(UpsertBooksEntityRequest request) {
        if (request == null) {
            return null;
        } else {
            BooksEntity booksEntity = new BooksEntity();
            booksEntity.setNamedBook(request.getNamedBook());
            booksEntity.setAuthor(request.getAuthor());

            return booksEntity;
        }
    }

    @Override
    @CategoryChecker
    public BooksEntity requestToBooksEntity(Long bookId, UpsertBooksEntityRequest request) {
        if (bookId == null && request == null) {
            return null;
        } else {
            BooksEntity booksEntity = requestToBooksEntity(request);
            booksEntity.setId(bookId);

            return booksEntity;
        }
    }

    @Override
    public BooksEntityResponse bookToResponse(BooksEntity booksEntity) {
        BooksEntityResponse response = new BooksEntityResponse();
        response.setNamedBook(booksEntity.getNamedBook());
        response.setAuthor(booksEntity.getAuthor());
        response.setCategoryName(booksEntity.getCategory().getCategoryName());

        return response;
    }
}
