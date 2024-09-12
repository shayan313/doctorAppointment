/*
package com.sadad.doctorappointment.base.utility.validators.impl;

import com.sadad.doctorappointment.base.utility.validators.Mobile;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class MobileValidator implements ConstraintValidator<Mobile, String> {
    @Override
    public void initialize(Mobile constraint) {
    }

    @Override
    public boolean isValid(String text, ConstraintValidatorContext cxt) {

        return !StringUtils.hasLength(text) || isMobile(text);

    }

    public static boolean isMobile(String text) {
        if (!isNumeric(text, 11))
            return false;
        String[] prefixList = new String[]{
                //hamrah avval
                "0910", "0911", "0912", "0913", "0914", "0915",
                "0916", "0917", "0918", "0919", "0990", "0991", "0992", "0993", "0994", "0995", "0996",
                //irancell
                "0930", "0933", "0935", "0936", "0937", "0938", "0923",
                "0939", "0900", "0901", "0902", "0903", "0904", "0905", "0941",
                //rightel
                "0920", "0921", "0922",
                //espadan
                "0931",
                //talya
                "0932",
                //kish
                "0934", "09999", "09990", "09998",
                "099910", "099911", "099913", "099914", "099999", "099914",
                "099810", "099811", "099812", "099813", "099814", "099815", "099816", "099817"
        };

        long count = Arrays.stream(prefixList).filter(text::startsWith).count();
        return count > 0;
    }

    public static boolean isNumeric(String text, int length) {
        return text.matches("\\d{" + length + "}");
    }
}
*/
