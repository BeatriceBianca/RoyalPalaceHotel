package controller;

import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/")
    public String redirectToJobList() {
        return "redirect:login";
    }

    @RequestMapping(value = "/login")
    public String getLoginForm(HttpServletRequest request, Model model) {
        return "login";
    }

//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/login?logout";
//    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String displayUsers() throws NoSuchAlgorithmException {
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
        } else if (user.getUserRole().equals("MAID")) {
            return "redirect:/maid";
        } else if (user.getUserRole().equals("RECEPTIONIST")) {
            return "redirect:/receptionist";
        }

        return "redirect:/error";
    }
}
