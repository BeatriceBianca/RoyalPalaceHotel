package controller;

import com.hotel.royalpalace.auxiliary.PDF;
import com.hotel.royalpalace.auxiliary.SmtpMailSender;
import com.hotel.royalpalace.model.*;
import com.hotel.royalpalace.model.info.RequestInfo;
import com.hotel.royalpalace.service.*;
import com.itextpdf.text.DocumentException;
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

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    SmtpMailSender smtpMailSender;

    @Autowired
    OfferService offerService;

    @Autowired
    PDF pdf;

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
//        try {
//            smtpMailSender.sendToSingle("bianca.luca96@gmail.com", "Reservation request",
//                    "Thank you for your request");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
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
    public ResponseEntity getAllChosenRooms(HttpServletRequest request) {

        return new ResponseEntity<>(requestService.getAllRChosenRooms(), HttpStatus.OK);
    }

    @RequestMapping(value = "/saveGuest", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String saveGuest(@RequestBody Guest guest) {

        Guest g = guestService.getByGuestEmail(guest.getGuestEmail());
        if (g == null) {
            guest.setRegisterDate(new Date());
        } else {
            guest.setRegisterDate(g.getRegisterDate());
            guest.setId(g.getId());
        }
        requestService.saveGuest(guest);
        return redirectToPage();
    }

    @RequestMapping(value = "/saveRequest", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void saveRequest(HttpServletResponse response,
                              @RequestBody RequestInfo requestInfo) throws ParseException, ServletException, IOException, DocumentException {

        Set<Room> requestedRooms = requestInfo.getRooms();
        Request requestModel = new Request(requestInfo);
        requestModel.setArrivalDate(df.parse(requestInfo.getArrivalDate()));
        requestModel.setDepartureDate(df.parse(requestInfo.getDepartureDate()));
        requestModel.setRequestDate(df.parse(requestInfo.getRequestDate()));
        requestService.saveRequest(requestModel);

        pdf.createPdfAndSendMail(requestModel, response);
        File file = new File("C:\\TEMP\\invoice.pdf");
        try {
            smtpMailSender.sendWithAttachament(requestModel.getCustomer().getGuestEmail(), "Reservation request",
                    "Thank you for your request", file);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        file.delete();
    }

    @RequestMapping(value = "/saveChosenRoom", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String saveChosenRoom(@RequestBody ChosenRooms room) {

        requestService.saveChosenRoom(room);
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

    @RequestMapping(value = "/getPDF", method = RequestMethod.POST, produces = "application/pdf")
    public void getPDF (HttpServletRequest request, HttpServletResponse response, @RequestBody RequestInfo requestInfo
    ) throws ServletException, IOException, ParseException {

        Request requestModel = new Request(requestInfo);
        requestModel.setArrivalDate(df.parse(requestInfo.getArrivalDate()));
        requestModel.setDepartureDate(df.parse(requestInfo.getDepartureDate()));
        requestModel.setRequestDate(df.parse(requestInfo.getRequestDate()));
        pdf.createPdf(requestModel, response);
    }

    @RequestMapping(value = "/getAllReservations", method = RequestMethod.GET)
    public ResponseEntity getAllReservations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        return new ResponseEntity<>(requestService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/findByEmail", method = RequestMethod.GET)
    public ResponseEntity getByGuestEmail(@RequestParam(value = "email") String email) {

        return new ResponseEntity<>(guestService.getByGuestEmail(email), HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllOffers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllOffers(HttpServletRequest request) {
        return new ResponseEntity<>(offerService.getAllOffers(), HttpStatus.OK);
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
