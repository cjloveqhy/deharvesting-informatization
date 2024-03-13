package com.cby.tcs.farm_cotton_picker.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.common.entity.BasicOption;
import com.cby.tcs.farm_cotton_picker.entity.fo.CottonPickerFo;
import com.cby.tcs.farm_cotton_picker.entity.fo.CottonPickerPageFo;
import com.cby.tcs.farm_cotton_picker.entity.po.FarmCottonPicker;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.farm_cotton_picker.entity.vo.CottonPickerVo;

import java.util.List;

/**
 * <p>
 * 农机管理-采棉机 服务类
 * </p>
 */
public interface FarmCottonPickerService extends IService<FarmCottonPicker> {

    /**
     * 获取所有品牌选项
     * @return {@link List}<{@link BasicOption}>
     */
    List<BasicOption> getBrandOptions();

    /**
     * 获取过滤分页的数据
     * @param entity {@link CottonPickerPageFo} 过滤分页数据
     * @return {@link Page}<{@link CottonPickerVo}>
     */
    Page<CottonPickerVo> getFilterPage(CottonPickerPageFo entity);

    /**
     * 新机入库（添加）
     * @param entity {@link CottonPickerFo}
     */
    void addFarmCottonPicker(CottonPickerFo entity);

    /**
     * 移除采棉机
     * @param id id编号
     */
    void delFarmCottonPicker(String id);

    /**
     * 修改采棉机的入库信息
     * @param entity {@link CottonPickerFo}
     */
    void updateFarmCottonPicker(CottonPickerFo entity);
}
