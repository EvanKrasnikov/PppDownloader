package ru.geographer29.pppdownloader.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.geographer29.pppdownloader.services.DateParserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @RequestMapping
    public String index(){
        return "index";
    }

    @RequestMapping("/submit")
    public ModelAndView submit(String firstDate, String secondDate, HttpSession session){
        System.out.printf("Request wan submitted \nFirst date = %s \nSecond date = %s\n ", firstDate, secondDate);

        DateParserService dps = new DateParserService();
        List<String> rinexes =  dps.getRinexLinks(firstDate, secondDate);

        ModelAndView mv = new ModelAndView();
        mv.addObject("links", rinexes);
        mv.setViewName("index");

        return mv;
    }

}
