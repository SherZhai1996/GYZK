package cn.edu.ujs.lp.intells.basicinformation.request.Excompany;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class ExcompanySaveRequest {

    @ApiModelProperty(value = "物业公司ID,新增物业公司为null，否则为该物业公司ID值")
    private String id;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "物业公司名称，不能为空", required = true)
    @NotBlank(message = "公司名称非空")
    private String excompanyName; //公司名称',

    @ApiModelProperty(value = "法人代表姓名")
    private String excompanyLegal; //`法人代表',

    @ApiModelProperty(value = "物业公司负责人，即项目经理姓名，在已有物业职工的前提下可自动查找并填入负责人iD和电话")
    private String excompanyLeadername;  //负责人（项目经理）姓名',

    @ApiModelProperty(value = "项目经理电话")
    private String excompanyLeadertel; //` '负责人（项目经理）电话',

    @ApiModelProperty(value = "项目经理ID，在已有物业职工的前提下可以选择，并自动填入负责人姓名和电话")
    private String excompanyLeaderID;  //,

    @ApiModelProperty(value = "员工数量")
    private int excompanyStaffamount;  //员工数量',

    @ApiModelProperty(value = "服务起始时间:YYYY-MM-DD格式")
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date excompanyServicestartdate; //服务起始时间',

    @ApiModelProperty(value = "服务结束时间:YYYY-MM-DD格式")
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date excompanyServiceenddate; //服务结束时间',

    @ApiModelProperty(value = "投诉电话")
    private String excompanyComplainttel; //` 投诉电话',

    @ApiModelProperty(value = "服务简介")
    private String servicesRemark; //` varchar(400) NOT NULL COMMENT '服务简介',

    @ApiModelProperty(value = "公司地址")
    private String excompanyAddress; /// varchar(200) NOT NULL COMMENT '公司地址',

}
