package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
class TrainingMapper {

    TrainingDto toDto(Training training) {
        return new TrainingDto(
                training.getId(),
                training.getUser().getId(),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType().name().toLowerCase(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

    Training toEntity(TrainingDto trainingDto, User user) {
        return new Training(
                user,
                trainingDto.startTime(),
                trainingDto.endTime(),
                ActivityType.valueOf(trainingDto.activityType().toUpperCase()),
                trainingDto.distance(),
                trainingDto.averageSpeed()
        );
    }
}