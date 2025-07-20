package com.dangminhphuc.dev;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EvaluationContextTest {

    private final ExpressionParser parser = new org.springframework.expression.spel.standard.SpelExpressionParser();

    @Test
    @DisplayName("Use ConversionService in EvaluationContext for type conversion")
    void useConversionService_whenSetValueWithDifferentTypes_thenConvertedAutomatically() {
        TypeConversionSupportBean bean = new TypeConversionSupportBean();

        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();

        // Purposefully using different formats to test type conversion
        // Reference: spring-core/validation-data-binding-type-conversion/type-conversion module
        this.parser.parseExpression("number").setValue(context, bean, "095");
        this.parser.parseExpression("string").setValue(context, bean, 01234);
        this.parser.parseExpression("booleans").setValue(context, bean, "true,false,true");

        assertEquals(95, bean.getNumber(), "Number should be converted from '095' to 95");
        assertEquals("01234", bean.getString(), "String should be converted from 01234 to '01234'");
        assertEquals(List.of(true, false, true), bean.getBooleans(), "Booleans should be converted from 'true,false,true' to List of Booleans");
    }

    @Test
    void doNotApplyParserConfiguration() {
        SpelParserConfiguration spelParserConfiguration = new SpelParserConfiguration(false, false);
        ExpressionParser parserWithConfig = new org.springframework.expression.spel.standard.SpelExpressionParser(spelParserConfiguration);

        TypeConversionSupportBean bean = new TypeConversionSupportBean();

        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();

        Object nullObject = parserWithConfig.parseExpression("nullable").getValue(context, bean);

        assertNull(nullObject, "Nullable field should remain null when not set");
        assertThrows(SpelEvaluationException.class,
                () -> parserWithConfig.parseExpression("booleans[1]").getValue(context, bean, String.class),
                "Expected an exception when trying to access a non-existent index in booleans list"
        );
    }

    @Test
    void applyParserConfiguration_whenParserConfigured_thenAutoGrowNullsAndCollections() {
        SpelParserConfiguration spelParserConfiguration = new SpelParserConfiguration(true, true);
        ExpressionParser parserWithConfig = new org.springframework.expression.spel.standard.SpelExpressionParser(spelParserConfiguration);

        TypeConversionSupportBean bean = new TypeConversionSupportBean();

        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();

        Nullable nullable = parserWithConfig.parseExpression("nullable").getValue(context, bean, Nullable.class);
        parserWithConfig.parseExpression("booleans[1]").getValue(context, bean, String.class);

        assertNotNull(nullable, "Nullable field should be initialized to a new Nullable instance when accessed");
        assertEquals(false, bean.getBooleans().get(0), "Booleans list should be initialized with an empty string");
    }
}
