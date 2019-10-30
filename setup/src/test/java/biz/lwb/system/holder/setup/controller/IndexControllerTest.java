package biz.lwb.system.holder.setup.controller;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
@SpringBootTest
@ContextConfiguration(classes = {ControllerContext.class})
@ActiveProfiles("default,inmemory")
public class IndexControllerTest {

    @Autowired
    private IndexController controller;

    @Test
    public void getServiceSpringInfoInjection() {
        assertThat(controller).isNotNull();
    }
}