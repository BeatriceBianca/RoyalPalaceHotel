package controller;

import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

@Controller
public class BaseController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/")
    public String redirectToLogin() { return "redirect:login"; }

    @RequestMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String successRedirect() throws NoSuchAlgorithmException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities();
        String email;
        if (authentication.getPrincipal() instanceof User) {
            email = ((User) authentication.getPrincipal()).getUserEmail();
        } else {
            email = authentication.getName();
        }
        User user = userService.getByUserEmail(email);

        if (user.getUserRole().equals("MANAGER")) {
            return "redirect:/manager";
        } else if (user.getUserRole().equals("RECEPTIONIST")) {
            return "redirect:/receptionist";
        } else if (user.getUserRole().equals("MAID")) {
            return "redirect:/maid";
        }

        return "redirect:/error";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }

    @RequestMapping(value = "/maid", method = RequestMethod.GET)
    public String maid(HttpServletRequest request) {
        return "maid";
    }

    @RequestMapping(value = "/receptionist", method = RequestMethod.GET)
    public String receptionist(HttpServletRequest request) {
        return "receptionist";
    }
}
