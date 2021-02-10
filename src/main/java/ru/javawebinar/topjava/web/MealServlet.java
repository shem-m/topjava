package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DAO;
import ru.javawebinar.topjava.dao.MealDAO;
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
    private final DAO mealDAO = new MealDAO();
    private final List<Meal> meals = mealDAO.getAll();
    private static final int CALORIES_PER_DAY = 2000;
    private static final String ADD_OR_UPDATE = "/mealForm.jsp";
    private static final String MEALS = "/meals.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("meals", filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            request.getRequestDispatcher(MEALS).forward(request, response);
        } else if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealDAO.delete(id);
            response.sendRedirect("meals");
        } else if (action.equalsIgnoreCase("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealDAO.getById(id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher(ADD_OR_UPDATE).forward(request, response);
        } else if (action.equalsIgnoreCase("insert")) {
            Meal meal = new Meal(LocalDateTime.now(), "", 0);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher(ADD_OR_UPDATE).forward(request, response);
        } else {
            response.sendRedirect("meals");
        }
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
            mealDAO.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            mealDAO.update(meal);
        }

        response.sendRedirect("meals");
//        request.setAttribute("meals", filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
//        request.getRequestDispatcher(MEALS).forward(request, response);
    }
}
