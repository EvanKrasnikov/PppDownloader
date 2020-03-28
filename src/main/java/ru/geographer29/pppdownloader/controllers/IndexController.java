package ru.geographer29.pppdownloader.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.geographer29.pppdownloader.services.LinkCreatorService;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    LinkCreatorService service;

    @RequestMapping
    public String index(){
        return "index";
    }

    @RequestMapping("/submit")
    public ModelAndView submit(String firstDate, String secondDate){
        System.out.printf("Request wan submitted \nFirst date = %s \nSecond date = %s\n ", firstDate, secondDate);
        List<?> list = service.getLinks(firstDate, secondDate, LinkCreatorService.Params.RINEX);

        ModelAndView mv = new ModelAndView();
        mv.addObject("tables", list);
        mv.setViewName("index");

        return mv;
    }

}
