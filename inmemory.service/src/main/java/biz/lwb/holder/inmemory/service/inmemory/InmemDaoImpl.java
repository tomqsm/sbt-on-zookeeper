package biz.lwb.holder.inmemory.service.inmemory;

import biz.lwb.holder.inmemory.service.api.FiveServiceSpring;
import biz.lwb.holder.inmemory.service.api.FiveServiceSpringData;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("inmemory")
public class InmemDaoImpl implements FiveServiceSpring {

    private static final Class<FiveServiceSpring> MAPPER_CLASS = FiveServiceSpring.class;

    @Autowired
    private SqlSession sqlSession;

    private FiveServiceSpring getMapper() {
        return sqlSession.getMapper(MAPPER_CLASS);
    }

    @Override
    public FiveServiceSpringData findById(Integer id) {
        return getMapper().findById(id);
    }

    @Override
    public List<FiveServiceSpringData> findAll() {
        return getMapper().findAll();
    }

}
