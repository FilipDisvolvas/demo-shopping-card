package net.sakrak.demoshoppingcart.validations;

import net.sakrak.demoshoppingcart.commands.CreateCustomerCommand;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidatorContext;

public class MatchFieldValidator implements HibernateConstraintValidator<MatchField, CreateCustomerCommand> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(MatchField constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.firstField();
        this.secondFieldName = constraintAnnotation.secondField();
    }

    @Override
    public boolean isValid(CreateCustomerCommand value, ConstraintValidatorContext context) {
        Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
        Object confirmPasswordValue = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);

        boolean result = ObjectUtils.nullSafeEquals(passwordValue, confirmPasswordValue);

        if (!result) {
                //context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode(secondFieldName).addConstraintViolation();
        }

        return result;
    }
}
