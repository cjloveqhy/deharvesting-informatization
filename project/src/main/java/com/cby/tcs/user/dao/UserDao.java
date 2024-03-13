package com.cby.tcs.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cby.tcs.user.entity.po.User;
import com.cby.tcs.user.entity.vo.UserOption;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {

    /**
     * 获取所有用户的信息作为可选项，以方便选择用户
     * @return {@link List}<{@link UserOption}>
     */
    List<UserOption> getUserOptions();
}
