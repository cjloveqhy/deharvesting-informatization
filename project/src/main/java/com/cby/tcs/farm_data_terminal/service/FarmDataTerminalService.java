package com.cby.tcs.farm_data_terminal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.common.entity.BasicOption;
import com.cby.tcs.farm_data_terminal.entity.fo.DataTerminalFo;
import com.cby.tcs.farm_data_terminal.entity.fo.DataTerminalPageFo;
import com.cby.tcs.farm_data_terminal.entity.po.FarmDataTerminal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.farm_data_terminal.entity.vo.DataTerminalVo;

import java.util.List;

/**
 * <p>
 * 农机管理-数据终端 服务类
 * </p>
 */
public interface FarmDataTerminalService extends IService<FarmDataTerminal> {

    /**
     * 获取所有品牌选项
     * @return {@link List}<{@link BasicOption}>
     */
    List<BasicOption> getBrandOptions();

    /**
     * 获取过滤分页的数据
     * @param entity {@link DataTerminalPageFo} 分页过滤信息
     * @return {@link Page}<{@link DataTerminalVo}>
     */
    Page<DataTerminalVo> getFilterPage(DataTerminalPageFo entity);

    /**
     * 新机入库（添加数据终端）
     * @param entity {@link DataTerminalFo}
     */
    void addFarmDataTerminal(DataTerminalFo entity);

    /**
     * 修改数据终端信息
     * @param entity {@link DataTerminalFo}
     */
    void updateFarmDataTerminal(DataTerminalFo entity);

    /**
     * 删除数据终端信息
     * @param id id编号
     */
    void delFarmDataTerminal(String id);
}
