package controller;

import com.hotel.royalpalace.auxiliary.Encryption;
import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.model.info.UserInfo;
import com.hotel.royalpalace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/newReservation")
    public String getNewReservationContent() { return "newReservation"; }

    @RequestMapping(value = "/viewReservations")
    public String getViewReservationsContent() { return "viewReservations"; }

    @RequestMapping(value = "/myProfile")
    public String getMyProfileContent() { return "myProfile"; }

    @RequestMapping(value = "/changePassword")
    public String getChangePasswordContent() { return "changePassword"; }

    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCurrentUser(HttpServletRequest request) {
        String currentUser = request.getUserPrincipal().getName();
        User user = userService.getByUserEmail(currentUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/computePassword?password={password}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity computePassword(@PathVariable("password") String password,
                                          HttpServletRequest request) throws NoSuchAlgorithmException {
        return new ResponseEntity<>(Encryption.computeMD5(password), HttpStatus.OK);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String addUser(@RequestParam(value = "lastName") String lastName,
                          @RequestParam(value = "firstName") String firstName,
                          @RequestParam(value = "userEmail") String email,
                          @RequestParam(value = "userRole") String role,
                          @RequestParam(value = "birthDate") Date birthDate,
                          @RequestParam(value = "hireDate") Date hireDate,
                          @RequestParam(value = "phone") String phone) throws NoSuchAlgorithmException {
        UserInfo userInfo = new UserInfo(lastName, firstName, role, birthDate, hireDate, phone, email, null);
        userService.createUser(userInfo);

        return "redirect:/manager";
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

        return "redirect:/manager";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String changePassword(@RequestParam(value = "newPassword") String newPassword,
                           HttpServletRequest request) throws NoSuchAlgorithmException {

        User currentUser = userService.getByUserEmail(request.getUserPrincipal().getName());
        userService.changePassword(currentUser, newPassword);

        return "redirect:/manager";
    }

}
