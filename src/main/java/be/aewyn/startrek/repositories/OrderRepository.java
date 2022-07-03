package be.aewyn.startrek.repositories;

import be.aewyn.startrek.domain.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class OrderRepository {
    private final JdbcTemplate template;

    public OrderRepository(JdbcTemplate template) {
        this.template = template;
    }

    private final RowMapper<Order> orderRowMapper = (rs, rowNum) ->
            new Order(
                    rs.getLong("id"),
                    rs.getLong("werknemerId"),
                    rs.getString("omschrijving"),
                    rs.getBigDecimal("bedrag")
            );

    public List<Order> findOrdersByEmployeeId(long employeeId) {
        var sql = """
                select id, werknemerId, omschrijving, bedrag
                from bestellingen
                where werknemerId = ?
                order by id
                """;
        return template.query(sql,orderRowMapper,employeeId);
    }
}
