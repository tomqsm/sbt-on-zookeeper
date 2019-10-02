package biz.lwb.system.holder.inmemory.service.db.mapper;

import biz.lwb.system.holder.inmemory.service.db.mapper.UserDao;
import biz.lwb.system.holder.inmemory.service.db.dto.UserDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("inmemory")
public class UserDaoImpl implements UserDao {

    private static final Class<UserDao> MAPPER_CLASS = UserDao.class;

    @Autowired
    private SqlSession sqlSession;

    private UserDao getMapper() {
        return sqlSession.getMapper(MAPPER_CLASS);
    }

    @Override
    public UserDto findById(Integer id) {
        return getMapper().findById(id);
    }

    @Override
    public List<String> findRolesByOsobaId(Long osobaId) {
        return getMapper().findRolesByOsobaId(osobaId);
    }
}
