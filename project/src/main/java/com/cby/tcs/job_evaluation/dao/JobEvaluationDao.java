package com.cby.tcs.job_evaluation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.job_evaluation.entity.fo.JobEvaluationPageFo;
import com.cby.tcs.job_evaluation.entity.fo.JobEvaluationSelfPageFo;
import com.cby.tcs.job_evaluation.entity.po.JobEvaluation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 作业评价表 Mapper 接口
 * </p>
 */
@Mapper
public interface JobEvaluationDao extends BaseMapper<JobEvaluation> {

    /**
     * 获取过滤分页数据
     * @param page 分页实体
     * @param entity {@link JobEvaluationPageFo} 分页过滤对象
     * @return {@link Page}<{@link JobEvaluation}>
     */
    Page<JobEvaluation> getFilterPage(Page page, @Param("params") JobEvaluationPageFo entity);

    /**
     * 获取个人的评价信息
     * @param page 分页实体
     * @param entity {@link JobEvaluationSelfPageFo} 分页过滤对象
     * @return {@link Page}<{@link JobEvaluation}>
     */
    Page<JobEvaluation> getSelfFilterPage(Page page, @Param("params") JobEvaluationSelfPageFo entity);

}
