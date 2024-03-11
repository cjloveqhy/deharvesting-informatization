package com.cby.tcs.harvest_schedule.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.harvest_schedule.entity.fo.AddHarvestScheduleFo;
import com.cby.tcs.harvest_schedule.entity.fo.DeleteHarvestScheduleRecordCottonFieldFo;
import com.cby.tcs.harvest_schedule.entity.fo.FilterPageFo;
import com.cby.tcs.harvest_schedule.entity.fo.HarvestScheduleRecordPageFo;
import com.cby.tcs.harvest_schedule.entity.po.HarvestSchedule;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.harvest_schedule.entity.vo.HarvestScheduleRecordVo;
import com.cby.tcs.harvest_schedule.entity.vo.HarvestScheduleDetailsVo;
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
    String createDispatchId();

    /**
     * 获取调度单信息
     * @param entity
     * @return
     */
    HarvestScheduleRecordVo getRecord(HarvestScheduleRecordPageFo entity);

    void deleteHarvestScheduleRecordCottonField(DeleteHarvestScheduleRecordCottonFieldFo entity);
    /**
     * 获取调度详细信息
     * @param dispatchId 调度编号
     * @return {@link HarvestScheduleDetailsVo}
     */
    HarvestScheduleDetailsVo getDetails(String dispatchId);

    void create(String dispatchId);
}
