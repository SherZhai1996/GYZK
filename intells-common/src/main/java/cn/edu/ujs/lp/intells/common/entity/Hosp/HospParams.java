package cn.edu.ujs.lp.intells.common.entity.Hosp;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 医院参数类
 */
@Entity
@Table(name = "tb_hosp_params")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospParams   extends BaseEntity {

    @Column(name = "clearinspectionmode")
    private String clearinspectionmode; //` varchar(32) NULL COMMENT '保洁巡检方式，(数据字典引用，db_inspectionmode)',

    @Column(name = "isCenterdispatchedworker")
	private boolean isCenterdispatchedworker; //` tinyint(1) DEFAULT '0', '工单处理是否中心派工，缺省为班组派工',

    @Column(name = "isCenterdispatchedworkerForDevice")
    private boolean isCenterdispatchedworkerForDevice; //` tinyint(1) DEFAULT '0', '设备运维工单处理是否中心派工，缺省为班组派工',

    @Column(name = "isCenterdispatchedworkerForClearinspection")
    private boolean isCenterdispatchedworkerForClearinspection;//` tinyint(1) DEFAULT '0', '保洁巡检工单处理是否中心派工，缺省为班组派工',

    @Column(name = "isCenterdispatchedworkerForSecurityinspection")
    private boolean isCenterdispatchedworkerForSecurityinspection; //` tinyint(1) DEFAULT '0', '保安巡检工单处理是否中心派工，缺省为班组派工',

    @Column(name = "dispatchedworkerinterval")
	private int dispatchedworkerinterval; //` int DEFAULT 30, '派工响应延时，单位分钟',

    @Column(name = "evaluateinterval")
	private int evaluateinterval; //` int DEFAULT 48, '评价延时，单位小时',

    @Column(name = "hosp_id")
	private String hospID; //` varchar(32) DEFAULT NULL COMMENT '医院ID（外键引用）',
}
