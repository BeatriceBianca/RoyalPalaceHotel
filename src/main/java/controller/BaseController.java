package controller;

import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.model.UserInfo;
import com.hotel.royalpalace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Beatrice Bianca on 16-Jul-17.
 */
@Controller
public class BaseController {

    @Autowired
    private UserService userService;

    @ModelAttribute("currentUserEmail")
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getName();
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        String userEmail = getCurrentUserEmail();
        return userService.getByUserEmail(userEmail);
    }
}
