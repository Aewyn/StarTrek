package be.aewyn.startrek.repositories;

import be.aewyn.startrek.domain.Employee;
import be.aewyn.startrek.exceptions.EmployeeNotFoundException;
import be.aewyn.startrek.exceptions.NotEnoughBudgetException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(EmployeeRepository.class)
@Sql("/insertEmployees.sql")
class EmployeeRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String WERKNEMERS = "werknemers";
    private final EmployeeRepository repository;

    public EmployeeRepositoryTest(EmployeeRepository repository) {
        this.repository = repository;
    }

    private long idFromTestEmployee(){
        return jdbcTemplate.queryForObject("select id from werknemers where voornaam='voornaam1test'",Long.class);
    }

    @Test
    void findAllGivesAllEmployeesSortedByName() {
        assertThat(repository.findAll())
                        .hasSize(countRowsInTable(WERKNEMERS))
                        .extracting(Employee::getFirstName)
                        .isSortedAccordingTo(String::compareToIgnoreCase);
    }

    @Test
    void findById(){
        assertThat(repository.findById(idFromTestEmployee()))
                .hasValueSatisfying(employee ->
                        assertThat(employee.getFirstName()).isEqualTo("voornaam1test"));
    }
    @Test
    void findByNonExistingIdShowsNoEmployees(){
        assertThat(repository.findById(-1)).isEmpty();
    }
    @Test
    void lowerBudgetWithSufficientFunds(){
        repository.decreaseEmployeeBudget(idFromTestEmployee(), BigDecimal.TEN);
    }
    @Test
    void lowerBudgetWithInsufficientFunds(){
        assertThatExceptionOfType(NotEnoughBudgetException.class)
                .isThrownBy(() -> repository.decreaseEmployeeBudget(idFromTestEmployee()
                        ,BigDecimal.valueOf(100000)));
    }
    @Test
    void lowerBudgetOfEmployeeThatDoesNotExist(){
        assertThatExceptionOfType(EmployeeNotFoundException.class).isThrownBy(() -> repository.decreaseEmployeeBudget(-1,BigDecimal.TEN));
    }
}