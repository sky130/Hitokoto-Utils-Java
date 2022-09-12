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
    public static String[] hitokoto_type = {"", "id", "uid", "hitokoto", "type", "from", "from_who", "creator", "creator_uid", "reviewer", "commit_from", "created_at", "length",};
    public static String[] setting_type = {"email_notification_global", "email_notification_hitokoto_appended", "email_notification_hitokoto_reviewed", "email_notification_poll_created", "email_notification_poll_result", "email_notification_poll_daily_report",};
    public static String[] summary_type = {"total", "pending", "refuse", "accept"};
    public static String[] setting = {"", "", "", "", "", ""};
    public static String url = "";
    public static String score = "";
    public static String type = "";
    public static String token = "";
    public static String charset = "";
    public static Object object = null;
    public static String from_who = "";
    public static String from = "";
    public static String hitokoto = "";
    public static String string = "";
    public static String uuid = "";
    public static String comment = "";
    public static int min_length = 0;
    public static int max_length = 0;


    public static void set(int set, CharSequence obj) {
        switch (set) {
            case 0:
                url = obj.toString();
                break;
            case 1:
                uuid = obj.toString();
                break;
            case 2:
                hitokoto = obj.toString();
                break;
            case 3:
                type = obj.toString();
                break;
            case 4:
                from = obj.toString();
                break;
            case 5:
                from_who = obj.toString();
                break;
            case 6:
                charset = obj.toString();
                break;
            case 7:
                max_length = Integer.valueOf(obj.toString()).intValue();
                break;
            case 8:
                token = obj.toString();
                break;
            case 9:
                min_length = Integer.valueOf(obj.toString()).intValue();
                break;
            case 10:
                comment = obj.toString();
                break;
            case 11:
                score = obj.toString();
                break;
        }
    }

    public static String get(int set) {
        switch (set) {
            case 0:
                return url;
            case 1:
                return uuid;
            case 2:
                return hitokoto;
            case 3:
                return type;
            case 4:
                return from;
            case 5:
                return from_who;
            case 6:
                return charset;
            case 7:
                return String.valueOf(max_length);
            case 8:
                return token;
            case 9:
                return String.valueOf(min_length);
            case 10:
                return comment;
            case 11:
                return score;
            default:
                return "";
        }
    }

    public static void setNotification(int type, boolean tf) {
        String str = "";
        if (tf == true) str = "TRUE";
        if (tf == false) str = "FALSE";
        setting[type] = str;
    }

    public static String putSetting() {
        String str = "";
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()//构建请求Body，数据类型为application/x-www-form-urlencoded
                .add("_method", "put")
                .add("email_global", setting[0])
                .add("email_hitokoto_appended", setting[1])
                .add("email_hitokoto_reviewed", setting[2])
                .add("email_poll_created", setting[3])
                .add("email_poll_result", setting[4])
                .add("email_poll_report_daily", setting[5])
                .build();
        Request postRequest = new Request.Builder()
                .url("https://hitokoto.cn/api/restful/v1/user/notification/settings?token=" + token)//请求接口
                .post(requestBody)//post请求
                .build();
        try {
            Response response = client.newCall(postRequest).execute();
            if (response.isSuccessful()) {
                str = response.body().string();
            } else {
                String var8 = String.valueOf(response.code());
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }
        d(str);
        return EsonUtils.getObjectText(EsonUtils.toObject(str), "status");
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
        String[] str_list = new String[13];
        String str = "";
        String[] type = new String[]{"id", "uid", "hitokoto", "type", "from", "from_who", "creator", "creator_uid", "reviewer", "commit_from", "created_at", "length"};
        Request request = (new Request.Builder()).url(url1).build();

        try {
            Response response = okClient.newCall(request).execute();
            if (response.isSuccessful()) {
                str = response.body().string();
            } else {
                String var8 = String.valueOf(response.code());
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }

        d(str);
        Object obj = EsonUtils.toObject(str);

        for (int a = 0; 12 > a; ++a) {
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
            if (response.isSuccessful()) {
                str = response.body().string();
            } else {
                String var8 = String.valueOf(response.code());
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }

        Object obj = EsonUtils.toObject(str);
        String[] strs = new String[6];
        String status = EsonUtils.getObjectText(obj, "status");
        d(status);
        d(str);
        switch (status) {
            case "200":
                obj = EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0);
                strs[0] = status;//状态
                strs[1] = EsonUtils.getObjectText(obj, "name");//用户名
                strs[2] = EsonUtils.getObjectText(obj, "email");//邮箱
                strs[3] = EsonUtils.getObjectText(obj, "id");//账号id
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
            if (response.isSuccessful()) {
                str = response.body().string();

            } else {
                String var8 = String.valueOf(response.code());
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }
        d(str);
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
    public static String like() {
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
            if (response.isSuccessful()) {
                str = response.body().string();
            } else {
                String var8 = String.valueOf(response.code());
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }
        d(str);
        Object obj = EsonUtils.toObject(str);
        String status = EsonUtils.getObjectText(obj, "status");
        return status;
    }

    //取消句子喜欢
    public static String unlike() {
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
            if (response.isSuccessful()) {
                str = response.body().string();
            } else {
                String var8 = String.valueOf(response.code());
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }
        d(str);
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
        String[] strs = new String[5];
        String status = EsonUtils.getObjectText(obj, "status");
        d(status);
        d(str);
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

    public static String[] getSummary() {
        String str = "";
        OkHttpClient okClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://hitokoto.cn/api/restful/v1/user/hitokoto/summary?token=" + token).build();
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
        String[] list = new String[5];
        list[0] = status;
        switch (status) {
            case "200":
                object = EsonUtils.getObject(EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0), "statistics");
                string = EsonUtils.getObjectText(EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0), "statistics");
                for (int a = 1; 4 > a; a++) {
                    list[a] = EsonUtils.getObjectText(obj, summary_type[a - 1]);
                }
                break;
        }
        d(str);
        return list;
    }

    public static String score() {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()//构建请求Body，数据类型为application/x-www-form-urlencoded
                .add("comment", comment)
                .add("score", score)
                .build();
        Request postRequest = new Request.Builder()
                .url("https://hitokoto.cn/api/restful/v1/hitokoto/" + uuid + "/score?token=" + token)//请求接口
                .post(requestBody)//post请求
                .build();
        String str = "";
        try {
            Response response = client.newCall(postRequest).execute();
            if (response.isSuccessful()) {
                str = response.body().string();
            } else {
                String var8 = String.valueOf(response.code());
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }
        d(str);
        return EsonUtils.getObjectText(EsonUtils.toObject(str), "status");
    }

    //举报句子
    public static String report() {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()//构建请求Body，数据类型为application/x-www-form-urlencoded
                .add("comment", comment)
                .build();
        Request postRequest = new Request.Builder()
                .url("https://hitokoto.cn/api/restful/v1/hitokoto/" + uuid + "/report?token=" + token)//请求接口
                .post(requestBody)//post请求
                .build();
        String str = "";
        try {
            Response response = client.newCall(postRequest).execute();
            if (response.isSuccessful()) {
                str = response.body().string();
            } else {
                String var8 = String.valueOf(response.code());
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }
        d(str);
        return EsonUtils.getObjectText(EsonUtils.toObject(str), "status");
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
            if (response.isSuccessful()) {
                str = response.body().string();
            } else {
                String var8 = String.valueOf(response.code());
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }
        Object obj = EsonUtils.toObject(str);
        String status = EsonUtils.getObjectText(obj, "status");
        switch (status) {
            case "200":
                token = EsonUtils.getObjectText(EsonUtils.getObject(EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0), "user"), "token");
                break;
        }
        d(str);
        return status;
    }

    //验证邮箱
    public static String verifyEmail() {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()//构建请求Body
                .add("_method", "PUT")
                .build();
        Request postRequest = new Request.Builder()
                .url("https://hitokoto.cn/api/restful/v1/user/email/verify?token=" + token)//请求接口
                .post(requestBody)//post请求
                .build();
        String str = "";
        try {
            Response response = client.newCall(postRequest).execute();
            if (response.isSuccessful()) {
                str = response.body().string();
            } else {
                String var8 = String.valueOf(response.code());
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }
        d(str);
        return EsonUtils.getObjectText(EsonUtils.toObject(str), "status");
    }

    //设置新密码
    public static String setPassword(String oldPw, String newPw) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()//构建请求Body，数据类型为application/x-www-form-urlencoded
                .add("_method", "PUT")
                .add("password", oldPw)
                .add("new_password", newPw)
                .build();
        Request postRequest = new Request.Builder()
                .url("https://hitokoto.cn/api/restful/v1/user/password?token=" + token)//请求接口
                .post(requestBody)//post请求
                .build();
        String str = "";
        try {
            Response response = client.newCall(postRequest).execute();
            if (response.isSuccessful()) {
                str = response.body().string();
            } else {
                String var8 = String.valueOf(response.code());
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }
        d(str);
        return EsonUtils.getObjectText(EsonUtils.toObject(str), "status");
    }

    //设置新邮箱
    public static String setEmail(String newEmail, String oldPw) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()//构建请求Body，数据类型为application/x-www-form-urlencoded
                .add("_method", "PUT")
                .add("password", oldPw)
                .add("email", newEmail)
                .build();
        Request postRequest = new Request.Builder()
                .url("https://hitokoto.cn/api/restful/v1/user/email?token=" + token)//请求接口
                .post(requestBody)//post请求
                .build();
        String str = "";
        try {
            Response response = client.newCall(postRequest).execute();
            if (response.isSuccessful()) {
                str = response.body().string();
            } else {
                String var8 = String.valueOf(response.code());
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }
        d(str);
        return EsonUtils.getObjectText(EsonUtils.toObject(str), "status");
    }

    //设置新邮箱
    public static String addHitokoto() {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()//构建请求Body，数据类型为application/x-www-form-urlencoded
                .add("type", type)
                .add("from_who", from_who)
                .add("hitokoto", hitokoto)
                .add("from", from)
                .build();
        Request postRequest = new Request.Builder()
                .url("https://hitokoto.cn/api/restful/v1/user/email?token=" + token)//请求接口
                .post(requestBody)//post请求
                .build();
        String str = "";
        try {
            Response response = client.newCall(postRequest).execute();
            if (response.isSuccessful()) {
                str = response.body().string();
            } else {
                String var8 = String.valueOf(response.code());
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }
        d(str);
        return EsonUtils.getObjectText(EsonUtils.toObject(str), "status");
    }

    //重置密码
    public static String resetPassword(String Email) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()//构建请求Body，数据类型为application/x-www-form-urlencoded
                .add("email", Email)
                .build();
        Request postRequest = new Request.Builder()
                .url("https://hitokoto.cn/api/restful/v1/auth/password/reset")//请求接口
                .post(requestBody)//post请求
                .build();
        String str = "";
        try {
            Response response = client.newCall(postRequest).execute();
            if (response.isSuccessful()) {
                str = response.body().string();
            } else {
                String var8 = String.valueOf(response.code());
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }
        d(str);
        return EsonUtils.getObjectText(EsonUtils.toObject(str), "status");
    }

    //取句子的点评
    public static String getScore() {
        String str = "";
        OkHttpClient okClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://hitokoto.cn/api/restful/v1/hitokoto/" + uuid + "/score?token=" + token).build();
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
        String status = EsonUtils.getObjectText(obj, "status");
        switch (status) {
            case "200":
                object = EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0);
                string = EsonUtils.getArrayText(EsonUtils.getArray(obj, "data"), 0);
                break;
        }
        return status;
    }

    //取句子的审核标记
    public static String getHitokotoMark() {
        String str = "";
        OkHttpClient okClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://hitokoto.cn/api/restful/v1/hitokoto/" + uuid + "/score?token=" + token).build();

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
        String status = EsonUtils.getObjectText(obj, "status");
        string = "";
        object = null;
        switch (status) {
            case "200":
                if (EsonUtils.getArrayLength(EsonUtils.getArray(obj, "data")) > 0) {
                    object = EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0);
                    string = EsonUtils.getArrayText(EsonUtils.getArray(obj, "data"), 0);
                }
                break;
        }
        return status;
    }

    //取所有标记
    public static String getMark() {
        String str = "";
        OkHttpClient okClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://hitokoto.cn/api/restful/v1//mark/").build();

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
        string = "";
        object = null;
        d(str);
        switch (status) {
            case "200":
                if (EsonUtils.getArrayLength(EsonUtils.getArray(obj, "data")) > 0) {
                    object = EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0);
                    string = EsonUtils.getArrayText(EsonUtils.getArray(obj, "data"), 0);
                }
                break;
        }
        return status;
    }

    public static String[] getSetting() {
        String str = "";
        OkHttpClient okClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://hitokoto.cn/api/restful/v1/user/notification/settings?token=" + token).build();
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
        string = "";
        d(str);
        object = null;
        String[] Str = new String[7];
        Str[0] = status;
        switch (status) {
            case "200":
                object = EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0);
                string = EsonUtils.getArrayText(EsonUtils.getArray(obj, "data"), 0);
                Str[1] = EsonUtils.getObjectText(obj, "email_notification_global");
                for (int a = 1; 7 > a; a++) {
                    Str[a] = EsonUtils.getObjectText(obj, setting_type[a - 1]);
                    if (EsonUtils.getObjectText(obj, setting_type[a - 1]).equals("0"))
                        setting[a - 1] = "FALSE";
                    if (EsonUtils.getObjectText(obj, setting_type[a - 1]).equals("1"))
                        setting[a - 1] = "TRUE";
                }
                break;
        }
        return Str;
    }

    public static String getLike() {
        String str = "";
        OkHttpClient okClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://hitokoto.cn/api/restful/v1//like?sentence_uuid=" + uuid).build();
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
        String status = EsonUtils.getObjectText(obj, "status");
        string = "";
        object = null;
        switch (status) {
            case "200":
                if (EsonUtils.getArrayLength(EsonUtils.getArray(EsonUtils.getArray(obj, "data"), "sets")) > 0) {
                    object = EsonUtils.getArray(EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0), "sets");
                    string = EsonUtils.getArrayText(EsonUtils.getArray(obj, "data"), 0);
                }
                break;
        }
        return status;
    }

    //取用户喜欢的列表
    public static String getUserLike(int offset, int limit) {
        if (limit > 200 & limit < 20) limit = Hitokoto.LIKE_LIMIT_DEFAULT;
        if (offset < 0) offset = Hitokoto.LIKE_OFFSET_DEFAULT;

        String str = "";
        OkHttpClient okClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://hitokoto.cn/api/restful/v1/user/hitokoto/like?token=" + token).build();

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
        d(str);
        String status = EsonUtils.getObjectText(obj, "status");
        switch (status) {
            case "200":
                object = EsonUtils.getArray(EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0), "collection");
                string = EsonUtils.getArrayText(EsonUtils.getArray(obj, "data"), 0);
                break;
        }
        return status;
    }

    public static Object getJsonObject() {
        return object;
    }

    public static String getJsonString() {
        return string;
    }

