package be.aewyn.startrek.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeTest {
    Employee em;
    Employee emBad;

    @BeforeEach
    void beforeEach(){
        em = new Employee(1L, "Jim","Henson", BigDecimal.valueOf(2000));
    }

    @Test
    void getId() {
        assertThat(em.getId()).isEqualTo(1L);
    }

    @Test
    void getFirstName(){
        assertThat(em.getFirstName()).isEqualTo("Jim");
    }

    @Test
    void getLastName() {
        assertThat(em.getLastName()).isEqualTo("Henson");
    }

    @Test
    void getBudget() {
        assertThat(em.getBudget()).isEqualTo(BigDecimal.valueOf(2000));
    }
}