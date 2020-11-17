package com.imdb.warehouse.event;

import com.imdb.warehouse.model.Movie;
import com.imdb.warehouse.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

/**
 * @author sulaiman kadkhodaei
 */

@Component
@RequiredArgsConstructor
public class Listener extends AbstractMongoEventListener<Movie> {
    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Movie> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Movie.SEQUENCE_NAME));
        }
    }
}
