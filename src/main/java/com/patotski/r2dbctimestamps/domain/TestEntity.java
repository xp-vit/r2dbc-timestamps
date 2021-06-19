package com.patotski.r2dbctimestamps.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("test_table")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestEntity {

    @Id
    Integer id;

    Instant timestamp_without_tz;

    Instant timestamp_with_tz;
}
