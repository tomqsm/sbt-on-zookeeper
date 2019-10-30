package biz.lwb.system.holder.inmemory.service.db.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonitorDto {

    private Long id;
    private String before;
    private String after;

}
