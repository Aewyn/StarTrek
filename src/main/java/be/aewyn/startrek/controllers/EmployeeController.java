package be.aewyn.startrek.controllers;

import be.aewyn.startrek.domain.Order;
import be.aewyn.startrek.exceptions.NotEnoughBudgetException;
import be.aewyn.startrek.forms.NewOrderForm;
import be.aewyn.startrek.services.EmployeeService;
import be.aewyn.startrek.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final OrderService orderService;

    public EmployeeController(EmployeeService employeeService, OrderService orderService) {
        this.employeeService = employeeService;
        this.orderService = orderService;
    }
    @GetMapping()
    public ModelAndView index(){
        var modelAndView = new ModelAndView();
        modelAndView = new ModelAndView("index", "allEmployees", employeeService.findAll());
        return modelAndView;
    }
    @GetMapping("employee/{employeeId}")
    public ModelAndView showEmployee(@PathVariable long employeeId){
        var modelAndView = new ModelAndView("employee");
        employeeService.findById(employeeId).ifPresent(employee -> {
            modelAndView.addObject("employee", employee);
        });
        return modelAndView;
    }
    @GetMapping("orders/{employeeId}")
    public ModelAndView getOrderFromEmployeeId(@PathVariable long employeeId){
        var modelAndView = new ModelAndView("orders");
        List<Order> orderList = orderService.findOrdersByEmployeeId(employeeId);
        if(orderList.size() > 0){
            modelAndView.addObject("orderList", orderList);
        }
        return modelAndView;
    }

    @GetMapping("orders/form/{employeeId}")
    public ModelAndView newOrder(@PathVariable long employeeId){
        var modelAndView = new ModelAndView("neworder");
        modelAndView.addObject("newOrderForm", new NewOrderForm(employeeId,"", null));
        modelAndView.addObject("employeeId", employeeId);
        return modelAndView;
    }
    @PostMapping("orders")
    public String order(@Valid NewOrderForm newOrderForm, Errors errors, RedirectAttributes redirectAttributes){
        if(errors.hasErrors()){
            return "neworder";
        }
        try{
            orderService.createOrder(newOrderForm);
        } catch (NotEnoughBudgetException e){
            redirectAttributes.addAttribute("employeeId", newOrderForm.employeeId());
            redirectAttributes.addAttribute("notEnoughMoney", newOrderForm.amount());
            return "redirect:/employee/{employeeId}";
        }
        //return getOrderFromEmployeeId(newOrderForm.employeeId()).addObject("orderList", orderService.findOrdersByEmployeeId(newOrderForm.employeeId()));
        redirectAttributes.addAttribute("employeeId", newOrderForm.employeeId());
        return "redirect:/orders/{employeeId}";
    }
}
