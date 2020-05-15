package com.dream.payroll.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 津贴表
 * </p>
 *
 * @author wyn
 * @since 2020-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Benefit对象", description="津贴表")
public class Benefit implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "津贴id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "员工工号")
    private String employeeId;

    @ApiModelProperty(value = "加班总时长，按小时算")
    private Double overTime;

    @ApiModelProperty(value = "加班天数")
    private Integer overDay;

    @ApiModelProperty(value = "总津贴")
    private BigDecimal benefitMoney;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
