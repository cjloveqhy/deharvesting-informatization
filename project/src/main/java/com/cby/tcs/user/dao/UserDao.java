package com.cby.tcs.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cby.tcs.user.entity.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {

}
