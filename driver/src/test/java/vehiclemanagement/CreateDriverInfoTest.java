package vehiclemanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;
import vehiclemanagement.config.kafka.KafkaProcessor;
import vehiclemanagement.domain.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMessageVerifier
public class CreateDriverInfoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        CreateDriverInfoTest.class
    );

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
    public DriverInfoRepository repository;

    @Test
    @SuppressWarnings("unchecked")
    public void test0() {
        //given:
        DriverInfo existingEntity = new DriverInfo();

        existingEntity.setCompanyName("ABC Company");
        existingEntity.setAffiliation("XYZ Affiliation");
        existingEntity.setContact("012-3456-7890");
        existingEntity.setPermission("Granted");
        existingEntity.setIsActive(true);
        existingEntity.setUserInfoId("usr123");
        existingEntity.setVehicleInfoId("v123");

        repository.save(existingEntity);

        //when:

        try {
            DriverInfo newEntity = new DriverInfo();

            newEntity.setCompanyName("ABC Company");
            newEntity.setAffiliation("XYZ Affiliation");
            newEntity.setContact("012-3456-7890");
            newEntity.setPermission("Granted");
            newEntity.setIsActive(true);
            newEntity.setUserInfoId("usr123");
            newEntity.setVehicleInfoId("v123");

            repository.save(newEntity);

            this.messageVerifier.send(
                    MessageBuilder
                        .withPayload(newEntity)
                        .setHeader(
                            MessageHeaders.CONTENT_TYPE,
                            MimeTypeUtils.APPLICATION_JSON
                        )
                        .build(),
                    "vehiclemanagement"
                );

            Message<?> receivedMessage =
                this.messageVerifier.receive(
                        "vehiclemanagement",
                        5000,
                        TimeUnit.MILLISECONDS
                    );
            assertNotNull("Resulted event must be published", receivedMessage);

            //then:
            String receivedPayload = (String) receivedMessage.getPayload();

            DriverInfoCreated outputEvent = objectMapper.readValue(
                receivedPayload,
                DriverInfoCreated.class
            );

            LOGGER.info("Response received: {}", outputEvent);

            assertEquals(outputEvent.getCompanyName(), "ABC Company");
            assertEquals(outputEvent.getAffiliation(), "XYZ Affiliation");
            assertEquals(outputEvent.getContact(), "012-3456-7890");
            assertEquals(outputEvent.getPermission(), "Granted");
            assertEquals(outputEvent.getIsActive(), true);
            assertEquals(outputEvent.getUserInfoId(), "usr123");
            assertEquals(outputEvent.getVehicleInfoId(), "v123");
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            assertTrue("exception", false);
        }
    }
}
