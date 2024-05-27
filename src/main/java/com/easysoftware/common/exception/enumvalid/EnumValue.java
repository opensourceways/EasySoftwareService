/* Copyright (c) 2024 openEuler Community
 EasySoftwareService is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.common.exception.enumvalid;

import com.easysoftware.common.exception.EnumValidException;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValue.Validator.class)
public @interface EnumValue {
    /**
     * Specifies the message template to be used in case of a validation error.
     *
     * @return The message template for validation errors
     */
    String message() default "{custom.value.invalid}";

    /**
     * Specifies the validation groups this constraint belongs to.
     *
     * @return The validation groups for the constraint
     */
    Class<?>[] groups() default {};

    /**
     * Specifies the payload associated with the constraint.
     *
     * @return The payload associated with the constraint
     */
    Class<? extends Payload>[] payload() default {};


    /**
     * Returns the enum class for validation.
     *
     * @return The enum class for validation
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * Returns the method to be used on the specified enum class.
     *
     * @return The method name for the enum class
     */
    String enumMethod();

    class Validator implements ConstraintValidator<EnumValue, Object> {

        /**
         * The class representing an Enum.
         */
        private Class<? extends Enum<?>> enumClass;

        /**
         * The method name related to the specified Enum class.
         */
        private String enumMethod;


        /**
         * Initializes the EnumValue annotation.
         *
         * @param enumValue The EnumValue annotation to initialize
         */
        @Override
        public void initialize(final EnumValue enumValue) {
            enumMethod = enumValue.enumMethod();
            enumClass = enumValue.enumClass();
        }

        /**
         * Validates the specified value.
         *
         * @param value                      The value to validate
         * @param constraintValidatorContext The context in which the constraint is evaluated
         * @return True if the value is valid, false otherwise
         */
        @Override
        public boolean isValid(final Object value, final ConstraintValidatorContext constraintValidatorContext) {
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

                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new EnumValidException();
                }

                Boolean result = (Boolean) method.invoke(null, value);
                return result != null && result;
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                throw new EnumValidException();
            }
        }
    }
}
