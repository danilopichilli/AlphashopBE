package com.xantrix.webapp.service;

import com.xantrix.webapp.model.document.Utente;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class UtentiModelListener extends AbstractMongoEventListener<Utente> {

    private final SequenceGeneratorService sequenceGenerator;

    public UtentiModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Utente> event) {
        if (event.getSource().getId() == null || event.getSource().getId().isEmpty()) {
            event.getSource().setId(String.valueOf(sequenceGenerator.generateSequence(Utente.SEQUENCE_NAME)));
        }
    }
}
