package com.cenfotec.componentes.emailservice.emailservice.pubsub.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddNewContactEvent {

    private String contactName;
    private String contactEmail;

}
