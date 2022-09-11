package ml.sky233.hitokoto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EsonUtils {
    //叒一个对你们来说没什么用的函数
    public static Object toObject(String var1) {
        try {
            JSONObject var2 = new JSONObject(var1);
            return var2;
        } catch (JSONException var3) {
            return null;
        }
    }

    //叕一个对你们来说没什么用的函数
    public static Object getObject(Object var1, String var2) {
        JSONObject var3 = (JSONObject) var1;
        if (var3 == null) {
            return null;
        } else {
            try {
                JSONObject var4 = var3.getJSONObject(var2);
                return var4;
            } catch (JSONException var5) {
                return null;
            }
        }
    }

    //叕又一个对你们来说没什么用的函数
    public static String getObjectText(Object var1, String var2) {
        JSONObject var3 = (JSONObject) var1;
        if (var3 == null) {
            return "";
        } else {
            try {
                String var4 = var3.getString(var2);
                return var4;
            } catch (JSONException var5) {
                return "";
            }
        }
    }

    //一个对你们来说没什么用的函数
    public static Object getArray(Object var1, String var2) {
        JSONObject var3 = (JSONObject) var1;
        if (var3 == null) {
            return null;
        } else {
            try {
                JSONArray var4 = var3.getJSONArray(var2);
                return var4;
            } catch (JSONException var5) {
                return null;
            }
        }
    }

    //一个对你们来说没什么用的函数
    public static int getArrayLength(Object var1) {
        JSONArray var2 = (JSONArray) var1;
        return var2 == null ? 0 : var2.length();
    }

    //一个对你们来说没什么用的函数
    public static Object getArrayObject(Object var1, int var2) {
        JSONArray var3 = (JSONArray) var1;
        if (var3 == null) {
            return null;
        } else {
            try {
                JSONObject var4 = var3.getJSONObject(var2);
                return var4;
            } catch (JSONException var5) {
                return null;
            }
        }
    }
    
    //一个对你们来说没什么用的函数
    public static String getArrayText(Object var1, int var2) {
        JSONArray var3 = (JSONArray)var1;
        if (var3 == null) {
            return "";
        } else {
            try {
                String var4 = var3.getString(var2);
                return var4;
            } catch (JSONException var5) {
                return "";
            }
        }
    }
}
