package com.dangminhphuc.dev.beans.advance;

import com.dangminhphuc.dev.beans.advance.xml.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

public class XmlConfigurationTest {
    private ApplicationContext context;
    private SimpleBean singletonBean;
    private PropertyBean propertyBean;
    private LifeCycleBean lifeCycleBean;


    @BeforeEach
    public void context() {
        this.context = new ClassPathXmlApplicationContext("beans.xml");
        this.singletonBean = this.context.getBean("singletonBean", SimpleBean.class);
        this.propertyBean = this.context.getBean("propertyBean", PropertyBean.class);
        this.lifeCycleBean = this.context.getBean("lifecycleBean", LifeCycleBean.class);
    }

    @Test
    @DisplayName("Singleton bean")
    public void singletonBean_shouldReturnOneInstance_whenGetMultipleTimes() {
        SimpleBean bean01 = this.context.getBean("singletonBean", SimpleBean.class);
        SimpleBean bean02 = this.context.getBean("singletonBean", SimpleBean.class);

        assertEquals(bean01, bean02, "singletonBean setting is singleton scope");
    }

    @Test
    @DisplayName("Prototype bean")
    public void prototypeBean_shouldReturnNewInstance_whenGetMultipleTimes() {
        SimpleBean bean01 = this.context.getBean("prototypeBean", SimpleBean.class);
        SimpleBean bean02 = this.context.getBean("prototypeBean", SimpleBean.class);

        assertNotEquals(bean01, bean02, "singletonBean setting is singleton scope");
    }

    @Test
    @DisplayName("Constructor bean with args index")
    public void constructorBean_shouldNewInstance_whenInputConstructorParamByIndex() {
        SimpleBean simpleBean = this.context.getBean("singletonBean", SimpleBean.class);
        ConstructorBean bean = this.context.getBean("constructorArgsIndexBean", ConstructorBean.class);

        assertNotNull(bean);
        assertEquals("Args Index", bean.getProperty());
        assertEquals(simpleBean, bean.getSimpleBean());
    }

    @Test
    @DisplayName("Constructor bean with args name")
    public void constructorBean_shouldNewInstance_whenInputConstructorParamByName() {
        ConstructorBean bean = this.context.getBean("constructorArgsNameBean", ConstructorBean.class);

        assertNotNull(bean);
        assertEquals("Args Name", bean.getProperty());
        assertEquals(singletonBean, bean.getSimpleBean());
    }

    @Test
    @DisplayName("Bean injected value from property")
    public void propertyBean_shouldNewInstance_whenInjectProperty() {
        PropertyBean bean = this.context.getBean("propertyBean", PropertyBean.class);

        assertNotNull(bean);
        assertEquals(95, bean.getVal());
        assertEquals(this.singletonBean, bean.getSimpleBean());
    }

    @Test
    @DisplayName("Test Life cycle, bean should execute init/destroy method when bean loaded")
    public void lifecycleBean_shouldExecuteInitDestroyMethod_whenBeanLoaded() {
        this.context.getBean("lifecycleBean", LifeCycleBean.class);

//        assertTrue(getConsoleOutput().contains("ON CONSTRUCTOR"));
//        assertTrue(getConsoleOutput().contains("ON INIT"));

        // close
        ((ClassPathXmlApplicationContext) this.context).close();
//        assertTrue(getConsoleOutput().contains("ON DESTROY"));
    }

    @Test
    @DisplayName("autowire byName; inject beans defined same name")
    public void autowireBean_shouldAutoInjectValue_whenConfigAutoWireByNames() {
        AutowireBean bean = this.context.getBean("autowireByNameBean", AutowireBean.class);

        assertNotNull(bean);
        assertEquals(this.propertyBean, bean.getPropertyBean());
        assertNull(bean.getLIFE_CYCLE_BEAN(), "lifecycle property shouldn't injected by name");
    }


    @Test
    @DisplayName("autowire byType; inject beans defined same type")
    public void autowireBean_shouldAutoInjectValue_whenConfigAutoWireByTypes() {
        AutowireBean bean = this.context.getBean("autowireByTypeBean", AutowireBean.class);

        assertNotNull(bean);
        assertEquals(this.propertyBean, bean.getPropertyBean());
        assertEquals(this.lifeCycleBean, bean.getLIFE_CYCLE_BEAN());
    }

    @Test
    @DisplayName("autowire by constructor")
    public void autowireBean_shouldAutoInjectValue_whenConfigAutoWireByConstructor() {
        AutowireBean bean = this.context.getBean("autowireConstructorBean", AutowireBean.class);

        assertNotNull(bean);
        assertEquals(this.propertyBean, bean.getPropertyBean());
        assertNull(bean.getLIFE_CYCLE_BEAN(), "LIFE_CYCLE_BEAN property should null, don't injected by autowire=constructor");
    }

//    @Test
//    public void dependsOn() {
//        this.context.getBean("2ndBean", DependsOnBean.class);
//
//        assertTrue(getConsoleOutput().contains("1st bean"));
//        assertTrue(getConsoleOutput().contains("2nd bean"));
//    }
}
