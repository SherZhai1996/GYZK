package cn.edu.ujs.lp.intells.common.service;

import cn.edu.ujs.lp.intells.common.entity.Common.DataDictionary;
import cn.edu.ujs.lp.intells.common.entity.Common.DataDictionaryList;
import cn.edu.ujs.lp.intells.common.utils.BaiduUtils;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import cn.edu.ujs.lp.intells.common.utils.RedisUtil;
import cn.edu.ujs.lp.intells.common.dao.DataDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DataDictionaryService {

    @Autowired
    private DataDictionaryRepository dataDictionaryRepository;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取指定地址经纬度
     * @param addr
     * @return
     * @throws Exception
     */
    public String[] getCoordinate(String addr) throws Exception
    {
        String[] result = null;

        if ((addr != null) && (addr != ""))
        {
            try {
                result = BaiduUtils.getCoordinate(addr);
            }
            catch (IOException e)
            {
                throw new Exception("获取指定地址经纬度失败："+e.getMessage());
            }
        }

        return result;
    }

    public void initialDataDictionary() {

        //加载db_accessoryclass_type数据字典
        List tmp = dataDictionaryRepository.get_db_accessoryclass_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_accessoryclass_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_accessoryclass_type", dataDictionaryList);
        }

        //加载db_grid_placeclass_type数据字典
        tmp = dataDictionaryRepository.get_db_grid_placeclass_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_grid_placeclass_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_grid_placeclass_type", dataDictionaryList);
        }

        //加载db_restaurantcertificate_type数据字典
        tmp = dataDictionaryRepository.get_db_restaurantcertificate_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_restaurantcertificate_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_restaurantcertificate_type", dataDictionaryList);
        }

        //加载get_db_tasksheetcarryingtools_type数据字典
        tmp = dataDictionaryRepository.get_db_tasksheetcarryingtools_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_tasksheetcarryingtools_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_tasksheetcarryingtools_type", dataDictionaryList);
        }

        //加载get_db_devicemaintain_period_type数据字典
        tmp = dataDictionaryRepository.get_db_devicemaintain_period_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_devicemaintain_period_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_devicemaintain_period_type", dataDictionaryList);
        }

        //加载get_db_deviceinspectionitem_type数据字典
        tmp = dataDictionaryRepository.get_db_deviceinspectionitem_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_deviceinspectionitem_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_deviceinspectionitem_type", dataDictionaryList);
        }

        //加载get_db_devicemaintainresponsibleunit_type数据字典
        tmp = dataDictionaryRepository.get_db_devicemaintainresponsibleunit_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_devicemaintainresponsibleunit_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_devicemaintainresponsibleunit_type", dataDictionaryList);
        }

        //加载get_db_devicemaintain_type数据字典
        tmp = dataDictionaryRepository.get_db_devicemaintain_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_devicemaintain_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_devicemaintain_type", dataDictionaryList);
        }

        //加载db_assetclass68_type数据字典
        tmp = dataDictionaryRepository.get_db_assetclass68_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_assetclass68_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_assetclass68_type", dataDictionaryList);
        }

        //加载db_assetclass_type数据字典
        tmp = dataDictionaryRepository.get_db_assetclass_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_assetclass_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_assetclass_type", dataDictionaryList);
        }

        //加载db_assetfinancialclass_type数据字典
        tmp = dataDictionaryRepository.get_db_assetfinancialclass_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_assetfinancialclass_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_assetfinancialclass_type", dataDictionaryList);
        }

        //加载db_assetsource_type数据字典
        tmp = dataDictionaryRepository.get_db_assetsource_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_assetsource_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_assetsource_type", dataDictionaryList);
        }

        //加载db_assetsubclass_type数据字典
        tmp = dataDictionaryRepository.get_db_assetsubclass_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_assetsubclass_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_assetsubclass_type", dataDictionaryList);
        }

        //加载db_classattr_type数据字典
        tmp = dataDictionaryRepository.get_db_classattr_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_classattr_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_classattr_type", dataDictionaryList);
        }

        //加载db_emergencyclass_type数据字典
        tmp = dataDictionaryRepository.get_db_emergencyclass_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_emergencyclass_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_emergencyclass_type", dataDictionaryList);
        }

        //加载db_extern_service_type数据字典
        tmp = dataDictionaryRepository.get_db_extern_service_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_extern_service_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_extern_service_type", dataDictionaryList);
        }

        //加载db_inspection_conclusion_type数据字典
        tmp = dataDictionaryRepository.get_db_inspection_conclusion_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_inspection_conclusion_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_inspection_conclusion_type", dataDictionaryList);
        }

        //加载db_inspection_period_type数据字典
        tmp = dataDictionaryRepository.get_db_inspection_period_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_inspection_period_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_inspection_period_type", dataDictionaryList);
        }

        //加载db_job_state数据字典
        tmp = dataDictionaryRepository.get_db_job_state();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_job_state");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_job_state", dataDictionaryList);
        }

        //加载db_marriage_state数据字典
        tmp = dataDictionaryRepository.get_db_marriage_state();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_marriage_state");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_marriage_state", dataDictionaryList);
        }

        //加载db_medicalinstrumentsclass_type数据字典
        tmp = dataDictionaryRepository.get_db_medicalinstrumentsclass_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_medicalinstrumentsclass_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_medicalinstrumentsclass_type", dataDictionaryList);
        }

        //加载db_skillcertificate_type数据字典
        tmp = dataDictionaryRepository.get_db_skillcertificate_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_skillcertificate_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_skillcertificate_type", dataDictionaryList);
        }

        //加载db_specialclass_type数据字典
        tmp = dataDictionaryRepository.get_db_specialclass_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_specialclass_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_specialclass_type", dataDictionaryList);
        }

        //加载db_sex数据字典
        tmp = dataDictionaryRepository.get_db_sex();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_sex");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_sex", dataDictionaryList);
        }

        //加载db_nation数据字典
        tmp = dataDictionaryRepository.get_db_nation();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_nation");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_nation", dataDictionaryList);
        }

        //加载db_staff_adminis _position数据字典
        tmp = dataDictionaryRepository.get_db_staff_adminis_position();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_staff_adminis_position");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_staff_adminis_position", dataDictionaryList);
        }

        //加载db_staff_degree数据字典
        tmp = dataDictionaryRepository.get_db_staff_degree();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_staff_degree");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_staff_degree", dataDictionaryList);
        }

        //加载db_staff_education_positon数据字典
        tmp = dataDictionaryRepository.get_db_staff_education_positon();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_staff_education_positon");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_staff_education_positon", dataDictionaryList);
        }

        //加载db_staff_technical_position数据字典
        tmp = dataDictionaryRepository.get_db_staff_technical_position();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_staff_technical_position");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_staff_technical_position", dataDictionaryList);
        }

        //加载db_staff_technical_position数据字典
        tmp = dataDictionaryRepository.get_db_staff_tutor_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_staff_tutor_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_staff_tutor_type", dataDictionaryList);
        }

        //加载db_tasksheet_type数据字典
        tmp = dataDictionaryRepository.get_db_tasksheet_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_tasksheet_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_tasksheet_type", dataDictionaryList);
        }

        //加载db_tasksheetdecchannel_type数据字典
        tmp = dataDictionaryRepository.get_db_tasksheetdecchannel_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_tasksheetdecchannel_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_tasksheetdecchannel_type", dataDictionaryList);
        }

        //加载db_tasksheetdecsource_type数据字典
        tmp = dataDictionaryRepository.get_db_tasksheetdecsource_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_tasksheetdecsource_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_tasksheetdecsource_type", dataDictionaryList);
        }

        //加载db_tasksheeteval_type数据字典
        tmp = dataDictionaryRepository.get_db_tasksheeteval_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_tasksheeteval_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_tasksheeteval_type", dataDictionaryList);
        }

        //加载 db_tasksheetreponsetime_type数据字典
        tmp = dataDictionaryRepository.get_db_tasksheetreponsetime_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_tasksheetreponsetime_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_tasksheetreponsetime_type", dataDictionaryList);
        }

        //加载db_tasksheetstate_type数据字典
        tmp = dataDictionaryRepository.get_db_tasksheetstate_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_tasksheetstate_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_tasksheetstate_type", dataDictionaryList);
        }

        //加载db_tasksheettrackstate_type数据字典
        tmp = dataDictionaryRepository.get_db_tasksheettrackstate_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_tasksheettrackstate_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_tasksheettrackstate_type", dataDictionaryList);
        }

        //加载db_tasksheeturgencydegree_type数据字典
        tmp = dataDictionaryRepository.get_db_tasksheeturgencydegree_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_tasksheeturgencydegree_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_tasksheeturgencydegree_type", dataDictionaryList);
        }

        //加载db_technicalwork_type数据字典
        tmp = dataDictionaryRepository.get_db_technicalwork_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_technicalwork_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_technicalwork_type", dataDictionaryList);
        }

        //加载db_use_status_type数据字典
        tmp = dataDictionaryRepository.get_db_use_status_type();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_use_status_type");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_use_status_type", dataDictionaryList);
        }

        //加载db_workstate数据字典
        tmp = dataDictionaryRepository.get_db_workstate();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_workstate");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_workstate", dataDictionaryList);
        }

        //加载db_credible数据字典
        tmp = dataDictionaryRepository.get_db_credible();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_credible");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_credible", dataDictionaryList);
        }

        //加载db_inspectionmode数据字典
        tmp = dataDictionaryRepository.get_db_County();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_County");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_County", dataDictionaryList);
        }

        //加载db_inspectionmode数据字典
        tmp = dataDictionaryRepository.get_db_District();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_District");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_District", dataDictionaryList);
        }

        //加载db_inspectionmode数据字典
        tmp = dataDictionaryRepository.get_db_Province();

        if ((tmp != null) && (tmp.size() > 0)) {
            DataDictionaryList dataDictionaryList = new DataDictionaryList("db_Province");

            insertDataDictionaryList(tmp, dataDictionaryList);

            if ((dataDictionaryList != null) && (dataDictionaryList.getCount() > 0))
                redisUtil.saveRedis("db_Province", dataDictionaryList);
        }

    }

    private boolean insertDataDictionaryList(List datas,DataDictionaryList dataDictionaryList)
    {
        boolean rt = false ;

        try {
            if ((datas != null) && (datas.size() > 0) && (dataDictionaryList != null)) {
                for (int i = 0; i < datas.size(); i++) {
                    Object[] tp = (Object[]) datas.get(i);
                    if ((tp != null) && (tp.length >= 2)) {
                        dataDictionaryList.Add((String) tp[0], (String) tp[1]);
                    }
                }
            }

            rt = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            rt = false ;
        }

        return rt;
    }

    /*
    获取指定类型的数据字典
     */
    public DataDictionaryList getDataDictionary(String typename)
    {
        DataDictionaryList tmp = null;

        try {
            if (!ExcelUtils.isNullofString(typename.trim()))
                tmp = (DataDictionaryList) redisUtil.getRedis(typename);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            tmp = null;
        }

        return tmp;
    }

    /**
     * 获取过滤后的字典内容, ID前缀过滤
     * @param typename
     * @param idp
     * @return
     */
    public DataDictionaryList getDataDictionarylikeP(String typename,String idp)
    {
        DataDictionaryList tmp = null;
        int size = 0;

        try {
            if (!ExcelUtils.isNullofString(typename.trim())) {
                tmp = (DataDictionaryList) redisUtil.getRedis(typename);

                if ((tmp != null) && (tmp.getDictionaryList() != null) && (tmp.getDictionaryList().size() > 0)) {
                    if (!ExcelUtils.isNullofString(idp.trim())) {
                        size = tmp.getDictionaryList().size();
                        for (int i=0;i<size;i++) {
                            DataDictionary dd = tmp.getDictionaryList().get(i);

                            if (dd.getId().substring(0, idp.length()).compareTo(idp.trim()) != 0) {
                                tmp.RemovebyID(dd.getId());
                                i--;
                                size = tmp.getDictionaryList().size();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            tmp = null;
        }

        return tmp;
    }
}
