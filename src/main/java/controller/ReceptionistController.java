package controller;

import com.hotel.royalpalace.model.ChosenRooms;
import com.hotel.royalpalace.model.Guest;
import com.hotel.royalpalace.model.Request;
import com.hotel.royalpalace.model.Room;
import com.hotel.royalpalace.model.info.RequestInfo;
import com.hotel.royalpalace.service.GuestService;
import com.hotel.royalpalace.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/receptionist")
public class ReceptionistController {

    @Autowired
    GuestService guestService;

    @Autowired
    RequestService requestService;

//    Pages Mapping

    @RequestMapping(value = "")
    public String receptionist() { return "receptionist/receptionist"; }

    @RequestMapping(value = "/homeReceptionist")
    public String home() { return "receptionist/home"; }

    @RequestMapping(value = "/guests")
    public String guests() { return "receptionist/guests"; }

    @RequestMapping(value = "/viewReservations")
    public String viewReservations() { return "receptionist/viewReservations"; }

//    ------------------------------------------------------------------------------

    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

//    @RequestMapping(value = "/findByIdNumber", method = RequestMethod.GET)
//    public ResponseEntity findByIdNumber(@RequestParam(value = "idNumber") String idNumber,
//                                    HttpServletRequest request) {
//
//        return new ResponseEntity<>(null, HttpStatus.OK);
//    }

    @RequestMapping(value = "/getAllGuests", method = RequestMethod.GET)
    public ResponseEntity getAllReservations() {

        return new ResponseEntity<>(guestService.getAll(), HttpStatus.OK);
    }

}
