package com.cby.tcs.job_evaluation.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.cotton_field.dao.CottonFieldDao;
import com.cby.tcs.cotton_field.entity.po.CottonField;
import com.cby.tcs.cotton_field.entity.vo.CottonFieldVo;
import com.cby.tcs.cotton_field.service.CottonFieldService;
import com.cby.tcs.exception.JobEvaluationException;
import com.cby.tcs.farm_cotton_picker.dao.FarmCottonPickerDao;
import com.cby.tcs.farm_cotton_picker.entity.po.FarmCottonPicker;
import com.cby.tcs.farm_uav.dao.FarmUavDao;
import com.cby.tcs.farm_uav.entity.po.FarmUav;
import com.cby.tcs.job_data.JobDataService;
import com.cby.tcs.job_evaluation.dao.JobEvaluationDao;
import com.cby.tcs.job_evaluation.entity.enums.JobType;
import com.cby.tcs.job_evaluation.entity.fo.AddJobEvaluationFo;
import com.cby.tcs.job_evaluation.entity.fo.JobEvaluationPageFo;
import com.cby.tcs.job_evaluation.entity.fo.JobEvaluationSelfPageFo;
import com.cby.tcs.job_evaluation.entity.po.JobEvaluation;
import com.cby.tcs.job_evaluation.entity.vo.AddOptionJobEvaluationVo;
import com.cby.tcs.job_evaluation.entity.vo.JobEvaluationPageVo;
import com.cby.tcs.job_evaluation.service.JobEvaluationService;
import com.cby.tcs.user.dao.UserDao;
import com.cby.tcs.user.entity.po.User;
import com.freedom.cloud.options.Option;
import com.freedom.cloud.utils.page.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    private final FarmCottonPickerDao farmCottonPickerDao;

    private final UserDao userDao;

    private final CottonFieldDao cottonFieldDao;

    private final FarmUavDao farmUavDao;

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

    @Override
    public Page<JobEvaluationPageVo> getSelfFilterPage(JobEvaluationSelfPageFo entity) {
        Page<JobEvaluationPageVo> voPage = new Page<>();
        if (!StpUtil.getLoginId().equals("9f2eb890e42791569fedb3c7b38f6123")) {
            if (jobEvaluationDao.selectCount(new LambdaQueryWrapper<JobEvaluation>()
                    .eq(JobEvaluation::getJobId, StpUtil.getLoginId())) > 0){
                entity.setJobType(JobType.Uav);
                entity.setJobIds(Arrays.asList(StpUtil.getLoginId().toString()));
            } else {
                entity.setJobType(JobType.Cotton_Picker);
                LambdaQueryWrapper<FarmCottonPicker> farmCottonPickerWrapper = new LambdaQueryWrapper<>();
                List<String> rackNumberList = farmCottonPickerDao
                        .selectList(farmCottonPickerWrapper.eq(FarmCottonPicker::getBelonger, StpUtil.getLoginId()))
                        .stream()
                        .map(FarmCottonPicker::getRackNumber)
                        .collect(Collectors.toList());
                entity.setJobIds(rackNumberList);
            }
        }
        if (entity.getJobIds().isEmpty()) return voPage;
        Page<JobEvaluation> evaluationPage = jobEvaluationDao.getSelfFilterPage(PageUtils.getPage(entity), entity);
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

    @Override
    public void add(AddJobEvaluationFo entity) {
        if (jobEvaluationDao.selectCount(new LambdaQueryWrapper<JobEvaluation>().eq(JobEvaluation::getOrderId, entity.getOrderId())) > 0){
            throw new JobEvaluationException("该订单号为：%s的评价信息已存在", entity.getOrderId());
        }
        JobEvaluation jobEvaluation = new JobEvaluation();
        jobEvaluation.setOrderId(entity.getOrderId()).setJobType(entity.getJobType())
                .setJobId(entity.getJobId()).setCottonFieldId(entity.getCottonFieldId())
                .setWorkTime(LocalDateTime.parse(entity.getWorkTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .setEvaluationResult(entity.getEvaluationResult()).setDeleted(0);
        jobEvaluationDao.insert(jobEvaluation);
    }

    @Override
    public AddOptionJobEvaluationVo getAddOption() {
        // 获取地块option
        List<Option> plotNameOption = cottonFieldDao.selectList(new LambdaQueryWrapper<CottonField>()
                        .select(CottonField::getId, CottonField::getPlotName)).stream()
                .map(item -> new Option().setLabel(item.getPlotName()).setValue(item.getId()))
                .toList();
        // 获取用户option
        List<Option> userOption = userDao.selectList(new LambdaQueryWrapper<User>()
                        .select(User::getId, User::getUsername)).stream()
                .map(item -> new Option().setLabel(item.getUsername()).setValue(item.getId()))
                .toList();

        // 获取飞手option
        List<String> farmUavIdList = farmUavDao.selectList(new LambdaQueryWrapper<FarmUav>()
                        .select(FarmUav::getBelonger))
                .stream()
                .map(FarmUav::getBelonger)
                .distinct()
                .toList();
        List<User> farmUavUserList = userDao.selectBatchIds(farmUavIdList);
        List<Option> farmUavOption = farmUavUserList.stream()
                .map(item -> new Option().setLabel(item.getUsername()).setValue(item.getId()))
                .toList();

        // 获取采棉机option
        List<FarmCottonPicker> farmCottonFieldList = farmCottonPickerDao.selectList(new LambdaQueryWrapper<FarmCottonPicker>()
                        .select(FarmCottonPicker::getRackNumber));
        List<Option> farmCottonPickerOption = farmCottonFieldList.stream()
                .map(item -> new Option().setLabel(item.getRackNumber()).setValue(item.getRackNumber()))
                .toList();
        AddOptionJobEvaluationVo addOptionJobEvaluationVo = new AddOptionJobEvaluationVo();
        addOptionJobEvaluationVo.setPlotNameOption(plotNameOption).setUserNameOption(userOption)
                .setUavNameOption(farmUavOption).setCottonFieldNameOption(farmCottonPickerOption);
        return addOptionJobEvaluationVo;
    }
}
