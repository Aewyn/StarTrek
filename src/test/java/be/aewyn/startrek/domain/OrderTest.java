package be.aewyn.startrek.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {
    Order order;

    @BeforeEach
    void beforeEach(){
        order = new Order(1L,4L,"Normal chair", BigDecimal.TEN);
    }

    @Test
    void getId() {
        assertThat(order.getId()).isEqualTo(1L);
    }

    @Test
    void getEmployeeId() {
        assertThat(order.getEmployeeId()).isEqualTo(4L);
    }

    @Test
    void getDescription() {
        assertThat(order.getDescription()).isEqualTo("Normal chair");
    }

    @Test
    void getAmount() {
        assertThat(order.getAmount()).isEqualTo(BigDecimal.TEN);
    }
}