package com.net.exception;

/**
 * "Success" 0 代表 成功 1 代表 sesson 失效 2 代表 失败
 * <p>
 * ServerErrorException 代表 Success 返回 1
 */
public class SessionKeyInvalidException extends RuntimeException {
    public SessionKeyInvalidException(int errorCode, String cause) {
        super("errCode：" + errorCode + ",errorMessage:" + cause, new Throwable("SessionKey Invalid"));
    }
}
