package com.example.mvpdemo.model.http;

/**
 * @date create at 2019/4/2 18:14
 * @describe 描述:address
 */
public interface ProtocolHttp {
    //String HTTP_HOST = "http://192.168.23.2/";
    String HTTP_HOST = "https://www.apiopen.top/";

    /**
     * Retrofit在传接口的时候，如果传的是一个完整的链接，在拦截器里面
     * 获取的url，就不会拼接前面host，如果传的不是一个完整的链接，则会拼接
     */
    String HTTP_COMMON = "http://ip.taobao.com/service/";
    String METHOD_LOGIN_CODE = HTTP_COMMON+"getIpInfo.php";//登录发送验证码
    String METHOD_WEATHER = "weatherApi";//获取天气信息
}
