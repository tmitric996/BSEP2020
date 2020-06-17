package security;

import javax.validation.*;
import java.lang.annotation.*;

@Target( { ElementType.FIELD, ElementType.PARAMETER} )
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SQLiSafeConstraintValidator.class)
@Documented

public @interface SQLiSafe {
	
	String message() default "{SQLiSafe}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
