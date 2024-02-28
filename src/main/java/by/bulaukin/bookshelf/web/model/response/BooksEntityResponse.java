package by.bulaukin.bookshelf.web.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BooksEntityResponse {

    private String namedBook;
    private String author;
    private String categoryName;

}
