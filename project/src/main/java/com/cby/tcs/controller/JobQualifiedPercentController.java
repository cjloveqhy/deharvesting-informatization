package com.cby.tcs.controller;

import com.cby.tcs.job_qualified_percent.service.JobQualifiedPercentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
