package cn.edu.ujs.lp.intells.basicinformation.dao.Device;

import cn.edu.ujs.lp.intells.basicinformation.entity.Device.Devicecategory;
import cn.edu.ujs.lp.intells.common.entity.Device.DevicecategoryBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.Device.DevicecategoryPageRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 设备类别Mapper类
 */
@Mapper
@Component
public interface DevicecategoryMapper {

    List<Long> Rcount(DevicecategoryPageRequest request);
    List<Devicecategory> getbyid(@Param("id") String id);
    List<Devicecategory> findByName(@Param("hospId") String hospId, @Param("superiorId") String superiorId, @Param("categoryName") String deviceCategorye);
    void deletebyCode(@Param("categoryCode") String categoryCode);
    Page<DevicecategoryBrief> page(DevicecategoryPageRequest request, RowBounds bounds);
}
