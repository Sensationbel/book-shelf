package by.bulaukin.bookshelf.web.model.request;

import lombok.*;

@Getter
@Setter
public class UpsertBooksEntityRequest {

    private String namedBook;
    private String author;
    private String categoryName;

}
