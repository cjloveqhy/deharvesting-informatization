package com.cby.tcs.message.entity.fo;

import com.freedom.cloud.enums.LogicalEnum;
import com.freedom.cloud.enums.NotificationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MessageFo implements Serializable {

    /**
     * 通知接收者
     */
    @NotEmpty(message = "消息接收者不可设置为空")
    private List<String> users;

    /**
     * 通知内容
     */
    private Object data;

    /**
     * 通知类型，I-info:普通通知类型，S-success:成功通知类型，W-warning：警告通知类型，E-error:错误通知类型
     */
    @NotNull(message = "通知类型不可为空")
    private NotificationType notificationType = NotificationType.Default;

    /**
     * 是否为多选
     */
    @NotNull(message = "multiple属性不可为空")
    private LogicalEnum multiple = LogicalEnum.NO;

    /**
     * 重连间隔时常
     */
    private Double reconnectTime;

    /**
     * 事件名
     */
    private String eventName;

}
