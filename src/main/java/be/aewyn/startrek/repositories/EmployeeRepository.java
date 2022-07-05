package be.aewyn.startrek.repositories;

import be.aewyn.startrek.domain.Employee;
import be.aewyn.startrek.exceptions.EmployeeNotFoundException;
import be.aewyn.startrek.exceptions.NotEnoughBudgetException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class EmployeeRepository {
    private final JdbcTemplate template;

    public EmployeeRepository(JdbcTemplate template) {
        this.template = template;
    }

    private final RowMapper<Employee> employeeRowMapper = (rs, rowNum) ->
            new Employee(
                    rs.getLong("id"),
                    rs.getString("voornaam"),
                    rs.getString("familienaam"),
                    rs.getBigDecimal("budget")
            );

    public List<Employee> findAll() {
        var sql = """
                select id, voornaam, familienaam, budget
                from werknemers
                order by voornaam
                """;
        return template.query(sql, employeeRowMapper);
    }

    public Optional<Employee> findById(long id) {
        try {
            var sql = """
                    select id, voornaam, familienaam, budget
                    from werknemers
                    where id = ?
                    """;
            return Optional.of(template.queryForObject(sql, employeeRowMapper, id));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<BigDecimal> getEmployeeBudget(long id) {
        var sql = """
                select budget
                from werknemers
                where id = ?
                """;
        try {
            return Optional.of(template.queryForObject(sql, (rs, rowNum) -> rs.getBigDecimal("budget"), id));
        } catch (IncorrectResultSizeDataAccessException | EmployeeNotFoundException e) {
            throw new EmployeeNotFoundException();
        }
    }
    public void decreaseEmployeeBudget(long id, BigDecimal amount) {
        var sql = """
                update werknemers
                set budget = budget - ?
                where id = ?
                """;
        try {
            if (this.getEmployeeBudget(id).get().compareTo(amount) < 0) {
                throw new NotEnoughBudgetException();
            }
        } catch (EmployeeNotFoundException e) {
            throw new EmployeeNotFoundException();
        }
        template.update(sql, amount, id);
    }
}
