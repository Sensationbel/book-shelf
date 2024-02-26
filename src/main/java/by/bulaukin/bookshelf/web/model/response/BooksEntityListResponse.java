package by.bulaukin.bookshelf.web.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BooksEntityListResponse {

    private List<BooksEntityResponse> booksList = new ArrayList<>();

}
