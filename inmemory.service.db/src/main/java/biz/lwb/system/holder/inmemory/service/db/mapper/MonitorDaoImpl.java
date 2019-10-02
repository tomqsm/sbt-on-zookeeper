package biz.lwb.system.holder.inmemory.service.db.mapper;

import biz.lwb.system.holder.inmemory.service.db.mapper.MonitorDao;
import biz.lwb.system.holder.inmemory.service.db.dto.MonitorDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("inmemory")
public class MonitorDaoImpl implements MonitorDao {

    private static final Class<MonitorDao> MAPPER_CLASS = MonitorDao.class;

    @Autowired
    private SqlSession sqlSession;

    private MonitorDao getMapper() {
        return sqlSession.getMapper(MAPPER_CLASS);
    }

    @Override
    public MonitorDto findById(Integer id) {
        return getMapper().findById(id);
    }

    @Override
    public List<MonitorDto> findAll() {
        return getMapper().findAll();
    }

}
