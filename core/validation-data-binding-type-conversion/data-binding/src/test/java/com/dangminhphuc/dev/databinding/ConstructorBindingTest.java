package com.dangminhphuc.dev.databinding;

import com.dangminhphuc.dev.databinding.constructor.Bar;
import com.dangminhphuc.dev.databinding.constructor.SimpleValueResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ConstructorBindingTest {

    /**
     * 1. Create a DataBinder with null as the target object. <br/>
     * 2. Set targetType to the target class. <br/>
     * 3. Call construct.
     */
    public static DataBinder dataBinder(Map<String, ?> input) {
        DataBinder binder = new DataBinder(null);
        binder.setTargetType(ResolvableType.forClass(Bar.class));
        binder.construct(new SimpleValueResolver(input));
        return binder;
    }

    @Test
    @DisplayName("Should return Object when input valid")
    public void construct_whenInputValid_thenReturnObject() {
        Map<String, ?> input = Map.of(
                "number", 1,
                "string", "Text",
                "stringCustomized", "Text resolved",
                "list[0]", "Element 1st",
                "list[1]", "Element 2nd",
                "map[number]", "Number",
                "map[string]", "String",
                "foo.id", "F1"
        );

        DataBinder binder = dataBinder(input);
        BindingResult br = binder.getBindingResult();

        Bar target = (Bar) br.getTarget();
        System.out.println("Constructed object: " + target);

        assertNotNull(target);
        assertEquals(1, target.getNumber());
        assertEquals("Text", target.getString());
        assertEquals("Text resolved", target.getString2nd());

        assertFalse(target.getList().isEmpty());
        assertEquals("Element 1st", target.getList().get(0));
        assertEquals("Element 2nd", target.getList().get(1));

        assertEquals("Number", target.getMap().get("number"));
        assertEquals("String", target.getMap().get("string"));

        assertNotNull(target.getFoo());
        assertEquals("F1", target.getFoo().getId());
    }

    @Test
    @DisplayName("Should return \"typeMismatch\" binding error when input has type mismatch")
    public void construct_whenInputHasTypeMismatch_thenHasErrors() {
        Map<String, ?> input = Map.of(
                "number", "One"
        );

        DataBinder binder = dataBinder(input);
        BindingResult bindingResult = binder.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();

        assertTrue(bindingResult.hasErrors());
        assertEquals("typeMismatch", errors.get(0).getCode());
    }
}