package com.imdb.warehouse.repository;

import com.imdb.warehouse.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author sulaiman kadkhodaei
 */

@Repository
public interface MovieRepository extends MongoRepository<Movie, Long> {
}
