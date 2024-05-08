package com.cby.tcs.controller;

import com.cby.tcs.message.entity.fo.MessageFo;
import com.cby.tcs.message.service.MessageService;
import com.freedom.cloud.annotation.EventMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * <p>
 * 消息通知表 前端控制器
 * </p>
 *
 * @author cjloveqhy
 * @since 2024-04-09
 */
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    /**
     * 发送消息通知
     */
    @PostMapping("/push")
    public void push(@Validated @RequestBody MessageFo message) {
      messageService.push(message);
    }

    /**
     * 推送消息通知
     */
    @EventMapping("/pull/{userId}")
    public SseEmitter pull(@PathVariable String userId) {
        SseEmitter sseEmitter = messageService.pull(userId);
        sseEmitter.complete();
        return sseEmitter;
    }

}
