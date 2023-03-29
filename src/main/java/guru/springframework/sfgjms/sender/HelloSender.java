package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.ServletOutputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloSender {
private final JmsTemplate jmsTemplate;
private final ObjectMapper objectMapper;
    @Scheduled(fixedRate =2000 )
    public void sendMessage(){
        System.out.println("Im sending message");
        HelloWorldMessage message= HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World im happay hamdolah")
                .build();
        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE,message);
        System.out.println("Message sent");
    }

    @Scheduled(fixedRate =2000 )
    public void sendAndReceiveMessage(){

        HelloWorldMessage message= HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World im happay hamdolah")
                .build();
        jmsTemplate.sendAndReceive(JmsConfig.MY_QUEUE_SEND_RECEIVE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage=session.createTextMessage();

                try {
                    helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                    helloMessage.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");

                    System.out.println("Sending Hello");

                    return helloMessage;

                } catch (JsonProcessingException e) {
                    throw new JMSException("boom");
                }
            }
        });
        System.out.println("Message sent");
    }
}
