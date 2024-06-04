package com.capgemini.wsb.fitnesstracker.training.internal;


import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;
    private final UserProvider userProvider;
    private final TrainingMapper trainingMapper;

    @PostMapping
    public TrainingDto createTraining(@RequestBody TrainingDto trainingDto) {

        User user = userProvider.getUser(trainingDto.userId()).orElseThrow(() -> new RuntimeException("User not found"));
        Training training = trainingMapper.toEntity(trainingDto, user);
        Training savedTraining = trainingService.createTraining(training);
        return trainingMapper.toDto(savedTraining);
    }

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings().stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public List<TrainingDto> getTrainingsByUser(@PathVariable Long userId) {
        return trainingService.findTrainingsByUser(userId).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/completed")
    public List<TrainingDto> getCompletedTrainings(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        Date searchDate = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        return trainingService.findCompletedTrainings(searchDate).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/activity")
    public List<TrainingDto> getTrainingsByActivity(@RequestParam("activityType") String activityType) {
        return trainingService.findTrainingsByActivityType(activityType).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public TrainingDto updateTraining(@PathVariable Long id, @RequestBody TrainingDto trainingDto) {
        User user = userProvider.getUser(trainingDto.userId()).orElseThrow(() -> new RuntimeException("User not found"));
        Training training = trainingMapper.toEntity(trainingDto, user);
        training.setId(id);
        Training updatedTraining = trainingService.updateTraining(training);
        return trainingMapper.toDto(updatedTraining);
    }

    @DeleteMapping("/{id}")
    public void deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
    }
}