package controller;

import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.service.RoomsService;
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

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    UserService userService;

    @Autowired
    RoomsService roomsService;

//    Pages Mapping

    @RequestMapping(value = "/home")
    public String home() { return "common/home"; }

    @RequestMapping(value = "/contact")
    public String contact() { return "common/contact"; }

    @RequestMapping(value = "/offers")
    public String offers() { return "common/offers"; }

    @RequestMapping(value = "/rooms")
    public String rooms() { return "common/rooms"; }

    @RequestMapping(value = "/newReservation")
    public String getNewReservationContent() { return "common/newReservation"; }

//---------------------------------------------------------------------------

//    @RequestMapping(value = "/manager/getAllRooms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity getAllRooms(HttpServletRequest request) {
//        return new ResponseEntity<>(roomsService.getAllRooms(), HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/manager/getAllRoomTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity getAllRoomTypes(HttpServletRequest request) {
//        return new ResponseEntity<>(roomsService.getAllRoomTypes(), HttpStatus.OK);
//    }

}
