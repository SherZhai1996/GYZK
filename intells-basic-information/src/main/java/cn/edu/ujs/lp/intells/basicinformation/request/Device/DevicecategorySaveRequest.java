package cn.edu.ujs.lp.intells.basicinformation.request.Device;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设备分类保存参数类
 */
@Data
public class DevicecategorySaveRequest {

    @ApiModelProperty(value = "设备分类ID,新增设备分类为null，否则为该设备分类ID值")
    private String id;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称，同一级别不能重名")
    private String categoryName;

    /**
     * 上级分类id(外键引用),第一级外键为null
     */
    @ApiModelProperty(value = "上级分类id(外键引用),第一级外键为null")
    private String superiorId;

    /**
     * 在用标志
     */
    @ApiModelProperty(value = "在用标志")
    private String isUsing;

    /**
     * 管理归属（外键关联，关联医院后勤相关职工）
     */
    @ApiModelProperty(value = "管理归属（外键关联，关联医院后勤相关职工）")
    private String manageAffiliation;

    /**
     * 责任归属(外键关联，关联外委公司)
     */
    @ApiModelProperty(value = "责任归属(外键关联，关联外委公司)")
    private String responAffiliation;

}
