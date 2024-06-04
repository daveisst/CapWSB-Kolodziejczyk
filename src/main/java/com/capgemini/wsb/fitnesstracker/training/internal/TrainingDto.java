package com.capgemini.wsb.fitnesstracker.training.internal;

import io.micrometer.common.lang.Nullable;

import java.util.Date;


public record TrainingDto(
        @Nullable Long id,
        Long userId,
        Date startTime,
        Date endTime,
        String activityType,
        double distance,
        double averageSpeed
) {
}