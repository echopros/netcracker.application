package com.gmail.netcracker.application.controller;

import com.gmail.netcracker.application.dto.model.Item;
import com.gmail.netcracker.application.dto.model.Priority;
import com.gmail.netcracker.application.service.interfaces.ItemService;
import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.validation.ItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/account")
public class ItemController {

    private final UserService userService;
    private final ItemService itemService;
    private final ItemValidator itemValidator;

    @Autowired
    public ItemController(ItemService itemService, UserService userService, ItemValidator itemValidator) {
        this.itemService = itemService;
        this.userService = userService;
        this.itemValidator = itemValidator;
    }

    @RequestMapping(value = "/update-{itemId}", method = RequestMethod.GET)
    public ModelAndView updateItem(@PathVariable("itemId") Long itemId, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("updateItem", itemService.getItem(itemId));
        modelAndView.setViewName("item/editItem");
        return modelAndView;
    }

    @RequestMapping(value = {"/update-{itemId}"}, method = RequestMethod.POST)
    public ModelAndView updateItem(@ModelAttribute("updateItem") Item item,
                                   BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("item/editItem");
        itemValidator.validate(item, bindingResult);
        itemValidator.validateItem(modelAndView, item, bindingResult, itemService);
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }
        itemService.update(item);
        modelAndView.setViewName("redirect:/account/user-"+userService.getAuthenticatedUser().getId()+"/wishList");
        return modelAndView;
    }

    @RequestMapping(value = "/wishList/deleteItem-{itemId}", method = RequestMethod.GET)
    public String deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.delete(itemId);
        return "redirect:/account/user-"+userService.getAuthenticatedUser().getId()+"/wishList";
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.GET)
    public ModelAndView createItem(@ModelAttribute(value = "createItem") Item item, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.setViewName("item/addItem");
        return modelAndView;
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public ModelAndView addItem(@ModelAttribute("createItem") Item item, BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("item/addItem");
        itemValidator.validate(item, bindingResult);
        itemValidator.validateItem(modelAndView, item, bindingResult, itemService);
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }
        itemService.add(item);
        modelAndView.setViewName("redirect:/account/user-"+userService.getAuthenticatedUser().getId()+"/wishList");
        return modelAndView;
    }

    @RequestMapping(value = "/getItem-{itemId}", method = RequestMethod.GET)
    public ModelAndView getItem(@PathVariable("itemId") Long itemId, ModelAndView modelAndView) {
        modelAndView.addObject("auth_user", userService.getAuthenticatedUser());
        modelAndView.addObject("getItem", itemService.getItem(itemId));
        modelAndView.setViewName("item/getItem");
        return modelAndView;
    }

    @RequestMapping(value = "/copy-{itemId}", method = RequestMethod.GET)
    public String copyItem(@PathVariable("itemId") Long itemId) {
        itemService.copyItem(itemId);
        return "redirect:/account/user-"+userService.getAuthenticatedUser().getId()+"/wishList";
    }

    @ModelAttribute("priorities")
    public List<Priority> getAllPriorities() {
        return itemService.getAllPriorities();
    }

    @RequestMapping(value = "/user-{ownerId}/item-{itemId}/book", method = RequestMethod.GET)
    public String bookItem( @PathVariable("itemId") Long itemId, @PathVariable("ownerId") Long ownerId) {
        itemService.bookItem(itemId);
        return "redirect:/account/user-"+ownerId+"/wishList";
    }

    @RequestMapping(value = "/user-{id}/item-{itemId}/cancel-booking", method = RequestMethod.GET)
    public String cancelBookingItem(@PathVariable("itemId") Long itemId, @PathVariable("id") Long owner) {
        itemService.cancelBookingItem(itemId);
        return "redirect:/account/user-"+owner+"/wishList";
    }

    @RequestMapping(value = "/user-{id}/wishList", method = RequestMethod.GET)
    public String userWishList(@PathVariable("id") Long userId, Model model) {
        model.addAttribute("auth_user", userService.getAuthenticatedUser());
        model.addAttribute("ownerId", userId);
        model.addAttribute("wishList", itemService.getWishList(userId));
        return "item/personWishList";
    }
}
