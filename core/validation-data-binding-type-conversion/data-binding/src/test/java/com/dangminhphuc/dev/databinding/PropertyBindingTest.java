package com.dangminhphuc.dev.databinding;

import com.dangminhphuc.dev.databinding.property.Bar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyBindingTest {
    private Bar object;

    @BeforeEach
    public void setUp() {
        this.object = new Bar();
    }

    @Test
    @DisplayName("Set and get simple and nested properties with BeanWrapper")
    public void propertyBinding_whenSetSimpleProperty_thenGetSimplePropertySuccessfully() {
        BeanWrapper wrapper = new BeanWrapperImpl(this.object);
        wrapper.setPropertyValue("number", 42);
        wrapper.setPropertyValue("string", "This is a string");

        System.out.println("Object after setting properties: " + this.object);

        assertEquals(42, object.getNumber(), "Number should be set to 42");
        assertEquals("This is a string", object.getString(), " Text should be set to 'This is a string'");

        // Verify using getPropertyValue
        assertEquals(42, wrapper.getPropertyValue("number"), "Number should be 42");
        assertEquals("This is a string", wrapper.getPropertyValue("string"), "String should be 'This is a string'");
    }

    @Test
    @DisplayName("Set and get nested properties with BeanWrapper")
    public void propertyBinding_whenSetNestedProperty_thenGetNestedPropertySuccessfully() {
        // for nested properties, we need to ensure the parent object is initialized
        this.object.setFoo(new Bar.Foo());

        BeanWrapper wrapper = new BeanWrapperImpl(this.object);
        wrapper.setPropertyValue("foo.id", "1");

        System.out.println("Object after setting nested property: " + this.object);

        // Verify the nested object's ID is set correctly
        assertEquals("1", this.object.getFoo().getId(), "Item ID should be set to 1");

        // Verify using getPropertyValue
        assertEquals("1", wrapper.getPropertyValue("foo.id"), "Nested ID should be '1'");
    }

    @Test
    @DisplayName("Set and get list properties with BeanWrapper")
    public void propertyBinding_whenSetIndexedProperty_thenReturnSuccess() {
        BeanWrapper wrapper = new BeanWrapperImpl(this.object);

        List<String> list = List.of("Item 1st", "Item 2nd");
        wrapper.setPropertyValue("list", list);

        System.out.println("Object after setting list property: " + this.object);

        // Verify the list contains the expected items
        assertEquals("Item 1st", object.getList().get(0), "First item should be 'Item 1st'");
        assertEquals("Item 2nd", object.getList().get(1), "Second item should be 'Item 2nd'");

        // Verify using getPropertyValue
        assertEquals("Item 1st", wrapper.getPropertyValue("list[0]"), "First item should be 'Item 1st'");
        assertEquals("Item 2nd", wrapper.getPropertyValue("list[1]"), "Second item should be 'Item 2nd'");
    }

    @Test
    @DisplayName("Set and get map properties with BeanWrapper")
    public void propertyBinding_whenSetMapProperty_thenReturnSuccess() {
        // for map properties, we need to ensure the map is initialized
        this.object.setMap(new java.util.HashMap<>());

        BeanWrapper wrapper = new BeanWrapperImpl(this.object);
        // Use the BeanWrapper to set properties in the map

        wrapper.setPropertyValue("map['key1']", "value1");
        wrapper.setPropertyValue("map['key2']", "value2");

        System.out.println("Object after setting map properties: " + this.object);

        // Verify the map contains the expected values
        assertEquals("value1", this.object.getMap().get("key1"), "Map should contain key1 with value 'value1'");
        assertEquals("value2", this.object.getMap().get("key2"), "Map should contain key2 with value 'value2'");

        // Verify using getPropertyValue
        assertEquals("value1", wrapper.getPropertyValue("map['key1']"), "Map should contain key1 with value 'value1'");
        assertEquals("value2", wrapper.getPropertyValue("map['key2']"), "Map should contain key1 with value 'value2'");
    }
}
