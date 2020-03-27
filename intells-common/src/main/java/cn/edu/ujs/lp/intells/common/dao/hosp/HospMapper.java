package cn.edu.ujs.lp.intells.common.dao.hosp;

import cn.edu.ujs.lp.intells.common.entity.Hosp.*;
import cn.edu.ujs.lp.intells.common.request.Hosp.HospPageRequest;
import cn.edu.ujs.lp.intells.common.entity.Hosp.HospBrief;
import cn.edu.ujs.lp.intells.common.entity.Hosp.HospMain;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 医院对象Mapper接口
 */
@Mapper
@Component
public interface HospMapper {

    /**
     * 依据医院ID或医院编码获取医院管理对象
     * @param Id
     * @param hospCode
     * @return
     */
    List<HospMain> getHospMainbyid(@Param("Id") String Id, @Param("hospCode") String hospCode);

    List<HospMain> getHospMainbyName(@Param("hospName") String hospName);

    /**
     * 依据医院ID或医院编码获取医院完整对象
     * @param Id
     * @param hospCode
     * @return
     */
    List<Hosp> getHospbyid(@Param("Id") String Id,@Param("hospCode") String hospCode);

    /**
     * 依据医院ID或医院编码删除医院对象
     * @param Id
     * @param hospCode
     */
    void delete(@Param("Id") String Id,@Param("hospCode") String hospCode);

    /**
     * 医院主要信息列表显示
     * @param request
     * @param bounds
     * @return
     */
    Page<HospBrief> page(HospPageRequest request, RowBounds bounds);

    List<Long> Rcount(HospPageRequest request);

    /**
     * 依据医院编码或医院ID获取医院参数列表
     * @param hospCode
     * @param hospId
     * @return
     */
    List<HospParamsBrief> listparams(@Param("hospCode") String hospCode,@Param("hospId") String hospId);

    /**
     * 依据记录ID获取医院参数对象列表
     * @param Id
     * @return
     */
    List<HospParams> getHospParamsbyid(@Param("Id") String Id, @Param("hospId") String hospId);

    /**
     * 依据记录ID或医院ID删除医院参数记录
     * @param Id
     * @param hospId
     */
    void deleteHospParams(@Param("Id") String Id,@Param("hospId") String hospId);

    /**
     * 依据记录ID或医院ID删除医院功能列表
     * @param Id
     * @param hospId
     */
    void deleteHospFunctionlist(@Param("Id") String Id,@Param("hospId") String hospId);

}
