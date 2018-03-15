package controller;

import com.hotel.royalpalace.model.Room;
import com.hotel.royalpalace.model.RoomType;
import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.model.info.UserInfo;
import com.hotel.royalpalace.service.RoomsService;
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
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Controller
public class RoomsController {

    @Autowired
    RoomsService roomsService;

    @RequestMapping(value = "/manager/getAllRooms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllRooms(HttpServletRequest request) {
        return new ResponseEntity<>(roomsService.getAllRooms(), HttpStatus.OK);
    }

    @RequestMapping(value = "/manager/getAllRoomTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllRoomTypes(HttpServletRequest request) {
        return new ResponseEntity<>(roomsService.getAllRoomTypes(), HttpStatus.OK);
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
