package com.cenfotec.componentes.emailservice.emailservice.service;

import com.cenfotec.componentes.emailservice.emailservice.pubsub.event.AddNewContactEvent;

public interface EmailNotificationService {

    void sendEmail(AddNewContactEvent addNewContactEvent);
}
