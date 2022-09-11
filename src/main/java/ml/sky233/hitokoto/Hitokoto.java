package ml.sky233.hitokoto;

public class Hitokoto {
    //请求一言数据类型
    public static final String TYPE_ANIMATION = "a";
    public static final String TYPE_COMICS = "b";
    public static final String TYPE_GAME = "c";
    public static final String TYPE_LITERATURE = "d";
    public static final String TYPE_ORIGINAL = "e";
    public static final String TYPE_INTERNET = "f";
    public static final String TYPE_OTHER = "g";
    public static final String TYPE_VIDEO = "h";
    public static final String TYPE_POEM = "i";
    public static final String TYPE_NETEASE = "j";
    public static final String TYPE_PHILOSOPHY = "k";
    public static final String TYPE_JOKE = "l";
    public static final String TYPE_DEFAULT = "a";

    //请求接口地址
    public static final String URL_INTERNATIONL = "https://international.v1.hitokoto.cn/";
    public static final String URL_NATIONL = "https://v1.hitokoto.cn/";
    public static final String URL_DEFAULT = "https://v1.hitokoto.cn/";

    //返回编码
    public static final String CHARSET_DEFAULT = "UTF-8";
    public static final int LENGTH_MAX = 0;
    public static final int LENGTH_MIN = 0;

    //设置类型
    public static final int URL = 0;
    public static final int TYPE = 3;
    public static final int CHARSET = 2;
    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 4;
    public static final int TOKEN = 5;

    //句子数组内容
    public static final int ID = 0;
    public static final int UUID = 1;
    public static final int HITOKOTO = 2;
    //public static final int TYPE = 3; 为了兼容,修改了设置类型的排序(bushi
    public static final int FROM = 4;
    public static final int FROM_WHO = 5;
    public static final int CREATOR = 6;
    public static final int CREATOR_UID = 7;
    public static final int REVIEWER = 8;
    public static final int COMMIT_FROM = 9;
    public static final int CREATED_AT = 10;
    public static final int LENGTH = 11;

    //结果类型
    public static final String RESULT_OK = "200";
    public static final String RESULT_BAD = "400";
    public static final String RESULT_ERROR = "-1";//参数配置错误返回

    //用户喜欢列表长度和偏移
    public static final int USER_LIKE_LIMIT = 20;
    public static final int USER_LIKE_OFFSET = 0;
}
