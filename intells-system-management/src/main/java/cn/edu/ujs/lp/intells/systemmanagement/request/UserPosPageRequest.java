package cn.edu.ujs.lp.intells.systemmanagement.request;

import cn.edu.ujs.lp.intells.common.request.PageRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * 用户位置分页查询
 *
 * @author Meredith
 * @date 2019-09-17
 */
@Data
public class UserPosPageRequest extends PageRequest {

    /**
     * 医院ID
     */
    @ApiModelProperty(value = "医院ID，可以为null")
    private String hospId;

    /**
     * 省
     */
    @ApiModelProperty(value = "网格区域全名，模糊查询，可以为null")
    private String gridFullname;

    @ApiModelProperty(value = "物业公司ID，可以为null")
    private String excompanyId;

    @ApiModelProperty(value = "服务班组ID，可以为null")
    private String exteamId;

    @ApiModelProperty(value = "物业职工名称，可以为null")
    private String exstaffName;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "开始时间，yyyy-MM-dd HH:mm:ss")
    private Date startDatetime;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "结束时间，yyyy-MM-dd HH:mm:ss")
    private Date endDatetime;


}
