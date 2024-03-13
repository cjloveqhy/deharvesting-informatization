package com.cby.tcs.farm_cotton_picker.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.farm_cotton_picker.entity.fo.CottonPickerPageFo;
import com.cby.tcs.farm_cotton_picker.entity.po.FarmCottonPicker;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cby.tcs.farm_cotton_picker.entity.vo.CottonPickerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 农机管理-采棉机 Mapper 接口
 * </p>
 */
@Mapper
public interface FarmCottonPickerDao extends BaseMapper<FarmCottonPicker> {

    /**
     * 获取所有品牌选项
     * @return {@link List}<{@link String}>
     */
    List<String> getBrandOptions();

    /**
     * 获取过滤分页的数据
     * @param entity {@link CottonPickerPageFo} 过滤分页数据
     * @return {@link Page}<{@link CottonPickerVo}>
     */
    Page<CottonPickerVo> getFilterPage(Page page, @Param("params") CottonPickerPageFo entity);
}
