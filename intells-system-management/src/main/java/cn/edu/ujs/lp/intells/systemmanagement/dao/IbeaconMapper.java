package cn.edu.ujs.lp.intells.systemmanagement.dao;

import cn.edu.ujs.lp.intells.common.entity.Device.IbeaconBrief;
import cn.edu.ujs.lp.intells.systemmanagement.entity.Ibeacon;
import cn.edu.ujs.lp.intells.systemmanagement.request.IbeaconPageRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface IbeaconMapper {

    List<Ibeacon> getbyid(@Param("Id") String Id);
    List<Ibeacon> findByName(@Param("hospId") String hospId, @Param("ibeaconName") String ibeaconName);
    void delete(@Param("ibeaconId") String ibeaconId);
    void enableIbeacon(@Param("ibeaconId") String ibeaconId,@Param("usingflag") String usingflag);
    List<Long> Rcount(IbeaconPageRequest request);
    Page<IbeaconBrief> page(IbeaconPageRequest request, RowBounds bounds);
}
