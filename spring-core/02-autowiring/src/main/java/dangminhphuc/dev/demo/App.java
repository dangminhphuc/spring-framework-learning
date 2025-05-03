package dangminhphuc.dev.demo;

import dangminhphuc.dev.demo.autowire.AppConfig;
import dangminhphuc.dev.demo.autowire.CheckoutService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CheckoutService service = context.getBean(CheckoutService.class);
        service.checkout(100);
        service.checkoutWithMultiplePaymentProcessors(200);
    }
}
