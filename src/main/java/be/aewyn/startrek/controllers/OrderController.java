package be.aewyn.startrek.controllers;

import be.aewyn.startrek.domain.Order;
import be.aewyn.startrek.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("{employeeId}")
    public ModelAndView getOrderFromId(@PathVariable long employeeId){
        var modelAndView = new ModelAndView("orders");
        List<Order> orderList = orderService.findOrdersByEmployeeId(employeeId);
        if(orderList.size() > 0){
            modelAndView.addObject("orderList", orderList);
        }
        return modelAndView;
    }

    @GetMapping("neworder")
    public ModelAndView newOrder(){
        return new ModelAndView("neworder");
    }
}
