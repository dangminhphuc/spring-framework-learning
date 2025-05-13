package dangminhphuc.dev.demo.extensionpoints.beanpostprocessor;

import org.springframework.beans.factory.config.BeanPostProcessor;

public class ServiceNameCustomize implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof Service) {
            System.out.println("Before initialization of bean: " + beanName + ": " + bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof Service) {
            System.out.println("After initialization of bean: " + beanName + ": " + bean);
        }
        return bean;
    }
}
