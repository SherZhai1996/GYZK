package cn.edu.ujs.lp.intells.common.request.Hosp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

/**
 * 医院参数保存信息
 */
@Data
public class HospParamsSaveRequest {

    @ApiModelProperty(value = "保洁巡检方式，不能为空", required = true)
    @NotBlank(message = "保洁巡检方式非空")
    private String clearinspectionmode; //` varchar(32) NULL COMMENT '保洁巡检方式，(数据字典引用，db_inspectionmode)',

    @ApiModelProperty(value = "工单处理是否中心派工，不能为空", required = true)
    private boolean isCenterdispatchedworker; //` tinyint(1) DEFAULT '0', '工单处理是否中心派工，缺省为班组派工',

    @ApiModelProperty(value = "设备运维工单处理是否中心派工，不能为空", required = true)
    private boolean isCenterdispatchedworkerForDevice; //` tinyint(1) DEFAULT '0', '设备运维工单处理是否中心派工，缺省为班组派工',

    @ApiModelProperty(value = "保洁巡检工单处理是否中心派工，不能为空", required = true)
    private boolean isCenterdispatchedworkerForClearinspection;//` tinyint(1) DEFAULT '0', '保洁巡检工单处理是否中心派工，缺省为班组派工',

    @ApiModelProperty(value = "保安巡检工单处理是否中心派工，不能为空", required = true)
    private boolean isCenterdispatchedworkerForSecurityinspection; //` tinyint(1) DEFAULT '0', '保安巡检工单处理是否中心派工，缺省为班组派工',

    @ApiModelProperty(value = "派工响应延时，单位分钟，不能为空", required = true)
    private int dispatchedworkerinterval; //` int DEFAULT 30, '派工响应延时，单位分钟',

    @ApiModelProperty(value = "评价延时，单位小时，不能为空", required = true)
    private int evaluateinterval; //` int DEFAULT 48, '评价延时，单位小时',

    @ApiModelProperty(value = "医院ID，不能为空", required = true)
    @NotBlank(message = "医院ID非空")
    private String hospID; //` varchar(32) DEFAULT NULL COMMENT '医院ID（外键引用）',
}
