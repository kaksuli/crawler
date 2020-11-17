package com.imdb.warehouse.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author sulaiman kadkhodaei
 */

@Data
@Document(collection = "database_sequences")
public class DatabaseSequence {

    @Id
    private String id;
    private Long seq;
}
