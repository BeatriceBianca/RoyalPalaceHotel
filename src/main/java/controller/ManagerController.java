package controller;

import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.model.info.UserInfo;
import com.hotel.royalpalace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "")
    public String getManagerPage() { return "manager"; }

    @RequestMapping(value = "/home")
    public String getHomeContent() { return "home"; }

    @RequestMapping(value = "/newAccount")
    public String getNewAccountContent() { return "newAccount"; }

    @RequestMapping(value = "/rooms")
    public String getRoomsContent() { return "rooms"; }

    @RequestMapping(value = "/reports")
    public String getReportsContent() { return "reports"; }

    @RequestMapping(value = "/guests")
    public String getGuestsContent() { return "guests"; }

    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCurrentUser(HttpServletRequest request) {
        String currentUser = request.getUserPrincipal().getName();
        User user = userService.getByUserEmail(currentUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String addUser(@RequestParam(value = "lastName") String lastName,
                          @RequestParam(value = "firstName") String firstName,
                          @RequestParam(value = "email") String email,
                          @RequestParam(value = "role") String role,
                          @RequestParam(value = "birthDate") Date birthDate,
                          @RequestParam(value = "hireDate") Date hireDate,
                          @RequestParam(value = "phone") String phone) throws NoSuchAlgorithmException {
        UserInfo userInfo = new UserInfo(lastName, firstName, role, birthDate, hireDate, phone, email, null);
        userService.createUser(userInfo);

        return "manager";
    }

}
