package com.dangminhphuc.dev.databinding;

import com.dangminhphuc.dev.ConstructorBindingObject;
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
    private static DataBinder getBinder(Map<String, ?> input) {
        DataBinder binder = new DataBinder(null);
        binder.setTargetType(ResolvableType.forClass(ConstructorBindingObject.class));
        binder.construct(new SimpleValueResolver(input));
        return binder;
    }

    @Test
    @DisplayName("Should return Object when input valid")
    public void construct_whenInputValid_thenReturnObject() throws Exception {
        Map<String, ?> input = Map.of(
                "number", 1,
                "text", "Text",
                "text_2nd", "Text resolved",
                "list[0]", "Element 1st",
                "list[1]", "Element 2nd",
                "map[number]", "Number",
                "map[string]", "String",
                "item.id", "1"
        );

        DataBinder binder = getBinder(input);
        BindingResult bindingResult = binder.getBindingResult();

        ConstructorBindingObject construct = (ConstructorBindingObject) bindingResult.getTarget();

        assertNotNull(construct);
        assertEquals(1, construct.getNumber());
        assertEquals("Text", construct.getText());
        assertEquals("Text resolved", construct.getTextCustomized());

        assertFalse(construct.getList().isEmpty());
        assertEquals("Element 1st", construct.getList().get(0));
        assertEquals("Element 2nd", construct.getList().get(1));


        assertEquals("Number", construct.getMap().get("number"));
        assertEquals("String", construct.getMap().get("string"));

        assertNotNull(construct.getItem());
        assertEquals("1", construct.getItem().getId());
    }

    @Test
    @DisplayName("Should return \"typeMismatch\" binding error when input has type mismatch")
    public void construct_whenInputHasTypeMismatch_thenHasErrors() {
        Map<String, ?> input = Map.of(
                "number", "One"
        );

        DataBinder binder = getBinder(input);
        BindingResult bindingResult = binder.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();

        assertTrue(bindingResult.hasErrors());
        assertEquals("typeMismatch", errors.get(0).getCode());
    }
}