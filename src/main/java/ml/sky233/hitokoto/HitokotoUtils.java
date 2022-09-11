package ml.sky233.hitokoto;

import android.app.Activity;
import android.util.Log;

import android.widget.Switch;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;


import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import ml.sky233.hitokoto.EsonUtils;
import ml.sky233.hitokoto.Hitokoto;

public class HitokotoUtils {
    public static String url = "";
    public static String type = "";
    public static String token = "";
    public static String charset = "";
    public static Object likeObject = null;
    public static String likeString = "";
    public static int min_length = 0;
    public static int max_length = 0;

    public static void set(int set, CharSequence obj) {
        switch (set) {
            case 0:
                url = obj.toString();
                break;
            case 3:
                type = obj.toString();
                break;
            case 2:
                charset = obj.toString();
                break;
            case 1:
                min_length = Integer.valueOf(obj.toString()).intValue();
                break;
            case 4:
                max_length = Integer.valueOf(obj.toString()).intValue();
                break;
            case 5:
                token = obj.toString();
                break;
        }
    }

    public static String[] getHitokoto() {
        String url1 = url;
        if (url1.equals("")) url1 = Hitokoto.URL_DEFAULT;//防止没有设置请求接口
        String url_post = url1;

        //设置接口参数
        if (type.equals("")) url1 = url1 + "?c=" + Hitokoto.TYPE_DEFAULT;//如果没有类型则选择默认
        if (!type.equals("")) url1 = url1 + "?c=" + type;//选择自定义类型
        if (!charset.equals("")) url1 = url1 + "&charset=" + charset;//选择编码,建议默认UTF-8
        if (max_length != 0) url1 = url1 + "&max_length=" + String.valueOf(max_length);//选择最大长度,默认30
        if (min_length != 0) url1 = url1 + "&min_length=" + String.valueOf(min_length);//选择最小长度,默认0
        d(url1);

        OkHttpClient okClient = new OkHttpClient();
        String[] str_list = new String[12];
        String str = "";
        String[] type = {"id", "uid", "hitokoto", "type", "from", "from_who", "creator", "creator_uid", "reviewer", "commit_from", "created_at", "length",};
        Request request = new Request.Builder().url(url1).build();
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
            str_list[a] = EsonUtils.getObjectText(obj, type[a]);
        }
        return str_list;
    }


    //登录一言
    public static String[] login(String email, String password) {
        String str = "";
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()//构建请求Body，数据类型为application/x-www-form-urlencoded
                .add("email", email)
                .add("password", password)
                .build();
        Request postRequest = new Request.Builder()
                .url("https://hitokoto.cn/api/restful/v1/auth/login")//请求接口
                .post(requestBody)//post请求
                .build();
        try {
            Response response = client.newCall(postRequest).execute();
            str = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object obj = EsonUtils.toObject(str);
        String[] strs = new String[4];
        String status = EsonUtils.getObjectText(obj, "status");
        d(status);
        switch (status) {
            case "200":
                obj = EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0);
                strs[0] = status;//状态
                strs[1] = EsonUtils.getObjectText(obj, "name");//用户名
                strs[2] = EsonUtils.getObjectText(obj, "email");//邮箱
                strs[3] = EsonUtils.getObjectText(obj, "name");//账号id
                strs[4] = EsonUtils.getObjectText(obj, "token");
                ;//令牌
                break;
            default:
                strs[0] = status;
                break;
        }
        token = strs[4];
        return strs;
    }

    //注册账号
    public static String register(String email, String password, String name) {
        OkHttpClient client = new OkHttpClient();
        String str = "";
        RequestBody requestBody = new FormBody.Builder()//构建请求Body，数据类型为application/x-www-form-urlencoded
                .add("email", email)
                .add("password", password)
                .add("name", name)
                .build();
        Request postRequest = new Request.Builder()
                .url("https://hitokoto.cn/api/restful/v1/auth/register")//请求接口
                .post(requestBody)//post请求
                .build();
        try {
            Response response = client.newCall(postRequest).execute();
            str = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object obj = EsonUtils.toObject(str);
        String status = EsonUtils.getObjectText(obj, "status");
        String token = "";
        switch (status) {
            case "200":
                token = EsonUtils.getObjectText(EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0), "token");
                break;
            default:
                token = status;
                break;
        }
        return token;
    }

    //喜欢句子
    public static String like(String uuid) {
        String str = "";
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()//构建请求Body
                .build();
        Request postRequest = new Request.Builder()
                .url("https://hitokoto.cn/api/restful/v1/like?token=" + token + "&sentence_uuid=" + uuid)//请求接口
                .post(requestBody)//post请求
                .build();
        try {
            Response response = client.newCall(postRequest).execute();
            str = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object obj = EsonUtils.toObject(str);
        String status = EsonUtils.getObjectText(obj, "status");
        return status;
    }

    //取消句子喜欢
    public static String unlike(String uuid) {
        String str = "";
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()//构建请求Body
                .build();
        Request postRequest = new Request.Builder()
                .url("https://hitokoto.cn/api/restful/v1/like/cancel?token=" + token + "&sentence_uuid=" + uuid)//请求接口
                .post(requestBody)//post请求
                .build();
        try {
            Response response = client.newCall(postRequest).execute();
            str = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object obj = EsonUtils.toObject(str);
        String status = EsonUtils.getObjectText(obj, "status");
        return status;
    }

    //获取用户信息
    public static String[] user() {
        String str = "";
        String[] type = {"id", "uid", "hitokoto", "type", "from", "from_who", "creator", "creator_uid", "reviewer", "commit_from", "created_at", "length",};
        OkHttpClient okClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://hitokoto.cn/api/restful/v1/user?token=" + token).build();
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
        Object obj = EsonUtils.toObject(str);
        String[] strs = new String[4];
        String status = EsonUtils.getObjectText(obj, "status");
        d(status);
        switch (status) {
            case "200":
                obj = EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0);
                strs[0] = status;//状态
                strs[1] = EsonUtils.getObjectText(obj, "name");//用户名
                strs[2] = EsonUtils.getObjectText(obj, "email");//邮箱
                strs[3] = EsonUtils.getObjectText(obj, "name");//账号id
                strs[4] = EsonUtils.getObjectText(obj, "token");
                ;//令牌
                break;
            default:
                strs[0] = status;
                break;
        }
        return strs;
    }

    public static String getToken() {
        return token;
    }

    //举报句子
    public static String report(String uuid, String comment) {
        OkHttpClient client = new OkHttpClient();
        String str = "";
        RequestBody requestBody = new FormBody.Builder()//构建请求Body，数据类型为application/x-www-form-urlencoded
                .add("comment", comment)
                .build();
        Request postRequest = new Request.Builder()
                .url("https://hitokoto.cn/api/restful/v1/hitokoto/" + uuid + "/report?token=" + token)//请求接口
                .post(requestBody)//post请求
                .build();
        try {
            Response response = client.newCall(postRequest).execute();
            str = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object obj = EsonUtils.toObject(str);
        return EsonUtils.getObjectText(obj, "status");
    }

    //刷新token
    public static String refreshToken() {
        OkHttpClient client = new OkHttpClient();
        String str = "";
        RequestBody requestBody = new FormBody.Builder()//构建请求Body，数据类型为application/x-www-form-urlencoded
                .add("_method", "PUT")
                .build();
        Request postRequest = new Request.Builder()
                .url("https://hitokoto.cn/api/restful/v1/user/token/refresh?token=" + token)//请求接口
                .post(requestBody)//post请求
                .build();
        try {
            Response response = client.newCall(postRequest).execute();
            str = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object obj = EsonUtils.toObject(str);
        String status = EsonUtils.getObjectText(obj, "status");
        switch (status) {
            case "200":
                token = EsonUtils.getObjectText(EsonUtils.getObject(EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0), "user"), "token");
                break;
        }
        return status;
    }

    //取用户喜欢的列表
    public static String getLike(int offset, int limit) {
        if (limit > 200 & limit < 20) limit = Hitokoto.USER_LIKE_LIMIT;
        if (offset < 0) offset = Hitokoto.USER_LIKE_OFFSET;

        String str = "";
        OkHttpClient okClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://hitokoto.cn/api/restful/v1/user?token=" + token).build();
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
        Object obj = EsonUtils.toObject(str);
        String status = EsonUtils.getObjectText(obj, "status");
        switch (status) {
            case "200":
                likeObject = EsonUtils.getArray(EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0), "collection");
                likeString = EsonUtils.getArrayText(EsonUtils.getArray(obj, "data"), 0);
                break;
        }
        return status;
    }

    public static Object getLikeObject() {
        return likeObject;
    }

    public static String getLikeString() {
        return likeString;
    }

//    private static int Lookfor(String str1, String str2, int start) {
//        return start >= 0 && start <= str1.length() && !"".equals(str1) && !"".equals(str2) ? str1.indexOf(str2, start) : -1;
//    }

    //自己用的一个Log工具
    public static void d(String str) {
        Log.d("Suiteki.test", str);
    }


}
