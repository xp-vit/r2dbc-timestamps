package com.patotski.r2dbctimestamps.service;

import com.patotski.r2dbctimestamps.domain.TestEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;


@SpringBootTest
class TestEntityServiceTest {

    @Autowired
    TestEntityService testEntityService;

    @Test
    void shouldStoreCorrectTimestampsAndRetriveWithRepo() {
        Instant now = Instant.now();
        TestEntity entity = TestEntity.builder()
                .timestamp_with_tz(now)
                .timestamp_without_tz(now)
                .build();

        TestEntity saved = testEntityService.saveUsingRepo(entity).block();

        Assertions.assertThat(testEntityService.getByIdFromRepo(saved.getId()).block()).isNotNull()
                .extracting(TestEntity::getId,
                        TestEntity::getTimestamp_without_tz,
                        TestEntity::getTimestamp_with_tz)
                .containsExactly(1, now, now);
    }

    @Test
    void shouldStoreCorrectTimestampsAndRetriveWithDbClient() {
        Instant now = Instant.now();
        TestEntity entity = TestEntity.builder()
                .timestamp_with_tz(now)
                .timestamp_without_tz(now)
                .build();

        testEntityService.saveUsingDbClient(entity).block();

        Assertions.assertThat(testEntityService.getByIdFromDbClient(1).block()).isNotNull()
                .extracting(TestEntity::getId,
                        TestEntity::getTimestamp_without_tz,
                        TestEntity::getTimestamp_with_tz)
                .containsExactly(1, now, now);
    }
}