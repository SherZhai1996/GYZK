package cn.edu.ujs.lp.intells.basicinformation.dao.hosp;

import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.Staff;
import cn.edu.ujs.lp.intells.common.entity.Hosp.StaffBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.StaffPageRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface StaffMapper {

    List<Long> Rcount(StaffPageRequest request);
    List<Staff> getbyid(@Param("Id") String Id);
    List<Staff> findByName(@Param("hospId") String hospId,@Param("deptId") String deptId, @Param("staffName") String staffName);
    void delete(@Param("staffId") String staffId);
    void deleteAtt(@Param("staffId") String staffId);
    Page<StaffBrief> page(StaffPageRequest request, RowBounds bounds);
}
