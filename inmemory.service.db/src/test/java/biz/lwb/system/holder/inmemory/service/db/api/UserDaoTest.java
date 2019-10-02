package biz.lwb.system.holder.inmemory.service.db.api;

import biz.lwb.system.holder.inmemory.service.SpringConfig;
import biz.lwb.system.holder.inmemory.service.db.dto.UserDto;
import biz.lwb.system.holder.inmemory.service.db.mapper.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SpringConfig.class)
@ActiveProfiles("inmemory")
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void findAllTest() {

        UserDto byId = userDao.findById(1);
        log.info("found: {}", byId);
        assertThat(byId.getRoles()).hasSize(1);

    }
}