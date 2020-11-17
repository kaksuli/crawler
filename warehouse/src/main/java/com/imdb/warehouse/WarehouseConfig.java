package com.imdb.warehouse;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author sulaiman kadkhodaei
 */

@Configuration
@ComponentScan(basePackages = {"com.imdb.warehouse"})
@EntityScan(basePackages = {"com.imdb.warehouse"})
@EnableMongoRepositories("com.imdb.warehouse.repository")
public class WarehouseConfig {
}
