package controller;

import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.model.UserInfo;
import com.hotel.royalpalace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Beatrice Bianca on 16-Jul-17.
 */
@Controller
@RequestMapping("")
public class BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getLoginPage(){
        return "login";
    }

    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String getManagerPage(){
        return "manager";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody UserInfo userInfo) {

        User user = userService.getUser(userInfo.getEmail(), userInfo.getPassword());

        if (user == null) {
            return new ResponseEntity<>("", HttpStatus.OK);
        } else {
            if (user.getUserRole().equals("MANAGER")) {
                return new ResponseEntity<>("manager", HttpStatus.OK);
            } else if (user.getUserRole().equals("MAID")) {
                return new ResponseEntity<>("maid", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("receptionist", HttpStatus.OK);
            }
        }
    }
}
