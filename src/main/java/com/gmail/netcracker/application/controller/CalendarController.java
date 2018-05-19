package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.service.interfaces.CalendarService;
import com.gmail.netcracker.application.service.interfaces.EventService;
import com.gmail.netcracker.application.service.interfaces.FilterService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.EventSerializer;
import com.gmail.netcracker.application.utilites.Filter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/account")
public class CalendarController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private FilterService filterService;

    @Autowired
    private CalendarService calendarService;

    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(Event.class, new EventSerializer())
            .create();

    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView calendarHome(ModelAndView modelAndView) {
        String eventList = gson.toJson(calendarService.getEventsFromRange(userService.getAuthenticatedUser(),
                                                                "2018-05-01 00:00:00.000000", "2018-06-01 00:00:00.000000"));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("eventList", eventList);
        modelAndView.addObject("priorities", eventService.getAllPriorities());
        modelAndView.addObject("filter", new Filter());
        modelAndView.setViewName("calendar/calendar");
        return modelAndView;
    }

    @RequestMapping(value = "/calendar", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView calendarWithFilter(@ModelAttribute("properties") Filter filter,
                                           BindingResult result,
                                           ModelAndView modelAndView) {

        String eventList = gson.toJson(filterService.filterOfPriority(filter.getPriorities()));
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("eventList", eventList);
        modelAndView.addObject("priorities", eventService.getAllPriorities());
        modelAndView.addObject("filter", filter);
        modelAndView.setViewName("calendar/calendar");
        return modelAndView;
    }
}
