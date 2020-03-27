package cn.edu.ujs.lp.intells.common.utils;

import java.util.Properties;

/**
 * 操作系统工具类
 */
public class OSUtils {

    /**
     * 判断当前操作系统是否Linux
     *
     * @return
     */
    public static boolean isOSLinux() {
        Properties prop = System.getProperties();

        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("linux") > -1) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 获取存在文件公共路径
     * @return
     */
    public static String getBasefilepath()
    {
        String baseFilepath;

        if (OSUtils.isOSLinux())
            baseFilepath =  "/root/DBfiles";
        else
            baseFilepath =  System.getProperty("user.dir") + "\\DBfiles";

        return baseFilepath;
    }

    /**
     * 获取设备二维码标签PDF模板文件
     * @return
     */
    public static String getDevicePDFtemplatefile()
    {
        String baseFilepath;

        if (OSUtils.isOSLinux())
            baseFilepath =  "/root/DBfiles/PDF/deviceqr.pdf";
        else
            baseFilepath =  System.getProperty("user.dir") + "\\DBfiles\\PDF\\deviceqr.pdf";

        return baseFilepath;
    }

    /**
     * 获取用户缺省的logo图片文件相对路径名
     * @return
     */
    public static String getUserlogopicfile()
    {
        String filepath;

        if (OSUtils.isOSLinux())
            filepath =  "/system/user.png";
        else
            filepath =  "\\system\\user.png";

        return filepath;
    }

    /**
     * 获取医院的地图显示logo文件相对路径名
     * @return
     */
    public static String getHosplogoiconfile()
    {
        String filepath;

        if (OSUtils.isOSLinux())
            filepath =  "/system/hospital.ico";
        else
            filepath =  "\\system\\hospital.ico";

        return filepath;
    }

    /**
     * 获取医院的缺省logo图片文件相对路径名
     * @return
     */
    public static String getHosplogopicfile()
    {
        String filepath;

        if (OSUtils.isOSLinux())
            filepath =  "/system/hosplogo.png";
        else
            filepath =  "\\system\\hosplogo.png";

        return filepath;
    }

    /**
     * 获取医院的缺省标志性图片文件相对路径名
     * @return
     */
    public static String getHosppicfile()
    {
        String filepath;

        if (OSUtils.isOSLinux())
            filepath =  "/system/hospital.png";
        else
            filepath =  "\\system\\hospital.png";

        return filepath;
    }

    /**
     * 获取网格二维码标签PDF模板文件
     * @return
     */
    public static String getGridPDFtemplatefile()
    {
        String baseFilepath;

        if (OSUtils.isOSLinux())
            baseFilepath =  "/root/DBfiles/PDF/gridqr.pdf";
        else
            baseFilepath =  System.getProperty("user.dir") + "\\DBfiles\\PDF\\gridqr.pdf";

        return baseFilepath;
    }


    /**
     * 获取二维码标签PDF中的微信公众号logo图片文件名
     * @return
     */
    public static String getPDFlogofile()
    {
        String baseFilepath;

        if (OSUtils.isOSLinux())
            baseFilepath =  "/root/DBfiles/PDF/logo.png";
        else
            baseFilepath =  System.getProperty("user.dir") + "\\DBfiles\\PDF\\logo.png";

        return baseFilepath;
    }

    /**
     * 获取二维码标签PDF中的applogo图片文件名
     * @return
     */
    public static String getPDFapplogofile()
    {
        String baseFilepath;

        if (OSUtils.isOSLinux())
            baseFilepath =  "/root/DBfiles/PDF/applogo.png";
        else
            baseFilepath =  System.getProperty("user.dir") + "\\DBfiles\\PDF\\applogo.png";

        return baseFilepath;
    }

    /**
     * 获取Excel导入模板的文件名
     * @return
     */
    public static String getGridtemplatefile()
    {
        String baseFilepath;

        if (OSUtils.isOSLinux())
            baseFilepath =  "/root/DBfiles/Template/gridtemplate.xlsx";
        else
            baseFilepath =  System.getProperty("user.dir") + "\\DBfiles\\Template\\gridtemplate.xlsx";

        return baseFilepath;
    }

    public static String getDepttemplatefile()
    {
        String baseFilepath;

        if (OSUtils.isOSLinux())
            baseFilepath =  "/root/DBfiles/Template/depttemplate.xlsx";
        else
            baseFilepath =  System.getProperty("user.dir") + "\\DBfiles\\Template\\depttemplate.xlsx";

        return baseFilepath;
    }

    public static String getStafftemplatefile()
    {
        String baseFilepath;

        if (OSUtils.isOSLinux())
            baseFilepath =  "/root/DBfiles/Template/stafftemplate.xlsx";
        else
            baseFilepath =  System.getProperty("user.dir") + "\\DBfiles\\Template\\stafftemplate.xlsx";

        return baseFilepath;
    }

    public static String getExcompanytemplatefile()
    {
        String baseFilepath;

        if (OSUtils.isOSLinux())
            baseFilepath =  "/root/DBfiles/Template/excompanytemplate.xlsx";
        else
            baseFilepath =  System.getProperty("user.dir") + "\\DBfiles\\Template\\excompanytemplate.xlsx";

        return baseFilepath;
    }

    public static String getExservicetemplatefile()
    {
        String baseFilepath;

        if (OSUtils.isOSLinux())
            baseFilepath =  "/root/DBfiles/Template/exservicetemplate.xlsx";
        else
            baseFilepath =  System.getProperty("user.dir") + "\\DBfiles\\Template\\exservicetemplate.xlsx";

        return baseFilepath;
    }

    public static String getExteamtemplatefile()
    {
        String baseFilepath;

        if (OSUtils.isOSLinux())
            baseFilepath =  "/root/DBfiles/Template/exteamtemplate.xlsx";
        else
            baseFilepath =  System.getProperty("user.dir") + "\\DBfiles\\Template\\exteamtemplate.xlsx";

        return baseFilepath;
    }

    public static String getExstafftemplatefile()
    {
        String baseFilepath;

        if (OSUtils.isOSLinux())
            baseFilepath =  "/root/DBfiles/Template/exstafftemplate.xlsx";
        else
            baseFilepath =  System.getProperty("user.dir") + "\\DBfiles\\Template\\exstafftemplate.xlsx";

        return baseFilepath;
    }

    public static String getDevicecategorytemplatefile()
    {
        String baseFilepath;

        if (OSUtils.isOSLinux())
            baseFilepath =  "/root/DBfiles/Template/devicecategorytemplate.xlsx";
        else
            baseFilepath =  System.getProperty("user.dir") + "\\DBfiles\\Template\\devicecategorytemplate.xlsx";

        return baseFilepath;
    }

    public static String getDevicebilltemplatefile()
    {
        String baseFilepath;

        if (OSUtils.isOSLinux())
            baseFilepath =  "/root/DBfiles/Template/devicebilltemplate.xlsx";
        else
            baseFilepath =  System.getProperty("user.dir") + "\\DBfiles\\Template\\devicebilltemplate.xlsx";

        return baseFilepath;
    }

    /**
     * 获取系统用户的Excel导入模板文件名
     * @return
     */
    public static String getUsertemplate()
    {
        String baseFilepath;

        if (OSUtils.isOSLinux())
            baseFilepath =  "/root/DBfiles/Template/usertemplate.xlsx";
        else
            baseFilepath =  System.getProperty("user.dir") + "\\DBfiles\\Template\\usertemplate.xlsx";

        return baseFilepath;
    }
}
