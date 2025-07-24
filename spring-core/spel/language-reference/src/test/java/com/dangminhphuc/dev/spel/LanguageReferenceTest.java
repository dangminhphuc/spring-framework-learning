package com.dangminhphuc.dev.spel;

import com.dangminhphuc.dev.ExampleBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class LanguageReferenceTest {
    private EvaluationContext context;
    private ExpressionParser parser;

    @BeforeEach
    void setUp() {
        // Initialize the parser before each test
        this.parser = new SpelExpressionParser(new SpelParserConfiguration(true, true));
        this.context = SimpleEvaluationContext.forReadWriteDataBinding().build();
    }

    @Nested
    @DisplayName("Literal Expression Tests")
    class LiteralExpression {

        @Test
        void shouldReturnStringValue_whenEvaluatingStringLiteral() {
            String expression = "'dangminhphuc.dev'";
            String result = parser.parseExpression(expression).getValue(String.class);
            assertEquals("dangminhphuc.dev", result, "String literal should evaluate correctly");
        }

        @Test
        void shouldReturnIntegerValue_whenEvaluatingIntegerLiteral() {
            int min = Integer.MIN_VALUE + 1;   // "-2147483648" is Integer.MIN_VALUE
            int max = Integer.MAX_VALUE;       // "2147483647"

            Integer minVal = parser.parseExpression(min + "").getValue(Integer.class);
            Integer maxVal = parser.parseExpression(max + "").getValue(Integer.class);

            assertEquals(min, minVal, "Should evaluate to Integer.MIN_VALUE");
            assertEquals(max, maxVal, "Should evaluate to Integer.MAX_VALUE");
        }

        @Test
        void shouldReturnBooleanValue_whenEvaluatingIntegerLiteral() {
            String expression = String.valueOf(Integer.MAX_VALUE); // "2147483647"
            Integer intValue = parser.parseExpression(expression).getValue(Integer.class);
            assertEquals(Integer.MAX_VALUE, intValue, "Should evaluate to Integer.MAX_VALUE");
        }

        @Test
        void shouldReturnNullObject_whenEvaluatingIntegerLiteral() {
            Object nullable = parser.parseExpression("null").getValue(Integer.class);
            assertNull(nullable, "Should evaluate to null when using 'null' literal");
        }
    }

    @Nested
    class PropertyCollections {

        @Test
        void shouldNavigateToProperty_whenUsingDotNotation() {
            ExampleBean bean = new ExampleBean();

            Integer value = parser.parseExpression("bar.i + 1900").getValue(context, bean, Integer.class);
            assertEquals(1900, value, "Should evaluate to 1900 when bar.i is 0");
        }

        @Test
        void shouldReturnValue_whenUsingCollectionIndex() {
            ExampleBean bean = new ExampleBean();
            bean.setBooleans(List.of(true, false, true));

            Boolean single = parser.parseExpression("booleans[2]").getValue(context, bean, Boolean.class);
            Boolean combine = parser.parseExpression("booleans[0] && true").getValue(context, bean, Boolean.class);

            assertEquals(Boolean.TRUE, single, "Should evaluate to 102 when fooList[1].i is 2");
            assertEquals(Boolean.TRUE, combine);
        }

        @Test
        void shouldReturnFieldValue_whenUsingIndexedProperty() {
            ExampleBean bean = new ExampleBean();
            bean.setNumber(1);
            bean.setString("dangminhphuc.dev");

            Integer val1 = parser.parseExpression("#root['number']").getValue(context, bean, Integer.class);
            String val02 = parser.parseExpression("#root['string']").getValue(context, bean, String.class);

            assertEquals(1, val1, " Should evaluate to 1 for number field");
            assertEquals("dangminhphuc.dev", val02, "Should evaluate to 'dangminhphuc.dev' for string field");
        }

        @Test
        void shouldReturnMapValue_whenUsingMapIndex() {
            ExampleBean bean = new ExampleBean();
            bean.setMap(Map.of("key1", "value1", "key2", "value2"));

            String val1 = parser.parseExpression("map['key1']").getValue(context, bean, String.class);
            String val2 = parser.parseExpression("map['key2']").getValue(context, bean, String.class);

            assertEquals("value1", val1, "Should evaluate to 'value1' for key1");
            assertEquals("value2", val2, "Should evaluate to 'value2' for key2");
        }
    }

    @Nested
    class InlineExpression {

        @Test
        void parsesInlineListOfIntegers() {
            List<?> result = parser.parseExpression("{1, 2, 3}").getValue(context, List.class);

            List<Integer> expected = List.of(1, 2, 3);
            assertEquals(expected, result);
        }

        @Test
        void parsesInlineMapWithStringKeysAndIntegerValues() {
            Map<?, ?> result = parser.parseExpression("{'key1': 1, 'key2': 2}").getValue(context, java.util.Map.class);

            Map<String, Integer> expected = Map.of("key1", 1, "key2", 2);
            assertEquals(expected, result);
        }

        @Test
        void parsesInlineMapWithNestedMapsAndNullEntry() {
            String inlineExpression = "{'001':{id:'DMP',name:'dangminhphuc'}, '002':{id:'',name:''}, '003':null}";

            // evaluate an inline map expression to a Map<String, String>
            java.util.Map<?, ?> result = parser.parseExpression(inlineExpression).getValue(context, java.util.Map.class);

            // Asserts
            assertNotNull(result);
            java.util.Map<?, ?> item01 = (java.util.Map<?, ?>) result.get("001");
            assertAll(
                    () -> assertNotNull(item01),
                    () -> assertEquals("DMP", item01.get("id")),
                    () -> assertEquals("dangminhphuc", item01.get("name"))
            );

            java.util.Map<?, ?> item02 = (java.util.Map<?, ?>) result.get("002");
            assertAll(
                    () -> assertNotNull(item02),
                    () -> assertNotNull(item02.get("id")),
                    () -> assertNotNull(item02.get("name"))
            );

            java.util.Map<?, ?> item03 = (java.util.Map<?, ?>) result.get("003");
            assertNull(item03);
        }
    }

    /**
     * You can invoke methods by using the typical Java programming syntax.
     * You can also invoke methods directly on literals such as strings or numbers. Varargs are supported as well.
     */
    @Nested
    class MethodInvocation {
        @Test
        void invokeMethod_whenUsingMethodExpression() {
            String expression = "'abc'.substring(1, 3)";
            String bc = parser.parseExpression(expression).getValue(String.class);
            assertEquals("bc", bc, "Should return substring 'bc' from 'abc'");
        }
    }

    @Nested
    class OperatorsExpression {
    }
}
