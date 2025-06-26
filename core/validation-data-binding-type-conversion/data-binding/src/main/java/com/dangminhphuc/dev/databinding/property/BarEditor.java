package com.dangminhphuc.dev.databinding.property;

import java.beans.PropertyEditorSupport;

public class BarEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] parts = text.split(",");

        int number = parts[0].isEmpty() ? 0 : Integer.parseInt(parts[0].trim());
        String string = parts[1].trim();

        Bar object = new Bar();
        object.setNumber(number);
        object.setString(string);

        setValue(object);
    }
}
