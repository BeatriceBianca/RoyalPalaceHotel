package controller;

import com.hotel.royalpalace.model.ChosenRooms;
import com.hotel.royalpalace.model.Guest;
import com.hotel.royalpalace.model.Request;
import com.hotel.royalpalace.model.Room;
import com.hotel.royalpalace.model.info.RequestInfo;
import com.hotel.royalpalace.repository.ChosenRoomRepository;
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

    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

    @RequestMapping(value = "")
    public String getReceptionistPage() { return "receptionist"; }

    @RequestMapping(value = "/findByIdNumber", method = RequestMethod.GET)
    public ResponseEntity findByIdNumber(@RequestParam(value = "idNumber") String idNumber,
                                    HttpServletRequest request) {

        return new ResponseEntity<>(guestService.getByIdNumber(idNumber), HttpStatus.OK);
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
        return "redirect:/manager";
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
        return "redirect:/manager";
    }

    @RequestMapping(value = "/saveChosenRoom", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String saveChosenRoom(@RequestBody Room room) {

        ChosenRooms chosenRooms = new ChosenRooms(room);
        requestService.saveChosenRoom(chosenRooms);
        return "manager";
    }

    @RequestMapping(value = "/removeChosenRoom", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String removeChosenRoom(@RequestBody Room room) {

        ChosenRooms chosenRooms = new ChosenRooms(room);
        requestService.removeChosenRoom(chosenRooms);
        return "manager";
    }

    @RequestMapping(value = "/getAllReservations", method = RequestMethod.GET)
    public ResponseEntity getAllReservations() {

        return new ResponseEntity<>(requestService.getAll(), HttpStatus.OK);
    }
}
