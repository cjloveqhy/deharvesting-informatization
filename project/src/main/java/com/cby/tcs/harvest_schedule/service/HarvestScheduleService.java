package com.cby.tcs.harvest_schedule.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.harvest_schedule.entity.fo.AddHarvestScheduleFo;
import com.cby.tcs.harvest_schedule.entity.fo.FilterPageFo;
import com.cby.tcs.harvest_schedule.entity.po.HarvestSchedule;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.harvest_schedule.entity.vo.HarvestScheduleVo;

public interface HarvestScheduleService extends IService<HarvestSchedule> {

    void add(AddHarvestScheduleFo entity);

    /**
     * 获取过滤分页数据
     *
     * @param entity {@link FilterPageFo} 过滤分页信息
     * @return
     */
    Page<HarvestScheduleVo> filterPage(FilterPageFo entity);

    /**
     * 创建调度订单编号
     * @return 调度订单编号
     */
    String createId();

}
