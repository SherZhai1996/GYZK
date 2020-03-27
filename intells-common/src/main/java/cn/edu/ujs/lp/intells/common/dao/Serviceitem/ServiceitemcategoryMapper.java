package cn.edu.ujs.lp.intells.common.dao.Serviceitem;

import cn.edu.ujs.lp.intells.common.entity.Serviceitem.Serviceitemcategory;
import cn.edu.ujs.lp.intells.common.entity.Serviceitem.ServiceitemcategoryBrief;
import cn.edu.ujs.lp.intells.common.request.Serviceitem.ServiceitemcategoryPageRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ServiceitemcategoryMapper {
    List<Long> Rcount(ServiceitemcategoryPageRequest request);
    Page<ServiceitemcategoryBrief> page(ServiceitemcategoryPageRequest request, RowBounds bounds);
    List<ServiceitemcategoryBrief> list(ServiceitemcategoryPageRequest request);

    List<Serviceitemcategory> getbyid(@Param("Id") String Id);
    List<Serviceitemcategory> findByName(@Param("superiorId") String superiorId, @Param("serviceitemcategoryName") String serviceitemcategoryName);
    List<Serviceitemcategory> findByFullname(@Param("serviceitemcategoryFullname") String serviceitemcategoryFullname);

    List<String> GetserviceitemcategoryFullnamebyID(@Param("sicId") String Id);
    List<String> GetserviceitemcategoryNamebyID(@Param("sicId") String Id);

    void deletebyCode(@Param("serviceitemcategorycode") String serviceitemcategorycode);
}
