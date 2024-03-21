package com.cby.tcs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.job_evaluation.entity.enums.JobType;
import com.cby.tcs.job_qualified_percent.entity.fo.JobQualifiedPercentPageFo;
import com.cby.tcs.job_qualified_percent.entity.vo.JobQualifiedPercentPageVo;
import com.cby.tcs.job_qualified_percent.service.JobQualifiedPercentService;
import com.freedom.cloud.annotation.ParamsToEntity;
import com.freedom.cloud.options.Option;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 作业合格率总览表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/job-qualified-percent")
@RequiredArgsConstructor
public class JobQualifiedPercentController {

  private final JobQualifiedPercentService jobQualifiedPercentService;

  /**
   * 分页获取数据
   */
  @GetMapping("/page")
  public ResultEntity getPageData(@ParamsToEntity(required = false, params = {@RequestParam("jobType")}) JobQualifiedPercentPageFo entity) {
    Page<JobQualifiedPercentPageVo> pageVo = jobQualifiedPercentService.getPageData(entity);
    return ResultEntity.success(pageVo);
  }

  /**
   * 综合合格率
   */
  @GetMapping("/passRate")
  public ResultEntity passRate(@RequestParam JobType type) {

    return ResultEntity.success();
  }

  /**
   * 月作业评价结果
   */
  @GetMapping("/monthJobEvaluation")
  public ResultEntity monthJobEvaluation(@RequestParam JobType type, @RequestParam(required = false) LocalDate month) {

    return ResultEntity.success();
  }

  /**
   * 合格率排行TOP3
   */
  @GetMapping("/passRateRanking")
  public ResultEntity passRateRanking(@RequestParam JobType type) {
    List<Option<Double>> options = jobQualifiedPercentService.passRateRanking(type);
    return ResultEntity.success(options);
  }

}
