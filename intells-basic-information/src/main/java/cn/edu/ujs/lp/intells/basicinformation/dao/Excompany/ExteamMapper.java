package cn.edu.ujs.lp.intells.basicinformation.dao.Excompany;

import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Exteam;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExteamBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExteamPageRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ExteamMapper {

    List<Long> Rcount(ExteamPageRequest request);
    List<Exteam> getbyid(@Param("Id") String Id);
    List<Exteam> findExteamByExcompanyIDandExteamName(@Param("hospId") String hospId,@Param("excompanyId") String excompanyId, @Param("exteamName") String exteamName);
    void delete(@Param("Id") String Id);
    void delete_exteam_bycode(@Param("tcode") String tcode);
    void deleteAtt(@Param("Id") String Id);
    Page<ExteamBrief> page(ExteamPageRequest request, RowBounds bounds);
}
