package losnazar.task.lib;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PairValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PairValid {
    String message() default "Invalid currency pair. Should be: BTC, ETH or XRP.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
