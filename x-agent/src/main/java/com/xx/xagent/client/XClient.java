package com.xx.xagent.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.xx.xagent.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

import static cn.hutool.http.ContentType.JSON;
import static com.xx.xagent.utils.SignUtils.genSign;

/**
 * 调用第三方接口的客户端
 */
public class XClient {

    private String accessKey;
    private String secretKey;

    /**
     * 全参构造
     * @param accessKey
     * @param secretKey
     */
    public XClient(String accessKey, String secretKey){
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * 空参构造
     */
    public XClient(){}

    /**
     * 用于创建请求头（包含accessKey和secretKey）
     * @return
     */
    private Map<String,String> getHeaderMap(String body){
        // 创建一个HashMap对象
        Map<String,String> map = new HashMap<>();

        // 将accessKey和其对应的值放入map中
        map.put("accessKey",accessKey);

        // 将secretKey和其对应的值放入map中
//        map.put("secretKey",secretKey);

        // 生成随机数（包含4个随机数字的字符串）
        map.put("nonce", RandomUtil.randomNumbers(4));

        // 请求体内容
        map.put("body",body);

        // 生成签名
        map.put("sign",genSign(body,secretKey));

        return map;
    }
    public String getNameByGet(String name){

        HashMap<String, Object> paraMap = new HashMap<>();
        paraMap.put("name",name);
        // 使用HttpUtils工具发起GET请求，并获取服务器返回的结果
        String result = HttpUtil.get("http://localhost:8889/xapi/name/",paraMap);
        // 打印服务器返回的结果
        System.out.println(result);
        return result;
    }

    public String getNameByPost(String name){
        String json = JSONUtil.toJsonStr(name);
        HashMap<String, Object> paraMap = new HashMap<>();
        paraMap.put("name",name);
        // 使用HttpUtils工具发起Post请求，并获取服务器返回的结果
        String result = HttpUtil.post("http://localhost:8889/xapi/name/",paraMap);
        // 打印服务器返回的结果
        System.out.println(result);
        return result;
    }

    public String getUserNameByPost(User user){
       // 将user对象转换为JSON字符串
        String json = JSONUtil.toJsonStr(user);
        // 使用HttpRquest工具发起POST请求，并获取服务器的相应
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8889/xapi/name/12")
                // 添加构造的请求头
                .addHeaders(getHeaderMap(json))
                .body(json).execute();
        // 打印服务器返回的状态码
        System.out.println("服务器返回状态码：" + httpResponse.getStatus());

        String result = httpResponse.body();
        System.out.println("获取服务器返回的结果：" + result);

        return result;
    }
}
