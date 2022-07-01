package be.aewyn.startrek.domain;

import java.math.BigDecimal;

public class Order {
    private final long id;
    private final long employeeId;
    private final String description;
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
