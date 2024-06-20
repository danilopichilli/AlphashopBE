package com.xantrix.webapp.service;

import com.xantrix.webapp.model.document.Utenti;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class UtentiModelListener extends AbstractMongoEventListener<Utenti> {

    private final SequenceGeneratorService sequenceGenerator;

    public UtentiModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Utenti> event) {
        if (event.getSource().getId() == null || event.getSource().getId().isEmpty()) {
            event.getSource().setId(String.valueOf(sequenceGenerator.generateSequence(Utenti.SEQUENCE_NAME)));
        }
    }
}
