package controller;

import com.hotel.royalpalace.model.*;
import com.hotel.royalpalace.model.info.RequestInfo;
import com.hotel.royalpalace.service.GuestService;
import com.hotel.royalpalace.service.RequestService;
import com.hotel.royalpalace.service.RoomsService;
import com.hotel.royalpalace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    UserService userService;

    @Autowired
    RoomsService roomsService;

    @Autowired
    RequestService requestService;

    @Autowired
    GuestService guestService;

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
    public String getNewReservationContent() {
        return "common/newReservation";
    }

//---------------------------------------------------------------------------

    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

    @RequestMapping(value = "/getAllRooms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllRooms(HttpServletRequest request) {
        return new ResponseEntity<>(roomsService.getAllRooms(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllRoomTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllRoomTypes(HttpServletRequest request) {
        return new ResponseEntity<>(roomsService.getAllRoomTypes(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getReservationsBetweenDates", method = RequestMethod.GET)
    public ResponseEntity getReservationsBetweenDates(@RequestParam(value = "arrivalDate") String arrivalDate,
                                                      @RequestParam(value = "departureDate") String departureDate) throws ParseException {

        return new ResponseEntity<>(requestService.getAllReservationsBetweenDates(df.parse(arrivalDate), df.parse(departureDate)), HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllChosenRooms", method = RequestMethod.GET)
    public ResponseEntity getAllChosenRooms() {

        return new ResponseEntity<>(requestService.getAllRChosenRooms(), HttpStatus.OK);
    }

    @RequestMapping(value = "/saveGuest", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String saveGuest(@RequestBody Guest guest) {

        requestService.saveGuest(guest);
        return redirectToPage();
    }

    @RequestMapping(value = "/saveRequest", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String saveRequest(@RequestBody RequestInfo requestInfo) throws ParseException {

        Set<Room> requestedRooms = requestInfo.getRooms();
        Request request = new Request(requestInfo);
        request.setArrivalDate(df.parse(requestInfo.getArrivalDate()));
        request.setDepartureDate(df.parse(requestInfo.getDepartureDate()));
        request.setRequestDate(df.parse(requestInfo.getRequestDate()));
        requestService.saveRequest(request);
        return redirectToPage();
    }

    @RequestMapping(value = "/saveChosenRoom", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String saveChosenRoom(@RequestBody Room room) {

        ChosenRooms chosenRooms = new ChosenRooms(room);
        requestService.saveChosenRoom(chosenRooms);
        return redirectToPage();
    }

    @RequestMapping(value = "/removeChosenRoom", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String removeChosenRoom(@RequestBody Room room) {

        ChosenRooms chosenRooms = new ChosenRooms(room);
        requestService.removeChosenRoom(chosenRooms);
        return redirectToPage();
    }

    @RequestMapping(value = "/getAllReservations", method = RequestMethod.GET)
    public ResponseEntity getAllReservations() {

        return new ResponseEntity<>(requestService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/findByEmail", method = RequestMethod.GET)
    public ResponseEntity getByGuestEmail(@RequestParam(value = "email") String email) {

        return new ResponseEntity<>(guestService.getByGuestEmail(email), HttpStatus.OK);
    }

    private String redirectToPage() {
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
}
