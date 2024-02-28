package by.bulaukin.bookshelf.validator;

import by.bulaukin.bookshelf.web.model.request.UpsertAuthorAndBookNameRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class AuthorAndBookNameRequestValidator implements ConstraintValidator<RequestParamValid, UpsertAuthorAndBookNameRequest> {

    @Override
    public boolean isValid(UpsertAuthorAndBookNameRequest value, ConstraintValidatorContext context) {
        if(ObjectUtils.isEmpty(value.getNamedBook())) {
            context.buildConstraintViolationWithTemplate("Book Name must be specify!")
                    .addConstraintViolation();

            return false;
        } else if(ObjectUtils.isEmpty(value.getAuthor())) {
            context.buildConstraintViolationWithTemplate("Author Name must be specify!")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }

}
