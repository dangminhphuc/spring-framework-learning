package com.dangminhphuc.dev.beans.advance;

import com.dangminhphuc.dev.beans.advance.annotation.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

public class AnnotationConfigurationTest {
    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("Singleton bean, return same instance when get bean multiple times")
    public void singletonBean_shouldReturnSameInstance_whenGetSingletonBeansMultipleTimes() {
        String beanName = "singletonBean";

        SimpleBean1st bean01 = this.context.getBean(beanName, SimpleBean1st.class);
        SimpleBean1st bean02 = this.context.getBean(beanName, SimpleBean1st.class);

        assertEquals(bean01, bean02, "singleton beans should return same instance");
    }

    @Test
    @DisplayName("Prototype bean")
    public void prototypeBean_shouldReturnNewInstance_whenGetMultipleTimes() {
        String beanName = "prototypeBean";

        PrototypeBean bean01 = this.context.getBean(beanName, PrototypeBean.class);
        PrototypeBean bean02 = this.context.getBean(beanName, PrototypeBean.class);

        assertNotEquals(bean01, bean02, "prototype beans should return difference instances");
    }

    @Test
    @DisplayName("Should autowire constructor when argument type matches a bean")
    public void shouldAutoInjectBean_whenConstructorArgTypeMatches() {
        AutowireConstructorBean bean = this.context.getBean("autowireConstructorBean", AutowireConstructorBean.class);

        SimpleBean1st simpleBean = this.context.getBean("singletonBean", SimpleBean1st.class);

        assertNotNull(bean);
        assertEquals("autowireConstructor", bean.getMode());
        assertEquals(simpleBean, bean.getSimpleBean());
    }

    @Test
    @DisplayName("Should autowire property when property type matches a bean")
    public void shouldAutoInjectBean_whenPropertyTypeMatches() {
        AutowirePropertyBean bean = this.context.getBean("autowirePropertyBean", AutowirePropertyBean.class);

        SimpleBean1st simpleBean = this.context.getBean("singletonBean", SimpleBean1st.class);

        assertNotNull(bean);
        assertEquals("autowireByType", bean.getMode());
        assertEquals(simpleBean, bean.getSimpleBean());
    }

    @Test
    @DisplayName("Should autowire property when property name matches a bean")
    public void shouldAutoInjectBean_whenPropertyNameMatches() {
        AutowireByNameBean bean = this.context.getBean("autowireByNameBean", AutowireByNameBean.class);

        SimpleBean1st simpleBean = this.context.getBean("singletonBean", SimpleBean1st.class);

        assertNotNull(bean);
        assertEquals("autowireByName", bean.getMode());
        assertEquals(simpleBean, bean.getSingletonBean());
    }

    @Test
    @DisplayName("Test Life cycle, bean should execute init/destroy method when bean loaded")
    public void shouldExecuteInitDestroyMethod_whenBeanInstanced() {
        LifeCycleBean bean = this.context.getBean("lifecycleBean", LifeCycleBean.class);

        assertEquals(bean.getStates().get(0), "constructor");
        assertEquals(bean.getStates().get(1), "postConstruct");

        // close
        this.context.close();

        assertEquals(bean.getStates().get(2), "preDestroy");
    }

    @Test
    @DisplayName("Should initialize lazy bean when bean requested")
    public void shouldNotInitializeLazyBean_untilBeanRequested() {
        String beanName = "lazyBean";
        this.context.containsBean(beanName);

        // request bean
        this.context.getBean(beanName, LazyBean.class);

        assertTrue(this.context.containsBean(beanName));
    }

    @Test
//    @DisplayName("Should initialize lazy bean when bean requested")
    public void shouldInjectPrimaryBean_whenHasMultipleBeanSameTypes() {
        AdvanceBean bean = this.context.getBean("advanceBean", AdvanceBean.class);

        PrimaryBean expected = this.context.getBean("primaryBean", PrimaryBean.class);
        assertEquals(expected, bean.getPrimaryBean());
    }

    @Test
//    @DisplayName("Should initialize lazy bean when bean requested")
    public void shouldInjectQualifiedBean_whenSpecifyABean() {
        AdvanceBean bean = this.context.getBean("advanceBean", AdvanceBean.class);

        QualifiedBean expected = this.context.getBean("qualifiedBean", QualifiedBean.class);
        assertEquals(expected, bean.getQualifiedBean());
        assertNotEquals(expected, bean.getPrimaryBean());
    }

}
