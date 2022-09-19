package org.openapitools.jackson.nullable;

import org.junit.Before;
import org.junit.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class JsonNullableValueExtractorTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUnwrap() {
        final UnitIssue2 unitIssue2 = new UnitIssue2();
        unitIssue2.setRestrictedString("a >15 character long string");
        unitIssue2.setNullableRestrictedString("a >15 character long string");
        unitIssue2.setRestrictedInt(Integer.valueOf(16));
        unitIssue2.setNullableRestrictedInt(Integer.valueOf(16));

        final Set<ConstraintViolation<UnitIssue2>> validate = validator.validate(unitIssue2);

        assertEquals(4, validate.size());
    }

    @Test
    public void testValidationIsNotApplied_whenValueIsUndefined() {
        UnitIssue3 unitIssue = new UnitIssue3();
        Set<ConstraintViolation<UnitIssue3>> violations = validator.validate(unitIssue);
        assertEquals(0, violations.size());
    }

    @Test
    public void testValidationIsAppliedOnDefinedValue_whenNullValueExtracted() {
        UnitIssue3 unitIssue = new UnitIssue3();
        unitIssue.setNotNullString(null);
        Set<ConstraintViolation<UnitIssue3>> violations = validator.validate(unitIssue);
        assertEquals(1, violations.size());
    }


    private static class UnitIssue2 {
        @Size(max = 10)
        public String restrictedString;
        @Size(max = 10)
        public JsonNullable<String> nullableRestrictedString;
        @Max(value = 15)
        public Integer restrictedInt;
        @Max(value = 15)
        public JsonNullable<Integer> nullableRestrictedInt;

        public void setRestrictedString(String restrictedString) {
            this.restrictedString = restrictedString;
        }

        public void setNullableRestrictedString(String nullableRestrictedString) {
            this.nullableRestrictedString = JsonNullable.of(nullableRestrictedString);
        }

        public void setRestrictedInt(Integer restrictedInt) {
            this.restrictedInt = restrictedInt;
        }

        public void setNullableRestrictedInt(Integer nullableRestrictedInt) {
            this.nullableRestrictedInt = JsonNullable.of(nullableRestrictedInt);
        }
    }

    private static class UnitIssue3 {
        @NotNull
        private JsonNullable<String> notNullString = JsonNullable.undefined();

        public void setNotNullString(String value) {
            notNullString = JsonNullable.of(value);
        }
    }
}