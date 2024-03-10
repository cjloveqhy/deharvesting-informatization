package com.cby.tcs.cotton_field.service;

import com.cby.tcs.cotton_field.entity.po.CottonField;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.cotton_field.entity.vo.CottonFieldVo;

import java.util.List;

public interface CottonFieldService extends IService<CottonField> {

    /**
     * 获取地块信息
     * @param factoryId 轧花厂id编号
     * @return {@link List}<{@link CottonFieldVo}>
     */
    List<CottonFieldVo> getCottons(String factoryId);

}
