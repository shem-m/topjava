package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface DAO {

    List<Meal> getAll();

    Meal getById(Integer id);

    void add(Meal meal);

    void update(Meal meal);

    void delete(Integer id);
}
