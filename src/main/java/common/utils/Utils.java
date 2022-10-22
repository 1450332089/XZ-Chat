package common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    //获取时间
    public static String getDate(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
    //对象转换为json字符串
    public static String getJsonString(Object object){
        String jsonOutput= JSON.toJSONString(object);
        return jsonOutput;
    }
    //对象转换为json对象
    public static JSONObject getJsonObject(Object object){
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
        return jsonObject;
    }

    //字符串转json
    public static JSONObject String2Json(String str){
        return JSON.parseObject(str);
    }
    //json转字符串
    public static String Json2String(JSONObject jsonObject){
        return JSON.toJSONString(jsonObject);
    }

    //获取ImageIcon
    public static ImageIcon getImageIcon(int id){
        URL url = Utils.class.getResource("/emoji/"+id+".gif");
        return new ImageIcon(url);
    }
}
