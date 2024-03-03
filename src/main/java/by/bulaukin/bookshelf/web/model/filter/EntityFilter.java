package by.bulaukin.bookshelf.web.model.filter;

import by.bulaukin.bookshelf.validator.RequestParamValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequestParamValid
public class EntityFilter {

    private String categoryName;

}
