package guru.springframework.sfgjms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {
    public final static String MY_QUEUE="my-helleo-world";
    public final static String MY_QUEUE_SEND_RECEIVE="my-helleo-world-Send-Receive";
    @Bean
    public MessageConverter messageConverter(){
        MappingJackson2MessageConverter converter=new MappingJackson2MessageConverter() ;
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
