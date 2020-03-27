package cn.edu.ujs.lp.intells.common.dao;

import cn.edu.ujs.lp.intells.common.entity.Common.DataDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
各类数据字典读取类
 */
@Repository
public interface DataDictionaryRepository extends JpaRepository<DataDictionary,String> {

    /**
     * 基本信息-职工信息-员工性别
     * @return
     */
    @Query(value = "select id,name from db_sex", nativeQuery = true)
    List get_db_sex();

    /**
     * 基本信息-职工信息-民族
     * @return
     */
    @Query(value = "select id,name from db_nation", nativeQuery = true)
    List get_db_nation();

    /**
     * 基本信息-职工信息-行政职务
     * @return
     */
    @Query(value = "select id,name from db_staff_adminis_position", nativeQuery = true)
    List get_db_staff_adminis_position();

    /**
     * 基本信息-职工信息-技术职务
     * @return
     */
    @Query(value = "select id,name from db_staff_technical_position", nativeQuery = true)
    List get_db_staff_technical_position();

    /**
     * 基本信息-职工信息-教学职称
     * @return
     */
    @Query(value = "select id,name from db_staff_education_positon", nativeQuery = true)
    List get_db_staff_education_positon();

    /**
     * 基本信息-职工信息-学历
     * @return
     */
    @Query(value = "select id,name from db_staff_degree", nativeQuery = true)
    List get_db_staff_degree();

    /**
     * 基本信息-职工信息-导师类型
     * @return
     */
    @Query(value = "select id,name from db_staff_tutor_type", nativeQuery = true)
    List get_db_staff_tutor_type();

    /**
     * 基本信息-外委公司-服务部门(服务类别) 如：综合维修等
     * @return
     */
    @Query(value = "select id,name from db_extern_service_type", nativeQuery = true)
    List get_db_extern_service_type();

    /**
     * 医院基本信息-区域场所类别-保洁巡检-巡检模板-巡检类别
     * @return
     */
    @Query(value = "select id,name from db_grid_placeclass_type", nativeQuery = true)
    List get_db_grid_placeclass_type();

    /**
     * 医院基本信息-外委职工信息-技术工种类
     * 型
     * @return
     */
    @Query(value = "select id,name from db_technicalwork_type", nativeQuery = true)
    List get_db_technicalwork_type();

    /**
     * 医院基本信息-外委职工信息-技术证件类
     * 型
     * @return
     */
    @Query(value = "select id,name from db_skillcertificate_type", nativeQuery = true)
    List get_db_skillcertificate_type();

    /**
     * 医院基本信息-外委职工信息-餐饮工种证件类型
     * @return
     */
    @Query(value = "select id,name from db_restaurantcertificate_type", nativeQuery = true)
    List get_db_restaurantcertificate_type();

    /**
     * 医院基本信息-外委职工信息-婚姻状况
     * @return
     */
    @Query(value = "select id,name from db_marriage_state", nativeQuery = true)
    List get_db_marriage_state();

    /**
     * 医院基本信息-外委职工信息-岗位状态
     * （在职,离职）
     * @return
     */
    @Query(value = "select id,name from db_job_state", nativeQuery = true)
    List get_db_job_state();

    /**
     * 一站式服务--工单类型
     * @return
     */
    @Query(value = "select id,name from db_tasksheet_type", nativeQuery = true)
    List get_db_tasksheet_type();

    /**
     * 一站式服务-工单状态类型（申请、受理）
     * @return
     */
    @Query(value = "select id,name from db_tasksheetstate_type", nativeQuery = true)
    List get_db_tasksheetstate_type();

    /**
     * 一站式服务-工单跟踪状态类型
     * @return
     */
    @Query(value = "select id,name from db_tasksheettrackstate_type", nativeQuery = true)
    List get_db_tasksheettrackstate_type();

    /**
     * 一站式服务-工单紧急程度类型
     * @return
     */
    @Query(value = "select id,name from db_tasksheeturgencydegree_type", nativeQuery = true)
    List get_db_tasksheeturgencydegree_type();

    /**
     * 一站式服务--工单评价等级工单申报
     * 渠道类型（电话等）
     * @return
     */
    @Query(value = "select id,name from db_tasksheetdecchannel_type", nativeQuery = true)
    List get_db_tasksheetdecchannel_type();

    /**
     * 一站式服务--工单评价等级工单申报来源
     * 类型（巡检等）
     * @return
     */
    @Query(value = "select id,name from db_tasksheetdecsource_type", nativeQuery = true)
    List get_db_tasksheetdecsource_type();

    /**
     * 一站式服务--工单评价等级运送服务携带
     * 工具类型
     * @return
     */
    @Query(value = "select id,name from db_tasksheetcarryingtools_type", nativeQuery = true)
    List get_db_tasksheetcarryingtools_type();

