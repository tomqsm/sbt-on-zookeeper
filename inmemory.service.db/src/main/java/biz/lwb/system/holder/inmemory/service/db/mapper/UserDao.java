package biz.lwb.system.holder.inmemory.service.db.mapper;

import biz.lwb.system.holder.inmemory.service.db.dto.UserDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDao {

    @Select("select * from users u join roles r on u.id=r.user_id where u.id=#{id}")
    @Results(value = {
            @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "findRolesByOsobaId")),
    })
    UserDto findById(@Param("id") Integer id);

    @Select("select role from roles where user_id=#{id}")
    List<String> findRolesByOsobaId(Long id);
}
