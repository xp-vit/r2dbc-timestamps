package com.patotski.r2dbctimestamps.service;


import com.patotski.r2dbctimestamps.domain.TestEntity;
import com.patotski.r2dbctimestamps.repo.TestEntityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TestEntityService {

    private final TestEntityRepo testEntityRepo;
    private final DatabaseClient databaseClient;

    public Mono<TestEntity> saveUsingRepo(TestEntity entity) {
        return testEntityRepo.save(entity);
    }

    public Mono<TestEntity> getByIdFromRepo(int id) {
        return testEntityRepo.findById(id);
    }

    public Mono<TestEntity> saveUsingDbClient(TestEntity entity) {
        return databaseClient.sql("INSERT INTO test_table (timestamp_without_tz, timestamp_with_tz) VALUES(:timestamp_without_tz, :timestamp_with_tz)")
                .bind("timestamp_without_tz", entity.getTimestamp_without_tz())
                .bind("timestamp_with_tz", entity.getTimestamp_with_tz())
                .map(row -> TestEntity.builder()
                        .id(row.get("id", Integer.class))
                        .timestamp_with_tz(row.get("timestamp_with_tz", Instant.class))
                        .timestamp_without_tz(row.get("timestamp_without_tz", Instant.class))
                        .build()).first();
    }

    public Mono<TestEntity> getByIdFromDbClient(int id) {
        return databaseClient.sql("SELECT * from test_table where id = :id")
                .bind("id", id)
                .map(row -> TestEntity.builder()
                        .id(row.get("id", Integer.class))
                        .timestamp_with_tz(row.get("timestamp_with_tz", Instant.class))
                        .timestamp_without_tz(row.get("timestamp_without_tz", Instant.class))
                        .build()).first();
    }

}
