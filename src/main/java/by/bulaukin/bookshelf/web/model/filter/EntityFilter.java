package by.bulaukin.bookshelf.web.model.filter;

import by.bulaukin.bookshelf.validator.RequestParamValid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RequestParamValid
public class EntityFilter {

    private String categoryName;

}
