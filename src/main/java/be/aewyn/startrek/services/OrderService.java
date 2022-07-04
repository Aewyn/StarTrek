package be.aewyn.startrek.services;

import be.aewyn.startrek.domain.Order;
import be.aewyn.startrek.exceptions.NotEnoughBudgetException;
import be.aewyn.startrek.forms.NewOrderForm;
import be.aewyn.startrek.repositories.EmployeeRepository;
import be.aewyn.startrek.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;

    public OrderService(OrderRepository orderRepository, EmployeeRepository employeeRepository) {
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Order> findOrdersByEmployeeId(long employeeId) {
        return orderRepository.findOrdersByEmployeeId(employeeId);
    }

    @Transactional(readOnly = false)
    public void createOrder(NewOrderForm nof) {
        //1. Check of er genoeg centen zijn
        var budget = employeeRepository.getEmployeeBudget(nof.employeeId()).get();
        if (budget.compareTo(nof.amount()) < 0) {
            throw new NotEnoughBudgetException("De werknemer heeft niet genoeg geld");
        } else {
            //2. Haal centjes weg bij werknemer
            employeeRepository.decreaseEmployeeBudget(nof.employeeId(), nof.amount());
            //3. Maak order aan
            orderRepository.createOrder(nof);
        }
    }
}
