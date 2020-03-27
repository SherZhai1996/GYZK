package cn.edu.ujs.lp.intells.common.dao;

import cn.edu.ujs.lp.intells.common.entity.User.RoleBrief;
import cn.edu.ujs.lp.intells.common.entity.Device.DevicebillBrief;
import cn.edu.ujs.lp.intells.common.entity.Device.DevicecategoryBrief;
import cn.edu.ujs.lp.intells.common.entity.Device.IbeaconBrief;
import cn.edu.ujs.lp.intells.common.entity.Hosp.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 数据库公共操作类
 */
@Mapper
@Component
public interface CommonMapper {

    List<String> getIbeaconUUIDbygridId(@Param("gridId") String gridId);

    List<DevicecategoryBrief> getDevicecategories(@Param("hospId") String hospId, @Param("categoryName") String categoryName);

    List<DevicebillBrief> getDevicebills(@Param("hospId") String hospId, @Param("deviceCategory") String deviceCategory);

    List<IbeaconBrief> getIbeacons(@Param("hospId") String hospId, @Param("ibeaconName") String ibeaconName);

    /**依据网格区域ID设备运维班组名称
     *
     * @param gridId
     * @return
     */
    List<String> getDeviceExteamnamebyGridid(@Param("gridId") String gridId);

    List<String> getRoleIDbyName(@Param("roleName") String roleName);


    List<String> getRoleNamebyID(@Param("roleId") String roleId);

    /**
     * 获取系统用户角色列表列表
     * @return
     */
    List<RoleBrief> getSystemRole();

    /**
     * 获取医院职工角色列表列表
     * @return
     */
    List<RoleBrief> getStaffRole();

    /**
     * 获取外委职工角色列表列表
     * @return
     */
    List<RoleBrief> getExstaffRole();


    /**
     * 依据医院名称模糊过滤获取医院主要信息对象列表
     * @param hospName, 可以为null，模糊查找
     * @return
     */
    List<HospBrief> getHosps(@Param("hospName") String hospName);

    List<HospFunctionBrief> gethospFunctions();

    /**
     * 获取科室部门对象列表
     * @param hospId
     * @param deptName, 可以为null，模糊查找
     * @return
     */
    List<DepartmentBrief> getDepts(@Param("hospId") String hospId,@Param("deptName") String deptName);

    /**
     * 获取网格区域列表
     * @param hospId
     * @param deptId, 可以为null
     * @param superiorGridid， 可以为null
     * @param gridName， 剋有为努力了，模糊查找
     * @return
     */
    List<GridBrief> getGrids(@Param("hospId") String hospId, @Param("deptId") String deptId, @Param("superiorGridid") String superiorGridid, @Param("gridName") String gridName);

    /**
     * 依据医院ID和网格编码获取网格所有子节点对象
     * @param hospId
     * @param gridCode
     * @return
     */
    List<GridBrief> getallsubGrids(@Param("hospId") String hospId, @Param("gridCode") String gridCode);

    /**
     * 依据医院ID和网格ID获取网格下一级子节点对象
     * @param hospId
     * @param superiorGridid
     * @return
     */
    List<GridBrief> getsubGrids(@Param("hospId") String hospId, @Param("superiorGridid") String superiorGridid);

    /**
     * 依据医院ID和上级网格ID获取自己和下一级网格子对象列表
     * @param hospId
     * @param superiorGridid
     * @return
     */
    List<GridBrief> getselfandsubGrids(@Param("hospId") String hospId, @Param("superiorGridid") String superiorGridid);

    /**
     * 依据医院ID获取所有楼栋对象列表
     * @param hospId
     * @return
     */
    List<GridBrief> getBuilds(@Param("hospId") String hospId);

    /**
     * 依据网格功能类别获取网格列表
     * @param hospId
     * @param gridPlaceclassid
     * @param gridName
     * @return
     */
    List<GridBrief> getGridsbyPlaceclass(@Param("hospId") String hospId, @Param("gridPlaceclassid") String gridPlaceclassid, @Param("gridName") String gridName);

    /**
     * 依据网格ID获取该网格的服务班组ID列表
     * @param hospId
     * @param gridId
     * @return
     */
    List<String> getGridExteams(@Param("gridId") String gridId);
    /**
     * 获取在职员工列表
     * @param hospId
     * @param deptId， 可以为null
     * @param staffName， 可以为null，模糊查找
     * @return
     */
    List<StaffBrief> getStaffs(@Param("hospId") String hospId, @Param("deptId") String deptId, @Param("staffName") String staffName);

    /**
     * 获取外委公司列表
     * @param hospId
     * @return
     */
    List<ExcompanyBrief> getExcompanys(@Param("hospId") String hospId);

    /**
     * 获取业务部门
     * @param hospId
     * @param excompanyId，可以为null
     * @return
     */
    List<ExServicesBrief> getExservices(@Param("hospId") String hospId, @Param("excompanyId") String excompanyId);

