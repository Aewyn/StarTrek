package be.aewyn.startrek.services;

import be.aewyn.startrek.domain.Order;
import be.aewyn.startrek.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> findOrdersByEmployeeId(long employeeId){
        return repository.findOrdersByEmployeeId(employeeId);
    }
}
