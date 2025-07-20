package com.dangminhphuc.dev.evaluation;

import com.dangminhphuc.dev.BindingBean;
import com.dangminhphuc.dev.Nullable;
import com.dangminhphuc.dev.accessor.ReadOnlyAccessor;
import com.dangminhphuc.dev.accessor.ReadWriteAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class EvaluationContextTest {

    @Nested
    @DisplayName("SimpleEvaluationContext Tests")
    class SimpleEvaluationContextTest {

        // parser used to parse SpEL expressions
        private ExpressionParser parser;

        private SimpleEvaluationContextTest() {
        }

        @BeforeEach
        void setUp() {
            this.parser = new SpelExpressionParser();
        }

        @Nested
        @DisplayName("Read-Only data binding")
        class ForReadOnlyDataBinding {
            @Test
            @DisplayName("spelReadOnlyAccess_shouldReturnCorrectPropertyValues")
            void spelReadOnlyAccess_shouldReturnCorrectPropertyValues() {
                // 1. Arrange
                BindingBean bean = new BindingBean();
                bean.setNumber(1995);
                bean.setString("dangminhphuc.dev");

                // Using SimpleEvaluationContext for read-only data binding
                SimpleEvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

                // 2. Act
                Integer number = parser.parseExpression("number").getValue(context, bean, Integer.class);
                String string = parser.parseExpression("string").getValue(context, bean, String.class);
                Nullable nullable = parser.parseExpression("nullable").getValue(context, bean, Nullable.class);

                // 3. Assert
                assertEquals(1995, number);
                assertEquals("dangminhphuc.dev", string);
                assertNull(nullable, "Nullable should be null as it was not set");
            }

            @Test
            @DisplayName("spelReadOnlyAccess_shouldThrowSpelEvaluationExceptionWhenSettingValue")
            void spelReadOnlyAccess_shouldThrowSpelEvaluationExceptionWhenSettingValue() {
                // 1. Arrange
                BindingBean bean = new BindingBean();
                bean.setNumber(1995);

                // Using SimpleEvaluationContext for read-only data binding
                SimpleEvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

                // 2. Act
                Integer number = parser.parseExpression("number").getValue(context, bean, Integer.class);

                // 3. Assert
                assertEquals(1995, number);
                assertThrows(SpelEvaluationException.class, () -> {
                    // Attempting to set a value in read-only context should throw an exception
                    parser.parseExpression("string").setValue(context, bean, "dangminhphuc.dev");
                }, "Setting value in read-only context should throw SpelEvaluationException");
            }
        }

        @Nested
        @DisplayName("Read-Write data binding")
        class ForReadWriteDataBinding {

            @Test
            @DisplayName("spelReadWriteAccess_shouldAllowReadAccess")
            void spelReadWriteAccess_shouldAllowReadAccess() {
                // 1. Arrange
                BindingBean bean = new BindingBean();
                bean.setNumber(1995);
                bean.setString("dangminhphuc.dev");

                // Using SimpleEvaluationContext for read-write data binding
                SimpleEvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();

                // 2. Act
                Integer number = parser.parseExpression("number").getValue(context, bean, Integer.class);
                String string = parser.parseExpression("string").getValue(context, bean, String.class);

                // 3. Assert
                assertEquals(1995, number);
                assertEquals("dangminhphuc.dev", string);
            }

            @Test
            @DisplayName("spelReadWriteAccess_shouldAllowReadAndWriteAccess")
            void spelReadWriteAccess_shouldAllowReadAndWriteAccess() {
                // 1. Arrange
                BindingBean bean = new BindingBean();
                bean.setNumber(1995);

                // Using SimpleEvaluationContext for read-write data binding
                SimpleEvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();

                // 2. Act
                parser.parseExpression("number").setValue(context, bean, 1999);
                parser.parseExpression("string").setValue(context, bean, "dangminhphuc.dev");
                parser.parseExpression("nullable").setValue(context, bean, new Nullable());

                // 3. Assert
                assertEquals(1999, bean.getNumber());
                assertEquals("dangminhphuc.dev", bean.getString());
                assertNotNull(bean.getNullable());
            }
        }

        @Nested
        @DisplayName("Custom PropertyAccessors")
        class ForPropertyAccessors {

            PropertyAccessor readOnlyAccessor = new ReadOnlyAccessor();
            PropertyAccessor readWriteAccessor = new ReadWriteAccessor();


            @Test
            @DisplayName("A custom PropertyAccessor that restricts access to certain keys")
            void allowsAccessOnlyToAllowedFields() {
                // 1. Arrange
                BindingBean bean = new BindingBean();
                bean.setNumber(1995);
                bean.setString("dangminhphuc.dev");
                SimpleEvaluationContext context = SimpleEvaluationContext.forPropertyAccessors(readOnlyAccessor).build();

                // 2. Act
                Integer number = parser.parseExpression("number").getValue(context, bean, Integer.class);
                String string = parser.parseExpression("string").getValue(context, bean, String.class);

                // 3. Assert
                assertEquals(1995, number);
                assertEquals("dangminhphuc.dev", string);
            }

            @Test
            @DisplayName("Read-only PropertyAccessor should not allow writing values")
            void spelReadOnlyAccess_shouldThrowWhenWriting() {
                // 1. Arrange
                BindingBean bean = new BindingBean();
                SimpleEvaluationContext context = SimpleEvaluationContext.forPropertyAccessors(readOnlyAccessor).build();

                // 2. Act & Assert
                assertThrows(SpelEvaluationException.class, () -> parser.parseExpression("number").setValue(context, bean, 1995), "Setting value in read-only context should throw SpelEvaluationException");
            }

            @Test
            @DisplayName("A custom PropertyAccessor")
            void spelReadWriteAccess_shouldAllowSettingValues() {
                // 1. Arrange
                BindingBean bean = new BindingBean();
                SimpleEvaluationContext context = SimpleEvaluationContext.forPropertyAccessors(readWriteAccessor).build();

                // 2. Act
                parser.parseExpression("number").setValue(context, bean, 1995);
                parser.parseExpression("string").setValue(context, bean, "dangminhphuc.dev");

                // 3. Assert
                assertEquals(1995, bean.getNumber());
                assertEquals("dangminhphuc.dev", bean.getString());
            }

        }

        @Test
        @DisplayName("Use ConversionService in EvaluationContext for type conversion")
        void useConversionService_whenSetValueWithDifferentTypes_thenConvertedAutomatically() {
            // 1. Arrange
            BindingBean bean = new BindingBean();
            EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();

            // 2. Act
            // Purposefully using different formats to test type conversion
            // Reference: spring-core/validation-data-binding-type-conversion/type-conversion module
            this.parser.parseExpression("number").setValue(context, bean, "095");
            this.parser.parseExpression("booleans").setValue(context, bean, "true,false,true");

            // 3. Assert
            assertEquals(95, bean.getNumber());
            assertEquals(List.of(true, false, true), bean.getBooleans());
        }

    }
}
