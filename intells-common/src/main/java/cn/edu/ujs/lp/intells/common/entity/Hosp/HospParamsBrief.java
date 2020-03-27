package cn.edu.ujs.lp.intells.common.entity.Hosp;

import lombok.Data;


/**
 * 医院参数除妖属性类
 */
@Data
public class HospParamsBrief {

    private String clearinspectionmode; //` varchar(32) NULL COMMENT '保洁巡检方式，(数据字典引用，db_inspectionmode)',

    private boolean isCenterdispatchedworker; //` tinyint(1) DEFAULT '0', '工单处理是否中心派工，缺省为班组派工',

    private boolean isCenterdispatchedworkerForDevice; //` tinyint(1) DEFAULT '0', '设备运维工单处理是否中心派工，缺省为班组派工',

    private boolean isCenterdispatchedworkerForClearinspection;//` tinyint(1) DEFAULT '0', '保洁巡检工单处理是否中心派工，缺省为班组派工',

    private boolean isCenterdispatchedworkerForSecurityinspection; //` tinyint(1) DEFAULT '0', '保安巡检工单处理是否中心派工，缺省为班组派工',

    private int dispatchedworkerinterval; //` int DEFAULT 30, '派工响应延时，单位分钟',

    private int evaluateinterval; //` int DEFAULT 48, '评价延时，单位小时',

    private String hospID; //` varchar(32) DEFAULT NULL COMMENT '医院ID（外键引用）',
}
