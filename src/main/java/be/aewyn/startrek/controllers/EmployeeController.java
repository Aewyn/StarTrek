package be.aewyn.startrek.controllers;

import be.aewyn.startrek.services.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service){
        this.service = service;
    }
    @GetMapping("{id}")
    public ModelAndView showEmployee(@PathVariable long id){
        var modelAndView = new ModelAndView("employee");
        service.findById(id).ifPresent(employee -> {
            modelAndView.addObject("employee", employee);
        });
        return modelAndView;
    }
}
