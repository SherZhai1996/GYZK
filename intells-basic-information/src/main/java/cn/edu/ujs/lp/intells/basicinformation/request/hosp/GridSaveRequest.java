package cn.edu.ujs.lp.intells.basicinformation.request.hosp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 网格保存参数
 */
@Data
public class GridSaveRequest {

    @ApiModelProperty(value = "网格区域ID,新增网格为null，否则为该网格ID值")
    private String id;

    /**
     * 网格区域名称
     */
    @ApiModelProperty(value = "网格区域名称，不能为空", required = true)
    @NotBlank(message = "网格区域名称非空")
    private String gridName;

    /**
     * 上一级网格ID
     */
    @ApiModelProperty(value = "上一级网格ID", required = true)
    private String superiorGridid;

    /**
     * 网格区域负责人
     */
    @ApiModelProperty(value = "网格区域负责人ID", required = true)
    private String gridLeaderID;

    /**
     * 网格所在楼栋ID(网格ID)
     */
    @ApiModelProperty(value = "网格所在楼栋ID", required = true)
    private String remark;

    /**
     * 网格区域面积
     */
    @ApiModelProperty(value = "网格区域面积")
    private double gridArea;

    /**
     * 网格区域用途ID
     */
    @ApiModelProperty(value = "网格区域用途ID")
    private String gridPlaceclassid;

    /**
     * 网格区域所属科室ID
     */
    @ApiModelProperty(value = "网格区域所属科室ID")
    private String deptId;

    @ApiModelProperty(value = "网格区域中心点经度")
    private double gridcenterX;

    @ApiModelProperty(value = "网格区域中心点纬度")
    private double gridcenterY;

    @ApiModelProperty(value = "网格区域半径（米）")
    private int gridradius;

    /**
     * 网格区域电话列表
     */
    @ApiModelProperty(value = "网格区域电话列表")
    private List<String> gridTels;
    //private List<GridTelBrief> gridTels;

    /**
     * 网格区域服务班组列表
     */
    @ApiModelProperty(value = "网格区域服务班组ID列表")
    private List<String> gridExteams;

}
