package cn.edu.ujs.lp.intells.common.service;

import cn.edu.ujs.lp.intells.common.dao.CommonMapper;
import cn.edu.ujs.lp.intells.common.dao.User.UserMapper;
import cn.edu.ujs.lp.intells.common.dao.hosp.HospMapper;
import cn.edu.ujs.lp.intells.common.entity.User.RoleBrief;
import cn.edu.ujs.lp.intells.common.entity.Common.TreeInterface;
import cn.edu.ujs.lp.intells.common.entity.Device.DevicebillBrief;
import cn.edu.ujs.lp.intells.common.entity.Device.DevicecategoryBrief;
import cn.edu.ujs.lp.intells.common.entity.Device.IbeaconBrief;
import cn.edu.ujs.lp.intells.common.entity.Hosp.*;
import cn.edu.ujs.lp.intells.common.utils.BaiduUtils;
import cn.edu.ujs.lp.intells.common.utils.OSUtils;
import cn.edu.ujs.lp.intells.common.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库公共访问服务
 */
@Service
public class DBCommonService {
    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private HospMapper hospMapper;

    @Autowired
    private DataDictionaryService dataDictionaryService;

    /**
     * 获取医院的地图显示logo文件相对路径名
     * @return
     * @throws Exception
     */
    public String getHosplogoiconfile() throws Exception {
        try
        {
            return OSUtils.getHosplogoiconfile();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取医院的地图显示logo文件相对路径名:"+e.getMessage());
        }
    }

