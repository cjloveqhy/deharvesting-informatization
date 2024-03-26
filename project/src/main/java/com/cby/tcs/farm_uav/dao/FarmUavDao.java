package com.cby.tcs.farm_uav.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cby.tcs.farm_uav.entity.po.FarmUav;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 农机管理-无人机 Mapper 接口
 * </p>
 */
@Mapper
public interface FarmUavDao extends BaseMapper<FarmUav> {
    /**
     * 获取无人机所属人的id集合
     */
    List<String> uavBelongerIds();
}