    /**
     * 一站式服务工单响应时间等级
     * @return
     */
    @Query(value = "select id,name from db_tasksheetreponsetime_type", nativeQuery = true)
    List get_db_tasksheetreponsetime_type();

    /**
     * 一站式服务--工单评价等级
     * @return
     */
    @Query(value = "select id,name from db_tasksheeteval_type", nativeQuery = true)
    List get_db_tasksheeteval_type();

    /**
     * 医院基本信息-外委职工信息-工作状态
     * @return
     */
    @Query(value = "select id,name from db_workstate", nativeQuery = true)
    List get_db_workstate();

    /**
     * 设备管理-资产来源
     * @return
     */
    @Query(value = "select id,name from db_assetsource_type", nativeQuery = true)
    List get_db_assetsource_type();

    /**
     * 设备管理-设备使用状态数据字典
     * @return
     */
    @Query(value = "select id,name from db_use_status_type", nativeQuery = true)
    List get_db_use_status_type();

    /**
     * 设备管理 资产大类
     * @return
     */
    @Query(value = "select id,name from db_assetclass_type", nativeQuery = true)
    List get_db_assetclass_type();

    /**
     * 设备管理-资产小类数据字典
     * @return
     */
    @Query(value = "select id,name from db_assetsubclass_type", nativeQuery = true)
    List get_db_assetsubclass_type();

    /**
     * 设备管理-财务分类 资产重要程度分类
     * @return
     */
    @Query(value = "select id,name from db_assetfinancialclass_type", nativeQuery = true)
    List get_db_assetfinancialclass_type();

    /**
     * 设备管理 68分类
     * @return
     */
    @Query(value = "select id,name from db_assetclass68_type", nativeQuery = true)
    List get_db_assetclass68_type();

    /**
     * 设备管理-医疗器械分类数据字典
     * @return
     */
    @Query(value = "select id,name from db_medicalinstrumentsclass_type", nativeQuery = true)
    List get_db_medicalinstrumentsclass_type();

    /**
     * 设备管理-类别属性数据字典
     * @return
     */
    @Query(value = "select id,name from db_classattr_type", nativeQuery = true)
    List get_db_classattr_type();

    /**
     * 设备管理-资产大类数据字典-附属设备类 型
     * @return
     */
    @Query(value = "select id,name from db_accessoryclass_type", nativeQuery = true)
    List get_db_accessoryclass_type();

    /**
     * 设备管理-特种设备类型-数据字典
     * @return
     */
    @Query(value = "select id,name from db_specialclass_type", nativeQuery = true)
    List get_db_specialclass_type();

    /**
     * 设备管理-应急设备数据字典
     * @return
     */
    @Query(value = "select id,name from db_emergencyclass_type", nativeQuery = true)
    List get_db_emergencyclass_type();

    /**
     * 保洁巡检-巡检结果类型（正常不合格）
     * @return
     */
    @Query(value = "select id,name from db_inspection_conclusion_type", nativeQuery = true)
    List get_db_inspection_conclusion_type();

    /**
     * 保洁巡检-巡检周期类型
     * @return
     */
    @Query(value = "select id,name from db_inspection_period_type", nativeQuery = true)
    List get_db_inspection_period_type();

    /**
     * 设备保养周期类型
     * @return
     */
    @Query(value = "select id,name from db_devicemaintain_period_type", nativeQuery = true)
    List get_db_devicemaintain_period_type();

    /**
     * 设备巡检内容选项类型
     * @return
     */
    @Query(value = "select id,name from db_deviceinspectionitem_type", nativeQuery = true)
    List get_db_deviceinspectionitem_type();

    /**
     * 设备维保负责单位类型
     * @return
     */
    @Query(value = "select id,name from db_devicemaintainresponsibleunit_type", nativeQuery = true)
    List get_db_devicemaintainresponsibleunit_type();

    /**
     * 设备维保类型
     * @return
     */
    @Query(value = "select id,name from db_devicemaintain_type", nativeQuery = true)
    List get_db_devicemaintain_type();

    /**
     * 工单可信等级数据字典
     * @return
     */
    @Query(value = "select id,name from db_credible", nativeQuery = true)
    List get_db_credible();

    /**
     * 工巡检方式数据字典
     * @return
     */
    @Query(value = "select id,name from db_inspectionmode", nativeQuery = true)
    List get_db_inspectionmode();

    /**
     * 县市数据字典
     * @return
     */
    @Query(value = "select id,name from db_County", nativeQuery = true)
    List get_db_County();

    /**
     * 地市数据字典
     * @return
     */
    @Query(value = "select id,name from db_District", nativeQuery = true)
    List get_db_District();

    /**
     * 省份数据字典
     * @return
     */
    @Query(value = "select id,name from db_Province", nativeQuery = true)
    List get_db_Province();

}
