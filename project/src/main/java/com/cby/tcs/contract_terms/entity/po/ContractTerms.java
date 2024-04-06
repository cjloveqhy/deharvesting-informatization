package com.cby.tcs.contract_terms.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("contract_terms")
@EqualsAndHashCode(callSuper = true)
public class ContractTerms extends Model<ContractTerms> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 条款类型
     */
    @TableField("type")
    private String type;

    /**
     * 富文本正文内容
     */
    @TableField("conent")
    private String conent;



}
