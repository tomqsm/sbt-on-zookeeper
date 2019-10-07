package biz.lwb.system.holder.inmemory.service.db.api;

import biz.lwb.system.holder.inmemory.service.SpringConfig;
import biz.lwb.system.holder.inmemory.service.db.dto.MonitorDto;
import biz.lwb.system.holder.inmemory.service.db.mapper.MonitorDao;
import biz.lwb.system.holder.inmemory.service.db.mapper.MonitorDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringConfig.class)
@ActiveProfiles("inmemory")
public class MonitorDaoTest {


    @Autowired
    private MonitorDao repository;

    @Test
    public void fiveTest() {
        MonitorDto byId = repository.findById(3);
        assertThat(byId.getId()).isEqualTo(3L);
    }
}