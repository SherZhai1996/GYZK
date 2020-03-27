package cn.edu.ujs.lp.intells.common.dao.Serviceitem;

import cn.edu.ujs.lp.intells.common.entity.Serviceitem.Servicebill;
import cn.edu.ujs.lp.intells.common.entity.Serviceitem.ServicebillBrief;
import cn.edu.ujs.lp.intells.common.request.Serviceitem.ServicebillPageRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ServicebillMapper {
    List<Long> Rcount(ServicebillPageRequest request);
    Page<ServicebillBrief> page(ServicebillPageRequest request, RowBounds bounds);
    List<ServicebillBrief> list(ServicebillPageRequest request);

    List<Servicebill> getbyid(@Param("Id") String Id);
    List<Servicebill> findByName(@Param("categoryId") String categoryId, @Param("serviceitemName") String serviceitemName);

    void delete(@Param("servicebillid") String Id);
}
