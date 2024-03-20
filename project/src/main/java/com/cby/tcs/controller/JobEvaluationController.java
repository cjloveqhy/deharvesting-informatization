package com.cby.tcs.controller;

import com.cby.tcs.job_evaluation.service.JobEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
