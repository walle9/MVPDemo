package com.example.mvpdemo.model.http;

/**
 * Desc: 网络请求状态码
 */
public interface HttpCode {
  /**
   * 成功.
   */
  int SUCCESS = 0;
  /**
   * 参数为空.
   */
  int NO_PARAMETER = 1;
  /**
   * 服务器错误.
   */
  int SERVER_ERR = 3;
}
