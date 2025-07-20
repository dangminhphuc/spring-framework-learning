package com.dangminhphuc.dev.evaluation;

import com.dangminhphuc.dev.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import static org.junit.jupiter.api.Assertions.*;

public class ParserConfigurationTest {

//    @Test
//    void doNotApplyParserConfiguration() {
//        SpelParserConfiguration spelParserConfiguration = new SpelParserConfiguration(false, false);
//        ExpressionParser parserWithConfig = new org.springframework.expression.spel.standard.SpelExpressionParser(spelParserConfiguration);
//
//        TypeConversionSupportBean bean = new TypeConversionSupportBean();
//
//        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();
//
//        Object nullObject = parserWithConfig.parseExpression("nullable").getValue(context, bean);
//
//        assertNull(nullObject, "Nullable field should remain null when not set");
//        assertThrows(SpelEvaluationException.class, () -> parserWithConfig.parseExpression("booleans[1]").getValue(context, bean, String.class), "Expected an exception when trying to access a non-existent index in booleans list");
//    }
//
//    @Test
//    void applyParserConfiguration_whenParserConfigured_thenAutoGrowNullsAndCollections() {
//        SpelParserConfiguration spelParserConfiguration = new SpelParserConfiguration(true, true);
//        ExpressionParser parserWithConfig = new org.springframework.expression.spel.standard.SpelExpressionParser(spelParserConfiguration);
//
//        TypeConversionSupportBean bean = new TypeConversionSupportBean();
//
//        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();
//
//        Nullable nullable = parserWithConfig.parseExpression("nullable").getValue(context, bean, Nullable.class);
//        parserWithConfig.parseExpression("booleans[1]").getValue(context, bean, String.class);
//
//        assertNotNull(nullable, "Nullable field should be initialized to a new Nullable instance when accessed");
//        assertEquals(false, bean.getBooleans().get(0), "Booleans list should be initialized with an empty string");
//    }
}
