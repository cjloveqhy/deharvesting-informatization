package com.cby.tcs.ginnery.entity.fo;

import com.freedom.cloud.utils.page.PageQuery;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GinneryPageFo extends PageQuery<GinneryPageFo> implements Serializable {

    private String factoryName;

}
