package be.aewyn.startrek.controllers;

import be.aewyn.startrek.services.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    private final EmployeeService service;

    public IndexController(EmployeeService service){
        this.service = service;
    }

    @GetMapping("/")
    public ModelAndView index(){
        var modelAndView = new ModelAndView();
        modelAndView = new ModelAndView("index", "allEmployees", service.findAll());
        return modelAndView;
    }
}
