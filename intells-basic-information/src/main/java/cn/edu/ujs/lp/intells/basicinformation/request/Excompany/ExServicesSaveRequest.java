package cn.edu.ujs.lp.intells.basicinformation.request.Excompany;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ExServicesSaveRequest {

    @ApiModelProperty(value = "业务部门ID,新增业务部门为null，否则为该业务部门ID值")
    private String id;

    @ApiModelProperty(value = "业务部门名称，非空", required = true)
    @NotBlank(message = "业务部门名称非空")
    private String exserviceName; //业务部门名称',

    @ApiModelProperty(value = "外委公司ID")
    private String excompanyId; //所属外委公司ID',

    @ApiModelProperty(value = "服务类型ID，数据字典选取")
    private String serviceId; //服务类型ID',

    @ApiModelProperty(value = "业务主管姓名，在已有外委职工的前提下可自动查找并填入负责人iD和电话")
    private String leaderName; //业务主管姓名',

    @ApiModelProperty(value = "业务主管电话")
    private String leaderTel; //业务主管电话',

    @ApiModelProperty(value = "业务主管ID, 在已有外委职工的前提下可以选择，并自动填入主管姓名和电话")
    private String leaderID; //业务主管ID',

}
