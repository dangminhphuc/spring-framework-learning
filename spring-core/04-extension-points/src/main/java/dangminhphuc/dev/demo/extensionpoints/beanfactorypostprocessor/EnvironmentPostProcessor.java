package dangminhphuc.dev.demo.extensionpoints.beanfactorypostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryPostProcessor: Modifying bean definitions before initialization.");
        BeanDefinition greeting = beanFactory.getBeanDefinition("greeting");

        greeting.getPropertyValues().add("message", "Xin chao");
        System.out.println("BeanFactoryPostProcessor: Modified bean definitions before initialization.");
    }
}
