package controller;

import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RoomsController {

    @Autowired
    RoomsService roomsService;

    @RequestMapping(value = "/manager/getAllRooms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllRooms(HttpServletRequest request) {
        return new ResponseEntity<>(roomsService.getAllRooms(), HttpStatus.OK);
    }
}
