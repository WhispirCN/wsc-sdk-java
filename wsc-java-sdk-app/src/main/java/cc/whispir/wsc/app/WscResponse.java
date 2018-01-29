package cc.whispir.wsc.app;

import cc.whispir.wsc.util.StringUtils;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by garen on 2018/1/22.
 */
public class WscResponse implements Serializable{

    private static final long   serialVersionUID = 5014379068811962022L;


    private String              code;

    private String              msg;

    private String              body;
    private Map<String, String> params;



    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

}
