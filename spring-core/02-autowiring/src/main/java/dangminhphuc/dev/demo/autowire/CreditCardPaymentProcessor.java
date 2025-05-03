package dangminhphuc.dev.demo.autowire;

import org.springframework.stereotype.Component;

@Component("creditPaymentProcessor")
public class CreditCardPaymentProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing payment via Credit Card: $" + amount);
    }
}
