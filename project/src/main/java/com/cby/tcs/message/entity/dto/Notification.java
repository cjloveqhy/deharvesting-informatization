package com.cby.tcs.message.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Notification implements Serializable {

    private String title;

    private String content;

    private String description;

    private Object meta;

    private double duration;

    private boolean keepAliveOnHover = false;

    private String avatarSrc;

}
