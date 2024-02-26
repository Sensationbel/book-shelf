package by.bulaukin.bookshelf.web.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpsertAuthorAndBookNameRequest {

    private String namedBook;
    private String author;

}
