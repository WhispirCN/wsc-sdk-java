package cc.whispir.wsc.app;

/**
 * Created by garen on 2018/1/22.
 */
public interface WscAppClient {
    public <T extends WscResponse> T execute(WscRequest<T> request) throws WscException;


}