    /**
     * 获取服务班组列表
     * @param hospId
     * @param excompanyId， 可以为null
     * @param exservicesId， 可以为null
     * @return
     */
    List<ExteamBrief> getExteams(@Param("hospId") String hospId,@Param("excompanyId") String excompanyId, @Param("exservicesId") String exservicesId);

    /**
     * 获取外委员工列表
     * @param hospId
     * @param excompanyId ，可以为null
     * @param exteamId, 可以为null
     * @return
     */
    List<ExstaffBrief> getExstaffs(@Param("hospId") String hospId,@Param("excompanyId") String excompanyId, @Param("exteamId") String exteamId);

    List<String> getRolenamebyId(@Param("Id") String Id);
    List<String> getRoleidbyName(@Param("roleName") String roleName);

    List<String> getHospFunctionidbyName(@Param("functionName") String functionName);
    List<String> getHospFunctionnamebyId(@Param("Id") String Id);

    List<String> getHospCodebyId(@Param("Id") String Id);
    List<String> getHospNamebyId(@Param("Id") String Id);
    List<String> getHospservecentertelbyId(@Param("Id") String Id);

    //可以模糊查询
    List<String> getHospIdbyName(@Param("hospName") String hospName);


    List<String> getDeptFullnamebyId(@Param("Id") String Id);
    //可以模糊查询
    List<String> getDeptIdbyFullname(@Param("hospId") String hospId,@Param("deptFullname") String deptFullname);


    List<String> getGridCodebyId(@Param("Id") String Id);
    List<String> getGridIdbyCode(@Param("gridCode") String gridCode);
    List<String> getGridFullnamebyId(@Param("Id") String Id);
    //依据网格ID获取网格全路径ID列表
    List<String> getGridPathbyId(@Param("gridId") String gridId);
    //可以模糊查询
    List<String> getGridIdbyFullname(@Param("hospId") String hospId, @Param("gridFullname") String gridFullname);


    //可以模糊查找
    List<String> getStaffIdbyName(@Param("hospId") String hospId, @Param("deptId") String deptId, @Param("staffName") String staffName);
    List<String> getStaffIdbyFullname(@Param("hospId") String hospId, @Param("fullName") String fullName);
    List<String> getStaffFullnamebyId(@Param("staffId") String staffId);
    List<String> getStaffnamebyId(@Param("staffId") String staffId);

    List<String> getExcompanyNamebyId(@Param("Id") String Id);
    //可以模糊查找
    List<String> getExcompanyIdbyName(@Param("hospId") String hospId, @Param("excompanyName") String excompanyName);


    List<String> getExcompanyservicesNamebyId(@Param("Id") String Id);
    //名字可以模糊查找
    List<String> getExcompanyservicesIdbyName(@Param("hospId") String hospId, @Param("excompanyId") String excompanyId, @Param("exserviceName") String exserviceName);

    /**
     * 依据服务班组ID获取服务班组全名
     * @param Id
     * @return
     */
    List<String> getExteamFullnamebyId(@Param("Id") String Id);
    //依据服务班组ID获取包含服务公司和服务部门的服务班组全名
    List<String> getExteamFullpathnamebyId(@Param("Id") String Id);
    //名字可以模糊查找
    List<String> getExteamIdbyFullName(@Param("hospId") String hospId, @Param("exteamFullname") String exteamFullname);
    List<String> getExteamIdbyName(@Param("hospId") String hospId, @Param("excompanyId") String excompanyId, @Param("exteamName") String exteamName);


    List<String> getExteamnamebygridid(@Param("gridId") String gridId);


    //可以模糊查找
    List<String> getExstaffIdbyName(@Param("hospId") String hospId, @Param("excompanyId") String excompanyId, @Param("exteamId") String exteamId, @Param("exstaffName") String exstaffName);
    //依据职工ID获取外委公司名称+服务班组名称+职工姓名
    List<String> getExstaffFullnamebyId(@Param("exstaffId") String exstaffId);
    List<String> getExstaffnamebyId(@Param("exstaffId") String exstaffId);


    List<String> getDevicecategoryCodebyId(@Param("Id") String Id);
    List<String> getDevicecategoryIdbyFullname(@Param("hospId") String hospId, @Param("categoryFullname") String categoryFullname);
    List<String> getDevicecategoryFullnamebyId(@Param("Id") String Id);

    List<String> getDeviceNamebyId(@Param("Id") String Id);
    List<String> getDeviceIdbyName(@Param("hospId") String hospId, @Param("deviceCategory") String deviceCategory, @Param("assetName") String assetName);

    //依据网格编号获取设备责任班组列表
    List<String> getDevicetaskownerIDbyGridcode(@Param("hospId") String hospId, @Param("gridCode") String gridCode);
}
