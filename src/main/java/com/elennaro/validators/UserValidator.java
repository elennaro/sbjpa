package com.elennaro.validators;


import com.elennaro.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override public void validate(Object target, Errors errors) {
        User user = (User) target;

        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();

        if (confirmPassword == null || !password.equals(confirmPassword)) {
            //I know I do not have messages
            errors.rejectValue("confirmPassword", "user.confirmPassword", "Passwords do not match!");
        }
    }
}
