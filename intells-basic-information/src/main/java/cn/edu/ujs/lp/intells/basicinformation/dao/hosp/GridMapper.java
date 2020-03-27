package cn.edu.ujs.lp.intells.basicinformation.dao.hosp;

import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.Grid;
import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.GridExteamBrief;
import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.GridQRBrief;
import cn.edu.ujs.lp.intells.common.entity.Hosp.GridBrief;
import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.GridTelBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.GridPageRequest;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 网格对象Mapper接口
 */
@Mapper
@Component
public interface GridMapper {

    List<Long> Rcount(GridPageRequest request);
    List<Grid> getbyid(@Param("Id") String Id);
    List<Grid> getbyFullname(@Param("hospId") String hospId,@Param("gridFullname") String gridFullname);
    List<Grid> findGridBysuperIDandGridName(@Param("hospId") String hospId,@Param("superiorGridid") String superiorGridid, @Param("gridName") String gridName);
    List<GridTelBrief> getTelsbyGridId(@Param("gridId") String gridId);
    List<GridExteamBrief> getExteamsbyGridId(@Param("gridId") String gridId);
    void deleteGridbycode(@Param("gridCode") String gridCode);
    void clearGridexteam(@Param("gridid") String gridid);
    List<GridQRBrief> getQRlist(@Param("hospId") String hospId, @Param("gridCode") String gridCode);
    Page<GridBrief> page(GridPageRequest request, RowBounds bounds);
}
