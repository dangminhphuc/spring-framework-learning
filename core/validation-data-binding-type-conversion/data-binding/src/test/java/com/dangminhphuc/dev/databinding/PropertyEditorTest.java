package com.dangminhphuc.dev.databinding;

import com.dangminhphuc.dev.databinding.property.Bar;
import com.dangminhphuc.dev.databinding.property.BarEditor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyEditorTest {

    @Test
    @DisplayName("Set and get simple and nested properties with BeanWrapper")
    public void propertyEditor_whenEditorNull_thenPropertyEditorManagerAutoLoadEditor() {
        // Register the custom PropertyEditor for BindingObject
        // PropertyEditorManager.registerEditor(Bar.class, null);
        PropertyEditor editor = PropertyEditorManager.findEditor(Bar.class);

        String currentEditor = editor.getClass().getName();
        System.out.println("PropertyEditor for Bar: " + currentEditor);

        // Verify the properties
        assertEquals(BarEditor.class.getName(), currentEditor, "PropertyEditor should be BarEditor");
    }


    @Test
    @DisplayName("Set and get simple properties with PropertyEditor")
    public void propertyEditor_whenPropertyEditorManagerAutoLoad_thenSetValue() {
        // Register the custom PropertyEditor for BindingObject
        // PropertyEditorManager.registerEditor(Bar.class, null);
        PropertyEditor editor = PropertyEditorManager.findEditor(Bar.class);

        editor.setAsText("29,This is a string");

        Bar bar = (Bar) editor.getValue();
        System.out.println("Object after setting properties: " + bar);

        // Verify the properties
        assertEquals(29, bar.getNumber(), " Number should be set to 29");
        assertEquals("This is a string", bar.getString(), "Text should be set to 'This is a string'");
    }
}
