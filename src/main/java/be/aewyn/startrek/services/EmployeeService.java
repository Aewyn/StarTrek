package be.aewyn.startrek.services;

import be.aewyn.startrek.domain.Employee;
import be.aewyn.startrek.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository){
        this.repository = repository;
    }
    public List<Employee> findAll(){
        return repository.findAll();
    }

    public Optional<Employee> findById(long id){
        return repository.findById(id);
    }



}
