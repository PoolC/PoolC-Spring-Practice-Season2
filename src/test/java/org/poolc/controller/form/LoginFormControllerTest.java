package org.poolc.controller.form;

import org.junit.jupiter.api.Test;
import org.poolc.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LoginFormControllerTest {

    @Test
    void getLoginId() {
        LoginFormController form = new LoginFormController();
        form.setLoginId("123");
        assertThat("123").isEqualTo(form.getLoginId());
    }

    @Test
    void getPassword() {
        LoginFormController form = new LoginFormController();
        form.setPassword("123");
        assertThat("123").isEqualTo(form.getPassword());
    }

    @Test
    void testEquals() {
        LoginFormController form = new LoginFormController();
        LoginFormController form1 = form;
        assertThat(form.equals(form1)).isEqualTo(true);
    }


}