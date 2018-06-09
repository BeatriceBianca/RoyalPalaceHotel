package controller;

import com.hotel.royalpalace.service.GuestService;
import com.hotel.royalpalace.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
