package by.bulaukin.bookshelf.validator;

import by.bulaukin.bookshelf.web.model.request.UpsertBooksEntityRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class BooksEntityRequestValidator implements ConstraintValidator<RequestParamValid, UpsertBooksEntityRequest> {

    @Override
    public boolean isValid(UpsertBooksEntityRequest value, ConstraintValidatorContext context) {
        if(ObjectUtils.anyNull(value.getAuthor(), value.getNamedBook(), value.getCategoryName())) {
            context
                    .buildConstraintViolationWithTemplate("All fields of request must be specify!")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }
}
