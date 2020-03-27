package cn.edu.ujs.lp.intells.basicinformation.dao.hosp;

import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.Grid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 网格数据库访问类
 */
@Repository
public interface GridRepository  extends JpaRepository<Grid,String> {

    /**
     依据网格编号获取网格对象ID
     */
    @Query(value = "select GetIDbyGridCode(?1) as id ", nativeQuery = true)
    List getGridIDbycode(String gridcode);

    /**
     * 清除网格外围多边形点
     * @param gridId
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "call Clear_grid_location(?1)", nativeQuery = true)
    int clear_Grid_Location(String gridId);

    /**
     * 清除网格电话
     * @param gridId
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "call Clear_grid_tel(?1)", nativeQuery = true)
    int clear_Grid_Tel(String gridId);

    /**
     * 清除网格信标
     * @param gridId
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "call Clear_grid_ibeacon(?1)", nativeQuery = true)
    int clear_Grid_Ibeacon(String gridId);

    /**
     * 删除网格图片
     * @param gridId
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_grid set grid_picture=null where id=?1", nativeQuery = true)
    int clearGrid_picture(String gridId);

    /**
     * 更新网格图片
     * @param gridId
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_grid set grid_picture=?2 where id=?1", nativeQuery = true)
    int updateGrid_picture(String gridId,String fn);
}
