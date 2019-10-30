package biz.lwb.system.holder.setup.controller;

import biz.lwb.system.holder.inmemory.service.db.dto.MonitorDto;
import biz.lwb.system.holder.inmemory.service.db.mapper.MonitorDao;
import biz.lwb.system.holder.setup.filter.CorrelationIdFilter;
import biz.lwb.system.holder.setup.properties.CorrelationIdProperties;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class IndexControllerMockedTest {

    @InjectMocks
    private IndexController controller;

    @Mock
    private MonitorDao monitorDao;

    private MockMvc mockMvc;
    private CorrelationIdProperties correlationIdProperties = new CorrelationIdProperties();

    @BeforeEach
    public void setup() {
        correlationIdProperties.setHeaderName("X_CORRELATION");
        correlationIdProperties.setMdcName("X_CORRELATION");
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .addFilters(new CorrelationIdFilter(correlationIdProperties))
                .build();
    }

    @Test
    public void getServiceSpringInfoInjection() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(monitorDao.findAll()).thenReturn(List.of(new MonitorDto(1L, "before", "after")));

        List<MonitorDto> response = controller.getServiceSpringInfoInjection();
        assertThat(response).hasSize(1);
    }

    @Test
    public void filterSetsCollerationId() throws Exception {
        when(monitorDao.findAll()).thenReturn(List.of(new MonitorDto(1L, "before", "after")));

        mockMvc.perform(get("/service.spring.injection"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(header().string(correlationIdProperties.getHeaderName(), correlationIdMatcher))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(status().isOk());
    }

    private BaseMatcher<String> correlationIdMatcher = new BaseMatcher<>() {
        int expectedLength = 32;
        int actualLength;

        @Override
        public boolean matches(Object o) {
            actualLength = o.toString().length();
            return actualLength == expectedLength;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("correlationId header should have length ").appendValue(expectedLength);
        }

        @Override
        public void describeMismatch(Object item, Description description) {
            description.appendText("actual length is ").appendValue(item.toString().length());
        }
    };
}