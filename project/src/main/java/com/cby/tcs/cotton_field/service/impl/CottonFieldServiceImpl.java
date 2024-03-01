package com.cby.tcs.cotton_field.service.impl;

import com.cby.tcs.cotton_field.entity.po.CottonField;
import com.cby.tcs.cotton_field.dao.CottonFieldDao;
import com.cby.tcs.cotton_field.service.CottonFieldService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CottonFieldServiceImpl extends ServiceImpl<CottonFieldDao, CottonField> implements CottonFieldService {

  private final CottonFieldDao cotton_fieldDao;
}
