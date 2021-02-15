package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(Meal meal, int userId) {
        if (meal.getUserId() != userId) {
            return checkNotFoundWithId(null, meal.getId());
        }
        return checkNotFoundWithId(repository.save(meal), meal.getId());
    }

    public void delete(int id, int userId) {
        if (repository.get(id).getUserId() == userId) {
            repository.delete(id);
        } else {
            checkNotFoundWithId(null, id);
        }
    }

    public Meal get(int id, int userId) {
        if (repository.get(id).getUserId() != userId) {
            return checkNotFoundWithId(null, id);
        }
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getAllByDates(startDate, endDate).stream().filter(meal -> meal.getUserId() == userId).collect(Collectors.toList());
    }
}