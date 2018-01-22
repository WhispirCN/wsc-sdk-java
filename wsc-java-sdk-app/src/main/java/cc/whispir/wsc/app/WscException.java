package cc.whispir.wsc.app;

/**
 * Created by garen on 2018/1/22.
 */
public class WscException extends Exception {

    private String            errCode;
    private String            errMsg;

    public WscException() {
        super();
    }

    public WscException(String message, Throwable cause) {
        super(message, cause);
    }

    public WscException(String message) {
        super(message);
    }

    public WscException(Throwable cause) {
        super(cause);
    }

    public WscException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
