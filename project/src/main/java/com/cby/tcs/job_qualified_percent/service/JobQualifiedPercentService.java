package com.cby.tcs.job_qualified_percent.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.job_qualified_percent.entity.fo.JobQualifiedPercentPageFo;
import com.cby.tcs.job_qualified_percent.entity.po.JobQualifiedPercent;
import com.cby.tcs.job_qualified_percent.entity.vo.JobQualifiedPercentPageVo;
import com.freedom.cloud.utils.page.PageQuery;

/**
 * <p>
 * 作业合格率总览表 服务类
 * </p>
 */
public interface JobQualifiedPercentService extends IService<JobQualifiedPercent> {

    /**
     * 分页获取作业合格率列表
     * @param entity {@link PageQuery}
     * @return {@link Page}<{@link JobQualifiedPercentPageVo}>
     */
    Page<JobQualifiedPercentPageVo> getPageData(JobQualifiedPercentPageFo entity);

}
