package dangminhphuc.dev.demo.autowire;

import org.springframework.stereotype.Component;

@Component("paypalPaymentProcessor")
public class PaypalPaymentProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing payment via Paypal: $" + amount);
    }
}
