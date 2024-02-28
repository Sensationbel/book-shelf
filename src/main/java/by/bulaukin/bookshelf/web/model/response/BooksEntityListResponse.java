package by.bulaukin.bookshelf.web.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BooksEntityListResponse {

    private List<BooksEntityResponse> booksList = new ArrayList<>();

}