//    private static int Lookfor(String str1, String str2, int start) {
//        return start >= 0 && start <= str1.length() && !"".equals(str1) && !"".equals(str2) ? str1.indexOf(str2, start) : -1;
//    }

    public static String[] getInfo() {
        String str = "";
        OkHttpClient okClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://hitokoto.cn/api/restful/v1/hitokoto/" + uuid + "?token=" + token).build();

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
        String[] str_list = new String[13];
        String status = EsonUtils.getObjectText(obj, "status");
        str_list[0] = status;
        switch (status) {
            case "200":
                for (int a = 1; 12 > a; a++) {
                    str_list[a] = EsonUtils.getObjectText(obj, hitokoto_type[a]);
                }
                break;
        }
        return str_list;
    }

    //取用户历史提交
    public static String getHistory(int type) {
        String str = "";
        String url = "";

        switch (type) {
            case 1:
                url = "/pending";
                break;
            case 2:
                url = "/refuse";
                break;
            case 3:
                url = "/accept";
                break;
        }

        OkHttpClient okClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://hitokoto.cn/api/restful/v1/user/hitokoto/history" + url + "?token=" + token).build();

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
        String status = EsonUtils.getObjectText(obj, "status");
        switch (status) {
            case "200":
                object = EsonUtils.getArray(EsonUtils.getArrayObject(EsonUtils.getArray(obj, "data"), 0), "collection");
                string = EsonUtils.getArrayText(EsonUtils.getArray(obj, "data"), 0);
                break;
        }
        return status;
    }

    //自己用的一个Log工具
    public static void d(String str) {
        Log.d("Hitokoto.test", str);
    }

    public static String getUUID() {
        return uuid;
    }

}
