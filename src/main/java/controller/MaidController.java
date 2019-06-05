package controller;

import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.model.enums.Roles;
import com.hotel.royalpalace.model.info.UserInfo;
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
import java.util.Date;

@Controller
@RequestMapping("/maid")
public class MaidController {

    @Autowired
    UserService userService;

//    Pages Mapping

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String maid(HttpServletRequest request) {
        return "maid/maid";
    }

    @RequestMapping(value = "/homeMaid", method = RequestMethod.GET)
    public String home(HttpServletRequest request) {
        return "maid/home";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePassword() { return "maid/changePassword"; }

    @RequestMapping(value = "/myProfile")
    public String myProfile() { return "maid/myProfile"; }

//    -----------------------------------------------------------

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String changePassword(@RequestParam(value = "newPassword") String newPassword,
                                 HttpServletRequest request) throws NoSuchAlgorithmException {

        User currentUser = userService.getByUserEmail(request.getUserPrincipal().getName());
        userService.changePassword(currentUser, newPassword);

        return redirectToPage();
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String editUser(@RequestParam(value = "lastName") String lastName,
                           @RequestParam(value = "firstName") String firstName,
                           @RequestParam(value = "birthDate") Date birthDate,
                           @RequestParam(value = "phone") String phone,
                           HttpServletRequest request) throws NoSuchAlgorithmException {
        User currentUser = userService.getByUserEmail(request.getUserPrincipal().getName());
        UserInfo userInfo = new UserInfo(lastName, firstName, currentUser.getUserRole(), birthDate, currentUser.getHireDate(), phone, currentUser.getUserEmail(), currentUser.getUserPassword());
        userService.editUser(userInfo);

        return redirectToPage();
    }

    private String redirectToPage() {
        return userService.redirect();
    }
}
