package com.polkins.music.metadata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Default controller that exists to return a proper REST response for unmapped requests.
 */
@Controller
public class DefaultController {

    @RequestMapping({"/", "/api"})
    public String home() {
        return "redirect:swagger-ui/";
    }

}
