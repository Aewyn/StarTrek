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
        try{
            orderRepository.createOrder(nof);
            employeeRepository.decreaseEmployeeBudget(nof.employeeId(), nof.amount());
        }catch (NotEnoughBudgetException e){
            throw new NotEnoughBudgetException();
        }
    }
}
