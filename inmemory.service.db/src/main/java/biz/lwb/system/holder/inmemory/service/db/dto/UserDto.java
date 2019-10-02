package biz.lwb.system.holder.inmemory.service.db.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {

    private String name;
    private String surname;
    List<String> roles;

    public UserDto(){
        roles = List.of();
    }

}
