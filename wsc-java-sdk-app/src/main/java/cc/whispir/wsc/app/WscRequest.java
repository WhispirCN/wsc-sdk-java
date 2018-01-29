package cc.whispir.wsc.app;

import java.util.Map;

/**
 * Created by garen on 2018/1/22.
 */
public class WscRequest {

    private boolean secure;

    private String name;

    private Map<String,Object> event;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getEvent() {
        return event;
    }

    public void setEvent(Map<String, Object> event) {
        this.event = event;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }
}
