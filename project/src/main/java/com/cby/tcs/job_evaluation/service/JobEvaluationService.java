package com.cby.tcs.job_evaluation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.job_evaluation.entity.fo.AddJobEvaluationFo;
import com.cby.tcs.job_evaluation.entity.fo.JobEvaluationPageFo;
import com.cby.tcs.job_evaluation.entity.fo.JobEvaluationSelfPageFo;
import com.cby.tcs.job_evaluation.entity.po.JobEvaluation;
import com.cby.tcs.job_evaluation.entity.vo.AddOptionJobEvaluationVo;
import com.cby.tcs.job_evaluation.entity.vo.JobEvaluationPageVo;

/**
 * <p>
 * 作业评价表 服务类
 * </p>
 */
public interface JobEvaluationService extends IService<JobEvaluation> {

    /**
     * 获取过滤分页数据
     * @param entity {@link JobEvaluationPageFo} 分页过滤对象
     * @return {@link Page}<{@link JobEvaluationPageVo}>
     */
    Page<JobEvaluationPageVo> getFilterPage(JobEvaluationPageFo entity);

    /**
     * 获取个人的评价信息
     * @param entity {@link JobEvaluationSelfPageFo} 分页过滤对象
     * @return {@link Page}<{@link JobEvaluationPageVo}>
     */
    Page<JobEvaluationPageVo> getSelfFilterPage(JobEvaluationSelfPageFo entity);

    /**
     * 添加评价信息
     * @param entity <{@link AddJobEvaluationFo}>
     */
    void add(AddJobEvaluationFo entity);

    /**
     * 获取添加评价信息表单中的option
     * @return <{@link AddOptionJobEvaluationVo}>
     */
    AddOptionJobEvaluationVo getAddOption();
}
