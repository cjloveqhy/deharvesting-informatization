package com.cby.tcs.farm_uav.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.farm_uav.entity.fo.FarmUavPageFo;
import com.cby.tcs.farm_uav.entity.po.FarmUav;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.farm_uav.entity.vo.FarmUavVo;

/**
 * <p>
 * 农机管理-无人机 服务类
 * </p>
 */
public interface FarmUavService extends IService<FarmUav> {

    /**
     * 分页过滤获取无人机的信息
     * @param entity {@link FarmUavPageFo} 查询参数
     * @return {@link Page}<{@link FarmUavVo}> 分页数据
     */
    Page<FarmUavVo> getFilterPage(FarmUavPageFo entity);

}
