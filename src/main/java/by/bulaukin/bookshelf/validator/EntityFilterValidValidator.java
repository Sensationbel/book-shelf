package by.bulaukin.bookshelf.validator;

import by.bulaukin.bookshelf.web.model.filter.EntityFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class EntityFilterValidValidator implements ConstraintValidator<RequestParamValid, EntityFilter> {

    @Override
    public boolean isValid(EntityFilter value, ConstraintValidatorContext context) {
        if(ObjectUtils.isEmpty(value.getCategoryName())) {
            context
                    .buildConstraintViolationWithTemplate("Category name must be specify!!")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
