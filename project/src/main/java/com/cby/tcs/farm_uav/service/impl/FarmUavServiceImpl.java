package com.cby.tcs.farm_uav.service.impl;

import com.cby.tcs.farm_uav.dao.FarmUavDao;
import com.cby.tcs.farm_uav.service.FarmUavService;
import com.cby.tcs.farm_uav.entity.po.FarmUav;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 农机管理-无人机 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor
public class FarmUavServiceImpl extends ServiceImpl<FarmUavDao, FarmUav> implements FarmUavService {

  private final FarmUavDao farmUavDao;
}
