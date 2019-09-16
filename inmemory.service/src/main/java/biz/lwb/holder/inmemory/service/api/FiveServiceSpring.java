package biz.lwb.holder.inmemory.service.api;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FiveServiceSpring {
    @Select("select * from monitor where id=#{id}")
    FiveServiceSpringData findById(@Param("id") Integer id);

    @Select("select * from monitor")
    List<FiveServiceSpringData> findAll();
}
