package com.cby.tcs.job_qualified_percent.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.job_evaluation.entity.enums.JobType;
import com.cby.tcs.job_qualified_percent.entity.fo.JobQualifiedPercentPageFo;
import com.cby.tcs.job_qualified_percent.entity.po.JobQualifiedPercent;
import com.cby.tcs.job_qualified_percent.entity.vo.JobQualifiedPercentPageVo;
import com.freedom.cloud.options.Option;
import com.freedom.cloud.utils.page.PageQuery;

import java.util.List;

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

    /**
     * 获取合格率排行TOP3的数据
     *
     * @param type {@link JobType} 工作类型
     * @return {@link List}<{@link Option}<{@link Float}>>
     */
    List<Option<Float>> passRateRanking(JobType type);
}
