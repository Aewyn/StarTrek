package be.aewyn.startrek.domain;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Employee {
    @NotNull
    private final long id;
    @NotNull
    private final String firstName;
    @NotNull
    private final String lastName;
    @NotNull
    private final BigDecimal budget;

    public Employee(long id, String firstName, String lastName, BigDecimal budget) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.budget = budget;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getBudget() {
        return budget;
    }
}
