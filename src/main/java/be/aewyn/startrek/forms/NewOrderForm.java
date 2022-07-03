package be.aewyn.startrek.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public record NewOrderForm(@NotNull String description, @NotNull @Positive BigDecimal amount) {
}
