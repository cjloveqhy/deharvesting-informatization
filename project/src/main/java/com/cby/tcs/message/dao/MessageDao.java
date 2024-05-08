package com.cby.tcs.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cby.tcs.message.entity.po.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 消息通知表 Mapper 接口
 * </p>
 *
 * @author cjloveqhy
 * @since 2024-04-09
 */
@Mapper
public interface MessageDao extends BaseMapper<Message> {

}
