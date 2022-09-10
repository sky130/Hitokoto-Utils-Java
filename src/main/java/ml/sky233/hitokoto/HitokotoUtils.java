package ml.sky233.hitokoto;

import android.app.Activity;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import ml.sky233.hitokoto.EsonUtils;
import ml.sky233.hitokoto.Hitokoto;

public class HitokotoUtils {

    public static String[] getHitokoto(String Type, String charset, int min_length, int max_length, Activity activity) {
        if (getSet("add", activity).equals("")) saveSet("add", Hitokoto.ADD_DEFAULT, activity);//防止没有设置请求接口
        String url = getSet("add", activity) + "?c=" + Type;//设置接口参数

        //设置接口参数
        if (!charset.equals("")) url = url + "&charset=" + charset;
        if (max_length != 0) url = url + "&max_length=" + String.valueOf(max_length);
        if (min_length != 0) url = url + "&min_length=" + String.valueOf(min_length);
        d(url);

        OkHttpClient okClient = new OkHttpClient();
        String[] str_list = new String[12];
        String str = "";
        String[] type = {"id", "uid","hitokoto", "type","from", "from_who", "creator", "creator_uid", "reviewer", "commit_from", "created_at", "length",};
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = okClient.newCall(request).execute();
            if (response.isSuccessful()) {
                str = response.body().string();
            } else {
                String code = String.valueOf(response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        d(str);
        Object obj = EsonUtils.toObject(str);
        for (int a = 0; 12 > a; a++) {
            str_list[a] = EsonUtils.getObjectText(obj,type[a]);
        }
        d(String.valueOf(str_list));
        return str_list;
    }

    public static void d(String str) {
        Log.d("Suiteki.test", str);
    }

    //设置响应链接
    public static void setRequestAddress(String add, Activity activity) {
        saveSet("add", add, activity);
    }

    private static boolean saveSet(String name, String value, Activity activity) {
        boolean result = true;
        String pkg = activity.getPackageName();
        Properties properties = new Properties();
        String file = "/data/data/" + pkg + "/setting.xml";
        try {
            FileInputStream i = new FileInputStream(file);
            properties.load(i);
        } catch (Exception var7) {
            var7.printStackTrace();
            result = false;
        }
        properties.setProperty(name, value);
        try {
            FileOutputStream o = new FileOutputStream(file, false);
            properties.store(o, "");
        } catch (Exception var6) {
            var6.printStackTrace();
            result = false;
        }
        return result;
    }

    private static String getSet(String name, Activity activity) {
        Properties properties = new Properties();
        String pkg = activity.getPackageName();
        String file = "/data/data/" + pkg + "/setting.xml";
        try {
            FileInputStream i = new FileInputStream(file);
            properties.load(i);
        } catch (Exception var4) {
            var4.printStackTrace();
            return "";
        }

        String value = properties.getProperty(name, "");
        return value != null ? value : "";
    }



}
