package com.cby.tcs.ginnery.service.impl;

import com.cby.tcs.ginnery.entity.po.Ginnery;
import com.cby.tcs.ginnery.dao.GinneryDao;
import com.cby.tcs.ginnery.service.GinneryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GinneryServiceImpl extends ServiceImpl<GinneryDao, Ginnery> implements GinneryService {

  private final GinneryDao ginneryDao;
}
