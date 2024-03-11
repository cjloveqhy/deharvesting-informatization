package com.cby.tcs.harvest_schedule.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.harvest_schedule.entity.fo.FilterPageFo;
import com.cby.tcs.harvest_schedule.entity.po.HarvestSchedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cby.tcs.harvest_schedule.entity.vo.HarvestScheduleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HarvestScheduleDao extends BaseMapper<HarvestSchedule> {

    /**
     * 过滤分页获取数据
     */
    Page<HarvestScheduleVo> filterPage(Page page, @Param("params") FilterPageFo entity);
}
