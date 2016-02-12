package com.elennaro.controllers;

import com.elennaro.entities.User;
import com.elennaro.repositories.UsersRepository;
import com.elennaro.validators.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_FORM_URLENCODED_VALUE;

@Controller
@RequestMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private UserValidator userValidator;

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        if (binder.getTarget() instanceof User)
            binder.addValidators(userValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<User> getUsers() {
        List<User> users = new ArrayList<>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody User addUser(@Valid @RequestBody User user) {
        return usersRepository.registerUser(user);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String addUser(
            @Valid @ModelAttribute("user") User user,
            final BindingResult bindingResult,
            RedirectAttributes attr,
            HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            logger.warn("Validation exception.", bindingResult.getAllErrors());
            attr.addFlashAttribute("bindingResult", bindingResult);
            attr.addFlashAttribute("user", user);
            return "redirect:/users/add";
        }

        usersRepository.registerUser(user);

        return "redirect:/users";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        usersRepository.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody User getUser(@PathVariable("id") Long id) {
        return usersRepository.findOne(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUserForm(
            Model model,
            @ModelAttribute User user,
            BindingResult errors,
            HttpSession httpSession
    ) {
        if (model.containsAttribute("bindingResult"))
            ((BindingResult) model.asMap().get("bindingResult")).getAllErrors()
                    .forEach(objectError -> errors.addError(objectError));
        logger.info("Attributes", user);
        return "addUser";
    }
}
