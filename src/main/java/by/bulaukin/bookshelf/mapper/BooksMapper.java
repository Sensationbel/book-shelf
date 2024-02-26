package by.bulaukin.bookshelf.mapper;

import by.bulaukin.bookshelf.aspect.CategoryChecker;
import by.bulaukin.bookshelf.entity.BooksEntity;
import by.bulaukin.bookshelf.web.model.request.UpsertBooksEntityRequest;
import by.bulaukin.bookshelf.web.model.response.BooksEntityListResponse;
import by.bulaukin.bookshelf.web.model.response.BooksEntityResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(BooksMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BooksMapper {

//    @Mapping(source = "request.categoryName", target = "category.categoryName")

    BooksEntity requestToBooksEntity(UpsertBooksEntityRequest request);

    @Mapping(source = "bookId", target = "id")
    BooksEntity requestToBooksEntity(Long bookId, UpsertBooksEntityRequest request);

    @Mapping(source = "books.category.categoryName", target = "categoryName")
    BooksEntityResponse bookToResponse(BooksEntity books);

    List<BooksEntityResponse> booksListToResponseList(List<BooksEntity> books);

    default BooksEntityListResponse booksListToBooksEntityListResponse(List<BooksEntity> books) {
        BooksEntityListResponse response = new BooksEntityListResponse();
        response.setBooksList(booksListToResponseList(books));
        return response;
    }
}
