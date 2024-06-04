package com.capgemini.wsb.fitnesstracker.training.api;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    Training createTraining(Training training);

    List<Training> findAllTrainings();

    List<Training> findTrainingsByUser(Long userId);

    List<Training> findCompletedTrainings(Date date);

    List<Training> findTrainingsByActivityType(String activityType);

    Training updateTraining(Training training);

    void deleteTraining(Long id);
}
