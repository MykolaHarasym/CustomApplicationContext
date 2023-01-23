package context;

import org.bobocode.annotation.Bean;

@Bean("printHelloService")
public class PrintHelloService implements MessageService {

    public String getMessage(){
        return "Hello from printHelloService";
    }
}
