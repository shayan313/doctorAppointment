package com.sadad.doctorappointment.user.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniquePhoneNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePhoneNumber {
    String message() default "validation.phoneNumber.already.exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
