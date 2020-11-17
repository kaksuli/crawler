package com.imdb.warehouse.dao;

import com.imdb.warehouse.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author sulaiman kadkhodaei
 */

@Service
@RequiredArgsConstructor
public class MovieDAO {
    private final MovieRepository repository;
}
