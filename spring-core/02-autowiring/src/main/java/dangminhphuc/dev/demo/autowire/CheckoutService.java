package dangminhphuc.dev.demo.autowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutService {
    @Autowired
    @Qualifier("creditPaymentProcessor")
    private PaymentProcessor paymentProcessor;

    @Autowired
    private List<PaymentProcessor> paymentProcessors;

    public void checkout(double amount) {
        paymentProcessor.processPayment(amount);
    }

    public void checkoutWithMultiplePaymentProcessors(double amount) {
        for (PaymentProcessor paymentProcessor : paymentProcessors) {
            paymentProcessor.processPayment(amount);
        }
    }
}
