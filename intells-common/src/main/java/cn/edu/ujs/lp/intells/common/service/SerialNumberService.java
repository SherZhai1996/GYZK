package cn.edu.ujs.lp.intells.common.service;
import cn.edu.ujs.lp.intells.common.dao.SerialNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
编码产生类
 */
@Service
public class SerialNumberService {

    @Autowired
    private SerialNumberRepository serialNumberRepository;

    /**
    获取指定ID的医院的编号
     */
    public String GetHospCodebyHospID(String hospId) throws Exception
    {
        String result = null;

        try {
            List m_ll = serialNumberRepository.GetHospCodebyHospID(hospId);

            if ((m_ll != null) && (m_ll.size()>0))
            {
                result = m_ll.get(0).toString().replace("Y", "");
                result = result.replace("y", "");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取医院编码失败:"+e.getMessage());
        }

        return result;
    }

    /**
    获取一个序列号
     */
    private String getSerialNumber(String typename,int codelen) throws Exception
    {
        String result = null;

        try {
            List m_sn = serialNumberRepository.get(typename, codelen);

            if ((m_sn != null) && (m_sn.size() > 0)) {
                result = m_sn.get(0).toString();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取指定类别序号失败:"+e.getMessage());
        }

        return result;
    }

    /**
    复位指定的代码初值，即重新设置为1
     */
    public int reset(String typename) throws Exception
    {
        int result = -1;

        try {
            result = serialNumberRepository.reset(typename);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("指定类别序号复位失败："+e.getMessage());
        }

        return result;
    }

    /**
    获取医院的一个新编码
     */
    public String getHospCode() throws Exception
    {
        String rt = null;

        try {
            rt = getSerialNumber("医院编号", 4);

            if ((rt != null) && (rt.length() > 0))
                rt = "YY" + rt;
            else
                rt = null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取医院编号失败:"+e.getMessage());
        }

        return rt;
    }

    /**
     * 获取指定医院的编号四位数字部分
     * @param hospId
     * @return
     * @throws Exception
     */
    private String getDigitalHospCode(String hospId) throws Exception
    {
        String ct = null;

        try {
            if (hospId != null) {
                ct = GetHospCodebyHospID(hospId);

                if ((ct != null) && (ct.length() > 0)) {
                    ct = ct.replace("Y", "");
                    ct = ct.replace("y", "");
                } else {
                    ct = "0000";
                }
            } else
                ct = "0000";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取指定医院的编号数字部分失败:"+e.getMessage());
        }

        return ct;
    }

    /**
    获取一个新的网格区域编码，顶级为4位医院编码，此时可以为null
     */
    public String getGridCode(String hospId,String supergridcode) throws Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetGridCode(hospCode,supergridcode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取网格区域编码失败:" + e.getMessage());
            }
        }

        return rt;
    }

