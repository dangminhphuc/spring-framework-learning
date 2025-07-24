package com.dangminhphuc.dev.evaluation;

import com.dangminhphuc.dev.BindingBean;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserConfigurationTest {

    @Test
    void spElParser_withoutSpElParseConfigure_shouldReturnNullAndFailedOnIndexAccess() {
        // 1. Arrange
        BindingBean bean = new BindingBean();
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();

        // 2. Act
        Object nullObject = parser.parseExpression("nullable").getValue(context, bean);

        // 3. Assert
        assertNull(nullObject, "Nullable field should be null when accessed without SpEL parser configuration");
        assertThrows(SpelEvaluationException.class,
                () -> parser.parseExpression("booleans[1]").getValue(context, bean, String.class),
                "Accessing an uninitialized collection should throw an exception");
    }

//    @Test
//    void xxx() {
//        // 1. Arrange
//        SpelParserConfiguration configuration = new SpelParserConfiguration(true, true);
//        ExpressionParser parser = new SpelExpressionParser(configuration);
//
//        BindingBean bean = new BindingBean();
//        // 2. Act
//        Nullable nullable = parser.parseExpression("nullable").getValue(bean, Nullable.class);
//        parser.parseExpression("booleans[1]");
//
//        // 3. Assert
//        assertFalse(bean.getBooleans().get(0), "Booleans list should be initialized with an empty Boolean value at index 0");
//        assertEquals(List.of(false, true), bean.getBooleans(), "Booleans list should be initialized with a new Boolean value at index 1");
//
//    }
}
