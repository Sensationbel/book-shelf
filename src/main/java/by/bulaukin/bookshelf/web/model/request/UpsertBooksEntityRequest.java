package by.bulaukin.bookshelf.web.model.request;

import by.bulaukin.bookshelf.validator.RequestParamValid;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequestParamValid
public class UpsertBooksEntityRequest {

    private String namedBook;
    private String author;
    private String categoryName;

}
