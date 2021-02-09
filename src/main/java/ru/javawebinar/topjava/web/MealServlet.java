package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealsDAO;
import ru.javawebinar.topjava.dao.MealsDAOImpl;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private final MealsDAO mealsDAO = new MealsDAOImpl();
    private final List<Meal> meals = mealsDAO.getAll();
    private static final int CALORIES_PER_DAY = 2000;
    private static final String ADD_OR_UPDATE = "/mealForm.jsp";
    private static final String MEALS = "/meals.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealsDAO.delete(id);
            forward = MEALS;
            request.setAttribute("meals", filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        } else if (action.equalsIgnoreCase("edit")) {
            forward = ADD_OR_UPDATE;
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealsDAO.getById(id);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("meals")) {
            forward = MEALS;
            request.setAttribute("meals", filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        } else if (action.equalsIgnoreCase("insert")) {
            forward = ADD_OR_UPDATE;
            Meal meal = new Meal(LocalDateTime.now(), "", 0);
            request.setAttribute("meal", meal);
        }


        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);

//        request.setAttribute("meals", filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
//        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("add or update meal");
        request.setCharacterEncoding("UTF-8");

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int cal = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(dateTime, description, cal);

        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            mealsDAO.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            mealsDAO.update(meal);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(MEALS);
        request.setAttribute("meals", filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        requestDispatcher.forward(request, response);
    }
}
