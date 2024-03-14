package com.cby.tcs.farm_data_terminal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.farm_data_terminal.entity.fo.DataTerminalPageFo;
import com.cby.tcs.farm_data_terminal.entity.po.FarmDataTerminal;
import com.cby.tcs.farm_data_terminal.entity.vo.DataTerminalVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 农机管理-数据终端 Mapper 接口
 * </p>
 */
@Mapper
public interface FarmDataTerminalDao extends BaseMapper<FarmDataTerminal> {

    /**
     * 获取所有品牌选项
     * @return {@link List}<{@link String}>
     */
    List<String> getBrandOptions();

    /**
     * 获取过滤分页的数据
     * @param entity {@link DataTerminalPageFo} 分页过滤信息
     * @return {@link Page}<{@link DataTerminalVo}>
     */
    Page<DataTerminalVo> getFilterPage(Page page, @Param("params") DataTerminalPageFo entity);

}
