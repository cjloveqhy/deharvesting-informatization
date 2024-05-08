package com.cby.tcs.message.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.exception.MessageException;
import com.cby.tcs.message.dao.MessageDao;
import com.cby.tcs.message.entity.dto.Notification;
import com.cby.tcs.message.entity.fo.MessageFo;
import com.cby.tcs.message.entity.po.Message;
import com.cby.tcs.message.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.cloud.enums.LogicalEnum;
import com.freedom.cloud.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>
 * 消息通知表 服务实现类
 * </p>
 *
 * @author cjloveqhy
 * @since 2024-04-09
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message> implements MessageService {

  private final MessageDao messageDao;

  private final ObjectMapper objectMapper;

  private final TransactionTemplate transactionTemplate;

  private final ConcurrentMap<String, LinkedList<SseEmitter.SseEventBuilder>> emitters = new ConcurrentHashMap<>();

  @Override
  public void pushSingle(Object data, String user) {
    pushSingle(data, user, null);
  }

  @Override
  public void pushSingle(Object data, String user, Double reconnectTime) {
    pushSingle(data, user, reconnectTime, null);
  }

  @Override
  public void pushSingle(Object data, String user, Double reconnectTime, String eventName) {
    push(new MessageFo(List.of(user), data, NotificationType.Default, LogicalEnum.NO, reconnectTime, eventName));
  }

  @Override
  public void pushMultiple(Object data, List<String> users) {
    pushMultiple(data, users, null);
  }

  @Override
  public void pushMultiple(Object data, List<String> users, Double reconnectTime) {
    pushMultiple(data, users, reconnectTime, null);
  }

  @Override
  public void pushMultiple(Object data, List<String> users, Double reconnectTime, String eventName) {
    push(new MessageFo(users, data, NotificationType.Default, LogicalEnum.YES, reconnectTime, eventName));
  }

  @Override
  public void push(MessageFo message) {
    create(message);
  }

  @Override
  public void pushSingleInfo(Object data, String user) { 
    pushSingleInfo(data, user, null);
  }

  @Override
  public void pushSingleInfo(Object data, String user, Double reconnectTime) { 
    pushSingleInfo(data, user, reconnectTime, null);
  }

  @Override
  public void pushSingleInfo(Object data, String user, Double reconnectTime, String eventName) {
    pushInfo(new MessageFo(List.of(user), data, NotificationType.Info, LogicalEnum.NO, reconnectTime, eventName));
  }

  @Override
  public void pushMultipleInfo(Object data, List<String> users) { 
    pushMultipleInfo(data, users, null);
  }

  @Override
  public void pushMultipleInfo(Object data, List<String> users, Double reconnectTime) { 
    pushMultipleInfo(data, users, reconnectTime, null);
  }

  @Override
  public void pushMultipleInfo(Object data, List<String> users, Double reconnectTime, String eventName) {
    pushInfo(new MessageFo(users, data, NotificationType.Info, LogicalEnum.YES, reconnectTime, eventName));
  }

  @Override
  public void pushInfo(MessageFo message) { 
    this.push(message);
  }

  @Override
  public void pushSingleSuccess(Object data, String user) { 
    pushSingleSuccess(data, user, null);
  }

  @Override
  public void pushSingleSuccess(Object data, String user, Double reconnectTime) { 
    pushSingleSuccess(data, user, reconnectTime, null);
  }

  @Override
  public void pushSingleSuccess(Object data, String user, Double reconnectTime, String eventName) {
    pushSuccess(new MessageFo(List.of(user), data, NotificationType.Success, LogicalEnum.NO, reconnectTime, eventName));
  }

  @Override
  public void pushMultipleSuccess(Object data, List<String> users) { 
    pushMultipleSuccess(data, users, null);
  }

  @Override
  public void pushMultipleSuccess(Object data, List<String> users, Double reconnectTime) { 
    pushMultipleSuccess(data, users, reconnectTime, null);
  }

  @Override
  public void pushMultipleSuccess(Object data, List<String> users, Double reconnectTime, String eventName) {
    pushSuccess(new MessageFo(users, data, NotificationType.Success, LogicalEnum.YES, reconnectTime, eventName));
  }

  @Override
  public void pushSuccess(MessageFo message) {
    this.push(message);
  }

  @Override
  public void pushSingleWarning(Object data, String user) { 
    pushSingleWarning(data, user, null);
  }

  @Override
  public void pushSingleWarning(Object data, String user, Double reconnectTime) { 
    pushSingleWarning(data, user, reconnectTime, null);
  }

  @Override
  public void pushSingleWarning(Object data, String user, Double reconnectTime, String eventName) {
    pushWarning(new MessageFo(List.of(user), data, NotificationType.Error, LogicalEnum.NO, reconnectTime, eventName));
  }

  @Override
  public void pushMultipleWarning(Object data, List<String> users) { 
    pushMultipleWarning(data, users, null);
  }

  @Override
  public void pushMultipleWarning(Object data, List<String> users, Double reconnectTime) { 
    pushMultipleWarning(data, users, reconnectTime, null);
  }

  @Override
  public void pushMultipleWarning(Object data, List<String> users, Double reconnectTime, String eventName) {
    pushWarning(new MessageFo(users, data, NotificationType.Warning, LogicalEnum.YES, reconnectTime, eventName));
  }

  @Override
  public void pushWarning(MessageFo message) {
    this.push(message);
  }

  @Override
  public void pushSingleError(Object data, String user) { 
    pushSingleError(data, user, null);
  }

  @Override
  public void pushSingleError(Object data, String user, Double reconnectTime) { 
    pushSingleError(data, user, reconnectTime, null);
  }

  @Override
  public void pushSingleError(Object data, String user, Double reconnectTime, String eventName) {
    pushError(new MessageFo(List.of(user), data, NotificationType.Error, LogicalEnum.NO, reconnectTime, eventName));
  }

  @Override
  public void pushMultipleError(Object data, List<String> users) { 
    pushMultipleError(data, users, null);
  }

  @Override
  public void pushMultipleError(Object data, List<String> users, Double reconnectTime) { 
    pushMultipleError(data, users, reconnectTime, null);
  }

  @Override
  public void pushMultipleError(Object data, List<String> users, Double reconnectTime, String eventName) {
    pushError(new MessageFo(users, data, NotificationType.Error, LogicalEnum.YES, reconnectTime, eventName));
  }

  @Override
  public void pushError(MessageFo message) {
    this.push(message);
  }

  @Override
  public SseEmitter pull(String userId) {
    SseEmitter sseEmitter = new SseEmitter();
    SseEmitter.SseEventBuilder eventBuilder = getSseEmitter(userId);
    if (Objects.nonNull(eventBuilder)) {
      try {
        sseEmitter.send(eventBuilder);
      } catch (IOException e) {
        throw new MessageException("消息发送失败，该消息不符合规则");
      }
    }
    return sseEmitter;
  }

  private void create(MessageFo message) {
    createEmitter(message);
    Message msg = new Message();
    msg.setCreator(StpUtil.getLoginIdAsString())
            .setEventName(message.getEventName())
            .setReconnectTime(message.getReconnectTime())
            .setNotificationType(message.getNotificationType())
            .setMultiple(message.getMultiple())
            .setUsers(ArrayUtil.join(message.getUsers().toArray(String[]::new), ","));
    if (message.getNotificationType().equals(NotificationType.Default)) {
      String value;
      try {
        value = objectMapper.writeValueAsString(message.getData());
      } catch (JsonProcessingException e) {
        value = message.getData().toString();
      }
      msg.setData(value);
    }
    transactionTemplate.execute((action) -> save(msg));
  }

  private void createEmitter(MessageFo message) {
    if (message.getMultiple().equals(LogicalEnum.YES) && Objects.nonNull(message.getUsers()) && !message.getUsers().isEmpty()) {
      for (String userId : message.getUsers()) {
        addEvent(message, userId);
      }
    } else {
      String userId = message.getUsers().get(0);
      addEvent(message, userId);
    }
  }

  public void addEvent(MessageFo message, String userId) {
    SseEmitter.SseEventBuilder sseEvent = createSseEvent(message, userId);
    if (!emitters.containsKey(userId)) {
      emitters.put(userId, new LinkedList<>());
    }
    LinkedList<SseEmitter.SseEventBuilder> events = emitters.get(userId);
    events.add(sseEvent);
  }

  private SseEmitter.SseEventBuilder createSseEvent(MessageFo message, String userId) {
    SseEmitter.SseEventBuilder event = SseEmitter.event();
    if (Objects.nonNull(message.getData())) {
      if (message.getNotificationType().equals(NotificationType.Default)) {
        event.data(message.getData());
      } else {
        event.data(createNotification(message.getNotificationType(), message.getData()));
      }
    }
    if (StrUtil.isNotBlank(message.getEventName())) {
      event.name(message.getEventName());
    }
    if (Objects.nonNull(message.getReconnectTime())) {
      event.reconnectTime(message.getReconnectTime().longValue());
    }
    event.id(userId);
    return event;
  }

  public Notification createNotification(NotificationType type, Object data) {
    Notification notification = new Notification();
    if (!type.equals(NotificationType.Default)) {
      try {
        Map<String, Object> map = objectMapper.convertValue(data, new TypeReference<>() {
        });
        if (map.containsKey("title")) {
          notification.setTitle(map.get("title").toString());
        } else {
          throw new MessageException("若使用通知，则必须要传递title属性");
        }
        if (map.containsKey("content")) {
          notification.setContent(map.get("content").toString());
        } else  {
          throw new MessageException("若使用通知，则必须要传递content属性");
        }
        if (map.containsKey("description")) {
          notification.setDescription(map.get("description").toString());
        }
        if (map.containsKey("meta")) {
          notification.setMeta(map.get("meta"));
        }
        if (map.containsKey("duration")) {
          notification.setDuration(Double.parseDouble(map.get("duration").toString()));
        }
        if (map.containsKey("avatarSrc")) {
          notification.setAvatarSrc(map.get("avatarSrc").toString());
        }
      } catch (IllegalArgumentException e) {
        throw new MessageException("数据设置失败，请检查是否包含title、content属性");
      }
    }
    return notification;
  }

  private SseEmitter.SseEventBuilder getSseEmitter(String userId) {
    if (emitters.containsKey(userId) && !emitters.get(userId).isEmpty()) {
      return emitters.get(userId).pop();
    }
    return null;
  }
}
