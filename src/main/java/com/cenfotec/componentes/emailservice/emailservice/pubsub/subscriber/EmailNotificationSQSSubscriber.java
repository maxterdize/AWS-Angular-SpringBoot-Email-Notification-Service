package com.cenfotec.componentes.emailservice.emailservice.pubsub.subscriber;

import com.cenfotec.componentes.emailservice.emailservice.pubsub.event.AddNewContactEvent;
import com.cenfotec.componentes.emailservice.emailservice.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmailNotificationSQSSubscriber {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EmailNotificationService emailNotificationService;

    @SqsListener(value = "email-notification", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void subscribe(AddNewContactEvent addNewContactEvent) {
        logger.info(">>> receiving event from SQS: --> {}", addNewContactEvent.toString());
        emailNotificationService.sendEmail(addNewContactEvent);
    }

}