    /**
     * 依据网格区域ID获取蓝牙信标UUID
     * @param gridId
     * @return
     * @throws Exception
     */
    public String getIbeaconUUIDbygridId(String gridId) throws Exception
    {
        if ((commonMapper != null)&&(gridId != null))
        {
            try {
                List<String> ls = commonMapper.getIbeaconUUIDbygridId(gridId.replace(" ",""));

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据网格区域ID获取蓝牙信标UUID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     * 依据网格区域ID设备运维班组名称
     * @param gridId
     * @return
     * @throws Exception
     */
    public String getDeviceExteamnamebyGridid(String gridId) throws Exception
    {
        if ((commonMapper != null)&&(gridId != null))
        {
            try {
                List<String> ls = commonMapper.getDeviceExteamnamebyGridid(gridId.replace(" ",""));

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据网格区域ID设备运维班组名称失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 依据角色ID获取角色名称
     * @param Id
     * @return
     * @throws Exception
     */
    public String getRolenamebyId(String Id) throws Exception
    {
        if ((commonMapper != null)&&(Id != null))
        {
            try {
                List<String> ls = commonMapper.getRolenamebyId(Id.replace(" ",""));

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据角色ID获取角色名称失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 依据角色名称获取角色ID
     * @param roleName
     * @return
     * @throws Exception
     */
    public String getRoleidbyName(String roleName) throws Exception
    {
        if ((commonMapper != null)&&(roleName != null))
        {
            try {
                List<String> ls = commonMapper.getRoleidbyName(roleName.replace(" ",""));

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据角色名称获取角色ID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 依据医院功能ID获取功能名称
     * @param Id
     * @return
     * @throws Exception
     */
    public String getHospFunctionnamebyId(String Id) throws Exception
    {
        if ((commonMapper != null)&&(Id != null))
        {
            try {
                List<String> ls = commonMapper.getHospFunctionnamebyId(Id.replace(" ",""));

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据医院功能ID获取功能名称失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     * 依据医院功能名称获取功能ID
     * @param functionName
     * @return
     * @throws Exception
     */
    public String getHospFunctionidbyName(String functionName) throws Exception
    {
        if ((commonMapper != null)&&(functionName != null))
        {
            try {
                List<String> ls = commonMapper.getHospFunctionidbyName(functionName.replace(" ",""));

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据医院功能名称获取功能ID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 获取设备类别列表
     * @return
     * @throws Exception
     */
    public List<DevicecategoryBrief> getDevicecategories(String hospId, String categoryName) throws Exception
    {
        List<DevicecategoryBrief> result= null;

        if (commonMapper != null) {
            try {
                result = commonMapper.getDevicecategories(hospId,categoryName);

                if ((result != null)&&(result.size()>0))
                {
                    for (DevicecategoryBrief dcf:result)
                    {
                        dcf.getIsUsingName();
                        dcf.getSuperiorName(this);
                        dcf.getManageAffiliationName(this);
                        dcf.getResponAffiliationName(this);
                    }
                }
            } catch (Exception e) {
                throw new Exception("获取设备类别列表失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 获取设备类别树形结构
     * @param hospId
     * @param categoryName
     * @return
     * @throws Exception
     */
    public List<Object> getDevicecategoryTreeStr(String hospId, String categoryName) throws Exception
    {
        List<Object> list = null;

        try {
            List<DevicecategoryBrief> lst = getDevicecategories(hospId, categoryName);

            //转换为树形串
            if (lst != null) {

                List<TreeInterface> lt = new ArrayList<TreeInterface>();
                for (int i = 0; i < lst.size(); i++) lt.add(lst.get(i));

                list = TreeUtil.getTreelist(lt);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取设备类别树形结构失败："+e.getMessage());
        }

        return list;
    }

    /**
     * 获取设备明细列表
     * @return
     * @throws Exception
     */
    public List<DevicebillBrief> getDevicebills(String hospId, String deviceCategory) throws Exception
    {
        List<DevicebillBrief> result= null;

        if (commonMapper != null) {
            try {
                result = commonMapper.getDevicebills(hospId,deviceCategory);

                if ((result != null)&&(result.size()>0))
                {
                    for (DevicebillBrief dbb:result)
                    {
                        dbb.getGridName(this);
                        dbb.getDeviceCategoryName(this);
                        dbb.getTaskOwnerName(this);
                        dbb.getAdministratorsName(this);
                        dbb.getUseStatusName(dataDictionaryService);
                        dbb.getAssetSourceName(dataDictionaryService);
                        dbb.getIsUsingName();
                        dbb.getIsMedicaluseName();
                        dbb.getIsImportedName();
                    }
                }
            } catch (Exception e) {
                throw new Exception("获取设备明细列表失败:"+e.getMessage());
            }
        }

        return result;
    }


    /**
     * 获取蓝牙信标列表
     * @param hospId
     * @param ibeaconName
     * @return
     * @throws Exception
     */
    public List<IbeaconBrief> getIbeacons(String hospId, String ibeaconName) throws Exception
    {
        List<IbeaconBrief> result= null;

        if (commonMapper != null) {
            try {
                result = commonMapper.getIbeacons(hospId,ibeaconName);

                if ((result != null)&&(result.size()>0))
                {
                    for (IbeaconBrief dcf:result)
                    {
                        dcf.getIsUsingName();
                        dcf.getGridName(this);
                        dcf.getHospName(this);
                    }
                }
            } catch (Exception e) {
                throw new Exception("获取蓝牙信标列表失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 获取系统用户角色列表列表
     * @return
     * @throws Exception
     */
    public List<RoleBrief> getSystemRole() throws Exception
    {
        List<RoleBrief> result= null;

        if (commonMapper != null) {
            try {
                result = commonMapper.getSystemRole();
            } catch (Exception e) {
                throw new Exception("获取系统用户角色列表列表失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 为前端选择获取系统角色列表
     * @return
     * @throws Exception
     */
    public List<RoleBrief> getSystemRoleForSelect() throws Exception
    {
        List<RoleBrief> result= new ArrayList<>();

        try {
            RoleBrief rb = new RoleBrief();
            rb.setId("001");
            rb.setRoleName("系统超级管理员");
            result.add(rb);

            rb = new RoleBrief();
            rb.setId("002");
            rb.setRoleName("系统管理员");
            result.add(rb);

            rb = new RoleBrief();
            rb.setId("003");
            rb.setRoleName("系统用户");
            result.add(rb);
        }
        catch (Exception e)
        {
            throw new Exception("获取系统用户角色失败:"+e.getMessage());
        }

        return result;
    }


    /**
     * 获取医院职工角色列表列表
     * @return
     * @throws Exception
     */
    public List<RoleBrief> getStaffRole() throws Exception
    {
        List<RoleBrief> result= null;

        if (commonMapper != null) {
            try {
                result = commonMapper.getStaffRole();
            } catch (Exception e) {
                throw new Exception("获取医院职工角色列表列表:"+e.getMessage());
            }
        }

        return result;
    }


    /**
     * 获取外委职工角色列表列表
     * @return
     * @throws Exception
     */
    public List<RoleBrief> getExstaffRole() throws Exception
    {
        List<RoleBrief> result= null;

        if (commonMapper != null) {
            try {
                result = commonMapper.getExstaffRole();
            } catch (Exception e) {
                throw new Exception("获取外委职工角色列表列表失败:"+e.getMessage());
            }
        }

        return result;
    }


    /**
     * 查询医院，参数可以为null
     * @param hospName
     * @return
     * @throws Exception
     */
    public List<HospBrief> getHosps(String hospName) throws Exception {
        List<HospBrief> result= null;

        if ((commonMapper != null) && (dataDictionaryService != null)) {
            try {
                result = commonMapper.getHosps(hospName!=null?hospName.replace(" ",""):null);

                //处理数据字典的转换
                if ((result != null) && (result.size()>0))
                {
                    for (HospBrief hb:result)
                    {
                        hb.getPidName(dataDictionaryService);
                        hb.getCidName(dataDictionaryService);
                        hb.getCountyIdName(dataDictionaryService);
                        hb.getHospDetailaddress(dataDictionaryService);
                        hb.getManagementUser(userService);

                        double p1x = hb.getHospCenterX();
                        double p1y = hb.getHospCenterY();

                        String[] pc = dataDictionaryService.getCoordinate(hb.getRemark()!=null?hb.getRemark():"贵州省");
                        if ((pc != null) && (pc.length>=2))
                        {
                            double p2x = Double.parseDouble(pc[0]);
                            double p2y = Double.parseDouble(pc[1]);

                            if (BaiduUtils.getDistance(p1x,p1y,p2x,p2y)>2000000)
                            {
                                hb.setHospCenterX(p2x);
                                hb.setHospCenterY(p2y);
                            }
                        }
                        else
                        {
                            hb.setHospCenterX(0.0);
                            hb.setHospCenterY(0.0);
                        }
                    }
                }
            } catch (Exception e) {
                throw new Exception("查询医院失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 依据医院ID获取医院对象
     * @param id
     * @param hospCode
     * @return
     * @throws Exception
     */
    public Hosp getHospbyID(String id,String hospCode) throws  Exception
    {
        Hosp result=null;

        if (hospMapper != null)
        {
            try
            {
                List<Hosp> lst = hospMapper.getHospbyid(id,hospCode);
                if ((lst != null) && (lst.size()>0)) {
                    result = lst.get(0);

                    if (result != null) {
                        result.getHospDetailaddress(dataDictionaryService);

                        double p1x = result.getHospCenterX();
                        double p1y = result.getHospCenterY();

                        String[] pc = dataDictionaryService.getCoordinate(result.getRemark()!=null?result.getRemark():BaiduUtils.defaultAddr);
                        if ((pc != null) && (pc.length>=2))
                        {
                            double p2x = Double.parseDouble(pc[0]);
                            double p2y = Double.parseDouble(pc[1]);

                            //判断是否合规的中心坐标，不合规的设置为默认值
                            if (BaiduUtils.getDistance(p1x,p1y,p2x,p2y)>BaiduUtils.defaultProvinceRadius)
                            {
                                result.setHospCenterX(p2x);
                                result.setHospCenterY(p2y);
                            }
                        }
                        else
                        {
                            result.setHospCenterX(BaiduUtils.defaultCenterX);
                            result.setHospCenterY(BaiduUtils.defaultCenterY);
                        }

                        if (Math.abs(result.getHospRadius()-BaiduUtils.defaulthospRadius)>(BaiduUtils.defaulthospRadius/2))
                            result.setHospRadius(BaiduUtils.defaulthospRadius);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据ID，医院编码:["+id+","+hospCode+"]获取医院对象失败:"+e.getMessage());
            }
        }

        return result;
    }


    /**
     * 查询医院功能列表
     * @return
     * @throws Exception
     */
    public List<HospFunctionBrief> gethospFunctions() throws Exception {
        List<HospFunctionBrief> result= null;

        if (commonMapper != null) {
            try {
                result = commonMapper.gethospFunctions();

            } catch (Exception e) {
                throw new Exception("查询医院功能列表失败:"+e.getMessage());
            }
        }

        return result;
    }


    /**
     * 获取指定医院的科室部门列表
     * @param hospId
     * @return
     * @throws Exception
     */
    public List<DepartmentBrief> getDepts(String hospId,String deptName) throws Exception {
        List<DepartmentBrief> result= null;

        if (commonMapper != null) {
            try {
                result = commonMapper.getDepts(hospId.trim(),deptName!=null?deptName.replace(" ",""):null);

            if ((result!=null)&&(result.size()>0))
            {
                for (DepartmentBrief db:result)
                {
                    if ((db.getSuperiorId() != null) && (db.getSuperiorId() != ""))
                        db.setSuperiorName(getDeptFullnamebyId(db.getSuperiorId()));
                }
            }
            } catch (Exception e) {
                throw new Exception("查询科室部门失败:"+e.getMessage());
            }
        }

        return result;
    }


    /**
     * 获取科室部门的树形结构
     * @param hospId
     * @return
     * @throws Exception
     */
    public List<Object> getDeptTreeStr(String hospId,String deptName) throws Exception
    {
        List<Object> list = null;

        try {
            List<DepartmentBrief> lst = getDepts(hospId, deptName);

            //转换为树形串
            if (lst != null) {

                List<TreeInterface> lt = new ArrayList<TreeInterface>();
                for (int i = 0; i < lst.size(); i++) lt.add(lst.get(i));

                list = TreeUtil.getTreelist(lt);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取科室部门树形结构失败："+e.getMessage());
        }

        return list;
    }


    /**
     * 获取网格列表
     * @param hospId
     * @param deptId
     * @param superGridId
     * @param gridName
     * @return
     * @throws Exception
     */
    public List<GridBrief> getGrids(String hospId,String deptId,String superGridId,String gridName) throws Exception{
        List<GridBrief> list = null;

        if (commonMapper != null) {
            try {
                //获取所有网格记录
                list = commonMapper.getGrids(hospId.replace(" ",""),deptId!=null?deptId.replace(" ",""):null,superGridId!=null?superGridId.replace(" ",""):null,gridName!=null?gridName.replace(" ",""):null);

                if ((list!=null) && (list.size()>0))
                {
                    for (GridBrief gb:list)
                    {
                        gb.getSuperiorGridname(this);
                        gb.getDeptName(this);
                        gb.getGridPlaceclassidName(dataDictionaryService);
                        gb.getGridExteam(this);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("查询网格区域数据失败:" + e.getMessage());
            }
        }

        return list;
    }


    /**
     * 依据医院ID和网格ID获取网格下一级子节点对象
     * @param hospId
     * @param superGridId
     * @return
     * @throws Exception
     */
    public List<GridBrief> getsubGrids(String hospId,String superGridId) throws Exception{
        List<GridBrief> list = null;

        if (commonMapper != null) {
            try {
                //获取所有网格记录
                list = commonMapper.getsubGrids(hospId.replace(" ",""),superGridId!=null?superGridId.replace(" ",""):null);

                if ((list!=null) && (list.size()>0))
                {
                    for (GridBrief gb:list)
                    {
                        gb.getSuperiorGridname(this);
                        gb.getDeptName(this);
                        gb.getGridPlaceclassidName(dataDictionaryService);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("查询网格区域数据失败:" + e.getMessage());
            }
        }

        return list;
    }

    /**
     * 依据医院ID获取所有楼栋对象列表
     * @param hospId
     * @return
     * @throws Exception
     */
    public List<GridBrief> getBuilds(String hospId) throws Exception{
        List<GridBrief> list = null;

        if (commonMapper != null) {
            try {
                //获取所有网格记录
                list = commonMapper.getBuilds(hospId.replace(" ",""));

                if ((list!=null) && (list.size()>0))
                {
                    for (GridBrief gb:list)
                    {
                        gb.getSuperiorGridname(this);
                        gb.getDeptName(this);
                        gb.getGridPlaceclassidName(dataDictionaryService);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("查询网格区域数据失败:" + e.getMessage());
            }
        }

        return list;
    }

    /**
     * 依据医院ID和父节点ID获取子网格列表
     * @param hospId
     * @param superGridId
     * @return
     * @throws Exception
     */
    public List<GridBrief> getallsubGrids(String hospId,String superGridId) throws Exception{
        List<GridBrief> list = null;

        if ((commonMapper != null) && (hospId != null) && (superGridId != null)) {
            try {
                String gridCode = getGridCodebyId(superGridId);

                if ((gridCode != null) && (gridCode != "")) {

                    if (gridCode.length()<8)
                    {
                        //获取下一级子网格记录
                        list = commonMapper.getselfandsubGrids(hospId.replace(" ", ""), superGridId != null ? superGridId.replace(" ", "") : null);
                    }
                    else {
                        //获取所有子网格记录
                        list = commonMapper.getallsubGrids(hospId.replace(" ", ""), gridCode != null ? gridCode.replace(" ", "") : null);
                    }

                    if ((list != null) && (list.size() > 0)) {
                        for (GridBrief gb : list) {
                            gb.getSuperiorGridname(this);
                            gb.getDeptName(this);
                            gb.getGridPlaceclassidName(dataDictionaryService);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("查询网格区域数据失败:" + e.getMessage());
            }
        }

        return list;
    }


    /**
     * 依据网格功能类别获取网格列表
     * @param hospId
     * @param gridPlaceclassid
     * @param gridName
     * @return
     * @throws Exception
     */
    public List<GridBrief> getGridsbyPlaceclass(String hospId,String gridPlaceclassid,String gridName) throws Exception{
        List<GridBrief> list = null;

        if (commonMapper != null) {
            try {
                //获取所有网格记录
                list = commonMapper.getGridsbyPlaceclass(hospId.replace(" ",""),gridPlaceclassid!=null?gridPlaceclassid.replace(" ",""):null,gridName!=null?gridName.replace(" ",""):null);

                if ((list!=null) && (list.size()>0))
                {
                    for (GridBrief gb:list)
                    {
                        gb.getSuperiorGridname(this);
                        gb.getDeptName(this);
                        gb.getGridPlaceclassidName(dataDictionaryService);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("依据网格功能类别获取网格列表失败:" + e.getMessage());
            }
        }

        return list;
    }

    /**
     * 获取网格的树形结构Json串
     * @return
     */
    public List<Object> getGridTreeStr(String hospId,String deptId,String superGridId,String gridName) throws Exception
    {
        List<Object> list = null;

        try {
            List<GridBrief> lg = getGrids(hospId, deptId, superGridId, gridName);

            //转换为树形串
            if (lg != null) {

                List<TreeInterface> lt = new ArrayList<TreeInterface>();
                for (int i = 0; i < lg.size(); i++) lt.add(lg.get(i));

                list = TreeUtil.getTreelist(lt);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取网格树形结构失败："+e.getMessage());
        }

        return list;
    }

    /**
     * 依据医院ID和父节点ID获取子网对象树形结构
     * @param hospId
     * @param superGridId
     * @return
     * @throws Exception
     */
    public List<Object> getallsubGridTreeStr(String hospId,String superGridId) throws Exception
    {
        List<Object> list = null;

        try {
            List<GridBrief> lg = getallsubGrids(hospId, superGridId);

            //转换为树形串
            if (lg != null) {

                List<TreeInterface> lt = new ArrayList<TreeInterface>();
                for (int i = 0; i < lg.size(); i++) lt.add(lg.get(i));

                list = TreeUtil.getTreelist(lt);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取网格树形结构失败："+e.getMessage());
        }

        return list;
    }

    /**
     * 依据医院ID和父节点ID获取下一级子网对象树形结构
     * @param hospId
     * @param superGridId
     * @return
     * @throws Exception
     */
    public List<Object> getsubGridTreeStr(String hospId,String superGridId) throws Exception
    {
        List<Object> list = null;

        try {
            List<GridBrief> lg = getsubGrids(hospId, superGridId);

            //转换为树形串
            if (lg != null) {

                List<TreeInterface> lt = new ArrayList<TreeInterface>();
                for (int i = 0; i < lg.size(); i++) lt.add(lg.get(i));

                list = TreeUtil.getTreelist(lt);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取网格树形结构失败："+e.getMessage());
        }

        return list;
    }


    /**
     * 判断是否在指定的网格列表中存在指定的网格ID
     * @param lg
     * @param gridId
     * @return
     */
    private Boolean isExistofGridId(List<GridBrief> lg,String gridId)
    {
        Boolean result = false ;

        if ((lg != null) && (lg.size()>0) && (gridId != null) && (gridId.length()>0)) {

            for (GridBrief gb:lg) {
                if (gb.getId().compareTo(gridId) == 0) {
                    result  = true;
                    break;
                }
            }
        }

        return result;
    }
    /**
     * 依据医院ID和父节点ID和节点列表获取网格树
     * @param hospId
     * @param Idss
     * @return
     * @throws Exception
     */
    public List<Object> getsubGridTreeStrbyIds(String hospId,List<List<String>> Idss) throws Exception
    {
        List<Object> list = null;

        try {
            List<GridBrief> lg = null;

            //追加中间节点
            if ((Idss != null) && (Idss.size()>0))
            {
                lg = getBuilds(hospId);

                for (List<String> ids:Idss)
                {
                    if ((ids != null) && (ids.size()>0))
                    {
                        for (int i=0;i<(ids.size()>1?ids.size()-2:ids.size()-1);i++)
                        {
                            String id_temp = ids.get(i);

                            if (lg == null)
                                lg = getsubGrids(hospId, id_temp);
                            else {
                                List<GridBrief> lg_temp = getsubGrids(hospId, id_temp);

                                if ((lg_temp != null) && (lg_temp.size() > 0)) {
                                    for (GridBrief g_temp : lg_temp) {
                                        boolean isExistgrid = false;
                                        for (GridBrief g : lg) {
                                            if (g_temp.getId().compareTo(g.getId()) == 0) {
                                                isExistgrid = true;
                                                break;
                                            }
                                        }

                                        if (!isExistgrid) lg.add(g_temp);
                                    }
                                }
                            }
                        }
                    }
                }

                //处理叶节点
                for (List<String> ids:Idss)
                {
                    if ((ids != null) && (ids.size()>1))
                    {
                        String leafID = ids.get(ids.size()-1);
                        String secondID = ids.get(ids.size()-2);

                        if (isExistofGridId(lg,leafID) == false) {
                            List<GridBrief> lg_temp = getsubGrids(hospId, secondID);

                            if ((lg_temp != null) && (lg_temp.size() > 0)) {
                                for (GridBrief g_temp : lg_temp) {
                                    boolean isExistgrid = false;
                                    for (GridBrief g : lg) {
                                        if (g_temp.getId().compareTo(g.getId()) == 0) {
                                            isExistgrid = true;
                                            break;
                                        }
                                    }

                                    if (!isExistgrid) lg.add(g_temp);
                                }
                            }
                        }
                    }
                }
            }

            if (lg == null)
                lg = getsubGrids(hospId, null);
            else

            //转换为树形串
            if (lg != null) {

                List<TreeInterface> lt = new ArrayList<TreeInterface>();
                for (int i = 0; i < lg.size(); i++) lt.add(lg.get(i));

                list = TreeUtil.getTreelist(lt);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取网格树形结构失败："+e.getMessage());
        }

        return list;
    }

    /**
     * 查询在职职工，参数可以为null
     * @param hospId
     * @param deptId
     * @param staffName
     * @return
     * @throws Exception
     */
    public List<StaffBrief> getStaffs(String hospId,String deptId,String staffName) throws Exception {
        List<StaffBrief> result= null;

        if (commonMapper != null) {
            try {
                result = commonMapper.getStaffs(hospId.replace(" ",""),deptId!=null?deptId.replace(" ",""):null,staffName!=null?staffName.replace(" ",""):null);

                if ((result != null) && (result.size()>0))
                {
                    for (StaffBrief sb:result)
                    {
                        sb.getStaffSexName(dataDictionaryService);
                        sb.getStaffAdminisPositionName(dataDictionaryService);
                        sb.getStaffTechnicalPositionName(dataDictionaryService);
                        sb.getStaffEducationPositionName(dataDictionaryService);
                        sb.getStaffTutorTypeName(dataDictionaryService);
                        sb.getStaffDegreeName(dataDictionaryService);
                        sb.getstaffActivestatusName();
                        sb.getIsuseMedicalhelpName();
                        sb.getDeptName(this);
                    }
                }
            } catch (Exception e) {
                throw new Exception("查询在职职工失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 获取公司列表
     * @return
     */
    public List<ExcompanyBrief> getExcompanys(String hospId) throws Exception {
        List<ExcompanyBrief> result= null;

        if (commonMapper != null) {
            try {
                result = commonMapper.getExcompanys(hospId.replace(" ",""));

                if ((result != null) && (result.size()>0))
                {
                    for (ExcompanyBrief ec:result)
                    {
                        ec.getLeader(userService);
                    }
                }
            } catch (Exception e) {
                throw new Exception("查询外委公司失败！");
            }
        }

        return result;
    }

    /**
     * 查询业务部门，参数可以为null
     * @param excompanyID
     * @return
     * @throws Exception
     */
    public List<ExServicesBrief> getExservices(String hospId, String excompanyID) throws Exception {
        List<ExServicesBrief> result= null;

        if (commonMapper != null) {
            try {
                result = commonMapper.getExservices(hospId.replace(" ", ""), excompanyID != null ? excompanyID.replace(" ", "") : null);

                if ((result != null) && (result.size() > 0)) {
                    for (ExServicesBrief esb : result) {
                        if ((esb.getServiceId() != null) && (esb.getServiceId() != "")) {
                            esb.setServiceName(dataDictionaryService.getDataDictionary("db_extern_service_type").getNamebyID(esb.getServiceId()));
                        }

                        esb.getLeader(userService);
                    }
                }
            } catch (Exception e) {
                throw new Exception("查询业务部门失败！");
            }
        }

        return result;
    }


    /**
     * 查询服务班组，参数可以为null
     * @param excompanyID
     * @return
     * @throws Exception
     */
    public List<ExteamBrief> getExteams(String hospId,String excompanyID,String exservicesId) throws Exception {
        List<ExteamBrief> result= null;

        if (commonMapper != null) {
            try {
                result = commonMapper.getExteams(hospId.replace(" ",""),excompanyID!=null?excompanyID.replace(" ",""):null,exservicesId!=null?exservicesId.replace(" ",""):null);

                if ((result != null) && (result.size() > 0)) {
                    for (ExteamBrief etb : result) {
                        if ((etb.getServiceId() != null) && (etb.getServiceId() != "")) {
                            etb.setServiceName(dataDictionaryService.getDataDictionary("db_extern_service_type").getNamebyID(etb.getServiceId()));
                        }

                        if ((etb.getSuperId() != null)&&(etb.getSuperId() != ""))
                        {
                            etb.setSuperName(getExteamFullnamebyId(etb.getSuperId()));
                        }

                        etb.getLeader(userService);
                    }
                }
            } catch (Exception e) {
                throw new Exception("查询服务班组失败:"+e.getMessage());
            }
        }

        return result;
    }


    /**
     * 获取服务班组的树形结构
     * @param hospId
     * @param excompanyId
     * @param exserviceId
     * @return
     * @throws Exception
     */
    public List<Object> getExteamTreeStr(String hospId,String excompanyId,String exserviceId) throws Exception
    {
        List<Object> list = null;

        try {
            List<ExteamBrief> lst = getExteams(hospId, excompanyId, exserviceId);

            //转换为树形串
            if (lst != null) {

                List<TreeInterface> lt = new ArrayList<TreeInterface>();
                for (int i = 0; i < lst.size(); i++) lt.add(lst.get(i));

                list = TreeUtil.getTreelist(lt);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取服务班组树形结构失败："+e.getMessage());
        }

        return list;
    }


    /**
     * 查询外委职工，参数可以为null
     * @param excompanyID
     * @return
     * @throws Exception
     */
    public List<ExstaffBrief> getExstaffs(String hospId,String excompanyID,String exteamId) throws Exception {
        List<ExstaffBrief> result= null;

        if (commonMapper != null) {
            try {
                result = commonMapper.getExstaffs(hospId.replace(" ",""),excompanyID!=null?excompanyID.replace(" ",""):null,exteamId!=null?exteamId.replace(" ",""):null);

            if ((result!=null)&&(result.size()>0))
                for (ExstaffBrief esb:result)
                {
                    if ((esb.getExstaffActivestate() != null) && (esb.getExstaffActivestate() != "")) {
                        if ((esb.getExstaffActivestate() == null) ||(esb.getExstaffActivestate().compareTo("0") == 0))
                            esb.setExstaffActivestateName("非激活");
                        else
                            esb.setExstaffActivestateName("激活");
                    }

                    if ((esb.getExstaffJobstate() != null) && (esb.getExstaffJobstate() != "")) {
                        esb.setExstaffJobstateName(dataDictionaryService.getDataDictionary("db_job_state").getNamebyID(esb.getExstaffJobstate()));
                    }
                }
            } catch (Exception e) {
                throw new Exception("查询外委职工失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 由医院ID获取医院编码
     * @param hospId
     * @return
     * @throws Exception
     */
    public String getHospCodebyId(String hospId) throws Exception
    {
        if ((commonMapper != null)&&(hospId != null))
        {
            try {
                List<String> ls = commonMapper.getHospCodebyId(hospId.replace(" ",""));

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由医院ID获取医院编码失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 由医院ID获取医院名称
     * @param hospId
     * @return
     * @throws Exception
     */
    public String getHospNamebyId(String hospId) throws Exception
    {
        if ((commonMapper != null) && (hospId != null))
        {
            try {
                List<String> ls = commonMapper.getHospNamebyId(hospId.replace(" ",""));

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由医院ID获取医院名称失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     * 依据医院ID获取服务中心电话
     * @param hospId
     * @return
     * @throws Exception
     */
    public String getHospservecentertelbyId(String hospId) throws Exception
    {
        if ((commonMapper != null) && (hospId != null))
        {
            try {
                List<String> ls = commonMapper.getHospservecentertelbyId(hospId.replace(" ",""));

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由医院ID获取服务中心电话失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 由医院名称获取医院ID
     * @param hospName
     * @return
     * @throws Exception
     */
    public String getHospIdbyName(String hospName) throws Exception
    {
        if ((commonMapper != null) && (hospName != null))
        {
            try {
                List<String> ls = commonMapper.getHospIdbyName(hospName!=null?hospName.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由医院名称获取医院ID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 由科室ID获取科室名称
     * @param deptId
     * @return
     * @throws Exception
     */
    public String getDeptFullnamebyId(String deptId) throws Exception
    {
        if ((commonMapper != null)&&(deptId != null))
        {
            try {
                List<String> ls = commonMapper.getDeptFullnamebyId(deptId!=null?deptId.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由科室ID获取科室名称失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 由科室名称获取科室ID
     * @param deptFullname
     * @return
     * @throws Exception
     */
    public String getDeptIdbyFullname(String hospId,String deptFullname) throws Exception
    {
        if ((commonMapper != null)&&(deptFullname != null))
        {
            try {
                List<String> ls = commonMapper.getDeptIdbyFullname(hospId.replace(" ",""),deptFullname!=null?deptFullname.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由科室名称获取科室ID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     * 由网格ID获取网格编码
     * @param gridId
     * @return
     * @throws Exception
     */
    public String getGridCodebyId(String gridId) throws Exception
    {
        if ((commonMapper != null) && (gridId != null))
        {
            try {
                List<String> ls = commonMapper.getGridCodebyId(gridId!=null?gridId.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由网格ID获取网格编码失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     * 由网格编码获取网格ID
     *
     * @param gridCode
     * @return
     * @throws Exception
     */
    public String getGridIdbyCode(String gridCode) throws Exception
    {
        if ((commonMapper != null) && (gridCode != null))
        {
            try {
                List<String> ls = commonMapper.getGridIdbyCode(gridCode!=null?gridCode.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由网格编码获取网格ID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 由网格ID获取网格全名
     * @param gridId
     * @return
     * @throws Exception
     */
    public String getGridFullnamebyId(String gridId) throws Exception
    {
        if ((commonMapper != null)&&(gridId != null))
        {
            try {
                List<String> ls = commonMapper.getGridFullnamebyId(gridId!=null?gridId.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由网格ID获取网格全名失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 依据网格ID获取网格全路径ID列表
     * @param gridId
     * @return
     * @throws Exception
     */
    public List<String> getGridPathbyId(String gridId) throws Exception
    {
        List<String> result = null;

        if ((commonMapper != null)&&(gridId != null))
        {
            try {
                List<String> ls = commonMapper.getGridPathbyId(gridId!=null?gridId.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0) && (ls.get(0) != null) && (ls.get(0).length()>0)) {
                    result = new ArrayList<>();
                    String[] lss = ls.get(0).split(",");
                    for (String its : lss)
                    {
                        if (its != null)
                        result.add(its);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据网格ID获取网格全路径ID列表失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 依据网格ID获取该网格的服务班组ID列表
     * @param gridId
     * @return
     * @throws Exception
     */
    public List<String> getGridExteams(String gridId) throws Exception {
        List<String> result = null;

        if ((commonMapper != null) && (gridId != null) && (gridId.length()>0))
        {
            try
            {
                result = commonMapper.getGridExteams(gridId);
            }
            catch (Exception e)
            {
                throw new Exception("获取指定网络的服务班组失败："+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 由网格全名获取网格ID
     * @param gridFullname
     * @return
     * @throws Exception
     */
    public String getGridIdbyFullname(String hospId,String gridFullname) throws Exception
    {
        if ((commonMapper != null)&&(hospId != null)&&(gridFullname != null))
        {
            try {
                List<String> ls = commonMapper.getGridIdbyFullname(hospId.replace(" ",""),gridFullname!=null?gridFullname.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由网格全名获取网格ID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     * 由医院ID、科室ID和职工姓名获取职工ID
     * @param hospId
     * @param deptId
     * @param staffName
     * @return
     * @throws Exception
     */
    public String getStaffIdbyName(String hospId,String deptId,String staffName) throws Exception
    {
        if ((commonMapper != null)&&(hospId != null)&&(staffName != null))
        {
            try {
                List<String> ls = commonMapper.getStaffIdbyName(hospId.replace(" ",""),deptId!=null?deptId.replace(" ",""):null,staffName!=null?staffName.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由医院ID、科室ID和职工姓名获取职工ID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 由医院ID、职工全名获取职工ID
     * @param hospId
     * @param fullName
     * @return
     * @throws Exception
     */
    public String getStaffIdbyFullname(String hospId,String fullName) throws Exception
    {
        if ((commonMapper != null)&&(hospId != null)&&(fullName != null))
        {
            try {
                List<String> ls = commonMapper.getStaffIdbyFullname(hospId.replace(" ",""),fullName!=null?fullName.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由医院ID、职工全名获取职工ID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 由职工ID获取职工全名(科室名称->职工姓名)
     * @param staffId
     * @return
     * @throws Exception
     */
    public String getStaffFullnamebyId(String staffId) throws Exception
    {
        if ((commonMapper != null)&&(staffId != null))
        {
            try {
                List<String> ls = commonMapper.getStaffFullnamebyId(staffId!=null?staffId.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由职工ID获取职工全名失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 由职工ID获取职工名称
     * @param staffId
     * @return
     * @throws Exception
     */
    public String getStaffnamebyId(String staffId) throws Exception
    {
        if ((commonMapper != null)&&(staffId != null))
        {
            try {
                List<String> ls = commonMapper.getStaffnamebyId(staffId!=null?staffId.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由职工ID获取职工名称失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     * 由外委公司ID获取外委公司名称
     * @param Id
     * @return
     * @throws Exception
     */
    public String getExcompanyNamebyId(String Id) throws Exception
    {
        if ((commonMapper != null)&&(Id != null))
        {
            try {
                List<String> ls = commonMapper.getExcompanyNamebyId(Id!=null?Id.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由外委公司ID获取外委公司名称失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     * 由外委公司名称获取外委公司ID
     * @param hospId
     * @param excompanyName
     * @return
     * @throws Exception
     */
    public String getExcompanyIdbyName(String hospId,String excompanyName) throws Exception
    {
        if ((commonMapper != null)&&(hospId != null)&&(excompanyName != null))
        {
            try {
                List<String> ls = commonMapper.getExcompanyIdbyName(hospId.replace(" ",""),excompanyName!=null?excompanyName.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由外委公司名称获取外委公司ID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     * 由业务部门获取业务部门ID
     * @param hospId
     * @param
     * @return
     * @throws Exception
     */
    public String getExcompanyservicesIdbyName(String hospId,String excompanyId,String exserviceName) throws Exception
    {
        if ((commonMapper != null)&&(hospId != null)&&(excompanyId != null)&&(exserviceName != null))
        {
            try {
                List<String> ls = commonMapper.getExcompanyservicesIdbyName(hospId.replace(" ",""),excompanyId!=null?excompanyId.replace(" ",""):null,exserviceName!=null?exserviceName.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由业务部门名获取业务部门ID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 由业务部门ID获取业务部门名称
     * @param Id
     * @return
     * @throws Exception
     */
    public String getExcompanyservicesNamebyId(String Id) throws Exception
    {
        if ((commonMapper != null)&&(Id != null))
        {
            try {
                List<String> ls = commonMapper.getExcompanyservicesNamebyId(Id!=null?Id.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由业务部门ID获取业务部门名称失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     * 由服务班组ID获取服务班组名称
     * @param Id
     * @return
     * @throws Exception
     */
    public String getExteamFullnamebyId(String Id) throws Exception
    {
        if ((commonMapper != null)&&(Id != null))
        {
            try {
                List<String> ls = commonMapper.getExteamFullnamebyId(Id!=null?Id.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由服务班组ID获取服务班组名称失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 由服务班组全名获取服务班组ID
     * @param hospId
     * @param exteamFullname
     * @return
     * @throws Exception
     */
    public String getExteamIdbyFullName(String hospId,String exteamFullname) throws Exception
    {
        if ((commonMapper != null)&&(hospId != null)&&(exteamFullname != null))
        {
            try {
                List<String> ls = commonMapper.getExteamIdbyFullName(hospId.replace(" ",""),exteamFullname!=null?exteamFullname.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由服务班组全名获取服务班组ID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     *
     * 由服务班组名称获取服务班组ID
     * @param hospId
     * @param excompanyId
     * @param exteamName
     * @return
     * @throws Exception
     */
    public String getExteamIdbyName(String hospId,String excompanyId,String exteamName) throws Exception
    {
        if ((commonMapper != null)&&(hospId != null)&&(exteamName != null))
        {
            try {
                List<String> ls = commonMapper.getExteamIdbyName(hospId.replace(" ",""),excompanyId!=null?excompanyId.replace(" ",""):null,exteamName!=null?exteamName.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由服务班组名称获取服务班组ID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 依据服务班组ID获取包含服务公司和服务部门的服务班组全名
     * @param Id
     * @return
     * @throws Exception
     */
    public String getExteamFullpathnamebyId(String Id) throws Exception
    {
        if ((commonMapper != null)&&(Id != null))
        {
            try {
                List<String> ls = commonMapper.getExteamFullpathnamebyId(Id!=null?Id.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据服务班组ID获取包含服务公司和服务部门的服务班组全名失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 由网格id获取责任班组名称
     * @param gridId
     * @return
     * @throws Exception
     */
    public String getExteamnamebygridid(String gridId) throws Exception
    {
        if ((commonMapper != null)&&(gridId != null))
        {
            try {
                List<String> ls = commonMapper.getExteamnamebygridid(gridId!=null?gridId.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由网格id获取责任班组名称失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     * 由医院ID、外委公司ID、服务班组ID和职工姓名获取外委职工ID
     * @param hospId
     * @param excompanyId
     * @param exteamId
     * @param exstaffName
     * @return
     * @throws Exception
     */
    public String getExstaffIdbyName(String hospId, String excompanyId, String exteamId, String exstaffName) throws Exception
    {
        if ((commonMapper != null)&&(hospId != null)&&(excompanyId != null)&&(exstaffName != null))
        {
            try {
                List<String> ls = commonMapper.getExstaffIdbyName(hospId,excompanyId,exteamId,exstaffName.replace(" ",""));

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由医院ID、外委公司ID、服务班组ID和职工姓名获取职工ID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 由职工ID获取外委职工全名:外委公司名称+服务班组名称+职工姓名
     * @param exstaffId
     * @return
     * @throws Exception
     */
    public String getExstaffFullnamebyId(String exstaffId) throws Exception
    {
        if ((commonMapper != null)&&(exstaffId != null))
        {
            try {
                List<String> ls = commonMapper.getExstaffFullnamebyId(exstaffId!=null?exstaffId.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由职工ID获取职工全名失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 由外委职工ID获取外委职工名称
     * @param exstaffId
     * @return
     * @throws Exception
     */
    public String getExstaffnamebyId(String exstaffId) throws Exception
    {
        if ((commonMapper != null)&&(exstaffId != null))
        {
            try {
                List<String> ls = commonMapper.getExstaffnamebyId(exstaffId!=null?exstaffId.replace(" ",""):null);

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("由外委职工ID获取外委职工名称失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     * 依据设备运维分类ID获取设备运维分类编码
     * @param Id
     * @return
     * @throws Exception
     */
    public String getDevicecategoryCodebyId(String Id) throws Exception
    {
        if ((commonMapper != null) && (Id != null))
        {
            try {
                List<String> ls = commonMapper.getDevicecategoryCodebyId(Id.replace(" ",""));

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据设备运维分类ID获取设备运维分类编码失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 依据设备运维分类全名获取设备运维分类ID
     * @param hospId
     * @param categoryFullname
     * @return
     * @throws Exception
     */
    public String getDevicecategoryIdbyFullname(String hospId,String categoryFullname) throws Exception
    {
        if ((commonMapper != null) && (hospId != null) && (categoryFullname != null))
        {
            try {
                List<String> ls = commonMapper.getDevicecategoryIdbyFullname(hospId.replace(" ",""),categoryFullname.replace(" ",""));

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据设备运维分类全名获取设备运维分类ID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     * 依据设备运维分类ID获取设备运维分类全名
     * @param Id
     * @return
     * @throws Exception
     */
    public String getDevicecategoryFullnamebyId(String Id) throws Exception
    {
        if ((commonMapper != null) && (Id != null))
        {
            try {
                List<String> ls = commonMapper.getDevicecategoryFullnamebyId(Id.replace(" ",""));

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据设备运维分类ID获取设备运维分类全名失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     * 依据设备ID获取设备名称
     * @param Id
     * @return
     * @throws Exception
     */
    public String getDeviceNamebyId(String Id) throws Exception
    {
        if ((commonMapper != null) && (Id != null))
        {
            try {
                List<String> ls = commonMapper.getDeviceNamebyId(Id.replace(" ",""));

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据设备ID获取设备名称失败:"+e.getMessage());
            }
        }
        else
            return null;
    }


    /**
     * 依据医院ID、设备类别ID和设备名称获取设备ID
     * @param hospId
     * @param deviceCategory
     * @param assetName
     * @return
     * @throws Exception
     */
    public String getDeviceIdbyName(String hospId, String deviceCategory, String assetName) throws Exception
    {
        if ((commonMapper != null) && (hospId != null)&& (deviceCategory != null)&& (assetName != null))
        {
            try {
                List<String> ls = commonMapper.getDeviceIdbyName(hospId.replace(" ",""), deviceCategory.replace(" ",""), assetName.replace(" ",""));

                if ((ls != null) && (ls.size() > 0)) {
                    return ls.get(0);
                } else
                    return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据医院ID、设备类别ID和设备名称获取设备ID失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

    /**
     * 依据网格编号获取设备责任班组列表
     * @param hospId
     * @param gridCode
     * @return
     * @throws Exception
     */
    public List<String> getDevicetaskownerIDbyGridcode(String hospId, String gridCode) throws Exception
    {
        if ((commonMapper != null) && (hospId != null)&& (gridCode != null))
        {
            try {
                List<String> ls = commonMapper.getDevicetaskownerIDbyGridcode(hospId.replace(" ",""), gridCode.replace(" ",""));

                return ls;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据网格编号获取设备责任班组列表失败:"+e.getMessage());
            }
        }
        else
            return null;
    }

}
