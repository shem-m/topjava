package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int MEAL2_ID = START_SEQ + 3;
    public static final LocalDate START_DATE = LocalDate.of(2020, 1, 30);
    public static final LocalDate END_DATE = LocalDate.of(2020, 1, 30);

    public static final int NOT_FOUND = 10;

    public static final Meal meal1 = new Meal(MEAL1_ID, LocalDateTime.of(2020, 1, 30, 10, 0, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(START_SEQ + 3, LocalDateTime.of(2020, 1, 30, 13, 0, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(START_SEQ + 4, LocalDateTime.of(2020, 1, 30, 20, 0, 0), "Ужин", 500);
    public static final Meal meal4 = new Meal(START_SEQ + 5, LocalDateTime.of(2020, 1, 31, 0, 0, 0), "Еда на граничное значение", 100);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "newMeal", 410);
    }

    public static Meal getUpdated() {
        Meal updatedMeal = new Meal(meal2);
        updatedMeal.setDateTime(LocalDateTime.of(2020, 1, 30, 3, 0, 0));
        updatedMeal.setDescription("updatedMeal");
        updatedMeal.setCalories(700);
        return updatedMeal;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }

}
