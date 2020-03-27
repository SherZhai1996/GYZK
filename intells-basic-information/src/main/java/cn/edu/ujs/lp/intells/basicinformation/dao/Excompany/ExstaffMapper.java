package cn.edu.ujs.lp.intells.basicinformation.dao.Excompany;

import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Exstaff;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExstaffBrief;
import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Exstaffrestaurantcertificate;
import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Exstaffskillcertificate;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExstaffPageRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ExstaffMapper {

    List<Long> Rcount(ExstaffPageRequest request);
    List<Exstaff> getbyid(@Param("Id") String Id);
    List<Exstaff> findExstaffByExcompanyIDExteamIDandName(@Param("hospId") String hospId,@Param("excompanyId") String excompanyId, @Param("exteamId") String exteamId, @Param("exstaffName") String exstaffName);
    void delete(@Param("Id") String Id);
    void deleteAtt(@Param("exstaffId") String exstaffId);
    Page<ExstaffBrief> page(ExstaffPageRequest request, RowBounds bounds);

    void Clear_exstaff_allskillcertificate(@Param("exstaffId") String exstaffId);
    void deleteExstaff_skillcertificate(@Param("exstaffId") String exstaffId,@Param("skillcertificateId") String skillcertificateId);
    void clearExstaff_skillcertificate_pic(@Param("exstaffId") String exstaffId,@Param("skillcertificateId") String skillcertificateId);
    void UpdateExstaff_skillcertificate_pic(@Param("exstaffId") String exstaffId,@Param("skillcertificateId") String skillcertificateId, @Param("picfilename") String picfilename);

    List<Exstaffskillcertificate> getExstaffskillcertificate(@Param("exstaffId") String exstaffId,@Param("skillcertificateId") String skillcertificateId);
}
