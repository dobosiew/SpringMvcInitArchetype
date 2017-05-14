package ${package}.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String homeController(
            @RequestParam(value = "text", defaultValue = "Hello World", required = false) String text,
                                 Model model) {
        model.addAttribute("text", text);
        return "index";
    }
}