    /**
     获取一个新的科室编码，编码规则:DT(医院)nnnn(一级)DDDD(二级)LLLL(三级)QQQQ, 顶级为4位医院编码，此时可以为null
     */
    public String getDTCode(String hospId,String supercode) throws Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetDTSerialNumber(hospCode,supercode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取科室编码失败:" + e.getMessage());
            }
        }

        return rt;
    }

    /**
     * 根据获取服务事项编码, 服务事项分类code，编码规则:ST(医院)nnnn(一级)DDDD(二级)LLLL(三级)QQQQ
     * @param hospId
     * @param supercode
     * @return
     * @throws Exception
     */
    public String getSTCode(String hospId,String supercode) throws Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetSTSerialNumber(hospCode,supercode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取服务事项编码失败:" + e.getMessage());
            }
        }

        return rt;
    }

    /**
     * 根据获取耗材编码, 耗材分类code，编码规则:MT(医院)nnnn(一级)DDDD(二级)LLLL(三级)QQQQ
     * @param hospId
     * @param supercode
     * @return
     * @throws Exception
     */
    public String getMTCode(String hospId,String supercode) throws Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetMTSerialNumber(hospCode,supercode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取耗材编码失败:" + e.getMessage());
            }
        }

        return rt;
    }


    /**
     * 根据获取外委公司服务班组编码, 编码规则:ET(医院)nnnn(一级)DDDD(二级)LLLL(三级)QQQQ
     * @param hospId
     * @param supercode
     * @return
     * @throws Exception
     */
    public String getETCode(String hospId,String supercode) throws Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetETSerialNumber(hospCode,supercode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取外委公司服务班组编码失败:" + e.getMessage());
            }
        }

        return rt;
    }

    /**
    获取一个新的投诉工单编码，医院编码可以为null
    投诉工单工单编号,规则：TS+yyyy(医院编码)+YYYYMMDD+nnnnnn
     */
    public String getTSsheetCode(String hospId) throws Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetTSSerialNumber(hospCode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取投诉工单编码失败:" + e.getMessage());
            }
        }

        return rt ;
    }

    /**
     获取一个新的综合维修工单编码，医院编码可以为null
     综合维修工单编号,规则：ZW+yyyy(医院编码)+YYYYMMDD+nnnnnn
      */
    public String getZWsheetCode(String hospId) throws Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetZWSerialNumber(hospCode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取综合维修工单编号失败:" + e.getMessage());
            }
        }

        return rt;
    }

    /**
    获取一个新的应急保洁工单编码，医院编码可以为null
    应急保洁工单编号,规则：BJ+yyyy(医院编码)+YYYYMMDD+nnnnnn
    */
    public String getBJsheetCode(String hospId) throws Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetBJSerialNumber(hospCode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取应急保洁工单编号失败:" + e.getMessage());
            }
        }

        return rt;
    }

    /**
    获取一个新的运送服务工单编码，医院编码可以为null
    运送服务工单编号,规则：YS+yyyy(医院编码)+YYYYMMDD+nnnnnn
    */
    public String getYSsheetCode(String hospId) throws Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetYSSerialNumber(hospCode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取工勤搬运工单编号失败:" + e.getMessage());
            }
        }

        return rt;
    }

    /**
    获取一个新的保洁巡检工单编码，医院编码可以为null
    保洁巡检工单编号,规则：BX+yyyy(医院编码)+YYYYMMDD+nnnnnn
    */
    public String getBXsheetCode(String hospId) throws Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetBXSerialNumber(hospCode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取保洁巡检工单编号失败:" + e.getMessage());
            }
        }

        return rt;
    }

    /**
    获取一个新的保安巡检工单编码，医院编码可以为null
    保安巡检工单编号,规则：BA+yyyy(医院编码)+YYYYMMDD+nnnnnn
    */
    public String getBAsheetCode(String hospId) throws Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetBASerialNumber(hospCode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取保安巡检工单编号失败:" + e.getMessage());
            }
        }

        return rt;
    }

    /**
    获取一个新的设备巡检工单编码，医院编码可以为null
    设备巡检工单编号,规则：SX+yyyy(医院编码)+YYYYMMDD+nnnnnn
    */
    public String getSXsheetCode(String hospId) throws Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetSXSerialNumber(hospCode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取设备巡检工单编号失败:" + e.getMessage());
            }
        }

        return rt;
    }

    /**
    获取一个新的设备保养工单编码，医院编码可以为null
    设备保养工单编号,规则：BY+yyyy(医院编码)+YYYYMMDD+nnnnnn
    */
    public String getBYsheetCode(String hospId) throws Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetBYSerialNumber(hospCode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取设备保养工单编号失败:" + e.getMessage());
            }
        }

        return rt;
    }

    /**
    获取一个新的设备维修工单编码，医院编码可以为null
    设备维修工单编号,规则：WX+yyyy(医院编码)+YYYYMMDD+nnnnnn
    */
    public String getWXsheetCode(String hospId) throws  Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetWXSerialNumber(hospCode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取设备维修工单编号失败:" + e.getMessage());
            }
        }

        return rt;
    }

    /**
    获取一个新的设备类别编码，顶级编号为4位医院编号，此时可以为null
    设备类别分类编号,规则：SB+yyyy(医院编码)+上一级编号+nnn(本级编号)
    */
    public String getSBcategoryCode(String hospId,String supergridcode) throws Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetSBcategoryCode(hospCode,supergridcode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取设备类别编码失败:" + e.getMessage());
            }
        }

        return rt;
    }

    /**
    获取一个新的设备编码
    设备编号，编号规则：设备类别代码+nnnn(设备编号)
    */
    public String getSBbillCode(String hospId,String categoryCode) throws Exception
    {
        String rt =null;
        String hospCode = null;

        if (serialNumberRepository != null) {
            try {
                hospCode = GetHospCodebyHospID(hospId);

                if (hospCode != null)
                {
                    List<String> lst = serialNumberRepository.GetSBbillCode(hospCode,categoryCode);
                    if ((lst!= null)&&(lst.size()>0)) rt = lst.get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("获取设备编码失败:" + e.getMessage());
            }
        }

        return rt;
    }

}
