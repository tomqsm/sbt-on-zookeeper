package biz.lwb.system.holder.inmemory.service.db.mapper;

import biz.lwb.system.holder.inmemory.service.db.dto.MonitorDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MonitorDao {
    @Select("select * from monitor where id=#{id}")
    MonitorDto findById(@Param("id") Integer id);

    @Select("select * from monitor")
    List<MonitorDto> findAll();
}
