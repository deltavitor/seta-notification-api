package edu.costavitor.setanotificationapi.common;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class BigDecimalToIntegerConverter {

    public static Integer bigDecimalToInteger(BigDecimal value) {

        try {
            return value.intValueExact();
        } catch (ArithmeticException e) {
            log.error("Unable to convert BigDecimal {} to integer", value);
        }
        return null;
    }
}
