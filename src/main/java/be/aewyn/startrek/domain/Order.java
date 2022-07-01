package be.aewyn.startrek.domain;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Order {
    @NotNull
    private final long id;
    @NotNull
    private final long employeeId;
    @NotNull
    private final String description;
    @NotNull
    private final BigDecimal amount;

    public Order(long id, long employeeId, String description, BigDecimal amount) {
        this.id = id;
        this.employeeId = employeeId;
        this.description = description;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
