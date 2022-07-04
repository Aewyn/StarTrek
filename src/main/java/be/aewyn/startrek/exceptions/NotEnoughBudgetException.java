package be.aewyn.startrek.exceptions;

import java.io.Serial;

public class NotEnoughBudgetException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public NotEnoughBudgetException() {
    }

    public NotEnoughBudgetException(String message) {
        super(message);
    }
}
