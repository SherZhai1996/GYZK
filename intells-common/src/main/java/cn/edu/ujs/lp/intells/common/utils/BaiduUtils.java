package cn.edu.ujs.lp.intells.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class BaiduUtils {

    public static final String defaultAddr = "贵州省";
    public static final String defaultProvinceName = "贵州省";
    public static final double defaultCenterX = 116.413384;
    public static final double defaultCenterY = 39.910925;
    public static final int defaulthospRadius = 250;
    public static final int defaultbuildingRadius = 100;
    public static final int defaultProvinceRadius = 2000000;

    //依据地址获取经纬度坐标
    public static String[] getCoordinate(String addr) throws Exception {
        String lng = null;//经度
        String lat = null;//纬度
        String address = null;

        try {
            address = java.net.URLEncoder.encode(addr, "UTF-8");
        }catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        String key = "tn3YLx4gA7ZmtZCHoUwXfnRHMtn3mN3X";
        String url = String .format("http://api.map.baidu.com/geocoding/v3?address=%s&output=json&ak=%s", address, key);
        URL myURL = null;
        URLConnection httpsConn = null;

        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        InputStreamReader insr = null;
        BufferedReader br = null;
        try {
            httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
            if (httpsConn != null) {
                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8");
                br = new BufferedReader(insr);
                String data = null;
                int count = 1;
                // while((data= br.readLine())!=null){
                if ((data= br.readLine())!=null) {
                    JSONObject jsStr = JSONObject.parseObject(data);
                    lng = jsStr.getJSONObject("result").getJSONObject("location").getBigDecimal("lng").toString();
                    lat = jsStr.getJSONObject("result").getJSONObject("location").getBigDecimal("lat").toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("获取指定地址的经纬度数据失败："+e.getMessage());
        } finally {
            if(insr!=null){
                insr.close();
            }
            if(br!=null){
                br.close();
            }
        }

        return new String[]{lng,lat};
    }


    /**
     * 计算两点之间真实距离
     * @return 米
     */
    public static double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        // 维度
        double lat1 = (Math.PI / 180) * latitude1;
        double lat2 = (Math.PI / 180) * latitude2;

        // 经度
        double lon1 = (Math.PI / 180) * longitude1;
        double lon2 = (Math.PI / 180) * longitude2;

        // 地球半径
        double R = 6371;

        // 两点间距离 km，如果想要米的话，结果*1000就可以了
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;

        return d * 1000;
    }

}
