package pl.pacinho.charades.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.pacinho.charades.config.UIConfig;

@Controller
public class HomeController {

    @GetMapping
    public String home() {
        return "redirect:" + UIConfig.HOME;
    }
}