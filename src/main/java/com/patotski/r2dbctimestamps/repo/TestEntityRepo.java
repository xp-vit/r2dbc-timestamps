package com.patotski.r2dbctimestamps.repo;

import com.patotski.r2dbctimestamps.domain.TestEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TestEntityRepo extends R2dbcRepository<TestEntity, Integer> {
}
