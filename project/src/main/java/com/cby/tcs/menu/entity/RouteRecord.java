package com.cby.tcs.menu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RouteRecord implements Serializable {

    @JsonIgnore
    private String id;

    private String path;

    private String name;

    private String component;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String redirect;

    private RouteMeta meta;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<RouteRecord> children = new ArrayList<>();

}
