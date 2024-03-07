package com.cby.tcs.ginnery.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.ginnery.entity.fo.GinneryPageFo;
import com.cby.tcs.ginnery.entity.po.Ginnery;
import com.cby.tcs.ginnery.entity.vo.GinneryVo;

import java.util.List;

public interface GinneryService extends IService<Ginnery> {

    /**
     * 通过轧花厂厂名搜索调度信息
     *
     * @param entity@return {@link List}<{@link GinneryVo}>
     */
    Page<GinneryVo> search(GinneryPageFo entity);
}
