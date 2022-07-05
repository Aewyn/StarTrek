package be.aewyn.startrek.repositories;

import be.aewyn.startrek.domain.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

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
}