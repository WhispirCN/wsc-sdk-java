package cc.whispir.wsc.app;

/**
 * Created by garen on 2018/1/22.
 */
public interface WscAppClient {
    String postEvent(String name,String body,String conentType) throws Exception;
}
