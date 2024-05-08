package com.cby.tcs.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.message.entity.fo.MessageFo;
import com.cby.tcs.message.entity.po.Message;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * <p>
 * 消息通知表 服务类
 * </p>
 *
 * @author cjloveqhy
 * @since 2024-04-09
 */
public interface MessageService extends IService<Message> {

    void pushSingle(Object data, String user);

    void pushSingle(Object data, String user, Double reconnectTime);

    void pushSingle(Object data, String user, Double reconnectTime, String eventName);

    void pushMultiple(Object data, List<String> users);

    void pushMultiple(Object data, List<String> users, Double reconnectTime);

    void pushMultiple(Object data, List<String> users, Double reconnectTime, String eventName);

    void push(MessageFo message);

    void pushSingleInfo(Object data, String user);

    void pushSingleInfo(Object data, String user, Double reconnectTime);

    void pushSingleInfo(Object data, String user, Double reconnectTime, String eventName);

    void pushMultipleInfo(Object data, List<String> users);

    void pushMultipleInfo(Object data, List<String> users, Double reconnectTime);

    void pushMultipleInfo(Object data, List<String> users, Double reconnectTime, String eventName);

    void pushInfo(MessageFo message);

    void pushSingleSuccess(Object data, String user);

    void pushSingleSuccess(Object data, String user, Double reconnectTime);

    void pushSingleSuccess(Object data, String user, Double reconnectTime, String eventName);

    void pushMultipleSuccess(Object data, List<String> users);

    void pushMultipleSuccess(Object data, List<String> users, Double reconnectTime);

    void pushMultipleSuccess(Object data, List<String> users, Double reconnectTime, String eventName);

    void pushSuccess(MessageFo message);

    void pushSingleWarning(Object data, String user);

    void pushSingleWarning(Object data, String user, Double reconnectTime);

    void pushSingleWarning(Object data, String user, Double reconnectTime, String eventName);

    void pushMultipleWarning(Object data, List<String> users);

    void pushMultipleWarning(Object data, List<String> users, Double reconnectTime);

    void pushMultipleWarning(Object data, List<String> users, Double reconnectTime, String eventName);

    void pushWarning(MessageFo message);

    void pushSingleError(Object data, String user);

    void pushSingleError(Object data, String user, Double reconnectTime);

    void pushSingleError(Object data, String user, Double reconnectTime, String eventName);

    void pushMultipleError(Object data, List<String> users);

    void pushMultipleError(Object data, List<String> users, Double reconnectTime);

    void pushMultipleError(Object data, List<String> users, Double reconnectTime, String eventName);

    void pushError(MessageFo message);

    SseEmitter pull(String userId);

}
