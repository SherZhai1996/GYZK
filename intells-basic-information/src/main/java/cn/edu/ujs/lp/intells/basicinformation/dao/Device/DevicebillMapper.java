package cn.edu.ujs.lp.intells.basicinformation.dao.Device;

import cn.edu.ujs.lp.intells.basicinformation.entity.Device.Devicebill;
import cn.edu.ujs.lp.intells.common.entity.Device.DevicebillBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.Device.DevicebillPageRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 设备明细Mapper类
 */
@Mapper
@Component
public interface DevicebillMapper {

    List<Long> Rcount(DevicebillPageRequest request);
    List<Devicebill> getbyid(@Param("id") String id);
    List<Devicebill> findByCategoryandName(@Param("hospId") String hospId, @Param("deviceCategory") String deviceCategory, @Param("assetName") String assetName);
    void delete(@Param("devicebillid") String devicebillid);
    Page<DevicebillBrief> page(DevicebillPageRequest request, RowBounds bounds);
}
