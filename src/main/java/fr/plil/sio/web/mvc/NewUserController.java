package fr.plil.sio.web.mvc;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewUserController {

    @Resource
    private UserDao userDao;
    @Resource
    private UserSession userSession;
    @Resource
    private UserValidator userValidator;

    @RequestMapping(value = {"/newUser"}, method = RequestMethod.GET)
    public ModelAndView getNewUserForm() {
        return new ModelAndView("newUser", "user", new User());
    }

    @RequestMapping(value = {"/newUser"}, method = RequestMethod.POST)
    public String postNewUser(User user, BindingResult result) {

        if (!userSession.getUsername().equals("admin")) {
            result.rejectValue("username", "new.user.only.admin");
        }

        userValidator.validate(user, result);

        boolean present = (userDao.getFromUsername(user.getUsername()) != null);

        if (present) {
            result.rejectValue("username", "new.user.form.present");
        }

        if(result.hasErrors()) {
            return "newUser";
        }

        userDao.save(user);

        return "redirect:/";
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }

    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }
}
