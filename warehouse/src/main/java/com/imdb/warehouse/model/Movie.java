package com.imdb.warehouse.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author sulaiman kadkhodaei
 */

@Data
@Document(collation = "movies")
public class Movie {

    @Transient
    public static final String SEQUENCE_NAME = "movies_sequence";
    @Id
    private Long id;
}
