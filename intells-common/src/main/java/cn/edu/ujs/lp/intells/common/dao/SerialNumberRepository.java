package cn.edu.ujs.lp.intells.common.dao;

import cn.edu.ujs.lp.intells.common.entity.Common.SerialNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Repository
public interface SerialNumberRepository extends JpaRepository<SerialNumber,Integer> {

    /**
     * 依据医院ID获取医院Code
     * @param hospId
     * @return
     */
    @Query(value = "select hosp_code from tb_hospital_basic_information where id=(?1)", nativeQuery = true)
    List GetHospCodebyHospID(String hospId);

    /**
     * 复位指定类别的序号
     * @param typename
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "call ResetSerialNumber(?1)", nativeQuery = true)
    int reset(String typename);

    /**
     * 获取指定类别指定长度的序号
     * @param typename
     * @param codelen
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetSerialNumber(?1,?2) as sn", nativeQuery = true)
    List get(String typename, int codelen);

    /**
     * 获取投诉工单序列号
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetTSSerialNumber(?1) as sn", nativeQuery = true)
    List GetTSSerialNumber(String hospcode);

    /**
     * 获取应急保洁工单序列号
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetBJSerialNumber(?1) as sn", nativeQuery = true)
    List GetBJSerialNumber(String hospcode);

    /**
     * 获取综合维修工单序列号
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetZWSerialNumber(?1) as sn", nativeQuery = true)
    List GetZWSerialNumber(String hospcode);

    /**
     * 获取工勤搬运工单序列号
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetYSSerialNumber(?1) as sn", nativeQuery = true)
    List GetYSSerialNumber(String hospcode);

    /**
     * 获取保洁巡检工单序列号
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetBXSerialNumber(?1) as sn", nativeQuery = true)
    List GetBXSerialNumber(String hospcode);

    /**
     * 获取保安巡检工单序列号
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetBASerialNumber(?1) as sn", nativeQuery = true)
    List GetBASerialNumber(String hospcode);

    /**
     * 获取设备巡检工单序列号
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetSXSerialNumber(?1) as sn", nativeQuery = true)
    List GetSXSerialNumber(String hospcode);

    /**
     * 获取设备保养工单序列号
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetBYSerialNumber(?1) as sn", nativeQuery = true)
    List GetBYSerialNumber(String hospcode);

    /**
     * 获取设备维修工单序列号
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetWXSerialNumber(?1) as sn", nativeQuery = true)
    List GetWXSerialNumber(String hospcode);


    /**
     * 根据获取医院部门（科室）编码, 编码规则:DT(医院)nnnn(一级)DDDD(二级)LLLL(三级)QQQQ
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetDTSerialNumber(?1,?2) as sn", nativeQuery = true)
    List GetDTSerialNumber(String hospcode, String superCode);

    /**
     * 根据获取服务事项编码, 服务事项分类code，编码规则:ST(医院)nnnn(一级)DDDD(二级)LLLL(三级)QQQQ
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetSTSerialNumber(?1,?2) as sn", nativeQuery = true)
    List GetSTSerialNumber(String hospcode, String superCode);

    /**
     * 根据获取耗材编码, 耗材分类code，编码规则:MT(医院)nnnn(一级)DDDD(二级)LLLL(三级)QQQQ
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetMTSerialNumber(?1,?2) as sn", nativeQuery = true)
    List GetMTSerialNumber(String hospcode, String superCode);

    /**
     * 根据获取外委公司服务班组编码, 编码规则:ET(医院)nnnn(一级)DDDD(二级)LLLL(三级)QQQQ
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetETSerialNumber(?1,?2) as sn", nativeQuery = true)
    List GetETSerialNumber(String hospcode, String superCode);

    /**
     * 获取设备分类编码, 编码规则:SB(医院)nnnn(一级)DDD(二级)LLL(三级)QQQ
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetSBcategoryCode(?1,?2) as sn", nativeQuery = true)
    List GetSBcategoryCode(String hospcode, String superCode);

    /**
     * 获取设备明细编码, 编码规则:设备类别代码+nnnn(设备编号)
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetSBbillCode(?1,?2) as sn", nativeQuery = true)
    List GetSBbillCode(String hospcode, String categoryCode);

    /**
     * 获取设备分类编码, 编码规则:SB(医院)nnnn(一级)DDD(二级)LLL(三级)QQQ
     * @param hospcode
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "select GetGridCode(?1,?2) as sn", nativeQuery = true)
    List GetGridCode(String hospcode, String superCode);

}
