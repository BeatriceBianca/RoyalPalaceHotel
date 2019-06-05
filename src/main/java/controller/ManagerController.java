package controller;

import com.hotel.royalpalace.auxiliary.Encryption;
import com.hotel.royalpalace.model.*;
import com.hotel.royalpalace.model.info.UserInfo;
import com.hotel.royalpalace.service.OfferService;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    UserService userService;

    @Autowired
    RoomsService roomsService;

    @Autowired
    OfferService offerService;

    @RequestMapping(value = "")
    public String manager() { return "manager/manager"; }

    @RequestMapping(value = "/home")
    public String home() { return "manager/home"; }

    @RequestMapping(value = "/newAccount")
    public String newAccount() { return "manager/newAccount"; }

    @RequestMapping(value = "/reports")
    public String reports() { return "manager/reports"; }

    @RequestMapping(value = "/newOffer")
    public String newOffer() { return "manager/newOffer"; }

    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

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
//        TODO
        UserInfo userInfo = new UserInfo(lastName, firstName, null, birthDate, hireDate, phone, email, null);
        userService.createUser(userInfo);

        return "redirect:/manager";
    }

    @RequestMapping(value = "/editRoomType", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String editRoomType(@RequestBody Room room,
                               HttpServletRequest request) {

        roomsService.editRoomType(room);
        return "redirect:/manager";
    }

    @RequestMapping(value = "/editPrice", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String editPrice(@RequestBody RoomType roomType,
                            HttpServletRequest request) {

        roomsService.editPrice(roomType);
        return "redirect:/manager";
    }

    @RequestMapping(value = "/addOffer", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String addOffer(@RequestParam(value = "name") String name,
                          @RequestParam(value = "description") String description,
                          @RequestParam(value = "startDate") String startDate,
                          @RequestParam(value = "endDate") String endDate,
                          @RequestParam(value = "roomType") String roomType,
                          @RequestParam(value = "quantity") int quantity,
                          @RequestParam(value = "minDays") String minDays,
                          @RequestParam(value = "discount") int discount,
                          @RequestParam(value = "lateCheckout", required = false) boolean lateCheckout,
                          @RequestParam(value = "lunch", required = false) boolean lunch,
                          @RequestParam(value = "dinner", required = false) boolean dinner) throws ParseException {

        Offer offer = new Offer(name, description, df.parse(startDate), df.parse(endDate),
                roomType.equals("null") ? null : roomsService.findById(Long.parseLong(roomType)),
                quantity, minDays.equals("") ? 1 : Integer.parseInt(minDays), discount,
                lateCheckout && lateCheckout, lunch && lunch, dinner && dinner);
        offerService.newOffer(offer);
        return "redirect:/manager";
    }

}
