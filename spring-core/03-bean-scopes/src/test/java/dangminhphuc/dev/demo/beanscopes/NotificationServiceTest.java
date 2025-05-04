package dangminhphuc.dev.demo.beanscopes;

import dangminhphuc.dev.demo.beanscopes.prototype.TelegramNotificationService;
import dangminhphuc.dev.demo.beanscopes.singleton.EmailNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(SpringExtension.class)
class NotificationServiceTest {

    private EmailNotificationService singletonService01;

    private EmailNotificationService singletonService02;

    private TelegramNotificationService prototypeService01;

    private TelegramNotificationService prototypeService02;

    @BeforeEach
    void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        this.singletonService01 = context.getBean("emailService", EmailNotificationService.class);
        this.singletonService02 = context.getBean("emailService", EmailNotificationService.class);
        this.prototypeService01 = context.getBean("telegramService", TelegramNotificationService.class);
        this.prototypeService02 = context.getBean("telegramService", TelegramNotificationService.class);
    }

    @Test
    void testSingletonService() {
        assertSame(singletonService01, singletonService02, "Both singleton services should be same");
    }

    @Test
    void testPrototypeService() {
        assertNotSame(prototypeService01, prototypeService02, "Both prototype services should not be same");
    }
}