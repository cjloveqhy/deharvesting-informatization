package com.cby.tcs.cotton_field.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.cotton_field.entity.fo.CottonFieldByIdsFo;
import com.cby.tcs.cotton_field.entity.po.CottonField;
import com.cby.tcs.cotton_field.entity.vo.CottonFieldVo;

import java.util.List;

public interface CottonFieldService extends IService<CottonField> {

    /**
     * 获取地块信息
     * @param factoryId 轧花厂id编号
     * @return {@link List}<{@link CottonFieldVo}>
     */
    List<CottonFieldVo> getCottons(String factoryId);

    List<CottonFieldVo> getCottonsByIds(CottonFieldByIdsFo cottonFieldByIdsFo);

    /**
     * 根据棉地id编号获取地块信息
     * @param ids
     * @return
     */
    List<CottonFieldVo> getCottonsByIds(List<String> ids);

}
