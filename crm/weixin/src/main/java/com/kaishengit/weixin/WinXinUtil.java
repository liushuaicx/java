package com.kaishengit.weixin;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaishengit.weixin.exception.WeiXinException;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author 刘帅
 */
@Component
public class WinXinUtil {


    public static final String TOKEN_TYPE_NORMAL = "normal";
    public static final String TOKEN_TYPE_CONTACTS = "contacts";

    /**
     * 获得accessToken的Url
     */
    private static final String GET_ACCESS_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
    /**
     * 创建部门的url
     */
    private static final String CREATE_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=%s";
    /**
     * 删除部门的url
     */
    private static final String DELETE_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=%s&id=%s";
    /**
     * 创建user的url
     */
    private static final String CREATE_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=%s";
    /**
     * 删除user的url
     */
    private static final String DELETE_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=%s&userid=%s";
    /**
     * 发送消息
     */
    private static final String SEND_MESSAGE = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";

    @Value("${weixin.corpId}")
    private String corpId;
    @Value("${weixin.corpSecret}")
    private String corpSecret;
    @Value("${weixin.contacts.secret}")
    private String contactsSecret;
    @Value("${weixin.agentId}")
    private String agentId;


    /**
     * accessToken缓存
     */
    private LoadingCache<String, String> accessTokenCache = CacheBuilder.newBuilder()
            .expireAfterWrite(7200, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String type) throws Exception {
                    String url;
                    //判断获取的是通讯录的AccessToken的Url还是普通的AccessToken的Url
                    if (TOKEN_TYPE_CONTACTS.equals(type)) {
                        url = String.format(GET_ACCESS_TOKEN, corpId, contactsSecret);
                    } else {
                        url = String.format(GET_ACCESS_TOKEN, corpId, corpSecret);
                    }
                    String resultJson = sendHttpGetRequest(url);
                    //resultJson转为Map
                    Map<String, Object> map = JSON.parseObject(resultJson, HashMap.class);

                    if (map.get("errcode").equals(0)) {
                        return (String) map.get("access_token");
                    }
                    throw new WeiXinException(resultJson);
                }
            });

    /**
     * 获取AccessToken
     * @param type 获得AccessToken的类型,两个,一个时普通的normal,另一个时带有通信录密钥contacts
     * @return
     */
    public String getAccessToken(String type) {
        try {
            return accessTokenCache.get(type);
        } catch (ExecutionException e) {
            throw new RuntimeException("获取AccessToken异常", e);
        }
    }

    /**
     * 创建部门
     * @param name 部门名称
     * @param parentId 父id
     * @param id 部门id
     */
    public void createDept(String name,Integer parentId,Integer id) {
        //获取通讯录的AccessToken,然后和新增部门URL拼接
        String url = String.format(CREATE_DEPT_URL,getAccessToken(TOKEN_TYPE_CONTACTS));

        Map<String,Object> data = new HashMap<String,Object>();
        data.put("name",name);
        data.put("parentid",parentId);
        data.put("id",id);
        //利用阿里巴巴的fastJson把数据转为json
        String resultJson = sendHttpPostRequest(url,JSON.toJSONString(data));
        Map<String,Object> map = JSON.parseObject(resultJson,HashMap.class);

        if (!map.get("errcode").equals(0)) {
            throw new WeiXinException("创建部门失败:" + resultJson);
        }
    }

    /**
     * 删除部门
     * @param id 部门ID
     */
    public void deleteDept(Integer id) {

        String url = String.format(DELETE_DEPT_URL,getAccessToken(TOKEN_TYPE_CONTACTS),id);

        String resultJson = sendHttpGetRequest(url);
        Map<String,Object> map = JSON.parseObject(resultJson,HashMap.class);

        if (!map.get("errcode").equals(0)) {
            throw new WeiXinException("删除部门异常"+resultJson);
        }

    }

    /**
     * 创建员工
     * @param userId
     * @param name
     * @param departmentList 成员所属部门id列表,不超过20个
     * @param mobile
     */
    public void createUser(Integer userId, String name, List<Integer> departmentList, String mobile) {

        String url = String.format(CREATE_USER_URL,getAccessToken(TOKEN_TYPE_CONTACTS));

        Map<String,Object> data = new HashMap<String,Object>();
        data.put("userid",userId);
        data.put("name",name);
        data.put("department",departmentList);
        data.put("mobile",mobile);

        String resultJson = sendHttpPostRequest(url,JSON.toJSONString(data));
        Map<String,Object> map = JSON.parseObject(resultJson,HashMap.class);

        if (!map.get("errcode").equals(0)) {
            throw new WeiXinException("添加员工异常: " + resultJson);
        }
    }

    /**
     * 删除员工
     * @param id 员工id
     */
    public void deleteUser(Integer id) {

        String url = String.format(DELETE_USER_URL,getAccessToken(TOKEN_TYPE_CONTACTS),id);
        String resultJson = sendHttpGetRequest(url);
        Map<String,Object> map = JSON.parseObject(resultJson,HashMap.class);
        if (!map.get("errcode").equals(0)) {
            throw new WeiXinException("删除员工异常: " + resultJson);
        }
    }


    /**
     * 发送文本消息给用户
     * @param content 消息内容
     * @param userIdList 员工id列表
     */
    public void sendMessage(String content,List<Integer> userIdList) {

        String url = String.format(SEND_MESSAGE,getAccessToken(TOKEN_TYPE_NORMAL));

        StringBuilder stringBuilder = new StringBuilder();
        for (Integer userId : userIdList) {
            stringBuilder.append(userId).append("|");
        }
        String idString = stringBuilder.toString();
        idString = idString.substring(0,idString.lastIndexOf("|"));


        Map<String,Object> data = new HashMap<String,Object>();
        data.put("msgtype","text");
        data.put("touser","LiuShuai");
        data.put("agentid",agentId);
        Map<String,String> message = new HashMap<String, String>();
        message.put("content",content);
        data.put("text",message);

        String resultJson = sendHttpPostRequest(url,JSON.toJSONString(data));
        Map<String,Object> map = JSON.parseObject(resultJson,HashMap.class);
        if (!map.get("errcode").equals(0)) {
            throw new WeiXinException("发送消息异常: " + resultJson);
        }

    }










    /**
     * 发出Http的get请求
     * @param url 请求的地址
     * @return
     */
    private String sendHttpGetRequest(String url) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException("发出HTTP的Get请求异常", e);
        }
    }

    /**
     * 发出Http的Post请求
     * @param url 请求的url
     * @param json 请求传输的数据(请求体)
     * @return
     * @throws IOException
     */
    private String sendHttpPostRequest(String url, String json){

        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException("Http的Post请求异常", e);
        }
    }


}
