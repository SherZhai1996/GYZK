package cn.edu.ujs.lp.intells.basicinformation.dao.Excompany;

import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.ExServices;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExServicesBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExServicesPageRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ExServicesMapper {

    List<Long> Rcount(ExServicesPageRequest request);
    List<ExServices> getbyid(@Param("Id") String Id);
    List<ExServices> findExcompanyServicesByExcompanyIDandServiceName(@Param("hospId") String hospId, @Param("excompanyId") String excompanyId, @Param("exserviceName") String exserviceName);
    void delete(@Param("Id") String Id);
    Page<ExServicesBrief> page(ExServicesPageRequest request, RowBounds bounds);
}
