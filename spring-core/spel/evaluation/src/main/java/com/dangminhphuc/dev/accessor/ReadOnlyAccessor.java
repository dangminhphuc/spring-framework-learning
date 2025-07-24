package com.dangminhphuc.dev.accessor;

import com.dangminhphuc.dev.BindingBean;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypedValue;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class ReadOnlyAccessor implements PropertyAccessor {

    public ReadOnlyAccessor() {
    }

    @Override
    public boolean canRead(EvaluationContext context, Object target, String name) throws AccessException {
        // Allow reading all properties
        return true;
    }

    @Override
    public TypedValue read(EvaluationContext context, Object target, String name) throws AccessException {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(name, target.getClass());
            Method getter = pd.getReadMethod();
            Object value = getter.invoke(target);
            return new TypedValue(value);
        } catch (Exception e) {
            throw new AccessException("Failed to read property " + name, e);
        }
    }

    @Override
    public boolean canWrite(EvaluationContext context, Object target, String name) throws AccessException {
        // This accessor is read-only
        return false;
    }

    @Override
    public void write(EvaluationContext context, Object target, String name, Object newValue) throws AccessException {
        throw new UnsupportedOperationException("Writing is not supported by ReadOnlyAccessor");
    }

    @Override
    public Class<?>[] getSpecificTargetClasses() {
        return new Class[]{BindingBean.class};
    }
}
