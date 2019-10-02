package biz.lwb.system.holder.inmemory.service.db;

import biz.lwb.system.holder.inmemory.service.db.mapper.MonitorDao;
import biz.lwb.system.holder.inmemory.service.db.dto.MonitorDto;

import java.util.Collections;
import java.util.List;

public class MonitorDaoImpl implements MonitorDao {

    @Override
    public MonitorDto findById(Integer id) {
        MonitorDto data = new MonitorDto();
        data.setBefore("five");
        data.setAfter("five " + id);
        return data;
    }

    @Override
    public List<MonitorDto> findAll() {
        return Collections.emptyList();
    }
}
