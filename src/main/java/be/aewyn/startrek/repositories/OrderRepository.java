package be.aewyn.startrek.repositories;

import be.aewyn.startrek.domain.Order;
import be.aewyn.startrek.forms.NewOrderForm;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    public OrderRepository(JdbcTemplate template) {
        this.template = template;
        insert = new SimpleJdbcInsert(template)
                .withTableName("bestellingen")
                .usingGeneratedKeyColumns("id");
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

    public long createOrder(NewOrderForm nof){
        return insert.executeAndReturnKey(Map.of("werknemerId", nof.employeeId(), "omschrijving", nof.description(),"bedrag", nof.amount())).longValue();
    }
}
