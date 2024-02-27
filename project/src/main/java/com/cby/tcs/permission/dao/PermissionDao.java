package com.cby.tcs.permission.dao;

import com.cby.tcs.permission.entity.po.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionDao extends BaseMapper<Permission> {

}
