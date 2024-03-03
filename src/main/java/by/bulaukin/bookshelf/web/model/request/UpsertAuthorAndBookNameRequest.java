package by.bulaukin.bookshelf.web.model.request;

import by.bulaukin.bookshelf.validator.RequestParamValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequestParamValid
public class UpsertAuthorAndBookNameRequest {

    private String namedBook;
    private String author;

}
