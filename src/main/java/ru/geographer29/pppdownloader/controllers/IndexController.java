package ru.geographer29.pppdownloader.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping
    public String index(){
        return "index";
    }

    @RequestMapping("/submit")
    public String submit(String firstDate, String secondDate){
        System.out.printf("Request wan submitted \nFirst date = %s \nSecond date = %s\n ", firstDate, secondDate);
        return "index";
    }

}
