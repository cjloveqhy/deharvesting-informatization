package com.cby.tcs.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.user.entity.po.User;
import com.cby.tcs.user.entity.vo.UserOption;
import com.cby.tcs.user_role.entity.dto.FilterPageUserDTO;
import com.cby.tcs.user_role.entity.fo.UserRolePage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {

    /**
     * 获取所有用户的信息作为可选项，以方便选择用户
     * @return {@link List}<{@link UserOption}>
     */
    List<UserOption> getUserOptions();

    /**
     * 分页过滤获取数据
     * @param page 分页模型
     * @param entity 过滤数据
     * @return {@link Page}<{@link FilterPageUserDTO}>
     */
    Page<FilterPageUserDTO> filterPage(Page page, @Param("params") UserRolePage entity);
}
