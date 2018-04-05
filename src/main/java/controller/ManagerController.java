package controller;

import com.hotel.royalpalace.auxiliary.Encryption;
import com.hotel.royalpalace.model.Room;
import com.hotel.royalpalace.model.RoomType;
import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.model.info.UserInfo;
import com.hotel.royalpalace.service.RoomsService;
import com.hotel.royalpalace.service.UserService;
import com.hotel.royalpalace.service.impl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    UserService userService;

    @Autowired
    RoomsService roomsService;

    @RequestMapping(value = "")
    public String manager() { return "manager/manager"; }

    @RequestMapping(value = "/home")
    public String home() { return "manager/home"; }

    @RequestMapping(value = "/newAccount")
    public String newAccount() { return "manager/newAccount"; }

    @RequestMapping(value = "/reports")
    public String reports() { return "manager/reports"; }

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

    @RequestMapping(value = "/manager/editRoomType", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String editRoomType(@RequestBody Room room,
                               HttpServletRequest request) {

        roomsService.editRoomType(room);
        return "redirect:/manager";
    }

    @RequestMapping(value = "/manager/editPrice", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String editPrice(@RequestBody RoomType roomType,
                            HttpServletRequest request) {

        roomsService.editPrice(roomType);
        return "redirect:/manager";
    }
}
