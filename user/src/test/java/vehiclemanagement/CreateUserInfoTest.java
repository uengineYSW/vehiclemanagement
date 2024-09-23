
package vehiclemanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;

import javax.inject.Inject;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vehiclemanagement.config.kafka.KafkaProcessor;
import vehiclemanagement.domain.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMessageVerifier
public class CreateUserInfoTest {

   private static final Logger LOGGER = LoggerFactory.getLogger(CreateUserInfoTest.class);
   
   @Autowired
   private KafkaProcessor processor;
   @Autowired
   private MessageCollector messageCollector;
   @Autowired
   private ApplicationContext applicationContext;

   @Autowired
   ObjectMapper objectMapper;

   @Autowired
   private MessageVerifier<Message<?>> messageVerifier;

   @Autowired
   public UserInfoRepository repository;

   @Test
   @SuppressWarnings("unchecked")
   public void test0() {

      //given:  
      UserInfo existingEntity = new UserInfo();

      existingEntity.setName("홍길동");
      existingEntity.setJuminNum("940101-1234567");
      existingEntity.setBirthDate("1994-01-01");
      existingEntity.setAddress([object Object]);

      repository.save(existingEntity);

      //when:  

  
      
      try {


      UserInfo newEntity = new UserInfo();

         newEntity.setName("홍길동");
         newEntity.setJuminNum("940101-1234567");
         newEntity.setBirthDate("1994-01-01");
         newEntity.setAddress([object Object]);

      repository.save(newEntity);


   
           

         this.messageVerifier.send(MessageBuilder
                .withPayload(newEntity)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build(), "vehiclemanagement");

         Message<?> receivedMessage = this.messageVerifier.receive("vehiclemanagement", 5000, TimeUnit.MILLISECONDS);
         assertNotNull("Resulted event must be published", receivedMessage);

      //then:
         String receivedPayload = (String) receivedMessage.getPayload();

         UserInfoCreated outputEvent = objectMapper.readValue(receivedPayload, UserInfoCreated.class);


         LOGGER.info("Response received: {}", outputEvent);

         assertEquals(outputEvent.getName(), "홍길동");
         assertEquals(outputEvent.getJuminNum(), "940101-1234567");
         assertEquals(outputEvent.getBirthDate(), "1994-01-01");
         assertEquals(outputEvent.getAddress(), [object Object]);


      } catch (JsonProcessingException e) {
         // TODO Auto-generated catch block
         assertTrue("exception", false);
      }

     
   }

}
