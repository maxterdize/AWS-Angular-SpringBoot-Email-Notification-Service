package com.cenfotec.componentes.emailservice.emailservice.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.cenfotec.componentes.emailservice.emailservice.pubsub.event.AddNewContactEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class EmailNotificationServiceImpl implements EmailNotificationService{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String FROM = "maxter.rojas@outlook.com";
    private static final String FROM_NAME = "Max Rojas";
    private static final String TEMPLATE_NAME = "newContactTemplate";

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    @Override
    public void sendEmail(AddNewContactEvent addNewContactEvent) {
        logger.info(">>> Sending email to new contact...");
        try {

            amazonSimpleEmailService.sendTemplatedEmail(prepareSendTemplateEmailRequest(addNewContactEvent));
            logger.info("<<< Email successfully sent");

        } catch (MessageRejectedException messageRejectedException) {
            logger.error("The message could not be delivered. Email is not verified");
        }
    }

    private SendTemplatedEmailRequest prepareSendTemplateEmailRequest(AddNewContactEvent addNewContactEvent) {

        SendTemplatedEmailRequest sendTemplatedEmailRequest = new SendTemplatedEmailRequest();
        sendTemplatedEmailRequest.withDestination(getDestination(addNewContactEvent.getContactEmail()));
        sendTemplatedEmailRequest.withTemplate(TEMPLATE_NAME);
        sendTemplatedEmailRequest.withTemplateData(setTemplateData(addNewContactEvent.getContactName()));
        sendTemplatedEmailRequest.withSource(FROM);

        return sendTemplatedEmailRequest;
    }

    private Destination getDestination(String destinationEmail) {
        return new Destination(Arrays.asList(destinationEmail));
    }

    private String setTemplateData(String contactName) {
        return "{ \"name\":\"" + contactName +"\", \"favoriteanimal\": \"Tiger\"}";
    }
}
