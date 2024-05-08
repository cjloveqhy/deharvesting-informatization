package com.cby.tcs.message.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.freedom.cloud.enums.LogicalEnum;
import com.freedom.cloud.enums.NotificationType;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 消息通知表
 * </p>
 *
 * @author cjloveqhy
 * @since 2024-04-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("message")
@EqualsAndHashCode(callSuper = true)
public class Message extends Model<Message> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 通知接收者
     */
    @TableField("users")
    private String users;

    /**
     * 通知创建者
     */
    @TableField("creator")
    private String creator;

    /**
     * 通知内容
     */
    @TableField("data")
    private String data;

    /**
     * 通知类型，I-info:普通通知类型，S-success:成功通知类型，W-warning：警告通知类型，E-error:错误通知类型，D-default:默认通知类型，该类型将不会隐式转换成Notification
     */
    @TableField("notification_type")
    private NotificationType notificationType;

    /**
     * 是否为多选
     */
    @TableField("multiple")
    private LogicalEnum multiple;

    /**
     * 重连间隔时常
     */
    @TableField("reconnect_time")
    private Double reconnectTime;

    /**
     * 事件名
     */
    @TableField("event_name")
    private String eventName;

    /**
     *逻辑删除
     */
    @TableLogic("deleted")
    private Integer deleted;

    /**
     *更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     *创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
