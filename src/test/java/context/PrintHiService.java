package context;

import org.bobocode.annotation.Bean;

@Bean
public class PrintHiService implements MessageService {
    @Override
    public String getMessage() {
        return "Hi from printHiService";
    }
}
