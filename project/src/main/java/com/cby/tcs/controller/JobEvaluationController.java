package com.cby.tcs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.job_evaluation.entity.fo.JobEvaluationPageFo;
import com.cby.tcs.job_evaluation.entity.fo.JobEvaluationSelfPageFo;
import com.cby.tcs.job_evaluation.entity.vo.JobEvaluationPageVo;
import com.cby.tcs.job_evaluation.service.JobEvaluationService;
import com.freedom.cloud.annotation.ParamsToEntity;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 作业评价表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/job-evaluation")
@RequiredArgsConstructor
public class JobEvaluationController {

  private final JobEvaluationService jobEvaluationService;

  /**
   * 获取过滤分页数据
   */
    @GetMapping("/filterPage")
    public ResultEntity getFilterPage(@ParamsToEntity(required = false, params = {@RequestParam("jobType")}) JobEvaluationPageFo entity) {
      Page<JobEvaluationPageVo> page = jobEvaluationService.getFilterPage(entity);
      return ResultEntity.success(page);
    }

  /**
   * 获取个人的评价信息
   */
    @GetMapping("/getSelfFilterPage")
    public ResultEntity getSelfFilterPage(JobEvaluationSelfPageFo entity) {
      Page<JobEvaluationPageVo> page = jobEvaluationService.getSelfFilterPage(entity);
      return ResultEntity.success(page);
    }


}
