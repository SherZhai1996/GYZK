package cn.edu.ujs.lp.intells.basicinformation.dao.Excompany;

import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Excompany;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExcompanyBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExcompanyPageRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ExcompanyMapper {

    List<Long> Rcount(ExcompanyPageRequest request);
    List<Excompany> getbyid(@Param("Id") String Id);
    List<Excompany> getbyName(@Param("hospId") String hospId,@Param("excompanyName") String excompanyName);
    void delete(@Param("Id") String Id);
    Page<ExcompanyBrief> page(ExcompanyPageRequest request, RowBounds bounds);
}
