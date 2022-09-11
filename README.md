# Hitokoto-Utils-Java

一个用Java + okhttp写的一言类库,方便调用接口
目前差不多完工
~~完工期估计2周以内~~

---
## 简单示例
``` java
    ...
    import static ml.sky233.hitokoto.Hitokoto.*;
    import ml.sky233.hitokoto.HitokotoUtils;
    ...
```

#### 刷新句子
``` Java
    HitokotoUtils.set(URL, URL_DEFAULT);//默认为国内路线
    HitokotoUtils.set(TYPE, TYPE_DEFAULT);//默认类型为动画
    HitokotoUtils.set(CHARSET, CHARSET_DEFAULT);//默认编码UTF-8,尽量不要修改
    HitokotoUtils.set(MIN_LENGTH, LENGTH_MIN_DEFAULT);//默认为0
    HitokotoUtils.set(MAX_LENGTH, LENGTH_MAX_DEFAULT);//默认为30
    //以上内容非必须添加,可以直接调用getHitokoto(),所有参数均为默认
    String[] str = HitokotoUtils.getHitokoto();//返回的是数组类型(bushi
    System.out.print(str[HITOKOTO]);//打印句子
```
**打印结果**
``` log
    若汝无介意，愿作耳边墙。 思君无情拒，唯吾孤自哀。希愿回心意，眷恋越星海。欲采栀子花，赠予手心中。
```

---

#### 登录账号
``` Java
    String email = "example@example.com", password = "example123";
    String[] str = HitokotoUtils.login(email,password);
    if (str[LOGIN_STATUS].equal(RESULT_OK)) System.out.print(str[LOGIN_TOKEN]);//打印令牌
```
**打印结果**
``` log
    Z6C9FE7nxzmBaup7V76SlIsyUA0BVTxhIY6gMEyl
```

---
#### 刷新令牌
``` Java
    if (HitokotoUtils.refreshToken().equal(RESULT_OK)){
        System.out.print(HitokotoUtils.getToken);//刷新成功并打印令牌
    }
```

**打印结果**
``` log
    Z6C9FE7nxzmBaup7V76SlIsyUA0BVTxhIY6gMEyl
```
---

#### 喜欢列表
``` Java
    if (HitokotoUtils.getLike(LIKE_OFFSET_DEFAULT,LIKE_LIMIT_DEFAULT).equal(RESULT_OK)){
        System.out.print(HitokotoUtils.getLikeString());//刷新成功并打印列表
    }
```
**打印结果**
需要自己解析Json,可以使用配套的Eson(EasyJson)进行解析
``` json
    {
    "statistics": {
        "total": 21
    },
    "collection": [
        {
            "uuid": "c8b35bc2-bbb9-4b3f-a1ed-2b3e355bee37",
            "hitokoto": "若汝无介意，愿作耳边墙。 思君无情拒，唯吾孤自哀。希愿回心意，眷恋越星海。欲采栀子花，赠予手心中。",
            "type": "e",
            "from": "原创",
            "from_who": "Sky233",
            "creator": "sky233",
            "creator_uid": 11171,
            "reviewer": 1,
            "commit_from": "web",
            "operated_at": "2022-08-12T08:17:13.000000Z",
            "created_at": "1641130378"
        },
        {
            "uuid": "85e0bd7a-0fab-4719-9b56-21a34776b2f1",
            "hitokoto": "满招损，谦受益。",
            "type": "g",
            "from": "尚书·大禹谟",
            "from_who": null,
            "creator": "小强",
            "creator_uid": 11,
            "reviewer": 0,
            "commit_from": "web",
            "operated_at": "2022-08-12T08:14:09.000000Z",
            "created_at": "1472435170"
        }
    ]
}
```

---
## TO-DO
- [x] 刷新句子
- [x] 登录接口，成功返回用户信息（包含令牌）
- [x] 注册接口，成功返回用户信息。
- [x] 重置密码接口
- [x] 返回句子赞的相关信息
- [x] 提交赞，成功返回提交者 IP
- [x] 撤回已经发出的喜爱
- [x] 获取所有的审核标记
- [x] 获取用户信息
- [x] 申请验证邮箱
- [ ] ~~返回用户令牌的相关信息~~
- [x] 重置令牌，返回新令牌的相关信息
- [x] 修改密码
- [x] 修改邮箱（未来行为可能发生变化）
- [x] 获取用户通知设置
- [x] 设定用户通知设置，返回新设置
- [x] 获得用户一言提交数据的概览
- [x] 获得用户赞的句子
- [x] 获得用户历史的一言提交
- [x] 获得用户历史的一言提交（审核中部分）
- [x] 获得用户历史的一言提交（已拒绝部分）
- [x] 获得用户历史的一言提交（已上线部分）
- [x] 添加一言，返回审核队列中新句子的信息
- [x] 查看指定一言的信息（通过 UUID）
- [x] 查看指定一言的审核标记（通过 UUID）
- [x] 为已上线的句子评分，返回评分相关信息
- [x] 获得句子的评分信息
- [x] 举报一言存在问题，返回提交举报的相关信息

---
