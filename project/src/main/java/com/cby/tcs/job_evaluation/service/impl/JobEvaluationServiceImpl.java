package com.cby.tcs.job_evaluation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.cotton_field.entity.vo.CottonFieldVo;
import com.cby.tcs.cotton_field.service.CottonFieldService;
import com.cby.tcs.job_data.JobDataService;
import com.cby.tcs.job_evaluation.dao.JobEvaluationDao;
import com.cby.tcs.job_evaluation.entity.fo.JobEvaluationPageFo;
import com.cby.tcs.job_evaluation.entity.po.JobEvaluation;
import com.cby.tcs.job_evaluation.entity.vo.JobEvaluationPageVo;
import com.cby.tcs.job_evaluation.service.JobEvaluationService;
import com.freedom.cloud.utils.page.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 作业评价表 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor
public class JobEvaluationServiceImpl extends ServiceImpl<JobEvaluationDao, JobEvaluation> implements JobEvaluationService {

  private final JobEvaluationDao jobEvaluationDao;

  private final CottonFieldService cottonFieldService;

  private final JobDataService jobDataService;

  @Override
  public Page<JobEvaluationPageVo> getFilterPage(JobEvaluationPageFo entity) {
    Page<JobEvaluation> evaluationPage = jobEvaluationDao.getFilterPage(PageUtils.getPage(entity), entity);
    Page<JobEvaluationPageVo> voPage = new Page<>();
    if (evaluationPage.getRecords().isEmpty()) return voPage;
    BeanUtil.copyProperties(evaluationPage, voPage, "records");
    voPage.setRecords(new LinkedList<>());
    List<String> cottonFieldIds = evaluationPage.getRecords()
            .stream()
            .map(JobEvaluation::getCottonFieldId)
            .distinct()
            .toList();
    List<CottonFieldVo> fieldVoList = cottonFieldService.getCottonsByIds(cottonFieldIds);
    Map<String, CottonFieldVo> fieldVoMap = fieldVoList.stream().collect(Collectors.toMap(CottonFieldVo::getId, Function.identity()));
    Set<String> jobIds = evaluationPage.getRecords().stream().map(JobEvaluation::getJobId).collect(Collectors.toSet());
    Map<String, Object> infoMap = jobDataService.getInfo(entity.getJobType(), jobIds);
    for (JobEvaluation jobEvaluation : evaluationPage.getRecords()) {
      JobEvaluationPageVo jobEvaluationPageVo = BeanUtil.copyProperties(jobEvaluation, JobEvaluationPageVo.class);
      jobEvaluationPageVo.setCottonField(fieldVoMap.get(jobEvaluation.getCottonFieldId()))
              .setInfo(infoMap.get(jobEvaluation.getJobId()));
      voPage.getRecords().add(jobEvaluationPageVo);
    }
    return voPage;
  }
}
