package com.net.exception;

/**
 * "Success" 0 代表 成功 1 代表 sesson 失效 2 代表 失败
 * <p>
 * ServerErrorException 代表 Success 返回 2
 */
public class ServerErrorException extends RuntimeException {
    public ServerErrorException(String cause) {
        super(cause);
    }
}
