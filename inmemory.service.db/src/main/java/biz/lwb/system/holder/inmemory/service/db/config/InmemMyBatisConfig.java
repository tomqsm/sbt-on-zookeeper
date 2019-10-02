package biz.lwb.system.holder.inmemory.service.db.config;

import biz.lwb.system.holder.inmemory.service.db.mapper.MonitorDao;
import biz.lwb.system.holder.inmemory.service.db.mapper.UserDao;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
public class InmemMyBatisConfig {

    @Bean
    @DependsOn("inmemDataSource")
    SqlSessionFactoryBean inmemSqlSessionFactoryBean(DataSource inmemDataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(inmemDataSource);
        return sqlSessionFactoryBean;
    }

    @Bean
    SqlSessionFactory inmemSqlSessionFactory(@Qualifier("inmemSqlSessionFactoryBean") SqlSessionFactoryBean sqlSessionFactoryBeanDsOne) throws Exception {
        return sqlSessionFactoryBeanDsOne.getObject();
    }

    @Bean
    SqlSessionTemplate inmemDefaultSqlSessionTemplateSimple(@Qualifier("inmemSqlSessionFactory") SqlSessionFactory sqlSessionFactoryDsOne) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactoryDsOne, ExecutorType.SIMPLE);
        sqlSessionTemplate.getConfiguration().addMapper(MonitorDao.class);
        sqlSessionTemplate.getConfiguration().addMapper(UserDao.class);
        sqlSessionTemplate.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
        sqlSessionTemplate.getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionTemplate;
    }
}
