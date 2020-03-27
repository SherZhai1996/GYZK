package cn.edu.ujs.lp.intells.basicinformation.dao.hosp;

import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.Department;
import cn.edu.ujs.lp.intells.common.entity.Hosp.DepartmentBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.DeptPageRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Meredith
 * @data 2019-10-06
 */
@Mapper
@Component
public interface DeptMapper {

    List<Long> Rcount(DeptPageRequest request);
    List<Department> getbyid(@Param("Id") String Id);
    List<Department> findByName(@Param("hospId") String hospId,@Param("superiorId") String superiorId, @Param("deptName") String deptName);
    List<Department> findByFullname(@Param("hospId") String hospId,@Param("deptFullname") String deptFullname);
    void delete(@Param("dCode") String dCode);
    Page<DepartmentBrief> page(DeptPageRequest request, RowBounds bounds);
}
