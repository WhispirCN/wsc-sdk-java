package cc.whispir.wsc.app;

/**
 * Created by garen on 2018/1/22.
 */
public class DefaultWscClient implements WscAppClient {

    private String serverUrl;
    private String appId;
    private String appSecret;

    public DefaultWscClient(String serverUrl,String appId,String appSecret){
        this.serverUrl = serverUrl;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    @Override
    public <T extends WscResponse> T execute(WscRequest<T> request) throws WscException {

        return execute(request,null);
    }

    public <T extends WscResponse> T execute(WscRequest<T> request,String AccessToken) throws WscException{
        return null;
    }






}
