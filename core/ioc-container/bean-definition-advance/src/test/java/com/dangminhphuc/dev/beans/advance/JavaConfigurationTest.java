package com.dangminhphuc.dev.beans.advance;

import com.dangminhphuc.dev.beans.advance.java.AutowireBean;
import com.dangminhphuc.dev.beans.advance.java.ConstructorBean;
import com.dangminhphuc.dev.beans.advance.java.SetterBasedBean;
import com.dangminhphuc.dev.beans.advance.java.SimpleBean1st;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaConfigurationTest {
    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfiguration.class);

    @Test
    @DisplayName("Singleton bean, return same instance when get bean multiple times")
    public void singletonBean_shouldReturnSameInstance_whenGetSingletonBeansMultipleTimes() {
        String beanName = "singletonBean";

        SimpleBean1st bean01 = this.context.getBean(beanName, SimpleBean1st.class);
        SimpleBean1st bean02 = this.context.getBean(beanName, SimpleBean1st.class);

        assertEquals(bean01, bean02, "singleton beans should return same instance");
    }

    @Test
    @DisplayName("Prototype Bean return instance")
    public void prototypeBean() {
        ConstructorBean bean = this.context.getBean("constructorBean", ConstructorBean.class);
    }


    @Test
    @DisplayName("")
    public void setterBasedDI() {
        SetterBasedBean bean = this.context.getBean("setterBean", SetterBasedBean.class);

        SimpleBean1st singletonBean = this.context.getBean("singletonBean", SimpleBean1st.class);

        assertEquals("Setter", bean.getId());
        assertEquals(singletonBean, bean.getSingletonBean());

    }


    @Test
    @DisplayName("")
    public void autowire() {
        AutowireBean bean = this.context.getBean("autowireBean", AutowireBean.class);

        SimpleBean1st singletonBean = this.context.getBean("singletonBean", SimpleBean1st.class);

//        assertEquals("Setter", bean.getId());
        assertEquals(singletonBean, bean.getSingletonBean());

    }

}
