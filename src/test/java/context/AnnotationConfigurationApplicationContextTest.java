package context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.bobocode.context.AnnotationConfigurationApplicationContext;
import org.bobocode.context.ApplicationContext;
import org.bobocode.exceptions.NoSuchBeanException;
import org.bobocode.exceptions.NoUniqueBeanException;

class AnnotationConfigurationApplicationContextTest {

    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        applicationContext = new AnnotationConfigurationApplicationContext("context");
    }

    @Test
    void getBeanByTypeTest() {
        MessageService messageService = applicationContext.getBean(PrintHiService.class);
        assertEquals("Hi from printHiService", messageService.getMessage());
    }

    @Test
    void getBeanByTypeWhenMoreThenOneBeenFoundTest() {
        assertThrows(NoUniqueBeanException.class, () -> applicationContext.getBean(MessageService.class));
    }

    @Test
    void getBeanByNoExistedTypeTest() {
        assertThrows(NoSuchBeanException.class, () -> applicationContext.getBean(ApplicationContext.class));
    }

    @Test
    void getBeanByNameAndTypeTest() {
        MessageService messageService = applicationContext.getBean("printHelloService", PrintHelloService.class);
        assertEquals("Hello from printHelloService", messageService.getMessage());
    }

    @Test
    void getBeanByTypeAndNotExistedNameTest() {
        assertThrows(NoSuchBeanException.class, () -> applicationContext.getBean("notExistedName", PrintHelloService.class));
    }

    @Test
    void getAllBeansByType() {
        Map<String, Object> beans = applicationContext.getBeans(MessageService.class);
        assertEquals(2, beans.size());

        assertTrue(beans.containsKey("printHelloService"));
        assertTrue(beans.containsKey("printHiService"));
    }
}
