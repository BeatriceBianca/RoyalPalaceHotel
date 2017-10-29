package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ManagerController {

    @RequestMapping(value = "/manager")
    public String getLoginForm(HttpServletRequest request, Model model) {
        return "manager/home";
    }
}
