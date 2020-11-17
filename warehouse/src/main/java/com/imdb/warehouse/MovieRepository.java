package com.imdb.warehouse;

import com.imdb.warehouse.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author sulaiman kadkhodaei
 */

public interface MovieRepository extends MongoRepository<Movie, Long> {
}
