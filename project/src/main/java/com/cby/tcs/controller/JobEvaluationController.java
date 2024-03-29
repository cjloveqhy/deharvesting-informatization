package com.cby.tcs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.job_evaluation.entity.fo.AddJobEvaluationFo;
import com.cby.tcs.job_evaluation.entity.fo.JobEvaluationPageFo;
import com.cby.tcs.job_evaluation.entity.fo.JobEvaluationSelfPageFo;
import com.cby.tcs.job_evaluation.entity.vo.AddOptionJobEvaluationVo;
import com.cby.tcs.job_evaluation.entity.vo.JobEvaluationPageVo;
import com.cby.tcs.job_evaluation.service.JobEvaluationService;
import com.freedom.cloud.annotation.ParamsToEntity;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

  /**
   * 添加评价信息
   */
  @PostMapping("/add")
  public ResultEntity add(@RequestBody AddJobEvaluationFo entity) {
    jobEvaluationService.add(entity);
    return ResultEntity.success("添加成功");
  }

  /**
   * 获取添加评价信息表单中的option
   */
  @GetMapping("/getAddOption")
  public ResultEntity getAddOption() {
    AddOptionJobEvaluationVo addOptions = jobEvaluationService.getAddOption();
    return ResultEntity.success(addOptions);
  }
}


