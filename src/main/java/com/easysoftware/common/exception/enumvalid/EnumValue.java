package com.easysoftware.common.exception.enumvalid;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.easysoftware.common.exception.EnumValidException;

import java.lang.annotation.ElementType;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.RetentionPolicy;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValue.Validator.class)
public @interface EnumValue {
    String message() default "{custom.value.invalid}";
 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
    Class<? extends Enum<?>> enumClass();
 
    String enumMethod();

    class Validator implements  ConstraintValidator<EnumValue, Object> {
 
        private Class<? extends Enum<?>> enumClass;
        private String enumMethod;
 
        @Override
        public void initialize(EnumValue enumValue) {
            enumMethod = enumValue.enumMethod();
            enumClass = enumValue.enumClass();
        }
 
        @Override
        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
            if (value == null) {
                return Boolean.TRUE;
            }
 
            if (enumClass == null || enumMethod == null) {
                return Boolean.TRUE;
            }
 
            Class<?> valueClass = value.getClass();

            try {
                Method method = enumClass.getMethod(enumMethod, valueClass);
                if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
                    throw new EnumValidException();
                    }
 
                if(!Modifier.isStatic(method.getModifiers())) {
                    throw new EnumValidException();
                }

                Boolean result = (Boolean)method.invoke(null, value);
                return result == null ? false : result;
            } catch (Exception e) {
                throw new EnumValidException();
            }
        }
    }
}
