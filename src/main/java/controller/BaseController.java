package controller;

import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.model.enums.Roles;
import com.hotel.royalpalace.service.GuestService;
import com.hotel.royalpalace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        return userService.redirect();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(@RequestParam(value="logout", required = false) String logout,
                               HttpServletRequest request) {
        if (logout != null) {
            request.getSession().invalidate();

        }
        return "login";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String successRedirect() {
        return userService.redirect();
    }

    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCurrentUser(HttpServletRequest request) {
        if (request.getUserPrincipal() != null) {
            String currentUser = request.getUserPrincipal().getName();
            User user = userService.getByUserEmail(currentUser);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
