package losnazar.task.lib;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import losnazar.task.model.CurrencyPrice;

public class PairValidator implements ConstraintValidator<PairValid, String> {

    @Override
    public boolean isValid(String param, ConstraintValidatorContext constraintValidatorContext) {
        return (CurrencyPrice.Pair.BTC.getValue().contains(param)
                || CurrencyPrice.Pair.ETH.getValue().contains(param)
                || CurrencyPrice.Pair.XRP.getValue().contains(param)) && !param.equals("USD");
    }
}
