package controller;

import com.hotel.royalpalace.model.Guest;
import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.service.GuestService;
import com.hotel.royalpalace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

@Controller
public class BaseController {

    @Autowired
    UserService userService;

    @Autowired
    GuestService guestService;

    @RequestMapping(value = "/")
    public String redirectToIndex() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities();
        String email;
        if (authentication.getPrincipal() instanceof User) {
            email = ((User) authentication.getPrincipal()).getUserEmail();
        } else {
            email = authentication.getName();
        }
        if (!email.equals("anonymousUser")) {
            User user = userService.getByUserEmail(email);

            if (user.getUserRole().equals("MANAGER")) {
                return "redirect:/manager";
            } else if (user.getUserRole().equals("RECEPTIONIST")) {
                return "redirect:/receptionist";
            } else if (user.getUserRole().equals("MAID")) {
                return "redirect:/maid";
            }
        }

        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(@RequestParam(value="logout", required = false) String logout,
                               HttpServletRequest request) {
        if (logout != null) {
            request.getSession().invalidate();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authentication.getAuthorities();
            String email;
            if (authentication.getPrincipal() instanceof User) {
                email = ((User) authentication.getPrincipal()).getUserEmail();
            } else {
                email = authentication.getName();
            }
            if (!email.equals("anonymousUser")) {
                User user = userService.getByUserEmail(email);

                if (user.getUserRole().equals("MANAGER")) {
                    return "redirect:/manager";
                } else if (user.getUserRole().equals("RECEPTIONIST")) {
                    return "redirect:/receptionist";
                } else if (user.getUserRole().equals("MAID")) {
                    return "redirect:/maid";
                }
            }

            return "index";
        }
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

//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String logout() {
//
//    }

}
