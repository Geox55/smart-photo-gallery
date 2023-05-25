package it.sevenbits.practice.googlephoto.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Quizzes database config
 */
@Configuration
public class QuizzesDatabaseConfig {
    /**
     * Quizzes database config
     * @return - datasource
     */
    @Bean
    @Qualifier("quizzesDataSource")
    @FlywayDataSource
    @ConfigurationProperties(prefix = "spring.datasource.quizzes")
    public DataSource quizzesDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * quizzes jdbc
     * @param quizzesDataSource - datasource
     * @return - jdbcTemplate
     */
    @Bean
    @Qualifier("quizzesJdbcOperations")
    public JdbcOperations quizzesJdbcOperations(
            @Qualifier("quizzesDataSource") final
            DataSource quizzesDataSource
    ) {
        return new JdbcTemplate(quizzesDataSource);
    }
}
