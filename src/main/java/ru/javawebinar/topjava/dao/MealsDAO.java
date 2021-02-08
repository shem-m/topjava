package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealsDAO {

    List<Meal> getAll();

    Meal getById();

    void save(Meal meal);

    void delete(Long id);
}
